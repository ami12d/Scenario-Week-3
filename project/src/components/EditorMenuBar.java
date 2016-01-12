package components;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.UIManager;

import com.mxgraph.analysis.StructuralException;
import com.mxgraph.analysis.mxGraphProperties.GraphType;
import com.mxgraph.analysis.mxAnalysisGraph;
import com.mxgraph.analysis.mxGraphProperties;
import com.mxgraph.analysis.mxGraphStructure;
import com.mxgraph.analysis.mxTraversal;
import com.mxgraph.costfunction.mxCostFunction;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxResources;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphView;

import components.EditorActions.AlignCellsAction;
import components.EditorActions.AutosizeAction;
import components.EditorActions.BackgroundAction;
import components.EditorActions.BackgroundImageAction;
import components.EditorActions.ColorAction;
import components.EditorActions.ExitAction;
import components.EditorActions.GridColorAction;
import components.EditorActions.GridStyleAction;
import components.EditorActions.HistoryAction;
import components.EditorActions.ImportAction;
import components.EditorActions.KeyValueAction;
import components.EditorActions.NewAction;
import components.EditorActions.OpenAction;
import components.EditorActions.PageBackgroundAction;
import components.EditorActions.PageSetupAction;
import components.EditorActions.PrintAction;
import components.EditorActions.PromptPropertyAction;
import components.EditorActions.PromptValueAction;
import components.EditorActions.SaveAction;
import components.EditorActions.ScaleAction;
import components.EditorActions.SelectShortestPathAction;
import components.EditorActions.SelectSpanningTreeAction;
import components.EditorActions.SetLabelPositionAction;
import components.EditorActions.SetStyleAction;
import components.EditorActions.StyleAction;
import components.EditorActions.StylesheetAction;
import components.EditorActions.ToggleAction;
import components.EditorActions.ToggleConnectModeAction;
import components.EditorActions.ToggleCreateTargetItem;
import components.EditorActions.ToggleDirtyAction;
import components.EditorActions.ToggleGridItem;
import components.EditorActions.ToggleOutlineItem;
import components.EditorActions.TogglePropertyItem;
import components.EditorActions.ToggleRulersItem;
import components.EditorActions.WarningAction;
import components.EditorActions.ZoomPolicyAction;

