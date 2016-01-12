/**
 * Copyright (c) 2012, JGraph Ltd
 */
package com.mxgraph.analysis;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.mxgraph.costfunction.mxCostFunction;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraph.mxICellVisitor;

import components.EditorActions.SaveAction;

import com.mxgraph.view.mxGraphView;

public class mxGraphStructure
{
	/**
	 * The default style for vertexes
	 */
	private static String basicVertexStyleString = "ellipse;strokeColor=black;fillColor=orange;gradientColor=none";

	/**
	 * The default style for edges 
	 */
	private static String basicEdgeStyleString = "strokeColor=red;noEdgeStyle=1;";

	private static String basicArrowStyleString = "endArrow=block;";

	/**
	 * @param aGraph
	 * @return true if the graph is connected
	 */
	public static boolean isValid(mxAnalysisGraph aGraph)
	{
		new SaveAction(false);
		
		int lowest = getLowestDegreeVertex(aGraph, null);
		//System.out.println(lowest);
		
	    final JFrame parent = new JFrame();
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - parent.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - parent.getHeight()) / 2);
	    parent.setLocation(x, y);
		
		JLabel label = new JLabel("My label");

		
		if ((isConnected(aGraph) == true) && (isCyclicUndirected(aGraph) == true) 
				&& lowest!= 0 && lowest!= 1)
		{
			label.setText("Circuit is valid");
		    parent.add(label);
		    
		    parent.pack();
		    parent.setVisible(true);
			return true;
		}
		
		label.setText("Circuit is not valid");
	    parent.add(label);
	    
	    parent.pack();
	    parent.setVisible(true);
		return false;
	}
	
	public static boolean isConnected(mxAnalysisGraph aGraph)
	{
		Object[] vertices = aGraph.getChildVertices(aGraph.getGraph().getDefaultParent());
		int vertexNum = vertices.length;

		if (vertexNum == 0)
		{
			throw new IllegalArgumentException();
		}

		//data preparation
		int connectedVertices = 1;
		int[] visited = new int[vertexNum];
		visited[0] = 1;

		for (int i = 1; i < vertexNum; i++)
		{
			visited[i] = 0;
		}

		ArrayList<Object> queue = new ArrayList<Object>();
		queue.add(vertices[0]);

		//repeat the algorithm until the queue is empty
		while (queue.size() > 0)
		{
			//cut out the first vertex
			Object currVertex = queue.get(0);
			queue.remove(0);

			//fill the queue with neighboring but unvisited vertexes
			Object[] neighborVertices = aGraph.getOpposites(aGraph.getEdges(currVertex, null, true, true, false, true), currVertex, true,
					true);

			for (int j = 0; j < neighborVertices.length; j++)
			{
				//get the index of the neighbor vertex
				int index = 0;

				for (int k = 0; k < vertexNum; k++)
				{
					if (vertices[k].equals(neighborVertices[j]))
					{
						index = k;
					}
				}

				if (visited[index] == 0)
				{
					queue.add(vertices[index]);
					visited[index] = 1;
					connectedVertices++;
				}
			}
		}

		// if we visited every vertex, the graph is connected
		if (connectedVertices == vertexNum)
		{
			return true;
		}
		else
		{
			return false;
		}
	};

	/**
	 * @param aGraph
	 * @param parent
	 * @return true if the graph contains cycles regardless of edge direction
	 */
	public static boolean isCyclicUndirected(mxAnalysisGraph aGraph)
	{
		mxGraph graph = aGraph.getGraph();
		mxIGraphModel model = graph.getModel();
		Object[] cells = model.cloneCells(aGraph.getChildCells(graph.getDefaultParent(), true, true), true);
		mxGraphModel modelCopy = new mxGraphModel();
		mxGraph graphCopy = new mxGraph(modelCopy);
		Object parentCopy = graphCopy.getDefaultParent();
		graphCopy.addCells(cells);
		//		mxAnalysisGraph aGraphCopy = new mxAnalysisGraph(graphCopy, aGraph.getGenerator(), aGraph.getProperties());
		mxAnalysisGraph aGraphCopy = new mxAnalysisGraph();
		aGraphCopy.setGraph(graphCopy);
		aGraphCopy.setGenerator(aGraph.getGenerator());
		aGraphCopy.setProperties(aGraph.getProperties());

		Object[] leaf = new Object[1];

		do
		{
			leaf[0] = getUndirectedLeaf(aGraphCopy);

			if (leaf[0] != null)
			{
				graphCopy.removeCells(leaf);
			}
		}
		while (leaf[0] != null);

		int vertexNum = aGraphCopy.getChildVertices(parentCopy).length;

		if (vertexNum > 0)
		{
			return true;
		}
		else
		{
			return false;
		}

	};

	/**
	 * A helper function for getting a leaf vertex (degree <= 1), not taking into account edge direction - for internal use
	 * @param aGraph
	 * @return the first undirected leaf that could be found in the graph, null if none
	 */
	private static Object getUndirectedLeaf(mxAnalysisGraph aGraph)
	{
		Object parent = aGraph.getGraph().getDefaultParent();
		Object[] vertices = aGraph.getChildVertices(parent);
		int vertexNum = vertices.length;
		Object currVertex;

		for (int i = 0; i < vertexNum; i++)
		{
			currVertex = vertices[i];
			int edgeCount = aGraph.getEdges(currVertex, parent, true, true, false, true).length;

			if (edgeCount <= 1)
			{
				return currVertex;
			}
		}

		return null;
	};




	/**
	 * @param aGraph
	 * @param omitVertex vertices in this array will be omitted, set this parameter to null if you don't want this feature
	 * @return a vertex that has lowest degree, or one of those in case if there are more
	 */
	static public int getLowestDegreeVertex(mxAnalysisGraph aGraph, Object[] omitVertex)
	{
		Object[] vertices = aGraph.getChildVertices(aGraph.getGraph().getDefaultParent());
		int vertexCount = vertices.length;

		int lowestEdgeCount = Integer.MAX_VALUE;
		Object bestVertex = null;
		List<Object> omitList = null;

		if (omitVertex != null)
		{
			omitList = Arrays.asList(omitVertex);
		}

		for (int i = 0; i < vertexCount; i++)
		{
			if (omitVertex == null || !omitList.contains(vertices[i]))
			{
				int currEdgeCount = aGraph.getEdges(vertices[i], null, true, true, true, true).length;

				if (currEdgeCount == 0)
				{
					//return vertices[i];
				}
				else
				{
					if (currEdgeCount < lowestEdgeCount)
					{
						lowestEdgeCount = currEdgeCount;
						bestVertex = vertices[i];
					}
				}
			}
		}

		return lowestEdgeCount;
	};

	/**
	 * @param graph
	 * @param sourceVertex
	 * @param targetVertex
	 * @return Returns true if the two vertices are connected directly by an edge. If directed, the result is true if they are connected by an edge that points from source to target, if false direction isn't takein into account, just connectivity.
	 */
	public static boolean areConnected(mxAnalysisGraph aGraph, Object sourceVertex, Object targetVertex)
	{
		Object currEdges[] = aGraph.getEdges(sourceVertex, aGraph.getGraph().getDefaultParent(), true, true, false, true);
		List<Object> neighborList = Arrays.asList(aGraph.getOpposites(currEdges, sourceVertex, true, true));
		return neighborList.contains(targetVertex);
	};

	/**
	 * @param graph
	 * Make a graph simple (remove parallel edges and self loops)
	 */
