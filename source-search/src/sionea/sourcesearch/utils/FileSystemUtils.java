/**
 * 
 */
package sionea.sourcesearch.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.datatools.sqltools.sqleditor.SQLEditor.AdaptedSourceViewer;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelExtension;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import sionea.sourcesearch.editor.EditorService;
import sionea.sourcesearch.ui.SourceSearchView;

/**
 * @author Anoop
 *
 */
public class FileSystemUtils {
	private static final String SEARCH_ANNOTATION_TYPE = "source-search.type";
	private static final String SEARCH_MARKER = "sourcesearch.marker";
	public final static String DEFAULT_PROJECT_NAME = "sourcesearch";
	public static final String DEFAULT_FILE_NAME = "externalfile.sql";
	private static Map<IRegion, Annotation> regionAnnotations=new HashMap<IRegion, Annotation>();
	
	
	public static IFile createFileWithContent(String defaultProjectName, String fileName,String fileContent) throws CoreException {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(defaultProjectName);
        IProjectDescription projectDescription = ResourcesPlugin.getWorkspace().newProjectDescription(defaultProjectName);

		if(!project.exists()) {
			project.create(projectDescription,IResource.FORCE|IResource.HIDDEN,new NullProgressMonitor());
			project.open(new NullProgressMonitor());

		}
		IFile fileToOpen = project.getFile(fileName);
		File file=fileToOpen.getLocation().toFile();
		if (!file.exists() ) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ResourcesPlugin.getWorkspace().getRoot().getProject(defaultProjectName).refreshLocal(IResource.DEPTH_INFINITE,new NullProgressMonitor() );
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = (ITextEditor) page.getActiveEditor();
		IAnnotationModel model = getAnnotationModel(editor);
		Set<IRegion> regions=new HashSet<>();
		regions.addAll(regionAnnotations.keySet());
    	removeHighlights( regions,model);
    	fileToOpen.deleteMarkers(SEARCH_MARKER, true, IResource.DEPTH_INFINITE);
		ResourcesPlugin.getWorkspace().getRoot().getProject(defaultProjectName).refreshLocal(IResource.DEPTH_INFINITE,new NullProgressMonitor() );

		fileToOpen.setContents(new ByteArrayInputStream(fileContent.getBytes()), IFile.BACKGROUND_REFRESH|IFile.FORCE, new NullProgressMonitor());
		return fileToOpen;
	}
	public static void openFileInEditor(IFile fileToOpen, String searchText, String editorId) throws PartInitException {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = (ITextEditor) page.getActiveEditor();
		if(editor != null && editor.getEditorInput()!= null) {
			IFile file = (IFile) editor.getEditorInput().getAdapter(IFile.class);
			EditorService.getEditorFiles().add(file);
			page.closeEditor(editor, false);
		}

		IEditorPart editorPart=IDE.openEditor( page, fileToOpen,editorId);
		// make editor window non-editable
		 editor = (ITextEditor) page.getActiveEditor();

		ITextViewer viewer = (ITextViewer) editor.getAdapter(ITextOperationTarget.class);
		if (viewer != null) {			
			List<IRegion> regions =findSearchStringRegions(viewer.getDocument(), searchText);
			highlightSearchTextRegions(editorPart,regions);
			

		}
		
	}
	private static void highlightSearchTextRegions(IEditorPart editorPart, List<IRegion> regions) {
		Map<IAnnotationModel, HashMap<Annotation, Position>> mapsByAnnotationModel= new HashMap<>();

		
			 
			final IFileEditorInput input = (IFileEditorInput)editorPart.getEditorInput();
			IAnnotationModel model = getAnnotationModel(editorPart);
                 //creates a marker in the file and goes to the marker
                try {              	
                 	
                 	input.getFile().getWorkspace().run(new IWorkspaceRunnable() {
    					@Override
    					public void run(IProgressMonitor monitor) throws CoreException {
    						for (IRegion iRegion : regions) {
    							addHighlights(iRegion, mapsByAnnotationModel, model);
    							createMarker(input.getFile(), iRegion);
    							
    						}
    						

    					}
    				}, input.getFile(), IWorkspace.AVOID_UPDATE, null);

				} catch (CoreException e) {
					e.printStackTrace();
				}finally {
					ITextViewer viewer = (ITextViewer) editorPart.getAdapter(ITextOperationTarget.class);
					viewer.getTextWidget().setBackground(new Color(Display.getCurrent(), 211, 211, 211));
					viewer.setEditable(false);
					ResourceAttributes attributes= new ResourceAttributes();
					//attributes.setReadOnly(true);
					try {
						input.getFile().setResourceAttributes(attributes);
						viewer.getTextWidget().setEditable(false);
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
               
		
		
	}
	private static IAnnotationModel getAnnotationModel(IEditorPart editorPart) {
		IAnnotationModel model =null;
		if(editorPart!=null) {
			model= editorPart.getAdapter(IAnnotationModel.class);
			if (model == null) {
				ITextEditor textEditor= null;
				if (editorPart instanceof ITextEditor) {
					textEditor= (ITextEditor) editorPart;
				}
				if (textEditor != null) {
					IDocumentProvider dp= textEditor.getDocumentProvider();
					if (dp != null)
						model= dp.getAnnotationModel(textEditor.getEditorInput());
				}
			}
		}
		return model;
	}
	public static void addHighlights(IRegion match, Map<IAnnotationModel, HashMap<Annotation, Position>> mapsByAnnotationModel,IAnnotationModel model) {
	 
			int offset= match.getOffset();
			int length= match.getLength();
			if (offset >= 0 && length >= 0) {
				Position position= new Position(match.getOffset(), match.getLength());
				if (position != null) {
					Map<Annotation, Position> map= getMap(mapsByAnnotationModel, model);
					if (map != null) {
						Annotation annotation= new Annotation(SEARCH_ANNOTATION_TYPE, false, null);
						regionAnnotations.put(match, annotation);
						map.put(annotation, position);
					}
				}
			}
		
		for (Entry<IAnnotationModel, HashMap<Annotation, Position>> entry : mapsByAnnotationModel.entrySet()) {
			addAnnotations(entry.getKey(), entry.getValue());
		}

	}
	private static void addAnnotations(IAnnotationModel model, Map<Annotation, Position> annotationToPositionMap) {
		if (model instanceof IAnnotationModelExtension) {
			IAnnotationModelExtension ame= (IAnnotationModelExtension) model;
			ame.replaceAnnotations(new Annotation[0], annotationToPositionMap);
		} else {
			for (Entry<Annotation, Position> entry : annotationToPositionMap.entrySet()) {
				model.addAnnotation(entry.getKey(), entry.getValue());
			}
		}
	}
	public static void removeHighlights(Set<IRegion> matches,IAnnotationModel model) {
		if(model==null) {
			return;
		}
		Map<IAnnotationModel, HashSet<Annotation>> setsByAnnotationModel= new HashMap<>();
		for (IRegion match : matches) {
			Annotation annotation= regionAnnotations.remove(match);
			if (annotation != null) {
				Set<Annotation> annotations= getSet(setsByAnnotationModel, model);
				if (annotations != null)
					annotations.add(annotation);
			}
		}

		for (Entry<IAnnotationModel, HashSet<Annotation>> entry : setsByAnnotationModel.entrySet()) {
			removeAnnotations(entry.getKey(), entry.getValue());
		}

	}
	private static Set<Annotation> getSet(Map<IAnnotationModel, HashSet<Annotation>> setsByAnnotationModel,IAnnotationModel model) {
 		if (model == null)
			return null;
		HashSet<Annotation> set= setsByAnnotationModel.get(model);
		if (set == null) {
			set= new HashSet<>();
			setsByAnnotationModel.put(model, set);
		}
		return set;
	}
	private static void removeAnnotations(IAnnotationModel model, Set<Annotation> annotations) {
		if(model==null) {
			return;
		}
		if (model instanceof IAnnotationModelExtension) {
			IAnnotationModelExtension ame= (IAnnotationModelExtension) model;
			Annotation[] annotationArray= new Annotation[annotations.size()];
			ame.replaceAnnotations(annotations.toArray(annotationArray), Collections.emptyMap());
		} else {
			for (Annotation element : annotations) {
				model.removeAnnotation(element);
			}
		}
	}
	private static Map<Annotation, Position> getMap(Map<IAnnotationModel, HashMap<Annotation, Position>> mapsByAnnotationModel, IAnnotationModel model) {
		
		if (model == null)
			return null;
		HashMap<Annotation, Position> map= mapsByAnnotationModel.get(model);
		if (map == null) {
			map= new HashMap<>();
			mapsByAnnotationModel.put(model, map);
		}
		return map;
	}
	public static IMarker createMarker(IFile file,IRegion match) throws CoreException {
		if (match.getOffset() < 0 || match.getLength() < 0) {
			return null;
		}
		Position position= new Position(match.getOffset(), match.getLength());
		IMarker marker= file.createMarker(SEARCH_MARKER);
		HashMap<String, Integer> attributes= new HashMap<>();
 		attributes.put(IMarker.CHAR_START, Integer.valueOf(position.getOffset()));
		attributes.put(IMarker.CHAR_END, Integer.valueOf(position.getOffset()+position.getLength()));
		//attributes.put(IMarker.LINE_NUMBER, Integer.valueOf(position.getOffset()));
		marker.setAttributes(attributes);
		return marker;
	}
	
	private static  List<IRegion> findSearchStringRegions(IDocument document,String searchText) {
		List<IRegion> regionlist= new ArrayList<IRegion>();
		final Matcher matcher = getSearchStringMatcher(document, searchText);
		final int regionStart = 0;
		final int regionEnd = document.getLength();
		int start = regionStart;
		while (matcher.find(start)) {
			start = matcher.start();
			final int end = matcher.end();
			if (!(start >= regionStart && end <= regionEnd)) {
				return regionlist; // no further reporting requested
			}
			if (end != start) { // don't report 0-length matches
				final int length = end - start;
				try {
					System.out.println(document.get(start, length));
				} catch (BadLocationException e) {
 					e.printStackTrace();
				}
				
				regionlist.add(new Region(start,length));

			}
			start = matcher.end();

		}
		return regionlist;
	}
	/**
	 * Gets the search string matcher to perform file search
	 * 
	 * @param document
	 *            document of the file to search
	 * @param fileSearchText
	 *            search string
	 * @return Matcher object
	 */
	public static Matcher getSearchStringMatcher(final IDocument document,
			final String fileSearchText) {
        final StringBuffer buffer = new StringBuffer( );
		PatternHelper.appendAsRegEx(fileSearchText, buffer);
		String searchText=buffer.toString().replaceAll("(\\s+)", "(\\\\s*\\\\n*)");
		searchText=searchText.replaceAll("\\n+", "\\\\n*");
		searchText=searchText+"\\n*";
		Pattern pattern = createPattern(searchText,false);

		return pattern.matcher(document.get());
	}
	
	 /**
     * Creates a pattern using the pattern string
     * 
     * @param patternText pattern string
     * @param isCaseSensitive the pattern is case sensitive or not
     * 
     * @return pattern
     */
    public static Pattern createPattern( final String patternText, final boolean isCaseSensitive ) {
        int regexOptions = Pattern.MULTILINE;
        if( !isCaseSensitive ) {
            regexOptions |= Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;
        }
       
        return Pattern.compile(patternText, regexOptions );
    }
	public static void clearMarkers() {
		Collection<IEditorPart> parts = EditorService.getDefualt().getOpenedReadOnlyEditors(SourceSearchView.SQL_EDITOR_ID);
		for (IEditorPart editor : parts) {
            IFile file = (IFile) editor.getEditorInput().getAdapter(IFile.class);
            try {
            	ITextViewer viewer = (ITextViewer) editor.getAdapter(ITextOperationTarget.class);
        		if (viewer != null) {	
        			viewer.getTextWidget().setEditable(true);
        			viewer.setEditable(true);
        			file.deleteMarkers(SEARCH_MARKER, true, IResource.DEPTH_INFINITE);
        			file.refreshLocal(IResource.DEPTH_INFINITE,new NullProgressMonitor() );
        			viewer.getTextWidget().setEditable(false);
        			viewer.setEditable(false);
        			viewer.resetVisibleRegion();    
        			AdaptedSourceViewer ruler = (AdaptedSourceViewer) editor.getAdapter(ITextOperationTarget.class);
                	ruler.refresh();
        		}				
			} catch (CoreException e) {
 				e.printStackTrace();
			}


		}
	}

}
