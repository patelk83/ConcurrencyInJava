/*
 *     Assignment 3
*  Develop GUI for handling multihtreading.
*  Fall 2019
* Course: Concurrent Programming: Theory and Practice
* Professor: Patrick McKee
* Author: Kriyanshi Patel
 * 
 * 
 */


import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Future;



public class PrimeNumbers {

 

    
    static SwingWorker<Boolean, Void> worker1;   
    static boolean executing = false;
    
    /*
     * Function to check number is prime or not
     * If number is prime then return true otherwise false
     * 
     */
    public static boolean isPrime(int n) {
       
    	if(n <= 2)
    		return false;    	
        if (n % 2 == 0) return false;        
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }
    
    /*
     * Check future is completed or not
     * 
     */
    public static boolean allFuturesDone(ArrayList<Future<Integer>> allfutures) {
        for (Future<Integer> future : allfutures) {
            if (!future.isDone()) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {        
             
        //  Arraylist Store all prime numbers
        ArrayList<Integer> completed_primes = new ArrayList<Integer>();
        int CurrentNum = 0;
        int TotalNum = 0;
        long execution_time = 0;  
       /*
        * Designing Swing Components
        * 
        * 
        */
        JFrame frame = new JFrame("Prime Number GUI");
        frame.setSize(400,300);
        frame.setResizable(false);
        frame.setLayout(null);
        
        JLabel CurrentNum_label = new JLabel("Current Prime number is: " + CurrentNum);
        CurrentNum_label.setBounds(25, 20, 200, 30);
        frame.add(CurrentNum_label);
        
        
       
        JLabel TotalNum_label= new JLabel("Total Prime numbers is: " + TotalNum);
        TotalNum_label.setBounds(25, 40, 200, 30);
        frame.add(TotalNum_label);
        
        JLabel Execution_time_label = new JLabel("Execution Time is: " + execution_time);
        Execution_time_label.setBounds(25, 60, 200, 30);
         frame.add( Execution_time_label);
         
         JLabel MaxNumber_label = new JLabel("Max number");
         MaxNumber_label.setBounds(25, 100, 100, 30);
         frame.add(MaxNumber_label);

         JSpinner MaxNumber = new JSpinner(new SpinnerNumberModel(1000, 1, 100000000, 1));
         MaxNumber.setBounds(120, 100, 200, 30);
         frame.add(MaxNumber);
             
         JLabel num_threads_label = new JLabel("Num of Threads");
         num_threads_label.setBounds(25, 140, 100, 30);
         frame.add(num_threads_label);

         JComboBox<Integer> NumThread = new JComboBox<Integer>();         
         NumThread.addItem(1);
         NumThread.addItem(2);
         NumThread.addItem(4);
         NumThread.addItem(6);
         NumThread.addItem(8);
         NumThread.addItem(10);
         NumThread.addItem(12);
         NumThread.addItem(14);
         NumThread.addItem(16);
         NumThread.addItem(20);
         NumThread.addItem(50);
         NumThread.setBounds(120, 140, 200, 30);
         frame.add(NumThread);
          
        
        JButton startbtn = new JButton("Start");
        startbtn.setBounds(25, 220, 100, 30);
        frame.add(startbtn);

        JButton cancelbtn = new JButton("Cancel");
        cancelbtn.setBounds(150, 220, 100, 30);
        cancelbtn.setEnabled(false);
        frame.add(cancelbtn);

      /*
       * Start button ActionListener
       *   Thread dispatch using SwingWorker with override doInBackground and done method.
       * 
       */
        startbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                
                startbtn.setEnabled(false);
                MaxNumber.setEnabled(false);
                NumThread.setEnabled(false);
                cancelbtn.setEnabled(true);               
                
                int thread = (Integer)NumThread.getSelectedItem();
                int number = (Integer)MaxNumber.getValue();
                executing = true;

                
                worker1 = new SwingWorker<Boolean, Void>() {
                	long exetime = System.currentTimeMillis();
                    @Override
                    public Boolean doInBackground() {
                    		
                        
                        ExecutorService exec = Executors.newFixedThreadPool(thread);                        
                        ArrayList<Future<Integer>> allfutures = new ArrayList<Future<Integer>>();

                        for (int i = 1; i <= number; i++) {
                            final int num = i;
                            if (this.isCancelled()) {
                                break;
                            }

                            SwingWorker<Integer, Void> worker2 = new SwingWorker<Integer, Void>() {
                                @Override
                                public Integer doInBackground() {
                                    if (!this.isCancelled()) {
                                       
                                        if (primenumber.isPrime(num)) {
                                            if (!this.isCancelled()) {
                                                return num;
                                            } 
                                        } 
                                    }
                                    
                                  return null;
                                    
                                }

                                @Override
                                public void done() {
                                    if (!this.isCancelled()) {
                                        try {
                                            Integer Pnumber = get();
                                            if (Pnumber != null) {
                                                if (executing && !this.isCancelled())  {
                                                   
                                                     completed_primes.add(Pnumber);
                                                             
                                                         CurrentNum_label.setText("Current Prime number: " + Pnumber);
                                                         TotalNum_label.setText("Total Prime number: " + completed_primes.size());
                                                        Execution_time_label.setText("Execution Time is " + String.format("%.3f", ((System.currentTimeMillis() - exetime)/1000F))+" seconds");
                                                        
                                                   
                                                }
                                            }
                                        } catch (Exception e) {
                                        } 
                                    }
                                }
                            };

                            
                            @SuppressWarnings("unchecked")
                            Future<Integer> future = (Future<Integer>) exec.submit(worker2);
                            allfutures.add(future);
                        }

                         while (!allFuturesDone(allfutures)) {
                            if (this.isCancelled()) {
                               
                                for (Future<Integer> future : allfutures) {
                                    future.cancel(true);
                                }
                                break;
                            }
                        }

                        return true;
                    }
                
                    @Override
                    public void done() {
                        if (executing && !this.isCancelled()) {
                            
                            cancelbtn.setEnabled(false);
                            startbtn.setEnabled(true);
                            MaxNumber.setEnabled(true);
                            NumThread.setEnabled(true);                        
                                                                                                         
                            completed_primes.clear();
                            executing = false;
                        } 
                    }
                };
               worker1.execute();
            }
        });

        /*
         * Cancel Button ActionListener 
         * 
         * 
         */
        cancelbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	executing = false;

                worker1.cancel(true);
              
                cancelbtn.setEnabled(false);
                startbtn.setEnabled(true);
               MaxNumber.setEnabled(true);
                NumThread.setEnabled(true);
              
                CurrentNum_label.setText("Current Prime number: " + Collections.max(completed_primes));
                TotalNum_label.setText("Total Prime number: " + completed_primes.size());
               
                completed_primes.clear();                
                
            }
        });
        
               
        frame.setVisible(true);
    }

    


}  