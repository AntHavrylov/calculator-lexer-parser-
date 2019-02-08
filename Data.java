package Default;


import java.util.ArrayList;

public class Data {
	
	public static ArrayList<Data> dataList = new ArrayList<Data>();
	String name;
	int value;

	
	
	//Constructor Data
	Data(String name,int value){
		this.name = name;
		this.value = value;
	}
	
	//Setup format to print object Data
	public String toString() {
		return String.format("%s = %d",name,value);
	}
	
	//Method returns Token with name 's' in dataList
	public static Token getToken(String s) {
		boolean founded = false;
		
		for(int i = 0 ; i < dataList.size() ; i ++ ) {
			if(dataList.get(i).name.equals(s)) {
				founded = true;
				return new Token("number",""+dataList.get(i).value);
			}
		}
		if(!founded) {
			System.out.println("Value '"+s + "' didn't found in data will be returned value equals to 0");
		}
		return new Token("number",""+0);
		
	}
	
	
	/*
	 * This method looks in dataList for Tokens with name that include sign minus
	 * for example "-$" | $ - name of Token
	 * changes name to "$" and change sign of value 
	 * */
	public static void reSign() {
		//System.out.println("Data before resign: " + dataList);
		for(int i = 0 ; i < dataList.size() ; i++) {
			//there is char '-$'
			if(dataList.get(i).name.charAt(0)=='-') {
				Data n = new Data(""+dataList.get(i).name.charAt(1),dataList.get(i).value*(-1));
				dataList.remove(i);
				if(hasToken(n.name)) {
					int ind = getIndex(n.name);
					dataList.remove(ind);
				}
				dataList.add(n);
			}			
		}
		//System.out.println("Saved data: " + dataList);
	}
	
	// This method check's if there is a Token with name 's' in dataList
	public static boolean hasToken(String s) {
		boolean founded=false;
		for(int i = 0 ; i < dataList.size() ; i ++ ) {
			if(dataList.get(i).name.equals(s)) {
				
				founded = true;
			}
		}
		return founded;
	}
	
	//Method returns index of Token with name 's' in dataList
	public static int getIndex(String s) {
		int index=-1; 
		for(int i = 0 ; i < dataList.size() ; i ++ ) {
			if(dataList.get(i).name.equals(s)) {
				
				index =i;
			}
		}
		return index;
	}
	
	//Method returns value of Token with name 's' in dataList
	public static int getValueOf(String s) {
		boolean founded = false;
		int result=0;
		for(int i = 0 ; i < dataList.size() ; i ++ ) {
			if(dataList.get(i).name.equals(s)) {
				result = dataList.get(i).value;
				founded = true;
			}
		}
		if(!founded) {
			System.out.println(s + " didn't found in data will be returned value equals to 0");
		}
		return result;
	}
	
	//Value setter for specific Data
		public void setValue(int n) {
			this.value = n;
	}
		
	
	//Method sets value for Token with name 's' in dataList
	public static void setValueOf(String s,int v) {
		boolean founded = false;
		
		for(int i = 0 ; i < dataList.size() ; i ++ ) {
			if(dataList.get(i).name.equals(s)) {
				dataList.get(i).setValue(v);
				founded = true;
			}
		}
		if(!founded) {
			System.out.println(s + " didn't found in data will be returned value equals to 0");
		}
	}	
}
