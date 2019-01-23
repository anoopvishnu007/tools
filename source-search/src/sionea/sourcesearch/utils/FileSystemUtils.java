/**
 * 
 */
package sionea.sourcesearch.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Anoop
 *
 */
public class FileSystemUtils {
	
	
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
		fileToOpen.setContents(new ByteArrayInputStream(fileContent.getBytes()), IFile.BACKGROUND_REFRESH|IFile.FORCE, new NullProgressMonitor());
		return fileToOpen;
	}
	public static void openFileInEditor(IFile fileToOpen, String searchText, String editorId) throws PartInitException {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editorPart=IDE.openEditor( page, fileToOpen,editorId);
		// make editor window non-editable
		IEditorPart editor = (ITextEditor) page.getActiveEditor();
		
		ITextViewer viewer = (ITextViewer) editor.getAdapter(ITextOperationTarget.class);
		if (viewer != null) {			
			List<IRegion> regions =findSearchStringRegions(viewer.getDocument(), searchText);
			highlightSearchTextRegions(editorPart,regions);
			viewer.setEditable(false);
			viewer.getTextWidget().setBackground(new Color(Display.getCurrent(), 211, 211, 211));
		}
		
	}
	private static void highlightSearchTextRegions(IEditorPart editorPart, List<IRegion> regions) {
		for (IRegion iRegion : regions) {
			((ITextEditor)editorPart).setHighlightRange(iRegion.getOffset(), iRegion.getLength(), false);
			final IFileEditorInput input = (IFileEditorInput)editorPart.getEditorInput();
            IMarker marker = null;
                //creates a marker in the file and goes to the marker
                try {
					marker = input.getFile().createMarker( IMarker.TEXT );
					 if( marker != null ) {
		                    marker.setAttribute( IMarker.CHAR_START, iRegion.getOffset() );
		                    marker.setAttribute( IMarker.CHAR_END, iRegion.getOffset() + iRegion.getLength() );
					 }
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
               
		}
		
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
		Pattern pattern = createPattern(fileSearchText,false);

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
        return Pattern.compile( patternText, regexOptions );
    }

}