//	public static void makeSimple(mxAnalysisGraph aGraph)
//	{
//		// remove all self-loops
//		// reduce all valences >1 to 1
//		mxGraph graph = aGraph.getGraph();
//		Object parent = graph.getDefaultParent();
//
//		Object[] edges = aGraph.getChildEdges(parent);
//		//removing self-loops
//		for (int i = 0; i < edges.length; i++)
//		{
//			Object currEdge = edges[i];
//
//			if (aGraph.getTerminal(currEdge, true) == aGraph.getTerminal(currEdge, false))
//			{
//				graph.removeCells(new Object[] { currEdge });
//			}
//		}
//
//		edges = graph.getChildEdges(parent);
//		Set<Set<Object>> vertexSet = new HashSet<Set<Object>>();
//		ArrayList<Object> duplicateEdges = new ArrayList<Object>();
//
//		for (int i = 0; i < edges.length; i++)
//		{
//			Object currEdge = edges[i];
//			Object source = aGraph.getTerminal(currEdge, true);
//			Object target = aGraph.getTerminal(currEdge, false);
//			Set<Object> currSet = new HashSet<Object>();
//			currSet.add(source);
//			currSet.add(target);
//
//			if (vertexSet.contains(currSet))
//			{
//				//we have a duplicate edge
//				duplicateEdges.add(currEdge);
//			}
//			else
//			{
//				vertexSet.add(currSet);
//			}
//		}
//
//		Object[] duplEdges = duplicateEdges.toArray();
//
//		graph.removeCells(duplEdges);
//	};

	/**
	 * Makes the graph connected
	 * @param aGraph
	 */
