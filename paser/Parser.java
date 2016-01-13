package paser;

import java.util.ArrayList;

public class Parser {

	String data;

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
		String[] toLook = {"id","style","value", " x", " y"};
		int location = 0;
		while (location != -1){
			//System.out.println(location);
			String id = "0";
			String style="0", value="0", x="0", y="0";
			for (String a:toLook){
				//System.out.println(x);
				Combined temp = find(a, location);
				if (temp == null) return components;
				location=temp.getPosition();
				String returned = temp.getString();
				System.out.println(location + " " + returned + " " + x);
				if(a.compareTo("id")==0) id = returned;
				else if (a.compareTo("style")==0) style=returned;
				else if (a.compareTo("value")==0) value = returned;
				else if (a.compareTo("x")==0) x = returned;
				else y = returned;
			}
			Component temp = new Component(id, style, value, x, y);
			components.add(temp);
			//location = data.indexOf("<mxCell", location) + 6;
		}
		return components;
	}

	public void getAndPrint(){
		ArrayList<Component> temp = getComponents();
		for (Component a:temp) a.print();
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
