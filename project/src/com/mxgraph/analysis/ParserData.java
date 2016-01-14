package com.mxgraph.analysis;

import wirecalculator.ParsedComponent;
import wirecalculator.ParsedWire;

import java.util.ArrayList;

public class ParserData {

	ArrayList<ParsedComponent> list = new ArrayList<>();
	ArrayList<ParsedWire> wires = new ArrayList<>();

	public void addComponent(String id, String style, String value){
		ParsedComponent temp = new ParsedComponent(style, value, Integer.valueOf(id));
		list.add(temp);
}
	public void addWire (String source, String target){
		ParsedWire temp = new ParsedWire(Integer.valueOf(source), Integer.valueOf(target));
        for(ParsedWire wire : wires) {
            if(wire.isEquals(temp)) { return; }
        }
		wires.add(temp);
	}

    public void print() {
        for(ParsedComponent pc : list){
            System.out.println("ID = " + pc.getID());
            System.out.println("Style = " + pc.getStyle());
            System.out.println("Value = " + pc.getValue());
        }
        for(ParsedWire pw : wires){
            System.out.println("Source = " + pw.getSource());
            System.out.println("Target = " + pw.getTarget());
        }
    }

	public ArrayList<ParsedComponent> getComponents(){return list;}
	public ArrayList<ParsedWire> getWires(){return wires;}
}
