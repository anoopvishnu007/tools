package sionea.sourcesearch.ui;

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditor;

import sionea.sourcesearch.SourceSearchPlugin;
import sionea.sourcesearch.data.SearchData;
import sionea.sourcesearch.data.SearchResult;
import sionea.sourcesearch.editor.EditorService;
import sionea.sourcesearch.utils.FileSystemUtils;

public class SourceSearchView extends ViewPart {
	public static final String ID = "source-search.view"; 

	private TableViewer viewer;
	private StyledText searchText;
	private StyledText sourceNameFilter;
	private ToolBar filterBar;
	private ToolItem filterItem;
	private ToolItem ownerDropDownItem;
	private ToolBar filterToolBar;
	private ToolBar historyToolBar;
	private ToolItem leftSearchItem;
	private ToolItem rightSearchItem;
 	private SearchData currentSearchData;
	private final LinkedList<SearchData> searchList =new LinkedList<SearchData>();
	public final static int COLUMN_SOURCE_NAME = 0;
	public final static int COLUMN_SOURCE_TYPE = 1;
	public final static int COLUMN_SOURCE_OWNER = 2;
	public final static int COLUMN_SOURCE_COUNT = 3; 

	@Override
	public void createPartControl(Composite parent) {

		//ConnectionSingleton.getInstance().connect("localhost:1521/K", "system", "k");

		GridLayout parentLayout = getParentLayout();
		parent.setLayout(parentLayout);

		Composite textComposite = new Composite(parent, SWT.NONE);
		GridData textCompositeGridData = new GridData(GridData.FILL, GridData.BEGINNING, true, false);
		textComposite.setLayoutData(textCompositeGridData);

		GridLayout layout = new GridLayout(3, false);
		layout.marginLeft = 2;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		textComposite.setLayout(layout);

		searchText = new StyledText(textComposite, SWT.SINGLE | SWT.BORDER | SWT.SEARCH | SWT.CANCEL);
		searchText.setSize(700, 20);
		sourceNameFilter = new StyledText(textComposite, SWT.SINGLE | SWT.BORDER | SWT.SEARCH | SWT.CANCEL);
		sourceNameFilter.setSize(400, 20);
		GridData searchTextGridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		GridData sourceNameTextGridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		searchText.setLayoutData(searchTextGridData);
		sourceNameFilter.setLayoutData(sourceNameTextGridData);

		searchText.setToolTipText("Enter Search Text here");
		sourceNameFilter.setToolTipText("Filter Source Name");

		searchText.addTraverseListener(e -> {
			switch (e.detail) {
			case SWT.TRAVERSE_RETURN:
				if (!searchText.isDisposed()) {
					SearchData data= getSearchDataFromControls();
					if(currentSearchData != null){
						currentSearchData.setNext(data);
						data.setPrevious(currentSearchData);

					}
					currentSearchData= data;
					if(!searchList.contains(data)) {
						searchList.add(currentSearchData);
					}
					setSearchresults(data);
					setBackwardButtonImage();
					setForwardButtonImage();
				}
				break;
			default:
				break;
			}
		});

		filterBar = new ToolBar(textComposite, SWT.FLAT | SWT.RIGHT);
		GridData gridData = new GridData(GridData.BEGINNING, GridData.CENTER, false, false);
		filterBar.setSize(20, 20);
		filterBar.setLayoutData(gridData);

		ToolItem clearItem = new ToolItem(filterBar, SWT.NONE);
		clearItem.setImage(SourceSearchPlugin.getDefault().getImageRegistry().get("icons/close.gif"));
		clearItem.setToolTipText("Clear search text");
		clearItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				searchText.setText("");
				searchText.setFocus();
			}
		});
		textComposite.setTabList(new Control[] { searchText,sourceNameFilter,filterBar });
		Composite toolBarComposite = new Composite(parent, SWT.NONE);
		GridData toolBarCompositeGridData = new GridData(GridData.FILL, GridData.BEGINNING, false, false);
		toolBarComposite.setLayoutData(toolBarCompositeGridData);

		GridLayout toolbarLayout = new GridLayout(2, true);
		toolbarLayout.marginLeft = 2;
		toolbarLayout.horizontalSpacing = 0;
		toolbarLayout.verticalSpacing = 0;
		toolbarLayout.marginWidth = 0;
		toolbarLayout.marginHeight = 0;
		toolbarLayout.makeColumnsEqualWidth= true;
		toolBarComposite.setLayout(toolbarLayout);
		filterToolBar = new ToolBar(toolBarComposite, SWT.FLAT | SWT.RIGHT);
		gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		filterToolBar.setLayoutData(gridData);

		filterItem = new ToolItem(filterToolBar, SWT.DROP_DOWN);
		filterItem.setText(" ");
		filterItem.setWidth(350);
		DropdownSelectionListener listenerOne = new DropdownSelectionListener(filterItem);
		listenerOne.add(" ");
		listenerOne.add("Package");
		listenerOne.add("Package Body");
		filterItem.addSelectionListener(listenerOne);
		new ToolItem(filterToolBar, SWT.SEPARATOR);

		ownerDropDownItem = new ToolItem(filterToolBar, SWT.DROP_DOWN);
		ownerDropDownItem.setText(" ");
		DropdownSelectionListener listenerTwo = new DropdownSelectionListener(ownerDropDownItem);
		listenerTwo.add(" "); 
		listenerTwo.add("1");
		listenerTwo.add("2");
		ownerDropDownItem.addSelectionListener(listenerTwo);
		ownerDropDownItem.setToolTipText("Filter Owner");
		new ToolItem(filterToolBar, SWT.SEPARATOR);
		historyToolBar = new ToolBar(toolBarComposite,  SWT.RIGHT);
		gridData = new GridData(SWT.RIGHT, GridData.CENTER, false, false);
		historyToolBar.setLayoutData(gridData);

		leftSearchItem = new ToolItem(historyToolBar, SWT.NONE);
		leftSearchItem.setImage(SourceSearchPlugin.getDefault().getImageRegistry().get("icons/backward_nav.png"));
		leftSearchItem.setToolTipText("Run previous search");
		leftSearchItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index= searchList.indexOf(currentSearchData);
				if(index >=0 && index<searchList.size()){
					currentSearchData = searchList.get(index-1);	
					setSearchresults(currentSearchData);
					setBackwardButtonImage();
					setForwardButtonImage();
				}
			}
		});
		new ToolItem(historyToolBar, SWT.SEPARATOR);
		rightSearchItem = new ToolItem(historyToolBar, SWT.NONE);
		rightSearchItem.setImage(SourceSearchPlugin.getDefault().getImageRegistry().get("icons/forward_nav.png"));
		rightSearchItem.setToolTipText("Run next search");
		rightSearchItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index= searchList.indexOf(currentSearchData);
				if(index >=0 && index<searchList.size()){
					currentSearchData = searchList.get(index+1);	
					setSearchresults(currentSearchData);
					setBackwardButtonImage();
					setForwardButtonImage();
				}

			}
		});
		filterBar.update();

		Composite viewComposite = new Composite(parent, SWT.NONE);
		viewComposite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		FillLayout fillLayout = new FillLayout();
		fillLayout.marginHeight = 0;
		fillLayout.marginWidth = 0;
		fillLayout.spacing = 0;
		viewComposite.setLayout(fillLayout);
		// table viewer
		viewer = new TableViewer(viewComposite, SWT.MULTI |SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setComparator(new SourceSearchSorter());
		SearchContentProviderTest contentProviderTest=new SearchContentProviderTest();
		viewer.setContentProvider(contentProviderTest);
		viewer.setLabelProvider(new SearchLabelProvider());

		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				try {
					openResultContentInEditor(contentProviderTest);
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}


		});
		Table searchResultTable = viewer.getTable();
		searchResultTable.setLinesVisible(true);
		searchResultTable.setHeaderVisible(true);
		//Code for setting the table viewer header background color. Support onwards Eclipse 4.7 oxygen version
		//searchResultTable.setHeaderBackground(searchResultTable.getDisplay().getSystemColor(SWT.COLOR_GRAY));

		final TableColumn sourceNameTC = new TableColumn(searchResultTable, SWT.LEFT);
		sourceNameTC.setText("Source Name");

		sourceNameTC.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				((SourceSearchSorter) viewer.getComparator()).doSort(COLUMN_SOURCE_NAME);
				searchResultTable.setSortColumn(sourceNameTC);
				searchResultTable.setSortDirection(((SourceSearchSorter) viewer.getComparator()).getDirection());
				viewer.refresh();
			}
		});

		final TableColumn sourceTypeTC = new TableColumn(searchResultTable, SWT.LEFT);
		sourceTypeTC.setText("Source Type");
		sourceTypeTC.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				((SourceSearchSorter) viewer.getComparator()).doSort(COLUMN_SOURCE_TYPE);
				searchResultTable.setSortColumn(sourceTypeTC);
				searchResultTable.setSortDirection(((SourceSearchSorter) viewer.getComparator()).getDirection());
				viewer.refresh();
			}
		});

		final TableColumn ownerTC = new TableColumn(searchResultTable, SWT.LEFT);
		ownerTC.setText("Owner");
		ownerTC.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				((SourceSearchSorter) viewer.getComparator()).doSort(COLUMN_SOURCE_OWNER);
				searchResultTable.setSortColumn(ownerTC);
				searchResultTable.setSortDirection(((SourceSearchSorter) viewer.getComparator()).getDirection());
				viewer.refresh();
			}
		});

		final TableColumn countTC = new TableColumn(searchResultTable, SWT.LEFT);
		countTC.setText("Count");
		countTC.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				((SourceSearchSorter) viewer.getComparator()).doSort(COLUMN_SOURCE_COUNT);
				searchResultTable.setSortColumn(countTC);
				searchResultTable.setSortDirection(((SourceSearchSorter) viewer.getComparator()).getDirection());
				viewer.refresh();
			}
		});
		// pack table to set column width
		Table t = viewer.getTable();
		for (int i = 0, n = t.getColumnCount(); i < n; i++) {
			t.getColumn(i).pack();
		}
		getSite().setSelectionProvider(viewer);
		 
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().addPartListener(getPartListner());
		IWorkbench workbench = PlatformUI.getWorkbench();
		 
		workbench.addWorkbenchListener( new IWorkbenchListener()
		{
		    public boolean preShutdown( IWorkbench workbench, boolean forced )
		    {                            
		    	Collection<IEditorPart> parts=EditorService.getDefualt().getOpenedReadOnlyEditors("org.eclipse.datatools.sqltools.sqleditor.SQLEditor");
            	EditorService.getDefualt().closeEditors(parts);
		        return true;
		    }
		 
		    public void postShutdown( IWorkbench workbench )
		    {
		 
		    }
		});


	}
	private IPartListener getPartListner() {
		IPartListener partListner= new IPartListener() {
			
			@Override
			public void partOpened(IWorkbenchPart part) {
				if(part instanceof IEditorPart) {
					ITextEditor editor= (ITextEditor)part;
					IEditorInput input=editor.getEditorInput();
		            IFile editorFile = (IFile) input.getAdapter(IFile.class);
		            IProject project=ResourcesPlugin.getWorkspace().getRoot().getProject("sourcesearch");
		            if(project.exists() && project.equals(editorFile.getProject())){		        		
		        		ITextViewer viewer = (ITextViewer) editor.getAdapter(ITextOperationTarget.class);
		        		if (viewer != null) {
		        			viewer.setEditable(false);
		        			viewer.getTextWidget().setBackground(new Color(Display.getCurrent(), 211, 211, 211));
		        		}
		            }

				}				
			}
			
			@Override
			public void partDeactivated(IWorkbenchPart part) {
				// TODO Auto-generated method stub
				 
			}
			
			@Override
			public void partClosed(IWorkbenchPart part) {
				/*Collection<IEditorPart> parts=EditorService.getDefualt().getOpenedReadOnlyEditors("org.eclipse.datatools.sqltools.sqleditor.SQLEditor");
            	EditorService.getDefualt().closeEditors(parts);*/
			}
			
			@Override
			public void partBroughtToTop(IWorkbenchPart part) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void partActivated(IWorkbenchPart part) {

				if(part instanceof IEditorPart) {
					ITextEditor editor= (ITextEditor)part;
					IEditorInput input=editor.getEditorInput();
		            IFile editorFile = (IFile) input.getAdapter(IFile.class);
		            IProject project=ResourcesPlugin.getWorkspace().getRoot().getProject("sourcesearch");
		            if(project.exists() && editorFile!=null &&  project.equals(editorFile.getProject())){		        		
		        		ITextViewer viewer = (ITextViewer) editor.getAdapter(ITextOperationTarget.class);
		        		if (viewer != null) {
		        			viewer.setEditable(false);
		        			viewer.getTextWidget().setBackground(new Color(Display.getCurrent(), 211, 211, 211));
		        			
		        		}
		            }

				}				
							
			}
		};
		return partListner;
	}
	private void openResultContentInEditor(SearchContentProviderTest contentProviderTest) throws CoreException {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		Object obj = selection.getFirstElement();			
		
		if(obj instanceof SearchResult) {
			final SearchResult result = (SearchResult) obj; 
			String fileContent=contentProviderTest.getText(result);
			IFile fileToOpen = FileSystemUtils.createFileWithContent("sourcesearch","externalfile.sql",fileContent);
			FileSystemUtils.openFileInEditor(fileToOpen,currentSearchData.getSearchString(),"org.eclipse.datatools.sqltools.sqleditor.SQLEditor");		
		}
	}
	
	private GridLayout getParentLayout() {
		GridLayout parentLayout = new GridLayout();
		parentLayout.marginLeft = 0;
		parentLayout.horizontalSpacing = 0;
		parentLayout.verticalSpacing = 0;
		parentLayout.marginWidth = 0;
		parentLayout.marginHeight = 2;
		return parentLayout;
	}
	private void setBackwardButtonImage() {
		if(currentSearchData!= null && currentSearchData.getPrevious()!=null){
			leftSearchItem.setEnabled(true);
		    leftSearchItem.setImage(SourceSearchPlugin.getDefault().getImageRegistry().get("icons/backward_nav_active.png"));		 
		}else{
			leftSearchItem.setImage(SourceSearchPlugin.getDefault().getImageRegistry().get("icons/backward_nav.png"));
			leftSearchItem.setDisabledImage(SourceSearchPlugin.getDefault().getImageRegistry().get("icons/backward_nav.png"));
			leftSearchItem.setEnabled(false);
		}
	}
	private void setForwardButtonImage() {
		if(currentSearchData!= null && currentSearchData.getNext()!=null){
			rightSearchItem.setEnabled(true);
			rightSearchItem.setImage(SourceSearchPlugin.getDefault().getImageRegistry().get("icons/forward_nav_active.png"));
		}else{
			rightSearchItem.setImage(SourceSearchPlugin.getDefault().getImageRegistry().get("icons/forward_nav.png"));
			rightSearchItem.setDisabledImage(SourceSearchPlugin.getDefault().getImageRegistry().get("icons/forward_nav.png"));
			rightSearchItem.setEnabled(false);
		}
	}
	private void setSearchresults(SearchData data) {
		if(data!=null){
			viewer.setInput(data);
			// pack table to set column width
			Table t = viewer.getTable();
			for (int i = 0, n = t.getColumnCount(); i < n; i++) {
				t.getColumn(i).pack();
			}
			setSearchDataInControls(data);
		}
	}
	private SearchData getSearchDataFromControls() {
		 SearchData data= new SearchData();
		 data.setSearchString(searchText.getText());
		 data.setSourceNameFilter(sourceNameFilter.getText());
		 data.setFilterPackage(filterItem.getText());
		 data.setFilterOwner(ownerDropDownItem.getText());
		 return data;
		
	}
	private void setSearchDataInControls(SearchData data) {
		searchText.setText(data.getSearchString());
		sourceNameFilter.setText(data.getSourceNameFilter());
		filterItem.setText(data.getFilterPackage());
		ownerDropDownItem.setText(data.getFilterOwner());

	}
	
	@Override
	public void setFocus() {
		searchText.setFocus();
	}

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
	}
}