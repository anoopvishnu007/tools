package sionea.sourcesearch.editor;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import sionea.sourcesearch.utils.FileSystemUtils;

/**
 * This service performs various actions on editors.
 * 
 * @author Anoop
 */
public class EditorService {
	private static EditorService editorService;
	private static  final Collection<IFile> editorFiles = new HashSet<IFile>();

	 private EditorService() {
		 
	 }
	public static EditorService getDefualt() {
		if(editorService == null) {
			editorService = new EditorService();
		}
		return editorService;
	}

    

    /**
     * Returns opened editors.
     * 
     * @param id - id of the opened editors.
     * @return collection of editors.
     */
    public Collection<IEditorPart> getOpenedReadOnlyEditors(final String id) {
        Collection<IEditorPart> editors = new HashSet<IEditorPart>();

        for (IEditorReference reference : this.getOpenEditorReferences()) {
            if (reference.getId().equals(id)) {
            	IEditorPart part= reference.getEditor(true);
            	ITextViewer viewer = (ITextViewer) part.getAdapter(ITextOperationTarget.class);
        		if (viewer != null && !viewer.isEditable()) {
        			editors.add(part);
        		}
            }
        }

        return editors;
    }

   
    /**
     * Closes the editors, no save confirmation dialog will appear.
     * 
     * @param editors - editors to close.
     */
    public void closeEditors(final Collection<IEditorPart> editors) {
        for (IEditorPart editor : editors) {
            IFile file = (IFile) editor.getEditorInput().getAdapter(IFile.class);
            editorFiles.add(file);
            this.closeEditor(editor);
        }
    }
	public void removeFiles(Collection<IFile> files) {
		for (IFile editorFile : files) {
        	 IProject project=ResourcesPlugin.getWorkspace().getRoot().getProject(FileSystemUtils.DEFAULT_PROJECT_NAME);
	            if(project.exists() && project.equals(editorFile.getProject())){
	            	try {
						editorFile.delete(true, new NullProgressMonitor());
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
		}
	}

    /**
     * Closes the editor, no save confirmation dialog will appear.
     * 
     * @param editor - editor to close.
     */
    public void closeEditor(final IEditorPart editor) {
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        page.closeEditor(editor, false);
    }

     
    
    /**
     * Closes all orphan editors.
     * 
     * @see #closeOrphanEditors(IWorkbenchPage)
     */
    public void closeOrphanEditors() {
        IWorkbench workbench = PlatformUI.getWorkbench();
        if (workbench != null) {
            for (IWorkbenchWindow window : workbench.getWorkbenchWindows()) {
                IWorkbenchPage page = window.getActivePage();
                if (page != null) {
                    this.closeOrphanEditors(page);
                }
            }
        }
    }

    /**
     * Closes all the editors with resources if the resources have no corresponding objects.
     * 
     * @param page - page which editors are closed.
     */
    public void closeOrphanEditors(final IWorkbenchPage page) {
        for (IEditorReference reference : page.getEditorReferences()) {
            final IEditorPart editor = reference.getEditor(true);
            IFile file = (IFile) editor.getEditorInput().getAdapter(IFile.class);
            if (!file.exists()) {// An opened editor may not work with files. 
               page.closeEditor(editor, false);
            }
        }
    }

    /**
     * Returns all the open editor references of all the active page pages of all the running
     * windows.
     * 
     * @return open editor references.
     */
    private IEditorReference[] getOpenEditorReferences() {
        IEditorReference[] references = new IEditorReference[] {};
        IWorkbench workbench = PlatformUI.getWorkbench();
        if (workbench != null) {
            for (IWorkbenchWindow window : workbench.getWorkbenchWindows()) {
                IWorkbenchPage activePage = window.getActivePage();
                if (activePage != null) {
                    references = (IEditorReference[]) Arrays.copyOfRange(activePage.getEditorReferences(),0,activePage.getEditorReferences().length);
                }
            }
        }
        return references;
    }
	public static Collection<IFile> getEditorFiles() {
		return editorFiles;
	}
}