public class EditorMenuBar extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4060203894740766714L;

	public enum AnalyzeType {
		IS_VALID, IS_CONNECTED, IS_CYCLIC_DIRECTED, IS_CYCLIC_UNDIRECTED, COMPLEMENTARY, REGULARITY, COMPONENTS, IS_DIRECTED;
	}

	public EditorMenuBar(final BasicGraphEditor editor) {
		final mxGraphComponent graphComponent = editor.getGraphComponent();
		final mxGraph graph = graphComponent.getGraph();
		mxAnalysisGraph aGraph = new mxAnalysisGraph();

		JMenu menu = null;
		JMenu submenu = null;

		// Creates the file menu
		menu = add(new JMenu(mxResources.get("file")));

		menu.add(editor.bind(mxResources.get("new"), new NewAction(), "/com/mxgraph/examples/swing/images/new.gif"));
		menu.add(editor.bind(mxResources.get("openFile"), new OpenAction(),
				"/com/mxgraph/examples/swing/images/open.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("save"), new SaveAction(false),
				"/com/mxgraph/examples/swing/images/save.gif"));
		menu.add(editor.bind(mxResources.get("saveAs"), new SaveAction(true),
				"/com/mxgraph/examples/swing/images/saveas.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("exit"), new ExitAction()));

		// Creates the edit menu
		menu = add(new JMenu(mxResources.get("edit")));

		menu.add(editor.bind(mxResources.get("undo"), new HistoryAction(true),
				"/com/mxgraph/examples/swing/images/undo.gif"));
		menu.add(editor.bind(mxResources.get("redo"), new HistoryAction(false),
				"/com/mxgraph/examples/swing/images/redo.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("cut"), TransferHandler.getCutAction(),
				"/com/mxgraph/examples/swing/images/cut.gif"));
		menu.add(editor.bind(mxResources.get("copy"), TransferHandler.getCopyAction(),
				"/com/mxgraph/examples/swing/images/copy.gif"));
		menu.add(editor.bind(mxResources.get("paste"), TransferHandler.getPasteAction(),
				"/com/mxgraph/examples/swing/images/paste.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("delete"), mxGraphActions.getDeleteAction(),
				"/com/mxgraph/examples/swing/images/delete.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("selectAll"), mxGraphActions.getSelectAllAction()));
		menu.add(editor.bind(mxResources.get("selectNone"), mxGraphActions.getSelectNoneAction()));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("edit"), mxGraphActions.getEditAction()));

		// Creates the view menu
		menu = add(new JMenu(mxResources.get("view")));

		JMenuItem item = menu.add(new TogglePropertyItem(graphComponent, mxResources.get("pageLayout"), "PageVisible",
				true, new ActionListener() {
					/**
					 * 
					 */
					public void actionPerformed(ActionEvent e) {
						if (graphComponent.isPageVisible() && graphComponent.isCenterPage()) {
							graphComponent.zoomAndCenter();
						} else {
							graphComponent.getGraphControl().updatePreferredSize();
						}
					}
				}));

		item.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * java.awt.event.ActionListener#actionPerformed(java.awt.event.
			 * ActionEvent)
			 */
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof TogglePropertyItem) {
					final mxGraphComponent graphComponent = editor.getGraphComponent();
					TogglePropertyItem toggleItem = (TogglePropertyItem) e.getSource();

					if (toggleItem.isSelected()) {
						// Scrolls the view to the center
						SwingUtilities.invokeLater(new Runnable() {
							/*
							 * (non-Javadoc)
							 * 
							 * @see java.lang.Runnable#run()
							 */
							public void run() {
								graphComponent.scrollToCenter(true);
								graphComponent.scrollToCenter(false);
							}
						});
					} else {
						// Resets the translation of the view
						mxPoint tr = graphComponent.getGraph().getView().getTranslate();

						if (tr.getX() != 0 || tr.getY() != 0) {
							graphComponent.getGraph().getView().setTranslate(new mxPoint());
						}
					}
				}
			}
		});

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("zoom")));

		submenu.add(editor.bind("400%", new ScaleAction(4)));
		submenu.add(editor.bind("200%", new ScaleAction(2)));
		submenu.add(editor.bind("150%", new ScaleAction(1.5)));
		submenu.add(editor.bind("100%", new ScaleAction(1)));
		submenu.add(editor.bind("75%", new ScaleAction(0.75)));
		submenu.add(editor.bind("50%", new ScaleAction(0.5)));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("custom"), new ScaleAction(0)));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("zoomIn"), mxGraphActions.getZoomInAction()));
		menu.add(editor.bind(mxResources.get("zoomOut"), mxGraphActions.getZoomOutAction()));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("actualSize"), mxGraphActions.getZoomActualAction()));

		menu = add(new JMenu("Analyze"));
		menu.add(editor.bind("Is Cyclic", new AnalyzeGraph(AnalyzeType.IS_CYCLIC_UNDIRECTED, aGraph)));
		menu.add(editor.bind("Regularity", new AnalyzeGraph(AnalyzeType.REGULARITY, aGraph)));
		menu.add(editor.bind("Get Components", new AnalyzeGraph(AnalyzeType.COMPONENTS, aGraph)));
		menu.add(editor.bind("Check Validity", new AnalyzeGraph(AnalyzeType.IS_VALID, aGraph)));
	}

	/**
	 *
	 */
	public static class InsertGraph extends AbstractAction {

		private static final long serialVersionUID = 4010463992665008365L;

		protected GraphType graphType;

		protected mxAnalysisGraph aGraph;

		/**
		 * @param aGraph
		 * 
		 */
		public InsertGraph(GraphType tree, mxAnalysisGraph aGraph) {
			this.graphType = tree;
			this.aGraph = aGraph;
		}

		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof mxGraphComponent) {
				mxGraphComponent graphComponent = (mxGraphComponent) e.getSource();
				mxGraph graph = graphComponent.getGraph();

				// dialog = new FactoryConfigDialog();
				String dialogText = "";
				if (graphType == GraphType.NULL)
					dialogText = "Configure null graph";
				else if (graphType == GraphType.COMPLETE)
					dialogText = "Configure complete graph";
				else if (graphType == GraphType.NREGULAR)
					dialogText = "Configure n-regular graph";

				GraphConfigDialog dialog = new GraphConfigDialog(graphType, dialogText);
				dialog.configureLayout(graph, graphType, aGraph);
				dialog.setModal(true);
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				Dimension frameSize = dialog.getSize();
				dialog.setLocation(screenSize.width / 2 - (frameSize.width / 2),
						screenSize.height / 2 - (frameSize.height / 2));
				dialog.setVisible(true);
			}
		}
	}

	/**
	 *
	 */
	public static class AnalyzeGraph extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 6926170745240507985L;

		mxAnalysisGraph aGraph;

		/**
		 * 
		 */
		protected AnalyzeType analyzeType;

		/**
		 * Examples for calling analysis methods from mxGraphStructure
		 */
		public AnalyzeGraph(AnalyzeType analyzeType, mxAnalysisGraph aGraph) {
			this.analyzeType = analyzeType;
			this.aGraph = aGraph;
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof mxGraphComponent) {
				mxGraphComponent graphComponent = (mxGraphComponent) e.getSource();
				mxGraph graph = graphComponent.getGraph();
				aGraph.setGraph(graph);

				if (analyzeType == AnalyzeType.IS_VALID) {
					boolean isValid = mxGraphStructure.isValid(aGraph);
					if (isValid) {
						System.out.println("Circuit is valid");
					} else {
						System.out.println("Circuit is not valid");
					}
				} else if (analyzeType == AnalyzeType.IS_CONNECTED) {
					boolean isConnected = mxGraphStructure.isConnected(aGraph);

					if (isConnected) {
						System.out.println("The graph is connected");
					} else {
						System.out.println("The graph is not connected");
					}
				}

				else if (analyzeType == AnalyzeType.IS_CYCLIC_UNDIRECTED) {
					boolean isCyclicUndirected = mxGraphStructure.isCyclicUndirected(aGraph);

					if (isCyclicUndirected) {
						System.out.println("The graph is cyclic undirected");
					} else {
						System.out.println("The graph is acyclic undirected");
					}
				} else if (analyzeType == AnalyzeType.REGULARITY) {
					try {
						int regularity = mxGraphStructure.regularity(aGraph);
						System.out.println("Graph regularity is: " + regularity);
					} catch (StructuralException e1) {
						System.out.println("The graph is irregular");
					}
				} else if (analyzeType == AnalyzeType.COMPONENTS) {
					Object[][] components = mxGraphStructure.getGraphComponents(aGraph);
					mxIGraphModel model = aGraph.getGraph().getModel();

					for (int i = 0; i < components.length; i++) {
						System.out.print("Component " + i + " :");

						for (int j = 0; j < components[i].length; j++) {
							System.out.print(" " + model.getValue(components[i][j]));
						}

						System.out.println(".");
					}

					System.out.println("Number of components: " + components.length);

				} else if (analyzeType == AnalyzeType.IS_DIRECTED) {
					boolean isDirected = mxGraphProperties.isDirected(aGraph.getProperties(),
							mxGraphProperties.DEFAULT_DIRECTED);

					if (isDirected) {
						System.out.println("The graph is directed.");
					} else {
						System.out.println("The graph is undirected.");
					}
				}
			}
		}
	};
};