
/*

Assignment 1
Find Prime Number using single threading and multithreading


Fall 2019
Course: Concurrent Programming: Theory and Practice
Professor: Patrick McKee

Author: Kriyanshi Patel

*/
import java.io.*;  
import java.util.*; 
import java.lang.*;

class PrimeNumbers {

	 static int number;
	  static  int THREADS;
   static int choice;
	//Arraylist to store primenumbers
    static ArrayList<Integer> primesnum = new ArrayList<Integer>(number);
	public static void main(String args[]) throws IOException, InterruptedException 
	{
		
		Scanner in = new Scanner(System.in);
		
		
		/* try{
				 number = Integer.parseInt(args[0]); 
			
		 } catch(Exception e){
			System.out.println(" Error, argument is necessary.");
			return;
		 }
		 */
		 
		 
		do{  
		System.out.println("");
		System.out.println("Please enter your choice:");
        System.out.println("1\t Single theaded");
        System.out.println("2\t Multi threaded");
        System.out.println("3\t Change parameter");
        System.out.println("4\t Exit");
		
		
		
		try{
		choice=in.nextInt();
		}catch(Exception e){
			System.out.println("Please enter valid choice");
			in.next();
		}
		
		 switch (choice) {
            case 1: //Single threading
										int n= number;
										
										//store all prime numbers in arraylist
							ArrayList<Integer> arr1 = new ArrayList<Integer>(n);
							System.out.println("Computing Primes..");
							long startTime =System.currentTimeMillis();
							for(int i=2; i<=n; i++)
								{		boolean isPrime=true;

								for(int j=2; j<=Math.sqrt(i);j++){
								if(i%j == 0){
								isPrime= false;
								break;}
								}
							if(isPrime ==  true){
								arr1.add(i);
							}
								
						}
						
						for(int k=0; k<arr1.size();k++){
							System.out.print(arr1.get(k)+" 		 ");
							
						}
						System.out.println("");
							
							long endTime =System.currentTimeMillis();
							System.out.println("Total numbers of prime are:"+ arr1.size());
								// converting Milliseconds into seconds
								float sec = (endTime - startTime) / 1000F;
						
								System.out.println("Execution time in seconds:"+ sec);
										break;
            
			case 2: 	
						///Multi threading		
												
											
								long startTimee =System.currentTimeMillis();
								System.out.println("Computing Primes..");
								THREADS= number;
							Thread[] t = new Thread[THREADS];
							Multithread.m = new SyncNumber();
							
							for (int i=0; i<THREADS; i++) {
								t[i] = new Thread(new Multithread(i) );
								t[i].start();
							}

							// wait for threads to finish
							for (int i=0; i<THREADS; i++)
								t[i].join();

							// print out all primes
							
							for (int m : primesnum)
								System.out.print("" + m + " ");
							System.out.println();
							System.out.println("Total prime numbers are: "+primesnum.size());
							
							long endTimee =System.currentTimeMillis();
									// converting Milliseconds into seconds
									float second = (endTimee - startTimee) / 1000F;
							
									System.out.println("Execution time in seconds:"+ second);
									break;
												
						
            case 3: 
					//Change paramters
					
					System.out.println("What value would you like to use?");
					try{
					number = in.nextInt();
					} catch(Exception e){
						System.out.println("Error,that value is too large. ");
						in.next();
					}
					
                    break;
            case 4: 
						// exit 
						System.out.println("Goodbye!"); 
						return;
            default: System.out.println("Invalid choice");	
		 }
		}while(true); 
		     
	}
	// function to check primenumber
	 public static boolean isPrime(int n) {
	if (n == 2 || n == 3 || n == 5) return true;
	if (n <= 1 || (n&1) == 0) return false;

	for (int i = 3; i*i <= n; i += 2)
	    if (n % i == 0) return false;

	return true;
    }// Synchronize block
	 public synchronized static void addtoPrime(int n) {
	primesnum.add(n);
	}
	
}

 class Multithread implements Runnable {
    public static SyncNumber m;
    final int k;
    public Multithread(int i) {
	k = i;
    }

    public void run() {
	for(int i=0; i < PrimeNumbers.number; i++) {
	    if(i % PrimeNumbers.THREADS == k)
		if(PrimeNumbers.isPrime(i)) 
		    m.addtoPrime(i);
	}
    }
}

 class SyncNumber {
    public synchronized void addtoPrime(int n) {
	PrimeNumbers.addtoPrime(n);
    }
}
	