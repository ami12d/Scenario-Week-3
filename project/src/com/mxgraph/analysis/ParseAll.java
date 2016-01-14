package com.mxgraph.analysis;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

public class ParseAll {

    public static ParserData printAll(mxAnalysisGraph aGraph) {
        ParserData objects = new ParserData();
        mxGraph graph = aGraph.getGraph();
        Object parent = graph.getDefaultParent();
        Object[] vertices = aGraph.getChildVertices(parent);
        for (Object curr : vertices) {
            mxCell current = (mxCell) curr;
            int edgeCount = current.getEdgeCount();
            mxCell currEdge;
            for (int j = 0; j < edgeCount; j++) {
                currEdge = (mxCell) current.getEdgeAt(j);
                String source = (currEdge.getSource()).getId();
                String target = (currEdge.getTarget()).getId();
                objects.addWire(source, target);
            }

            objects.addComponent(current.getId(), current.getStyle(), (String) current.getValue());

        }
        return objects;
    }

    public static void main(String[] args) {
        //ParserData results = printAll();
        //calculate(results.getComponents(), results.getWires());
    }
}
