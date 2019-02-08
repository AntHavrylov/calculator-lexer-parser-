package Default;

public class Token {
	
	private String type;
	private String value;
	public Token() {}
	public Token(String type,String value){
		this.type = type;
		this.value = value;
	}
	
	
	//setters
	void adValue(String s) {
		this.value = this.value+s;
	}
	void setValue(String v) {
		this.value = v;
	}
	
	void setType(String t) {
		this.type = t;
	}
	
	 //getters
	String getType() {
		return this.type;
	}
	String getValue() {
		return this.value;
	}
	public String toString() {
		return String.format("type: '%s' | value: '%s'\n",this.type,this.value);
	}
}
