/* <*** 
 __code by __ : Kailas.M
  Email       : kailasmanivannan@gmail.com
  github      : https://github.com/kailasmanivannan
 ***> */

import java.io.*;
import java.util.*;
import java.time.LocalDate;
public class Todo {

	static public void help(){
		System.out.print("Usage :-\n");
		System.out.print("$ ./todo add \"todo item\"  # Add a new todo\n"); 
		System.out.print("$ ./todo ls               # Show remaining todos\n");
		System.out.print("$ ./todo del NUMBER       # Delete a todo\n");
		System.out.print("$ ./todo done NUMBER      # Complete a todo\n");
		System.out.print("$ ./todo help             # Show usage\n");
		System.out.print("$ ./todo report           # Statistics\n");
	}

	public static void main(String args[]){
		// variable area   
	
		 File tobj = new File("./todo.txt");    // open file in current directory ( if not create it)
		 File cobj = new File("./done.txt");	// open file in current directory ( if not create it)
		 try{
			if(!tobj.exists()){tobj.createNewFile( );}
		 	if(!cobj.exists()){cobj.createNewFile( );}
		 }
		 catch(Exception ex){
			 System.out.println("error");
		 }
		 
	     LocalDate date = LocalDate.now();
		 int num = 0,count = 0,i,temp;
		 String line,mline=" ";
		 ArrayList<String> ar1 = new ArrayList<String> ();
		 ArrayList<String> ar2 = new ArrayList<String> ();

		//variable area

		//driver code

		try{
			if(args.length==0){         // if no args are given 
				help();
			}
			else if(args[0].equals("add")){           // add region

				/** add arg missing  **/
				if(args.length==1){System.out.print("Error: Missing todo string. Nothing added!");System.exit(0);}
				/**  add arg missing **/

				String item = args[1];
				try{
					FileWriter obj = new FileWriter(tobj,true);    // opening file in append mode
					obj.write(item);
					obj.write(System.getProperty("line.separator"));
					obj.close();
					System.out.println("Added todo: "+"\""+item+"\"");
				}
				catch(Exception ex){
					// do nothing 
				}
			}
			else if(args[0].equals("ls")){       // ls region
				int dis;                                            // s.no indicator
				ArrayList<String> items = new ArrayList<String>();  // for storing the todo_items
				try{
					BufferedReader obj = new BufferedReader ( new FileReader(tobj));
					while((line=obj.readLine())!=null){
						items.add(line);
					}
					obj.close();

					/***  empty todo file ***/
					if(items.size()==0){System.out.print("There are no pending todos!\n");System.exit(0);}
					/***  empty todo file ***/
					
					for(i = items.size()-1;i>=0;i--){
						dis=i+1;
						System.out.print("["+dis+"] "+items.get(i)+"\n");
					}
				}
				catch(Exception ex){
					// do nothing
				}
			}
			else if(args[0].equals("help")){   // help region
				help();
			}
			else if(args[0].equals("del")){     // del region
				count = 0;

				/** del arg missing  **/
				if(args.length==1){System.out.print("Error: Missing NUMBER for deleting todo.");System.exit(0);}
				/** del arg missing **/

				//(start)count the no.of.lines
				try 
				{
					BufferedReader obj = new BufferedReader ( new FileReader(tobj));
					while((line=obj.readLine())!=null){
						count++;
					}
					obj.close();
				}
				catch(Exception ex) { // do nothing
				}
				//(end) count the no.of.lines

				num=Integer.parseInt(args[1]);
				if(num>count||num==0)    // wrong input
					System.out.println("Error: todo #"+args[1]+ " does not exist. Nothing deleted.");
				else{					// valid input
					System.out.println("Deleted todo #"+args[1]);
					temp = Integer.parseInt(args[1]);

					/****  copy the text to ar1 and ar2 excluding the del line.  ****/
					try{
						count = 0;
						BufferedReader obj = new BufferedReader ( new FileReader(tobj));
						while((line=obj.readLine())!=null){
							count++;
							if(count<temp){
								ar1.add(line);
							}
							else if(count>temp){
								ar2.add(line);
							}
						}
						obj.close();
					/****  copy the text to ar1 and ar2 excluding the del line.  ****/

					/****  updating todo.txt   ****/
						FileWriter fout = new FileWriter (tobj,false);
						for(i = 0; i < ar1.size();i++){               // writing first set of strings
							line = ar1.get(i);
							fout.write(line);
							fout.write(System.getProperty("line.separator"));
						}
						for(i = 0;i < ar2.size();i++){
							line = ar2.get(i);
							if(i==ar2.size()-1) {fout.write(line); }  // no newline after last line 
							else{
								fout.write(line);
								fout.write(System.getProperty("line.separator"));
							}
						}
						fout.close();
					}
					catch(Exception ex){// do nothing
					}
					/****  updating todo.txt   ****/
				}
			}
			else if(args[0].equals("done")){    //  done part
				count = 0;

				/** done arg missing  **/
				if(args.length==1){System.out.print("Error: Missing NUMBER for marking todo as done.");System.exit(0);}
				/** done arg missing **/

				//(start) count the no.of.lines
				try 
				{
					BufferedReader obj = new BufferedReader ( new FileReader(tobj));
					while((line=obj.readLine())!=null){
						count++;
					}
					obj.close();
				}
				catch(Exception ex) { // do nothing
				}
				//(end) count the no.of.lines

				num=Integer.parseInt(args[1]);
				if(num>count||num==0)    // wrong input
					System.out.println("Error: todo #"+args[1]+ " does not exist.");
				else{                    // valid input
					System.out.print("Marked todo #"+args[1]+" as done.");
					temp = Integer.parseInt(args[1]);

					/****  copy the text to ar1 and ar2 excluding the completes task line.  ****/
					try{
						count = 0;
						BufferedReader obj = new BufferedReader ( new FileReader(tobj));
						while((line=obj.readLine())!=null){
							count++;
							if(count<temp){
								ar1.add(line);
							}
							else if(count==temp){
								mline=line;
							}
							else{
								ar2.add(line);
							}
						}
						obj.close();
					/****  copy the text to ar1 and ar2 excluding the completes task line.  ****/
						
					/***** updating todo after removing completed task *****/
						FileWriter fout = new FileWriter (tobj,false);
						for(i = 0; i < ar1.size();i++){               // writing first set of strings
							line = ar1.get(i);
							fout.write(line);
							fout.write(System.getProperty("line.separator"));
						}
						for(i = 0;i < ar2.size();i++){
							line = ar2.get(i);
							if(i==ar2.size()-1) {fout.write(line); }  // no nweline after last line 
							else{
								fout.write(line);
								fout.write(System.getProperty("line.separator"));
							}
						}
						fout.close();
						/***** updating todo after removing completed task *****/

						/***** updating done.txt *****/ 
						FileWriter dobj = new FileWriter(cobj,true);
						dobj.write("x ");
						dobj.write(date.toString());
						dobj.write(" ");
						dobj.write(mline);
						dobj.write(System.getProperty("line.separator"));
						dobj.close();
					}
					catch(Exception ex){// do nothing

					}
				}
			}
			else if(args[0].equals("report")){   //  report part
					num=count=0;    // num indicates no.of.todo records (reusing old variable for memory saving) :)
					
				/**** counting the records ****/
				try 
				{
					BufferedReader obj = new BufferedReader ( new FileReader(tobj));
					while((line=obj.readLine())!=null){
						count++;
					}
					obj.close();
					BufferedReader dobj = new BufferedReader ( new FileReader(cobj));
					while((line=dobj.readLine())!=null){
						num++;
					}
					dobj.close();
					System.out.println(date.toString()+" Pending : "+count+" Completed : "+num);
				}
				catch(Exception ex) { // do nothing
				 }
			}
		}
		catch(Exception ex){/*** do nothing ***/}
	}
}