//	public static void makeConnected(mxAnalysisGraph aGraph)
//	{
//		// an early check, to avoid running getGraphComponents() needlessly, which is CPU intensive
//		if (mxGraphStructure.isConnected(aGraph))
//		{
//			return;
//		}
//
//		Object[][] components = getGraphComponents(aGraph);
//		int componentNum = components.length;
//
//		if (componentNum < 2)
//		{
//			return;
//		}
//
//		mxGraph graph = aGraph.getGraph();
//		Object parent = graph.getDefaultParent();
//
//		// find a random vertex in each group and connect them.
//		for (int i = 1; i < componentNum; i++)
//		{
//			Object sourceVertex = components[i - 1][(int) Math.round(Math.random() * (components[i - 1].length - 1))];
//			Object targetVertex = components[i][(int) Math.round(Math.random() * (components[i].length - 1))];
//			graph.insertEdge(parent, null, aGraph.getGenerator().getNewEdgeValue(aGraph), sourceVertex, targetVertex);
//		}
//	};

	/**
	 * @param aGraph
	 * @return Object[components][vertices] 
	 */
	public static Object[][] getGraphComponents(mxAnalysisGraph aGraph)
	{
		Object parent = aGraph.getGraph().getDefaultParent();
		Object[] vertices = aGraph.getChildVertices(parent);
		int vertexCount = vertices.length;

		if (vertexCount == 0)
		{
			return null;
		}

		ArrayList<ArrayList<Object>> componentList = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> unvisitedVertexList = new ArrayList<Object>(Arrays.asList(vertices));
		boolean oldDirectedness = mxGraphProperties.isDirected(aGraph.getProperties(), mxGraphProperties.DEFAULT_DIRECTED);
		mxGraphProperties.setDirected(aGraph.getProperties(), false);

		while (unvisitedVertexList.size() > 0)
		{
			//check if the current vertex isn't already in a component

			//if yes, just remove it from the unvisited list
			Object currVertex = unvisitedVertexList.remove(0);
			int componentCount = componentList.size();
			boolean isInComponent = false;

			for (int i = 0; i < componentCount; i++)
			{
				if (componentList.get(i).contains(currVertex))
				{
					isInComponent = true;
				}
			}

			//if not, create a new component and run a BFS populating the component and reducing the unvisited list
			if (!isInComponent)
			{
				final ArrayList<Object> currVertexList = new ArrayList<Object>();

				mxTraversal.bfs(aGraph, currVertex, new mxICellVisitor()
				{
					public boolean visit(Object vertex, Object edge)
					{
						currVertexList.add(vertex);
						return false;
					}
				});

				for (int i = 0; i < currVertexList.size(); i++)
				{
					unvisitedVertexList.remove(currVertexList.get(i));
				}

				componentList.add(currVertexList);
			}
		}

		mxGraphProperties.setDirected(aGraph.getProperties(), oldDirectedness);
		Object[][] result = new Object[componentList.size()][];

		for (int i = 0; i < componentList.size(); i++)
		{
			result[i] = componentList.get(i).toArray();
		}

		return (Object[][]) result;
	};



	/**
	 * @param graph
	 * @param parent
	 * @param properties
	 * @return A helper function for <b>isDirectedCyclic</b> and it isn't for general use. It returns a node that hasn't incoming or outgoing edges. It could be considered a "leaf" in a directed graph, but this definition isn't formal.
	 */
	public static Object getDirectedLeaf(mxAnalysisGraph aGraph, Object parent)
	{
		Object[] vertices = aGraph.getChildVertices(parent);
		int vertexNum = vertices.length;
		Object currVertex;

		for (int i = 0; i < vertexNum; i++)
		{
			currVertex = vertices[i];
			int inEdgeCount = aGraph.getEdges(currVertex, parent, true, false, false, true).length;
			int outEdgeCount = aGraph.getEdges(currVertex, parent, false, true, false, true).length;

			if (outEdgeCount == 0 || inEdgeCount == 0)
			{
				return currVertex;
			}
		}

		return null;
	};

	/**
	 * Makes the complement of <b>aGraph</b>
	 * @param aGraph
	 */
	public static void complementaryGraph(mxAnalysisGraph aGraph)
	{
		ArrayList<ArrayList<mxCell>> oldConnections = new ArrayList<ArrayList<mxCell>>();
		mxGraph graph = aGraph.getGraph();
		Object parent = graph.getDefaultParent();
		//replicate the edge connections in oldConnections
		Object[] vertices = aGraph.getChildVertices(parent);
		int vertexCount = vertices.length;

		for (int i = 0; i < vertexCount; i++)
		{
			mxCell currVertex = (mxCell) vertices[i];
			int edgeCount = currVertex.getEdgeCount();
			mxCell currEdge = new mxCell();
			ArrayList<mxCell> neighborVertexes = new ArrayList<mxCell>();

			for (int j = 0; j < edgeCount; j++)
			{
				currEdge = (mxCell) currVertex.getEdgeAt(j);

				mxCell source = (mxCell) currEdge.getSource();
				mxCell destination = (mxCell) currEdge.getTarget();

				if (!source.equals(currVertex))
				{
					neighborVertexes.add(j, source);
				}
				else
				{
					neighborVertexes.add(j, destination);
				}

			}

			oldConnections.add(i, neighborVertexes);
		}

		//delete all edges and make a complementary model
		Object[] edges = aGraph.getChildEdges(parent);
		graph.removeCells(edges);

		for (int i = 0; i < vertexCount; i++)
		{
			ArrayList<mxCell> oldNeighbors = new ArrayList<mxCell>();
			oldNeighbors = oldConnections.get(i);
			mxCell currVertex = (mxCell) vertices[i];

			for (int j = 0; j < vertexCount; j++)
			{
				mxCell targetVertex = (mxCell) vertices[j];
				boolean shouldConnect = true; // the decision if the two current vertexes should be connected

				if (oldNeighbors.contains(targetVertex))
				{
					shouldConnect = false;
				}
				else if (targetVertex.equals(currVertex))
				{
					shouldConnect = false;
				}
				else if (areConnected(aGraph, currVertex, targetVertex))
				{
					shouldConnect = false;
				}

				if (shouldConnect)
				{
					graph.insertEdge(parent, null, null, currVertex, targetVertex);
				}
			}

		}
	};

	/**
	 * @param aGraph - the graph to search
	 * @param value - desired value
	 * @return the first vertex with the wanted value. If none are found, null is returned
	 */
	public static Object getVertexWithValue(mxAnalysisGraph aGraph, int value)
	{
		mxGraph graph = aGraph.getGraph();

		Object[] vertices = aGraph.getChildVertices(aGraph.getGraph().getDefaultParent());

		int childNum = vertices.length;
		int vertexValue = 0;
		mxCostFunction costFunction = aGraph.getGenerator().getCostFunction();
		mxGraphView view = graph.getView();

		for (int i = 0; i < childNum; i++)
		{
			Object currVertex = vertices[i];

			vertexValue = (int) costFunction.getCost(new mxCellState(view, currVertex, null));

			if (vertexValue == value)
			{
				return currVertex;
			}
		}
		return null;
	};

	/**
	 * Sets the style of the graph to that as in GraphEditor
	 * @param aGraph
	 * @param resetEdgeValues - set to true if you want to re-generate edge weights
	 */
	public static void setDefaultGraphStyle(mxAnalysisGraph aGraph, boolean resetEdgeValues)
	{
		mxGraph graph = aGraph.getGraph();
		Object parent = graph.getDefaultParent();
		Object[] vertices = aGraph.getChildVertices(parent);
		mxIGraphModel model = graph.getModel();

		for (int i = 0; i < vertices.length; i++)
		{
			model.setStyle(vertices[i], basicVertexStyleString);
		}

		Object[] edges = aGraph.getChildEdges(parent);
		boolean isDirected = mxGraphProperties.isDirected(aGraph.getProperties(), mxGraphProperties.DEFAULT_DIRECTED);
		String edgeString = basicEdgeStyleString;

		if (isDirected)
		{
			edgeString = edgeString + basicArrowStyleString;
		}
		else
		{
			edgeString = edgeString + "endArrow=none";
		}

		for (int i = 0; i < edges.length; i++)
		{
			model.setStyle(edges[i], edgeString);
		}

		if (resetEdgeValues)
		{
			for (int i = 0; i < edges.length; i++)
			{
				model.setValue(edges[i], null);
			}

			for (int i = 0; i < edges.length; i++)
			{
				model.setValue(edges[i], aGraph.getGenerator().getNewEdgeValue(aGraph));
			}
		}
	};

	/**
	 * @param aGraph
	 * @return the regularity of the graph
	 * @throws StructuralException if the graph is irregular
	 */
	public static int regularity(mxAnalysisGraph aGraph) throws StructuralException
	{
		mxGraph graph = aGraph.getGraph();
		Object[] vertices = aGraph.getChildVertices(graph.getDefaultParent());
		int vertexCount = vertices.length;
		Object currVertex = vertices[0];
		int regularity = aGraph.getEdges(currVertex, null, true, true).length;

		for (int i = 1; i < vertexCount; i++)
		{
			currVertex = vertices[i];

			if (regularity != aGraph.getEdges(currVertex, null, true, true).length)
			{
				throw new StructuralException("The graph is irregular.");
			}
		}

		return regularity;
	};

	/**
	 * @param aGraph
	 * @param vertex
	 * @return indegree of <b>vertex</b>
	 */
	public static int indegree(mxAnalysisGraph aGraph, Object vertex)
	{
		if (vertex == null)
		{
			throw new IllegalArgumentException();
		}

		if (mxGraphProperties.isDirected(aGraph.getProperties(), mxGraphProperties.DEFAULT_DIRECTED))
		{
			return aGraph.getEdges(vertex, aGraph.getGraph().getDefaultParent(), true, false, true, true).length;
		}
		else
		{
			return aGraph.getEdges(vertex, aGraph.getGraph().getDefaultParent(), true, true, true, true).length;
		}
	};

	/**
	 * @param aGraph
	 * @param vertex
	 * @return outdegree of <b>vertex</b>
	 */
	public static int outdegree(mxAnalysisGraph aGraph, Object vertex)
	{
		if (mxGraphProperties.isDirected(aGraph.getProperties(), mxGraphProperties.DEFAULT_DIRECTED))
		{
			return aGraph.getEdges(vertex, aGraph.getGraph().getDefaultParent(), false, true, true, true).length;
		}
		else
		{
			return aGraph.getEdges(vertex, aGraph.getGraph().getDefaultParent(), true, true, true, true).length;
		}
	};

	/**
	 * @param aGraph
	 * @param vertex
	 * @return true if <b>vertex</b> is a cut vertex
	 */
	public static boolean isCutVertex(mxAnalysisGraph aGraph, Object vertex)
	{
		mxGraph graph = aGraph.getGraph();
		mxIGraphModel model = graph.getModel();

		if (aGraph.getEdges(vertex, null, true, true, false, true).length >= 2)
		{
			Object[] cells = model.cloneCells(aGraph.getChildCells(graph.getDefaultParent(), true, true), true);
			mxGraphModel modelCopy = new mxGraphModel();
			mxGraph graphCopy = new mxGraph(modelCopy);
			graphCopy.addCells(cells);
			mxAnalysisGraph aGraphCopy = new mxAnalysisGraph();
			aGraphCopy.setGraph(graphCopy);
			aGraphCopy.setGenerator(aGraph.getGenerator());
			aGraphCopy.setProperties(aGraph.getProperties());

			Object newVertex = getVertexWithValue(aGraphCopy,
					(int) aGraph.getGenerator().getCostFunction().getCost(new mxCellState(graph.getView(), vertex, null)));

			graphCopy.removeCells(new Object[] { newVertex }, true);
			Object[][] oldComponents = getGraphComponents(aGraph);
			Object[][] newComponents = getGraphComponents(aGraphCopy);

			if (newComponents.length > oldComponents.length)
			{
				return true;
			}
		}

		return false;
	};

	/**
	 * @param aGraph
	 * @return all cut vertices of <b>aGraph</b>
	 */
	public static Object[] getCutVertices(mxAnalysisGraph aGraph)
	{
		ArrayList<Object> cutVertexList = new ArrayList<Object>();
		Object[] vertexes = aGraph.getChildVertices(aGraph.getGraph().getDefaultParent());
		int vertexNum = vertexes.length;

		for (int i = 0; i < vertexNum; i++)
		{
			if (isCutVertex(aGraph, vertexes[i]))
			{
				cutVertexList.add(vertexes[i]);
			}
		}

		return cutVertexList.toArray();
	};

	/**
	 * @param aGraph
	 * @param edge
	 * @return true if <b>edge</b> is a cut edge of <b>aGraph</b> 
	 */
	

	/**
	 * @param aGraph
	 * @return all cut edges of <b>aGraph</b>
	
	/**
	 * @param aGraph
	 * @return all source vertices of <b>aGraph</b>
	 * @throws StructuralException the graph must be directed
	 */
	public static Object[] getSourceVertices(mxAnalysisGraph aGraph) throws StructuralException
	{
		if (!mxGraphProperties.isDirected(aGraph.getProperties(), mxGraphProperties.DEFAULT_DIRECTED))
		{
			throw new StructuralException("The graph is undirected, so it can't have source vertices.");
		}

		ArrayList<Object> sourceList = new ArrayList<Object>();
		Object[] vertices = aGraph.getChildVertices(aGraph.getGraph().getDefaultParent());

		for (int i = 0; i < vertices.length; i++)
		{
			Object currVertex = vertices[i];
			Object[] outEdges = aGraph.getEdges(vertices[i], null, false, true, true, true);
			Object[] inEdges = aGraph.getEdges(vertices[i], null, true, false, true, true);

			if (inEdges.length == 0 && outEdges.length > 0)
			{
				sourceList.add(currVertex);
			}
		}

		return sourceList.toArray();
	};

	/**
	 * @param aGraph
	 * @return all sink vertices of <b>aGraph</b>
	 * @throws StructuralException the graph must be directed
	 */
	public static Object[] getSinkVertices(mxAnalysisGraph aGraph) throws StructuralException
	{
		if (!mxGraphProperties.isDirected(aGraph.getProperties(), mxGraphProperties.DEFAULT_DIRECTED))
		{
			throw new StructuralException("The graph is undirected, so it can't have sink vertices.");
		}

		ArrayList<Object> sourceList = new ArrayList<Object>();
		Object[] vertices = aGraph.getChildVertices(aGraph.getGraph().getDefaultParent());

		for (int i = 0; i < vertices.length; i++)
		{
			Object currVertex = vertices[i];
			Object[] outEdges = aGraph.getEdges(vertices[i], null, false, true, true, true);
			Object[] inEdges = aGraph.getEdges(vertices[i], null, true, false, true, true);

			if (inEdges.length > 0 && outEdges.length == 0)
			{
				sourceList.add(currVertex);
			}
		}

		return sourceList.toArray();
	};

	/**
	 * @param aGraph
	 * @return true if <b>aGraph</b> is biconnected
	 */
	public static boolean isBiconnected(mxAnalysisGraph aGraph)
	{
		int edgeCount = aGraph.getChildEdges(aGraph.getGraph().getDefaultParent()).length;

		if (getCutVertices(aGraph).length == 0 && edgeCount >= 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	};
};
