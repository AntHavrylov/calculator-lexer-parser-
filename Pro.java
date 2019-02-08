package Default;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * This is main class of program
 * */
public class Pro {
	
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		String line="";
		System.out.println("Greating you in Lexer&Parser.");
		System.out.println("This program will calculate mathimatic expressions.\n"
				+"All expressions types must be \"a=$;($ - is math. expression(s) or number(s))");
		System.out.println("For exit enter 'END'.\n");
				
		while(!line.equals("END")) {
			System.out.println("Enter expression:");
			line = sc.nextLine() ;
			
			if(line.equals("")) { /*empty expression*/
				System.out.println("Input can't be empty.");
			}else { /*expression is'nt empty*/
				//if last char of input is ';' need some addition char for split without errors 
				if(line.charAt(line.length()-1)==';') {
					line = line + " "; //addition char
				}
				//question for exit
				if(line.equals("END")) {
					String yup = "";
					while(!(yup.equals("Y")||yup.equals("N"))) {
						System.out.println("Enter 'Y' to exit or 'N' to continue work.");
						yup = sc.nextLine();
						if(yup.equals("N")) {
							line="";
						}
					}
				}
				
				if(!line.equals("END")&&!line.equals("")) {
					//divide input for expressions
					String commands[] = line.split(";");
					
					/*
					 * Every expression reduces to the required form 
					 * sends to method and turning to list of tokens 
					 * list of tokens sends to parser that will save of print results
					 */
					
					for(int i = 0 ; i < commands.length-1 ; i++ ) {
						commands[i] = commands[i] + ";";
						//System.out.println(commands[i]);
						ArrayList<Token> command = Lexer.makeTokenList(commands[i]);
						Parser.parce(command);
					}
					//if input isn't correct print error message
					if(!commands[commands.length-1].equals(" ")) {
						System.out.println("\""+ commands[commands.length-1] + "\" isn't correct expression, will not be parsed.");
					}
					//display all saved data
					System.out.println("Saved data: " + Data.dataList);
				}
			}
		}
		//if input = 'END' -> printGoodbuy message and end program
		System.out.println("GoodBye!");
	}
}
