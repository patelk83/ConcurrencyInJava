
/* Assignment 
	Find primenumber using single threading and parellel stream and compare them.

Fall 2019
Course: Concurrent Programming: Theory and Practice
Professor: Patrick McKee
Author: Kriyanshi Patel
	
	
	

*/
import java.io.*;  
import java.util.*; 
import java.lang.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class PrimeNumbers {


/*
*   Function to determine prime Number with stream and lambda expression
*
*/
 public static boolean isPrimeStream(int n) {
       
       return IntStream.rangeClosed(2, n / 2).parallel().noneMatch(i ->n % i ==0);
    
}

/*
*   Function to determine prime Number
*
*/

public static boolean isPrime(int n) {
       
        if (n % 2 == 0) return false;
        
	for(int i=3;i * i <= n; i += 2){
            if (n % i == 0) return false;
        }

         return true;
    }
	

	 static int number;	 
   	 static int choice; 
	//Arraylist to store all prime numbers
	 static ArrayList<Integer> primesnum = new ArrayList<Integer>(number); 
	public static void main(String args[]) throws IOException, InterruptedException 
	{

		// get parameter from user
		Scanner in = new Scanner(System.in);
				
		 try{
				 number = Integer.parseInt(args[0]); 
				
				System.out.println("Number is" + number);
							
		 } catch(Exception e){
			System.out.println(" Error, argument is necessary.");
			return;
		 }
		 	
		 
		do{  
			//selection menu option
		System.out.println("");
		System.out.println("Please enter your choice:");
        System.out.println("1\t Single theaded");
        System.out.println("2\t Parallel Stream");
        System.out.println("3\t Change parameter");
	System.out.println("4\t Exit");
		
		
		
		try{ 
			if(choice>0 || choice<5){
			choice=in.nextInt();}
			}
			
		catch(Exception e){
			System.out.println("Please enter valid choice");
			in.next();
		}
		
		 switch (choice) {
            case 1: 			/*
					*  Find prime number and display all prime number with total number and execution time using Single threading
					*
					*/
										int n= number;
										
										//store all prime numbers in arraylist
							ArrayList<Integer> arr1 = new ArrayList<Integer>(n);
							
							System.out.println("Computing Primes for"+ number);
							long startTime =System.currentTimeMillis();
							
								
								for(int i=2; i<=n; i++)
								{ 
								if(PrimeNumbers.isPrime(i) ==  true){
								arr1.add(i);
								}
								 }


						for(int k=0; k<arr1.size();k++){
							System.out.print(arr1.get(k)+" 		");
							
						}
						System.out.println("");
							
							long endTime =System.currentTimeMillis();
							System.out.println("Total numbers of prime are:"+ arr1.size());
								
								float sec = (endTime - startTime) / 1000F;
						
								System.out.println("Execution time in seconds:"+ sec);
										break;
            
			case 2: 	
						/*
					*  Find prime number and display all prime number with total number and execution time using parellel stream and lambda 					*	expression
					*
					*/
												
										
								long startTimee =System.currentTimeMillis();
								System.out.println("Computing Primes for"+ number);	
							
						 List<Integer> PrimeList = IntStream.rangeClosed(2, number)
														.parallel()
														.filter(PrimeNumbers::isPrimeStream)
														.sorted()
														.boxed()
														.collect(Collectors.toList());

                    							/// calculate endTime 
									long endTimee =System.currentTimeMillis();
									
									float second = (endTimee - startTimee) / 1000F;
							System.out.println(" "+ PrimeList + " ");
							System.out.println("Total numbers of prime are:"+ PrimeList.size());
									System.out.println("Execution time in seconds:"+ second);
									break;
												
						
            case 3: 
					/*
					*  Change parameter by user
					*
					*/
					
					System.out.println("What value would you like to use?");
					try{
					number = in.nextInt();
					
						
										
					} catch(Exception e){
						System.out.println("Error,Enter valid value ");
						in.next();
					}
					
                    break;



		
            case 4: 
						/*
					*  Exit from the program
					*
					*/
						System.out.println(" Thank you! Goodbye! :)"); 		
						return;
            default: System.out.println("Invalid choice");	
		 }
		}while(true); 
		     
	}
	
}
