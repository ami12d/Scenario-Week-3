package com.mxgraph.analysis;

import java.util.ArrayList;

public class Parser {

	String data;
	ArrayList<Component> list;

	public Parser (String data){
		this.data=data;
	}

	private class Combined{
		String returned;
		int position;

		public Combined (String a, int b){
			returned = a;
			position=b;
		}

		public String getString() {return returned;}
		public int getPosition() {return position;}
	}

	public Boolean isNumber(char i){
		if (i >= '0' && i <= '9') return true;
		else return false;
	}
	
	public void change (Component x, String toChange){
	    int location = data.indexOf(x.value);
	    //System.out.print (location + " " + data.charAt(location));// + " " + current.charAt(location) + current.charAt(location+1));
	    //location-=1;
		StringBuilder edited = new StringBuilder(data);
	    int length = x.value.length();
	    x.setValue(toChange);
	    System.out.println(length);
	    edited.delete(location, location+length);
	    edited.insert(location, toChange);
	    data=edited.toString();
		//return x;
	}

	public Combined find (String toFind, int begin){
		begin= data.indexOf(toFind, begin);
		if (begin==-1) return null;
		begin=data.indexOf("\"", begin);
		begin+=1;
		if(data.charAt(begin)=='\"') return new Combined("0",begin);
		else {
			char current = data.charAt(begin);
			StringBuilder value = new StringBuilder();
			while (current!='\"'){
				value.append(current);
				begin+=1;
				current = data.charAt(begin);
			}
			return new Combined(value.toString(), begin);
		}

	}

	public ArrayList<Component> getComponents(){
		ArrayList<Component> components = new ArrayList<Component>();
		if (data.isEmpty()) return components;
		String[] toLook = {"id","style","value"};
		int location = 0;
		location = data.indexOf("<mxCell");
		location+=1;
		location = data.indexOf("<mxCell", location);
		location+=1;
		location = data.indexOf("<mxCell", location);
		while (location != -1){
			//System.out.println(location);
			String id = "0";
			String style="0", value="0";
			int wire = 0;
			for (String a:toLook){
				//System.out.println(x);
				if (data.indexOf("edge",location)<location+10) wire=1;
				Combined temp = find(a, location);
				if (temp == null) return components;
				location=temp.getPosition();
				String returned = temp.getString();
				//System.out.println(location + " " + returned + " " + x);
				if(a.compareTo("id")==0) id = returned;
				else if (a.compareTo("style")==0) style=returned;
				else  value = returned;
				//else if (a.compareTo("x")==0) x = returned;
				//else y = returned;
			}
			Component temp = new Component(id, style, value);
			components.add(temp);
			location = data.indexOf("<mxCell", location);
		}
		return components;
	}

	public void getData(){
		list = getComponents();
		//for (Component a:temp) a.print();
		//temp.set(1,
		//change(temp.get(1),"On");
		
	}

	public void printComponents(){
		if (list!=null){
			for (Component a:list) a.print();
		}
	}
	
	public String getString(){return data;}
	
	public void findAndReplace(String a, String tochange){
		int index=-1;
		for (Component x:list){
			if (x.style.contains(a)) {
				index=list.indexOf(x);
				break;
			}
		}
		if (index==-1) return;
		change(list.get(index), tochange);
		printComponents();
		//System.out.println(data);
	}
	private ArrayList<Integer> getLocations (String toFind){
		ArrayList<Integer> locations = new ArrayList<Integer>();
		int location = data.indexOf(toFind);
		while(location!=-1){
			locations.add(location);
			location = data.indexOf(toFind, location);
		}
		return locations;
	}

}
