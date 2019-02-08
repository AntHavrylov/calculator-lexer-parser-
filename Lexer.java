package Default;

import java.util.ArrayList;

public class Lexer {
	
public static void makeNegatives(ArrayList<Token> at) {
	
	for(int i =0; i < at.size();i++) {
		if(i==0&&at.get(i).getValue().equals("-")) {
			if(at.get(i+1).getType().equals("number")) {
				at.get(i+1).setValue("-"+at.get(i+1).getValue());
				at.remove(i);
			}
			
			else if(at.get(i+1).getType().equals("letter")) {
				/*if(Data.hasToken(at.get(i+1).getValue())) {
					Data.setValueOf(at.get(i+1).getValue(), (-1)*Data.getValueOf(at.get(i+1).getValue()));
					at.remove(i);
					Data.reSign();*/
				
					at.get(i+1).setValue("-"+at.get(i+1).getValue());
					at.remove(0);
					//System.out.println(at);
					Data.reSign();
				
			}
		}
		
		else if(i!=0&&at.get(i).getValue().equals("-")&&at.get(i-1).getValue().equals("=")) {
			if(at.get(i+1).getType().equals("number")) {
				at.get(i+1).setValue("-"+at.get(i+1).getValue());
				at.remove(i);
			}
			else if(at.get(i+1).getType().equals("letter")) {
				//Data.setValueOf(at.get(i+1).getValue(), (-1)*Data.getValueOf(at.get(i+1).getValue()));
				at.set(i+1, new Token("number",""+((-1)*Data.getValueOf(at.get(i+1).getValue()))));
				at.remove(i);
			}	
		}
		else if(i!=0&&at.get(i).getValue().equals("-")&&at.get(i-1).getValue().equals("(")) {
			if(at.get(i+1).getType().equals("number")) {
				at.get(i+1).setValue("-"+at.get(i+1).getValue());
				at.remove(i);
			}
				
			else if(at.get(i+1).getType().equals("letter")) {
				Data.setValueOf(at.get(i+1).getValue(), (-1)*Data.getValueOf(at.get(i+1).getValue()));
				at.remove(i);
			}
				
		}		
	}
}	
	
public static ArrayList<Token> makeTokenList(String line) {
		
		ArrayList<Token> lexed = new ArrayList<Token>();
		int index=0;
		int chInd=0;		
		
		while(chInd<line.length()){
			
			char ch = line.charAt(chInd);
			char chNext = '$';
			
			
			String status = null;
			String statusNext = null;
			
			if(chInd<line.length()-1) {
				chNext = line.charAt(chInd+1);
			}
			
			//definition of indexes
			if(ch>=97&&ch<=122)		//{a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z}
				status = "letter";
			else if(ch>=48&&ch<=57) //{0,1,2,3,4,5,6,7,8,9}
				status = "number";
			else if(ch==42||ch==47)	//{*,/}
				status = "factor";
			else if(ch==43||ch==45)	//{+,-}
				status = "term";
			else if(ch==32)			//{space}
				status = "space";
			else if(ch==59)			//{;}
				status = "endl";
			else if(ch==61)			//{=}
				status = "identifier";
			else if(ch=='(')
				status = "lparen";
			else if(ch==')')
				status = "rparen";
			else 
				status = "error";
			
			if(chNext>=48&&chNext<=57) //{0,1,2,3,4,5,6,7,8,9}
				statusNext = "number";
			
			
			//check multiple-digit tokens
			if(status.equals(statusNext) && status=="number")
				status = "twoNumbs";
		
			switch(status) {
				case("endl"):
					Token end = new Token("endl",""+ch);
					lexed.add(end);
					chInd++;
					index++;
					break;
					
				case("letter"):
					Token let = new Token("letter",""+ch); 
					lexed.add(let);
					chInd++;
					index++;
					break;
					
				case("number"):
					if(lexed.size()==index) {
						Token num = new Token("number",""+ch);
						lexed.add(num);
						chInd++;
						index++;
					}
					else {
						lexed.get(index).adValue(""+ch);
						chInd++;
						index++;
					}
					break;
					
				case("twoNumbs"):
					if(lexed.size()==index) {
						Token num = new Token("number",""+ch);
						lexed.add(num);
						chInd++;
					}
					else {
						lexed.get(index).adValue(""+ch);
						chInd++;
					}
					break;
					
				case("term"):
					Token trm = new Token("term",""+ch); 
					lexed.add(trm);
					chInd++;
					index++;
					break;
					
				case("factor"):
					Token fct = new Token("factor",""+ch); 
					lexed.add(fct);
					chInd++;
					index++;
					break;
					
					case("identifier"):
					Token idnt = new Token("identifier",""+ch);
					lexed.add(idnt);
					chInd++;
					index++;
					break;
					
				case("space"):
					Token spc = new Token("space",""+"space");
					lexed.add(spc);
					chInd++;
					index++;
					break;
					
				case("lparen"):
					Token lp = new Token("lparen",""+ch);
					lexed.add(lp);
					chInd++;
					index++;
					break;
					
				case("rparen"):
					Token rp = new Token("rparen",""+ch);
					lexed.add(rp);
					chInd++;
					index++;
					break;
					
				case("error"):
					System.out.printf("Out of alphabet: '%c' is a bad symbol,token will not create.\n",ch);
					chInd++;
					break;
				
				default:
						
			}	
		}
		
		//remove all spaces
		for(int i = lexed.size()-1; i >= 0; i--) {
			if(lexed.get(i).getType() == "space") {
				lexed.remove(i);
				//System.out.println("token "+ i + " removed");
			}
		}
		
		makeNegatives(lexed);
		//System.out.println("lexed: "+lexed);
		return lexed;
	}

}
