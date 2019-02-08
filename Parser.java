package Default;

import java.util.ArrayList;

public class Parser {
	public static boolean hasError;
	
	
	/*
	 * This method check expression for errors
	 * */
	public static void chekError(ArrayList<Token> lt) {
		//actual Token to check
		Token actual;
		//next of actual Token to check
		Token next;
		//Show existation of brackets in expression
		boolean hasBracket=false;
		
		//size check
		if(lt.size()==1) {
			hasError = true;
		}
		
		
		
		//bracket counter
		int bracketCount=0;
		for(int i = 0 ; i < lt.size();i++) {
			if(lt.get(i).getValue().equals("(")) {
				bracketCount++;
			}else if(lt.get(i).getValue().equals(")")) {
				bracketCount--;
			}
		}
		
		//end of expression must be ';'
		if((lt.size()>2) && (!lt.get(lt.size()-1).getValue().equals(";"))) {
			hasError = true;
			//System.out.println("End of expression must be ';'.");
		}
		//if Token have "--" , signs "--" will be deleted
		for(int i = 0 ; i < lt.size()-1 ; i++) {
			actual = lt.get(i);
			next = lt.get(i+1);
			//System.out.println("actual "+actual);
			if(actual.getValue().length() > 1) {
				if(actual.getValue().charAt(0)=='-'&&actual.getValue().charAt(1)=='-') {
					String tmp = actual.getValue().substring(2, actual.getValue().length());
					actual.setValue(tmp);
				}
			}
			//length more then 2 tokens
			if(lt.size()>2){
				
				//if value of first token is number && second token isn't operator appropriation
				if((lt.get(0).getType().equals("number"))&&(lt.get(0).getType().equals("="))) {
					System.out.println("Wrong expresion.");
					hasError = true;
				}
				
				else if(
						lt.get(0).getValue().equals("+")
						||lt.get(0).getValue().equals("*")
						||lt.get(0).getValue().equals("/")
						||lt.get(0).getValue().equals("=")
						) {
					hasError = true;
				}
				
				if(lt.get(lt.size()-1).getType().equals("factor")||lt.get(lt.size()-1).getType().equals("term")) {
					hasError=true;
				}
				
				//first and last token is binary action
				if( 
					(((i==2&&actual.getValue().equals("+"))
					||(i==2&&actual.getType().equals("factor"))
					||((i==(lt.size()-2)&&actual.getType().equals("term"))
					||(i==(lt.size()-2))&&actual.getType().equals("factor"))))
					//&&(lt.get(i-2).getValue().equals("="))
					
						) {
						hasError = true;
						System.out.println("First or last token can't be binary operation.");
				}
				else if(actual.getType().equals("number")&&(next.getValue().equals("="))){
					hasError = true;
					//System.out.println("Wrong expression.");
				}
				// two tokens of one type
				else if(actual.getType().equals(next.getType())) {
					if(!(actual.getValue().equals("(")||actual.getValue().equals(")"))) {
						hasError = true;
						System.out.println("Can't be two onetype tokens beside.");
					}
				}
				//term and factor operators beside
				else if((actual.getType().equals("term") && next.getType().equals("factor"))
					||(actual.getType().equals("factor") && next.getType().equals("term"))) {
					hasError = true;
					System.out.println("Term and factor operators can't stay beside.");
				}
				//letter type and number type tokens
				else if((actual.getType().equals("number")&&next.getType().equals("letter"))
					||(actual.getType().equals("letter")&&next.getType().equals("number"))) {
					hasError = true;
					System.out.println("Can't be letter type and number type tokens beside.");
				}
				//'n(' or ')n' | n - number
				else if((actual.getType().equals("number")&&next.getType().equals("lparen"))
					||(actual.getType().equals("rparen")&&next.getType().equals("number"))) {
					hasError = true;
					System.out.println("Expression can't have part 'n(' or ')n' | n - number");
				}
				//'c(' or ')c' | c - letter
				else if((actual.getType().equals("letter")&&next.getType().equals("lparen"))
					||(actual.getType().equals("rparen")&&next.getType().equals("letter"))) {
					hasError = true;
					System.out.println("Expression can't have part 'c(' or ')c' | c - letter");
				}
				//'($' or '$)' | $ - binary operation
				else if((actual.getType().equals("lparen")&&next.getType().equals("term"))
					||(actual.getType().equals("term")&&next.getType().equals("rparen"))
					||(actual.getType().equals("lparen")&&next.getType().equals("factor"))
					||(actual.getType().equals("factor")&&next.getType().equals("rparen"))) {
					hasError = true;
					System.out.println("Expression can't have part '($' or '$)' | $ - binary operation");
				}
				//bracket counter isn't 0 -> number of left brackets isn't equal to number right brackets
				else if(bracketCount!=0) {
					hasError = true;
					System.out.println("Number of left brackets isn't equal to number right brackets");
					bracketCount = 0;
				}
				//check for existing brackets in list of tokens
				else if(actual.getValue().equals("(")) {
						hasBracket=true;
				//check for open bracket before closing bracket
				}else if(actual.getValue().equals(")")&&!hasBracket) {
					hasError = true;
					System.out.println("Have to be open bracket before closing bracket.");
					bracketCount=0;
				}
							
				
			}
		}
	}
	
	
	/*public static Token binAction(Token num1,Token act,Token num2) {
		Token t = new Token();	
		if(act.getType().equals("term"))
			t=binTerm(num1,act,num2);
		else 
			t=binFactor(num1,act,num2);
		
		return t;
	}*/
	
	
	/*
	 * Method makes "+" or "-" between tokens
	 * */
	public static Token binTerm(Token num1,Token term,Token num2){
		
		Token t = new Token();
		
		//get values of variables if it's need
		if(num1.getType().equals("letter"))
			num1 = Data.getToken(num1.getValue());
		if(num2.getType().equals("letter"))
			num2 = Data.getToken(num2.getValue());
		
		//"-" between tokens and write down result to Token 't'
		if(term.getValue().equals("-")) {
			t.setType("number");
			t.setValue(""+(Integer.parseInt(num1.getValue())-Integer.parseInt(num2.getValue())));
		}
		//"+" between tokens and write down result to Token 't'
		else {
			t.setType("number");
			t.setValue(""+(Integer.parseInt(num1.getValue())+Integer.parseInt(num2.getValue())));
		}
		//return result of expression
		return t;
	}
	
