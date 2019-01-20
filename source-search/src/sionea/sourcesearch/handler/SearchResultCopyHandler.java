/**
 * 
 */
package sionea.sourcesearch.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import sionea.sourcesearch.data.SearchResult;
import sionea.sourcesearch.ui.SourceSearchView;

/**
 * @author Anoop
 *
 */
public class SearchResultCopyHandler extends AbstractHandler {
	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		IViewPart view = page.findView(SourceSearchView.ID);
		Clipboard cb = new Clipboard(Display.getDefault());
		try {
			ISelection selection = view.getSite().getSelectionProvider()
					.getSelection();
			List<SearchResult> SearchResultList = new ArrayList<SearchResult>();
			if (selection != null && selection instanceof IStructuredSelection) {
				IStructuredSelection sel = (IStructuredSelection) selection;
				for (Iterator<SearchResult> iterator = sel.iterator(); iterator.hasNext();) {
					SearchResult searchResult = iterator.next();
					SearchResultList.add(searchResult);
				}
			}
			StringBuilder sb = new StringBuilder();
			for (SearchResult searchResult : SearchResultList) {
				sb.append(searchResult.getSourceName()+",");
			}
			sb.replace(sb.lastIndexOf(","),sb.lastIndexOf(",")+1,"" );
			TextTransfer textTransfer = TextTransfer.getInstance();
			cb.setContents(new Object[] { sb.toString() },
					new Transfer[] { textTransfer });
		}finally {
			cb.dispose();
		}
		return null;
	}

	 
}