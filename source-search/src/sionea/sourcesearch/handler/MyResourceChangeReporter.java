package sionea.sourcesearch.handler;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.CoreException;

public class MyResourceChangeReporter implements IResourceChangeListener {
	public void resourceChanged(IResourceChangeEvent event) {
		IResource res = event.getResource();
		try {
			switch (event.getType()) {
			case IResourceChangeEvent.PRE_CLOSE:
				System.out.print("Project ");
				System.out.print(res.getFullPath());
				System.out.println(" is about to close.");
				break;
			case IResourceChangeEvent.PRE_DELETE:
				System.out.print("Project ");
				System.out.print(res.getFullPath());
				System.out.println(" is about to be deleted.");
				break;
			case IResourceChangeEvent.POST_CHANGE:
				System.out.println("Resources have changed.");

				event.getDelta().accept(new DeltaPrinter());

				break;
			case IResourceChangeEvent.PRE_BUILD:
				System.out.println("Build about to run.");
				event.getDelta().accept(new DeltaPrinter());
				break;
			case IResourceChangeEvent.POST_BUILD:
				System.out.println("Build complete.");
				event.getDelta().accept(new DeltaPrinter());
				break;
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}