	/*
	 * Method makes "*" or "/" between tokens
	 * */
	public static Token binFactor(Token num1,Token fact,Token num2) {
		Token t = new Token();
		
		//get values of variables if it's need
		if(num1.getType().equals("letter"))
			num1 = Data.getToken(num1.getValue());
		if(num2.getType().equals("letter"))
			num2 = Data.getToken(num2.getValue());
		
		//"*" between tokens and write down result to Token 't'
		if(fact.getValue().equals("*")) {
			t.setType("number");
			t.setValue(""+(Integer.parseInt(num1.getValue())*Integer.parseInt(num2.getValue())));
		}
		//"divide" between tokens and write down result to Token 't'
		else {
			if(!num2.getValue().equals("0")) {
				//hasError = true;
				t.setType("number");
				t.setValue(""+(Integer.parseInt(num1.getValue())/Integer.parseInt(num2.getValue())));
			}
			//divide by zero error message
			else{
				hasError = true;
				System.out.println("Can't divide by zero");
				t = new Token("number","0");
			}
		}
		return t;
	}
	
	/*
	 * This processes expressions 
	 * add to database
	 * calculate expressions
	 * */
	public static void parce(ArrayList<Token> lt) {
		hasError = false;
		
		//System.out.println("Entered to parser: \n" + lt + "\n size: " + lt.size());
		 
		
		
		//calling method to check for errors every cycle
		chekError(lt);
		//if errors don't exists
		if(!hasError) {
			
			/*
			 * length of token list bigger than 4 - need to make shorter
			 * need to take tokens without first to tokens : variable and operator appropriation
			 * and send it to computing function, that will makes all operations and will return
			 * one token with result
			 */
			if(lt.size()>4) {
				ArrayList<Token> sub = new  ArrayList<Token>();
				
				if(lt.get(1).getValue().equals("=")) {				
					for(int i = 2 ; i < lt.size()-1 ; i++ ) {
						sub.add(lt.get(i));
					}
					for(int i = lt.size()-1;i>=2;i--) {
						lt.remove(i);
					}
					lt.addAll(2,computing(sub));
					lt.add(new Token("endl",";"));
				}else if (!lt.get(1).getValue().equals("=")) {
					
					for(int i = 0 ; i < lt.size()-1 ; i++ ) {
						sub.add(lt.get(i));
					}
					for(int i = lt.size()-1;i>=0;i--) {
						lt.remove(i);
					}
					lt.addAll(0,computing(sub));
					lt.add(new Token("endl",";"));
					
					
				}
				parce(lt);	
				
			}
			//write data to dataList
			
				//if value of second token is operator appropriation
			else if((lt.get(0).getType().equals("letter"))&&(lt.size()==4)&&(lt.get(1).getValue().equals("="))) {
					if(lt.get(2).getType().equals("letter")){
							lt.set(2, Data.getToken(lt.get(2).getValue()));
					}
					
					if(Data.hasToken(lt.get(0).getValue())) {
						Data.setValueOf(lt.get(0).getValue(), Integer.parseInt(lt.get(2).getValue()));
						//System.out.println("Saved data: "+Data.dataList);
						Data.reSign();
					}else {
						Data d = new Data(lt.get(0).getValue(),Integer.parseInt(lt.get(2).getValue()));
						Data.dataList.add(d);
						//System.out.println("Saved data: "+Data.dataList);
						Data.reSign();
					}
					
			}
			
			else if( lt.size()==4&&lt.get(0).getType().equals("number")) {
				lt.remove(lt.size()-1);
				System.out.println(computing(lt).get(0).getValue());
			}
			//if value of second token isn't operator appropriation
			else if((lt.get(0).getType().equals("letter"))&&(lt.size()==4)&&(!lt.get(1).getValue().equals("="))) {
				lt.remove(lt.size()-1);
				System.out.println(computing(lt).get(0).getValue());
			}
			
			
			//read data from dataList
			else if(lt.size()==2&&lt.get(0).getType().equals("letter")) {
				boolean has = false;
				
				if(lt.get(0).getValue().charAt(0)=='-') {
					for (int i=0;i<Data.dataList.size();i++) {
						if((""+lt.get(0).getValue().charAt(1)).equals(Data.dataList.get(i).name)) {
							
							System.out.println((-1)*Data.dataList.get(i).value);
							has = true;
						}
					}
				}else {
					for (int i=0;i<Data.dataList.size();i++) {
						if(lt.get(0).getValue().equals(Data.dataList.get(i).name)) {
							
							System.out.println(Data.dataList.get(i));
							has = true;
						}
					}
				}
				
				
				
				if(!has) {
					System.out.println("Have not value with name "+lt.get(0).getValue());
				}
				
			}else if((lt.size()==2&&lt.get(0).getType().equals("number"))) {
				System.out.println(lt.get(0).getValue());
			}
			
			
			
		}
		else {
			System.out.println("There are error(s) in expression, try again.");
		}
		
	}
	
