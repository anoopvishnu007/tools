package sionea.sourcesearch;

import org.eclipse.ui.IStartup;

public class SourceSearchStartup implements IStartup
{
	public void earlyStartup()
	{
		//IPreferenceStore prefs = InstaSearchPlugin.getDefault().getPreferenceStore();
		//boolean shownView = InstaSearchPlugin.getBoolPref("shownView"); // false first time
		
		//if( ! shownView ) 
		//{
		//	prefs.setValue("shownView", true); // do not show anymore on startup
			
			// Show view the first time after installation
		//	PlatformUI.getWorkbench().getDisplay().asyncExec( new ShowInstaSearchAction() );
		//}
	}
	


}
