
/* Assignment 2
	Author Kriyanshi Patel

	Use assignment 1 and optimize number of thread for server and local machine
	

*/
import java.io.*;  
import java.util.*; 
import java.lang.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;

class PrimeNumbers {

	 static int number;
	 static  int THREADS = 8; // as initial number of threads
   	static int choice; 
	
	// Arraylist to store primenumbers
       static ArrayList<Integer> primesnum = new ArrayList<Integer>(number); 
	public static void main(String args[]) throws IOException, InterruptedException 
	{
		Scanner in = new Scanner(System.in);
				
		 try{
				 number = Integer.parseInt(args[0]); 
				
				System.out.println("Number is" + number);
							
		 } catch(Exception e){
			System.out.println(" Error, argument is necessary.");
			return;
		 }
		 //find number of cores
		 int cores = Runtime.getRuntime().availableProcessors();	
		 
		do{  
			//selection menu
		System.out.println("");
		System.out.println("Please enter your choice:");
        System.out.println("1\t Single theaded");
        System.out.println("2\t Multi threaded");
        System.out.println("3\t Change parameter");
	System.out.println("4\t Change number of Threads");
	System.out.println("5\t Optimize");
        System.out.println("6\t Exit");
		
		
		
		try{ 
			if(choice>0 || choice<7){
			choice=in.nextInt();}
			}
			
		catch(Exception e){
			System.out.println("Please enter valid choice");
			in.next();
		}
		
		 switch (choice) {
            case 1: //Single threading
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
							//calculate endTime
							long endTime =System.currentTimeMillis();
							System.out.println("Total numbers of prime are:"+ arr1.size());
								// converting Milliseconds into seconds
								float sec = (endTime - startTime) / 1000F;
						
								System.out.println("Execution time in seconds:"+ sec);
										break;
            
			case 2: 	
						///Multi threading using future and callable	
												
									// startTime of multithreading	
								long startTimee =System.currentTimeMillis();
								System.out.println("Computing Primes for"+ number);
								// fixed thread pool size executor with current # threads 
							ExecutorService exec = Executors.newFixedThreadPool(THREADS);

                    //  arraylist  of futures to wait for values 
							ArrayList<Future<Integer>> futures = new ArrayList<Future<Integer>>();

                   
                    // find if number is prime or not. if number is prime then add future to an array list
						for (int i = 1; i <= number; i++) {
							final int no = i;
                        Callable<Integer> task = () -> {
                            if (PrimeNumbers.isPrime(no)) {
                                return no;
                            } else {
                                return null;
                            }
                        };
                        futures.add(exec.submit(task));
                    }

                    // Arraylist to store all prime numbers
                    ArrayList<Integer> arry2 = new ArrayList<Integer>();

                    
                    for (Future<Integer> future : futures) {
                        try {
                            Integer val = future.get();
                            if (val != null) {
                                arry2.add(val);
                            }
                        } catch (Exception e) {}
                    }

                  for(int s=0; s<arry2.size();s++){
							System.out.print(arry2.get(s)+"  ");
							
						}
						/// calculate endTime 
			long endTimee =System.currentTimeMillis();
									// converting Milliseconds into seconds
									float second = (endTimee - startTimee) / 1000F;
							System.out.println("Total numbers of prime are:"+ arry2.size());
									System.out.println("Execution time in seconds:"+ second);
									break;
												
						
            case 3: 
					//Change paramters by user
					
					System.out.println("What value would you like to use?");
					try{
					number = in.nextInt();
										
					} catch(Exception e){
						System.out.println("Error,Enter valid value ");
						in.next();
					}
					
                    break;



	case 4: 	//change # of threads 
			
	
		System.out.println("Number of threads are :"+ THREADS);
		
		System.out.println("Please enter how many threads do you want");			
		try{
		THREADS = in.nextInt();
		} catch(Exception e){
		System.out.println("Invalid value");
		continue;
		}
		break;
	case 5:
		//  optimization
		
        int u = 1; 
        double waitCount = 0.5;
        		
        THREADS = cores * u * (int) (1 + waitCount);
	System.out.println("Threads optimized to " + THREADS);
                System.out.println();
                break;
		
		
            case 6: 
						// Exit from selection menu
						System.out.println(" Thank you! Goodbye! :)"); 
						return;
            default: System.out.println("Invalid choice");	
		 }
		}while(true); 
		     
	}
	

//check number is prime or not.
 public static boolean isPrime(int n) {
       
        if (n % 2 == 0) return false;
        
	for(int i=3;i * i <= n; i += 2){
            if (n % i == 0) return false;
        }

         return true;
    }
}
	