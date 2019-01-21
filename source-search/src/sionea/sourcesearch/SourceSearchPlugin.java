package sionea.sourcesearch;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import sionea.sourcesearch.editor.EditorService;

/**
 * Activator class
 *
 */
public class SourceSearchPlugin extends AbstractUIPlugin {
	
	private static final String IMAGE_PATH = "icons/";
	
	private static SourceSearchPlugin singleton;

    public static SourceSearchPlugin getDefault() {
        if (singleton == null)
            singleton = new SourceSearchPlugin();
        return singleton;
    }

	@Override
	public void start(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("start activator");
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode("org.eclipse.core.resources");
		prefs.putBoolean(ResourcesPlugin.PREF_LIGHTWEIGHT_AUTO_REFRESH, true);
		prefs.flush();

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		EditorService.getDefualt().removeFiles(EditorService.getEditorFiles());
	}
	
	@Override
	protected void initializeImageRegistry(final ImageRegistry reg) {
		Bundle b = FrameworkUtil.getBundle(getClass());  
		reg.put(IMAGE_PATH+"close.gif", ImageDescriptor.createFromURL(b.getEntry(IMAGE_PATH+"close.gif")));
		reg.put(IMAGE_PATH+"search.gif", ImageDescriptor.createFromURL(b.getEntry(IMAGE_PATH+"close.gif")));
		reg.put(IMAGE_PATH+"backward_nav.png", ImageDescriptor.createFromURL(b.getEntry(IMAGE_PATH+"backward_nav.png")));
		reg.put(IMAGE_PATH+"forward_nav.png", ImageDescriptor.createFromURL(b.getEntry(IMAGE_PATH+"forward_nav.png")));
		reg.put(IMAGE_PATH+"backward_nav_active.png", ImageDescriptor.createFromURL(b.getEntry(IMAGE_PATH+"backward_nav_active.png")));
		reg.put(IMAGE_PATH+"forward_nav_active.png", ImageDescriptor.createFromURL(b.getEntry(IMAGE_PATH+"forward_nav_active.png")));
	}

}
