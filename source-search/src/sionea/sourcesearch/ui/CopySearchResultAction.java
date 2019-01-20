package sionea.sourcesearch.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

import sionea.sourcesearch.data.SearchResult;

public class CopySearchResultAction extends  Action {
	TableViewer viewer;
	
	public CopySearchResultAction(TableViewer viewer) {
		this.viewer=viewer;
	}

	@Override
	public void run() {
		Clipboard cb = new Clipboard(Display.getDefault());
        ISelection selection = viewer.getSelection();
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
	}

	

}
