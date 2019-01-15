package sionea.sourcesearch.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.MarkerUtilities;

import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.ide.model.ResourceFactory;
import org.eclipse.ui.part.FileEditorInput;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
//import org.eclipse.core.filesystem.EFS;
//import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

import sionea.sourcesearch.SearchContentProvider;
import sionea.sourcesearch.SearchResult;
import sionea.sourcesearch.db.ConnectionSingleton;

public class OpenEditorJob extends Job implements ISchedulingRule, IPartListener {

	private SearchResult result;
	private SearchContentProvider contentProvider;
	private ITextEditor editor;
	private IWorkbenchPage workbenchPage;

	public OpenEditorJob(SearchResult res, SearchContentProvider contentProvider, IWorkbenchPage workbenchPage) {
		super("Open Editor");

		this.result = res;
		this.contentProvider = contentProvider;
		this.workbenchPage = workbenchPage;

		File fileToOpen = new File("externalfile.xml");
		try {
			Files.write(fileToOpen.toPath(), "test string".getBytes());
			if (fileToOpen.exists() && fileToOpen.isFile()) {
				IFileStore fileStore = EFS.getLocalFileSystem().getStore(fileToOpen.toURI());

				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {

					IDE.openEditorOnFileStore(page, fileStore);
					
					// make editor window non-editable
					ITextEditor editor = (ITextEditor) page.getActiveEditor();
					ITextViewer viewer = (ITextViewer) editor.getAdapter(ITextOperationTarget.class);
					if (viewer != null) {
						viewer.setEditable(false);
					}

				} catch (PartInitException e) {
					// Put your exception handler here if you wish to
					e.printStackTrace();
				}
			} else {
				// Do something if the file does not exist
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//
		//
		// IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		// Path p = new Path("index.html");
		// IFile file = workspaceRoot.getFile(p);
		//
		// if( file == null || file.getRawLocation() == null )
		// file = workspaceRoot.getFileForLocation(p);
		//
		//
		//
		// IEditorInput input = new FileEditorInput(file);
		// IEditorDescriptor editorDesc;
		// try {
		// editorDesc = IDE.getEditorDescriptor("index.html");
		// IEditorPart editorPart = IDE.openEditor(workbenchPage, input,
		// editorDesc.getId());
		//
		// ITextEditor editor = (ITextEditor) IDE.openEditor(
		// PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(),
		// segment.getParentFile(), EDITOR.SQL, true);
		// editor.setHighlightRange(segment.offset, segment.length, true);
		// editor.selectAndReveal(segment.offset, segment.length);
		//
		// } catch (PartInitException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//

		// IWorkbenchPage page =
		// PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		//
		// String string = ConnectionSingleton.getInstance().getText(res.getSourceId());
		// IStorage storage = new StringStorage(null, res.getSourceName(),
		// res.getSourceName() + "["+res.getSourceType()+"]", string);
		// IStorageEditorInput input = new StringInput(storage);
		// if (page != null) {
		// try {
		// IEditorPart e = page.openEditor(input, "org.eclipse.ui.DefaultTextEditor");
		//
		// // page.openEditor(input, "sionea.sourcesearch.editor.UserEditor");
		//
		//
		// IMarker marker = null;
		//
		//// editor.getSite().set
		//// marker = file.createMarker(NewSearchUI.SEARCH_MARKER);
		//// marker.setAttribute(IMarker.TRANSIENT, true);
		//// marker.setAttribute(IMarker.MESSAGE, match.getElement());
		////
		//// MarkerUtilities.setLineNumber(marker, lineNumber);
		//// MarkerUtilities.setCharStart(marker, pos + match.getOffset());
		//// MarkerUtilities.setCharEnd(marker, pos + match.getOffset() +
		// match.getLength());
		//
		// } catch (PartInitException e1) {
		// e1.printStackTrace();
		// }
		// }

	}

	@Override
	public void partActivated(IWorkbenchPart part) {
		// TODO Auto-generated method stub

	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
		// TODO Auto-generated method stub

	}

	@Override
	public void partClosed(IWorkbenchPart part) {
		// TODO Auto-generated method stub

	}

	@Override
	public void partDeactivated(IWorkbenchPart part) {
		// TODO Auto-generated method stub

	}

	@Override
	public void partOpened(IWorkbenchPart part) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(ISchedulingRule rule) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isConflicting(ISchedulingRule rule) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		return Status.OK_STATUS;
	}

}
