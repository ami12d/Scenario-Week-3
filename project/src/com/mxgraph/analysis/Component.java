package com.mxgraph.analysis;

public class Component {
	
	String id;
	String style, value;//, x, y;

	Component(String id, String style,String value){
		this.id=id;
		this.style=style;
		this.value=value;
		
	}

	public String getID(){return id;}
	public String getStyle(){return style;}
	public String getValue(){return value;}
	//public String getX(){return x;}
	//public String getY(){return y;}

	public void setValue(String x){value=x;}
	public void print(){
		System.out.println("The ID is " + id + " the style is " + style + " the value is " + value); //+ " x is " + x + " y is " + y);
	}

}
