package sionea.sourcesearch;

import java.io.ByteArrayInputStream;
import java.io.File;

import javax.inject.Inject;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditor;

import sionea.sourcesearch.handler.MyResourceChangeReporter;

public class View extends ViewPart {
	public static final String ID = "source-search.view";

	@Inject
	IWorkbench workbench;

	private TableViewer viewer;
	private StyledText searchText;
	private StyledText sourceNameFilter;
	private ToolBar filterBar;
	private ToolBar toolBar;
	


	public final static int COLUMN_SOURCE_NAME = 0;
	public final static int COLUMN_SOURCE_TYPE = 1;
	public final static int COLUMN_SOURCE_OWNER = 2;
	public final static int COLUMN_SOURCE_COUNT = 3;

	private class StringLabelProvider extends ColumnLabelProvider {
		@Override
		public String getText(Object element) {
			return super.getText(element);
		}

		@Override
		public Image getImage(Object obj) {
			return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}

	}

	@Override
	public void createPartControl(Composite parent) {
		
		//ConnectionSingleton.getInstance().connect("localhost:1521/K", "system", "k");

		GridLayout parentLayout = new GridLayout();
		// parentLayout.marginTop = 2;
		parentLayout.marginLeft = 0;
		parentLayout.horizontalSpacing = 0;
		parentLayout.verticalSpacing = 0;
		parentLayout.marginWidth = 0;
		parentLayout.marginHeight = 2;
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
		FocusListener focusListener = new FocusListener() { // to set search tip

			public void focusGained(FocusEvent e) {
				if (searchText.isDisposed())
					return;

				// if (isShowingSearchTip()) {
				// searchText.setForeground(defaultSearchColor);
				//
				// if (SEARCH_TEXT_TIP.equals(searchText.getText()))
				// searchText.setText("");
				// }
			}

			public void focusLost(FocusEvent e) {
				// if (searchText.getText().length() == 0) {
				// searchText.setForeground(searchTipColor);
				// searchText.setText(SEARCH_TEXT_TIP);
				// }
			}
		};

		searchText.addFocusListener(focusListener);

		System.out.println("test");

		searchText.setToolTipText("Enter Search Text here");
		sourceNameFilter.setToolTipText("Filter Source Name");

		searchText.addTraverseListener(e -> {
			switch (e.detail) {
			case SWT.TRAVERSE_RETURN:
				if (!searchText.isDisposed()) {
					viewer.setInput(searchText.getText());
					// pack table to set column width
					Table t = viewer.getTable();
					for (int i = 0, n = t.getColumnCount(); i < n; i++) {
						t.getColumn(i).pack();
					}
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
		toolBar = new ToolBar(parent, SWT.FLAT | SWT.RIGHT);
		 gridData = new GridData(GridData.BEGINNING, GridData.CENTER, false, false);
		 toolBar.setLayoutData(gridData);

		ToolItem filterItem = new ToolItem(toolBar, SWT.DROP_DOWN);
	    DropdownSelectionListener listenerOne = new DropdownSelectionListener(filterItem);
	    listenerOne.add(" ");
	    listenerOne.add("package");
	    listenerOne.add("package body");
	    filterItem.addSelectionListener(listenerOne);
		filterItem.setToolTipText("Filter by...");
		// filterItem.setImage( getPluginImage("filter") );
		new ToolItem(toolBar, SWT.SEPARATOR);

		ToolItem ownerDropDownItem = new ToolItem(toolBar, SWT.DROP_DOWN);
	    DropdownSelectionListener listenerTwo = new DropdownSelectionListener(ownerDropDownItem);
	    listenerTwo.add(" "); 
	    listenerTwo.add("1");
	    listenerTwo.add("2");
	    ownerDropDownItem.addSelectionListener(listenerTwo);
	    ownerDropDownItem.setToolTipText("Filter Owner");
		new ToolItem(toolBar, SWT.SEPARATOR);

		ToolItem leftSearchItem = new ToolItem(toolBar, SWT.NONE);
		leftSearchItem.setImage(SourceSearchPlugin.getDefault().getImageRegistry().get("icons/backward_nav.png"));
		leftSearchItem.setToolTipText("Clear search text");
		leftSearchItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				searchText.setText("");
				searchText.setFocus();
			}
		});
		new ToolItem(toolBar, SWT.SEPARATOR);
		ToolItem rightSearchItem = new ToolItem(toolBar, SWT.NONE);
		rightSearchItem.setImage(SourceSearchPlugin.getDefault().getImageRegistry().get("icons/forward_nav.png"));
		rightSearchItem.setToolTipText("Clear search text");
		rightSearchItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				searchText.setText("");
				searchText.setFocus();
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

		// resultViewer = new TreeViewer(viewComposite, SWT.FULL_SELECTION);

		
		// resource handling
		IResourceChangeListener listener = new MyResourceChangeReporter();
		   ResourcesPlugin.getWorkspace().addResourceChangeListener(listener,
		      IResourceChangeEvent.PRE_CLOSE
		      | IResourceChangeEvent.PRE_DELETE
		      | IResourceChangeEvent.PRE_BUILD
		      | IResourceChangeEvent.POST_BUILD
		      | IResourceChangeEvent.POST_CHANGE);
		
		// table viewer
		viewer = new TableViewer(viewComposite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setComparator(new SourceSearchSorter());

		viewer.setContentProvider(new SearchContentProviderTest());
		viewer.setLabelProvider(new SearchLabelProvider());
		
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
				Object obj = selection.getFirstElement();
				
				SearchResult res = null;
				
				if(obj instanceof SearchResult) {
					final SearchResult result = (SearchResult) obj; 
					
					IWorkspaceRunnable myRunnable = 
							new IWorkspaceRunnable() {

								@Override
								public void run(IProgressMonitor monitor) throws CoreException {
									IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("test");
									IFile fileToOpen = project.getFile(new Path("externalfile.xml"));
									File file=fileToOpen.getLocation().toFile();
									if (file.exists() ) {
										ResourcesPlugin.getWorkspace().getRoot().getProject("test").refreshLocal(IResource.DEPTH_INFINITE, monitor);
										//IFileStore fileStore = EFS.getLocalFileSystem().getStore(fileToOpen.toURI());
										fileToOpen.setContents(new ByteArrayInputStream(("test string"+result.getSourceId()+"").getBytes()), IFile.BACKGROUND_REFRESH|IFile.FORCE, monitor);
										refreshFolder(project);
										IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
										//IDE.openEditorOnFileStore(page, fileStore);
										final IEditorPart editorPart = IDE.openEditor( page, fileToOpen );
										
										// make editor window non-editable
										ITextEditor editor = (ITextEditor) page.getActiveEditor();
										ITextViewer viewer = (ITextViewer) editor.getAdapter(ITextOperationTarget.class);
										if (viewer != null) {
											viewer.setEditable(false);
										}
										try {
											revealInEditor(page.getActiveEditor(), "test");
										} catch (BadLocationException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									} else {
										// Do something if the file does not exist
									}
								}
						};
						IWorkspace workspace = ResourcesPlugin.getWorkspace();
						try {
							workspace.run(myRunnable, null, IWorkspace.AVOID_UPDATE, null);
						} catch (CoreException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					//new OpenEditorJob(res, (SearchContentProvider)viewer.getContentProvider(), getSite().getPage()).schedule();
				}
			}
		});
		Table t = viewer.getTable();
		t.setLinesVisible(true);
		t.setHeaderVisible(true);

		TableColumn tc = new TableColumn(t, SWT.LEFT);
		tc.setText("Source Name");
		tc.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				((SourceSearchSorter) viewer.getComparator()).doSort(COLUMN_SOURCE_NAME);
				viewer.refresh();
			}
		});

		tc = new TableColumn(t, SWT.LEFT);
		tc.setText("Source Type");
		tc.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				((SourceSearchSorter) viewer.getComparator()).doSort(COLUMN_SOURCE_TYPE);
				viewer.refresh();
			}
		});

		tc = new TableColumn(t, SWT.LEFT);
		tc.setText("Owner");
		tc.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				((SourceSearchSorter) viewer.getComparator()).doSort(COLUMN_SOURCE_OWNER);
				viewer.refresh();
			}
		});

		tc = new TableColumn(t, SWT.LEFT);
		tc.setText("Count");
		tc.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				((SourceSearchSorter) viewer.getComparator()).doSort(COLUMN_SOURCE_COUNT);
				viewer.refresh();
			}
		});
		
		for (int i = 0, n = t.getColumnCount(); i < n; i++) {
			t.getColumn(i).pack();
		}

		
	}
	/**
     * refresh the reqm output folder for the given project. The refresh is done in a separate thread
     * 
     * @param project
     */
    public synchronized void  refreshFolder( final IProject project ) {
            final Job refreshJob = new Job( "refreshjob" ) {
                @Override
                protected IStatus run( final IProgressMonitor monitor ) {

                    try {
                    	ResourcesPlugin.getWorkspace().getRoot().getProject("test").getFile("externalfile.xml")
                                    .refreshLocal( IResource.FORCE, monitor );
                    } catch( final CoreException e ) {
                       // ErrorLogger.logError( "Error in refreshing  outputfolder ", e );
                    }

                    return Status.OK_STATUS;
                }
            };
            refreshJob.schedule();
    }
	 public static void revealInEditor( final IEditorPart editorPart , String searchString)
		        throws BadLocationException {
		        if( editorPart == null ) {
		            return;
		        }
		        final ITextOperationTarget target = (ITextOperationTarget)editorPart.getAdapter( ITextOperationTarget.class );
		        if( target instanceof ITextViewer ) {
		            final ITextViewer textViewer = (ITextViewer)target;
		            //gets the opened document
		            final IDocument document = textViewer.getDocument();
		            final IRegion searchStringRegion = findSearchStringRegion( document,searchString);
		            if( searchStringRegion != null ) {
		                revealInEditor( editorPart, searchStringRegion.getOffset(), searchStringRegion.getLength() );
		            }
		        }

		    }
	 private static IRegion findSearchStringRegion(IDocument document, String searchString) {
		 final FindReplaceDocumentAdapter findAdapter = new FindReplaceDocumentAdapter( document );
	        try {
	            final IRegion startRegion = findAdapter.find( 0, "test", true, true, false, false );
	             return startRegion;

	        } catch( final BadLocationException e ) {
	            //ErrorLogger.logError( e.getMessage(), e );
	        }
		return null;
	}
	/**
	     * Reveals the corresponding offset in the editor
	     * 
	     * @param editor opened editor
	     * @param offset character start offset to mark
	     * @param length length of the marking string
	     */
	    public static void revealInEditor( final IEditorPart editor, final int offset, final int length ) {
	        //if the opened editor is a text editor
	        if( editor instanceof ITextEditor ) {
	            ((ITextEditor)editor).selectAndReveal( offset, length );
	            return;
	        }
	        //gets the IGotoMarker object to go to a marker in a file
	        final IGotoMarker gotoMarkerTarget;
	        if( editor instanceof IGotoMarker ) {
	            gotoMarkerTarget = (IGotoMarker)editor;
	        } else {
	            gotoMarkerTarget = editor == null ? null : (IGotoMarker)editor.getAdapter( IGotoMarker.class );
	        }
	        if( gotoMarkerTarget != null ) {
	            final IFileEditorInput input = (IFileEditorInput)editor.getEditorInput();
	            IMarker marker = null;
	            try {
	                //creates a marker in the file and goes to the marker
	                marker = input.getFile().createMarker( IMarker.TEXT );
	                if( marker != null ) {
	                    marker.setAttribute( IMarker.CHAR_START, offset );
	                    marker.setAttribute( IMarker.CHAR_END, offset + length );
	                    gotoMarkerTarget.gotoMarker( marker );
	                    marker.delete();
	                }
	            } catch( final CoreException ex ) {
	                //ErrorLogger.logError( ex.getMessage(), ex );
	            }
	            final ISelectionProvider provider = editor.getEditorSite().getSelectionProvider();
	            if( provider != null ) {
	                provider.setSelection( new TextSelection( offset, length ) );
	            }
	        }
	    }
	@Override
	public void setFocus() {
		// viewer.getControl().setFocus();
		searchText.setFocus();
	}

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);

	}
}