	/*
	 * Method that make calculations
	 * */
	public static ArrayList<Token> computing(ArrayList<Token> slt) {
		if(!hasError) {
			//call function that makes all fix all '-$'($ name) to '$'
			Lexer.makeNegatives(slt);
			
			ArrayList<Token> subList = new ArrayList<Token>();
			
			//stop point of recursion 
			if(slt.size()==1) {
				return slt;
			}
			//operations to make shorter expression
			else {
				
				int startSub = 0;	// start bracket index
				int endSub = 0;		// end bracket index
			//boolean hasParen = false;
				for(int i=0; i < slt.size();i++) {
					
					if(slt.get(i).getValue().equals("(")) {
						startSub = i;
						//hasParen = true;
					}
					else if(slt.get(i).getValue().equals(")")) {
						endSub = i;
					//	hasParen = true;
						//create sub expression between brackets = sub-expression
						for(int j = startSub+1 ; j < endSub ; j++) {
							subList.add(slt.get(j));
						}
						//System.out.println("sub is: "+subList);
						
						//send sub-expression to calculate and put result in position of '('
						slt.set(startSub,computing(subList).get(0));	
						//removing sub-expression
						for(int k = endSub;k >= startSub+1;k--)	{		
							slt.remove(k);
						}
					computing(slt);
					}
					
				}
				//on factor expression put to token result of operation and remove variables to calculate 
				for(int i =0 ; i < slt.size();i++) {
					if(slt.get(i).getValue().equals("*")||slt.get(i).getValue().equals("/")) {
						slt.set(i,binFactor(slt.get(i-1),slt.get(i),slt.get(i+1)));
						slt.remove(i+1);
						slt.remove(i-1);
						i=0;
						
					}
				}
				
				//on term expression put to token result of operation and remove variables to calculate
				for(int i =0 ; i < slt.size();i++) {
					if(slt.get(i).getValue().equals("-")||slt.get(i).getValue().equals("+")) {
						slt.set(i,binTerm(slt.get(i-1),slt.get(i),slt.get(i+1)));
						slt.remove(i+1);
						slt.remove(i-1);
						i=0;
					}
				}
			}
		  return computing(slt);	//sending expression with changes to function again
		}
		
		//slt.clear();
		//slt.add(new Token("number","0"));
		
		return slt;
		
	}
	
	
}
