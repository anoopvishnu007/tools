package sionea.sourcesearch.editor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

/**
 * This service performs various actions on editors.
 * 
 * @author Anoop
 */
public class EditorService {

    /**
     * Returns all open editor.
     * 
     * @return open editors.
     */
    public Collection<IEditorPart> getOpenedEditors() {
        Collection<IEditorPart> editors = new HashSet<IEditorPart>();

        for (IEditorReference reference : this.getOpenEditorReferences()) {
            IEditorPart editor = reference.getEditor(true);
            editors.add(editor);
        }

        return editors;
    }

    /**
     * Returns opened editors.
     * 
     * @param id - id of the opened editors.
     * @return collection of editors.
     */
    public Collection<IEditorPart> getOpenedEditors(final String id) {
        Collection<IEditorPart> editors = new HashSet<IEditorPart>();

        for (IEditorReference reference : this.getOpenEditorReferences()) {
            if (reference.getId().equals(id)) {
                editors.add(reference.getEditor(true));
            }
        }

        return editors;
    }

    /**
     * Searches for all editors with the specified file.
     * 
     * @param file - file opened by editors.
     * @return a collection of editors with the specified file.
     */
    public Collection<IEditorPart> getOpenedEditors(final IFile file) {
        Collection<IEditorPart> editors = new ArrayList<IEditorPart>();

        for (IEditorReference reference : this.getOpenEditorReferences()) {
            IEditorPart editor = reference.getEditor(true);
            IEditorInput input = editor.getEditorInput();
            IFile editorFile = (IFile) input.getAdapter(IFile.class);
            if (editorFile != null && editorFile.equals(file)) {
                editors.add(editor);
            }
        }

        return editors;
    }

    /**
     * Searches for all editors of the given type and with the specified opened file.
     * 
     * @param file - file opened by editors.
     * @param id - id of the editors.
     * @return a collection of editors of the given type and with the specified file.
     */
    public Collection<IEditorPart> getOpenedEditors(final IFile file, final String id) {
        Collection<IEditorPart> editors = new ArrayList<IEditorPart>();

        for (IEditorReference reference : this.getOpenEditorReferences()) {
            if (reference.getId().equals(id)) {
                IEditorPart editor = reference.getEditor(true);
                IEditorInput input = editor.getEditorInput();
                IFile editorFile = (IFile) input.getAdapter(IFile.class);
                if (editorFile != null && editorFile.equals(file)) {
                    editors.add(editor);
                }
            }
        }

        return editors;
    }

    /**
     * Checks if there is a dirty editor in the collection of editors.
     * 
     * @param editors - collection of editors.
     * @return true if there is a dirty editor.
     */
    public boolean hasDirtyEditor(final Collection<IEditorPart> editors) {
        boolean has = false;
        for (IEditorPart editor : editors) {
            if (editor.isDirty()) {
                has = true;
                break;
            }
        }
        return has;
    }

    /**
     * Saves the content of the editors.
     * 
     * @param editors - editors which content is saved.
     * @param confirm - indicates whether to ask any questions to the user.
     */
    public void saveEditors(final Collection<IEditorPart> editors) {
        for (IEditorPart editor : editors) {
            this.saveEditor(editor);
        }
    }

    /**
     * Saves the contents of the editor.
     * 
     * @param editor - editor which content is saved.
     */
    public void saveEditor(final IEditorPart editor) {
        editor.doSave(new NullProgressMonitor());
    }

    /**
     * Closes the editors, no save confirmation dialog will appear.
     * 
     * @param editors - editors to close.
     */
    public void closeEditors(final Collection<IEditorPart> editors) {
        for (IEditorPart editor : editors) {
            this.closeEditor(editor);
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

    public void reopenEditors(Collection<IEditorPart> editors) throws PartInitException {
        Set<IEditorPart> activeEditors = new HashSet<IEditorPart>();
        Map<IEditorPart, IWorkbenchPage> pages = new HashMap<IEditorPart, IWorkbenchPage>();  
        Map<IEditorPart, String> ids = new HashMap<IEditorPart, String>();  
        Map<IEditorPart, IFile> files = new HashMap<IEditorPart, IFile>();  
        
        for (IEditorPart editor : editors) {
            IWorkbenchPage page = editor.getEditorSite().getPage();
            String id = editor.getSite().getId();
            IFile file = (IFile) editor.getEditorInput().getAdapter(IFile.class);

            pages.put(editor, page);
            ids.put(editor, id);
            files.put(editor, file);
            
            IEditorPart activeEditor = page.getActiveEditor();
            activeEditors.add(activeEditor);
            
            this.closeEditor(editor);
        }
        
        for (IEditorPart editor : editors) {
            IWorkbenchPage page = pages.get(editor);
            String id = ids.get(editor);
            IFile file = files.get(editor);
            
            IEditorPart reopnedEditor = IDE.openEditor(page, file, id, false);
            if (activeEditors.contains(editor)) {
                activeEditors.remove(editor);
                activeEditors.add(reopnedEditor);
            }
        }
        
        for (IEditorPart editor : activeEditors) {
            IWorkbenchPage page = editor.getEditorSite().getPage();
            page.activate(editor);
        }
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
}
