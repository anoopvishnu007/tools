/**
 * 
 */
package sionea.sourcesearch.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
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
	public static void openFileInEditor(IFile fileToOpen, String editorId) throws PartInitException {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IDE.openEditor( page, fileToOpen,editorId);
		// make editor window non-editable
		ITextEditor editor = (ITextEditor) page.getActiveEditor();
		ITextViewer viewer = (ITextViewer) editor.getAdapter(ITextOperationTarget.class);
		if (viewer != null) {
			viewer.setEditable(false);
			viewer.getTextWidget().setBackground(new Color(Display.getCurrent(), 211, 211, 211));
		}
	}

}
