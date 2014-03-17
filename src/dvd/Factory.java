package dvd;


import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

//import javax.imageio.ImageIO;

import org.apache.commons.math3.distribution.*;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;




public class Factory {
	
/////-------------------------------------------- Testing	-------------------------------\\\\\\\\\\	
	public static int dvdsStarted = 0;
	public static double totalRepairTime = 0;
	public static int repairNumber = 0;
	public static int dvdsM2 = 0;
	public static int brokenDVDs = 0;
	public static int hour = 0;
	public static int m2Int = 0;
	public static int cbInt = 0;
	public static int m3_12Int = 0;
	public static int m3_3Int = 0;
	public static int m4Int = 0;
	public static int cSwapInt = 0;
	
	
/////------------------------------------ Declarations and init() --------------------------\\\\\\\\\
	
	// These are all for the GUI.
	protected Shell shell;
	private Label dvdProduced;
	private Label dvdFactoryImage;
	private Label lblBuffer1;
	private Label lblBuffer0;
	private Label lblCrateFront0;
	private Label lblCrateFront1;
	private Label lblCrateIn0;
	private Label lblCrateIn1;
	private Label lblCrateBack1;
	private Label lblCrateBack0;
	private Button cbtnIdleM1_0;
	private Button cbtnIdleM1_1;
	private Button cbtnIdleM1_2;
	private Button cbtnIdleM1_3;
	private Button cbtnRepairingM1_0;
	private Button cbtnRepairingM1_1;
	private Button cbtnRepairingM1_2;
	private Button cbtnRepairingM1_3;
	private Button cbtnIdleM2_0;
	private Button cbtnIdleM2_1;
	private Button cbtnBusyM2_0;
	private Button cbtnBusyM2_1;
	private Label lblBrokenDVDs;
	private Label lblCartridgeSize_0;
	private Label lblCartridgeSize_1;
	private Button cbtnCleaningM4_0;
	private Button cbtnCleaningM4_1;
	private Button cbtnRefillM4_0;
	private Button cbtnRefillM4_1;
	private Label lblCurrentTime;

	

	// These are the variables we can increase or decrease and may  the production.
	public static double currentTime = 0;
	public static int bufferSize = 20;
	public static int crateSize = 20;
	public static int amountM1 = 4;
	public static int amountM2 = 2;
	public static int amountM3 = 2;
	public static int amountM4 = 2;
	
	 
	public static PriorityQueue<Event> eventList = new PriorityQueue<Event>();
	public static Queue<DVD> producedDVDQueue = new LinkedList<DVD>();
	
	// states for all machines 1
	public static boolean[] m1Repairing = new boolean[amountM1];
	public static boolean[] m1Idle = new boolean[amountM1];
	public static double[] m1RestTime = new double[amountM1];
	public static ArrayList<DVD> m1DVDWaiting = new ArrayList<DVD>();
	public static double[] m1StartRepairTime = new double[amountM1];
	
	// states for all buffers 
	public static ArrayList<Queue<DVD>> bufferList = new ArrayList<Queue<DVD>>();

	// state for all machines 2
	public static boolean[] m2Idle = new boolean[amountM2];
	public static ArrayList<DVD> m2WaitingDVD = new ArrayList<DVD>();
	public static boolean[] m2Busy = new boolean[amountM2]; //!!!!! nieuw
	
	// state for conveyor belt 
	public static boolean[] cbIdle = new boolean[amountM2];
	public static double[] cbIdleTime = new double[amountM2];
	public static ArrayList<Queue<Double>> cbWaitingTime = new ArrayList<Queue<Double>>();
	public static ArrayList<Queue<DVD>> cbWaitingDVD = new ArrayList<Queue<DVD>>();
	public static boolean[] cbWaitingForSwap = new boolean[amountM2];
	
	// state for all crates in front of machine 3
	public static ArrayList<ArrayList<DVD>> crateFrontList = new ArrayList<ArrayList<DVD>>();
	
	// state for all crates in machine 3
	public static ArrayList<ArrayList<DVD>> crateInList = new ArrayList<ArrayList<DVD>>();
	
	// state for all crates in front of machine 4
	public static ArrayList<ArrayList<DVD>> crateBackList = new ArrayList<ArrayList<DVD>>();
	
	// state for all machine 3	
	public static boolean[] m3_3WaitingForSwap = new boolean[amountM3];
	
	// state for all machine 4
	public static boolean[] m4Idle = new boolean[amountM4];
	public static boolean[] m4Repairing = new boolean[amountM4];
	public static int[] cartridge = new int[amountM4]; 
	public static int[] countDVDs = new int[amountM4];
	
	
	
	//Initial states are declared here
	public static void init(){
		
		
		// Production Step 1 is running and needs initial events for the process to begin
		// We need to create m1Amount*2 initial events, 
		// One event for each machine in production Step 1, 
		// and a scheduled breaking time for each machine in production step 1.
		for ( int i = 0; i < amountM1; i++){
			m1Repairing[i] = false;
			m1Idle[i] = false;
			m1RestTime[i] = 0;
			
			DVD dvd = new DVD(0,0);
			Event m1FinishedEvent = new Event((currentTime+eventTimeM1()),1,i,dvd);
			Event m1StartRepairEvent = new Event((currentTime+eventTimeStartRepairM1()),2,i,null);
			
			eventList.add(m1FinishedEvent);
			eventList.add(m1StartRepairEvent);
			m1DVDWaiting.add(i,null);
			dvdsStarted++;
		}
		
		// ProductionStep 2 is running, and all buffers are empty
		for ( int i = 0; i < amountM2; i++){
			cbIdle[i] = false;
			m2Idle[i] = false;
			m2Busy[i] = false;
			cbWaitingForSwap[i] = false;
			Queue<DVD> buffer = new LinkedList<DVD>();
			bufferList.add(buffer);
			Queue<Double> cbWaitTime = new LinkedList<Double>();
			cbWaitingTime.add(cbWaitTime);
			Queue<DVD> cbWaitDVD = new LinkedList<DVD>();
			cbWaitingDVD.add(cbWaitDVD);
			m2WaitingDVD.add(i,null);
		}
		
		// All crates are empty
		for ( int i = 0; i < amountM3; i++){
			m3_3WaitingForSwap[i] = true;
			ArrayList<DVD> crateFront = new ArrayList<DVD>();
			ArrayList<DVD> crateIn = new ArrayList<DVD>();
			ArrayList<DVD> crateBack = new ArrayList<DVD>();
			
			crateFrontList.add(crateFront);
			crateInList.add(crateIn);
			crateBackList.add(crateBack);
		}
		
		// ProductionStep 4 is running, and cartridgeSize is initialized
		for ( int i = 0; i < amountM4; i++){
			m4Repairing[i] = false;
			m4Idle[i] = true;
			cartridge[i] = getCartridgeSize();
			countDVDs[i] = 0;
		}
		
		
		// This is the ending event. It is currently set at 7 days.
		Event endSimulationEvent = new Event((7*24*60*60),11,0,null);
		eventList.add(endSimulationEvent);
		
		// We use another event to check on the machines and print data for testing. This event is currently scheduled once each day.
		Event newHourCheck = new Event(currentTime+(7*24*60*60)+1,10,0,null);
		eventList.add(newHourCheck);
		
	}
	
	
	
/////------------------------------------ Event Handlers--------------------------------------\\\\\\\\\


	private static void m1ScheduledFinished(Event e){
		currentTime = e.eventTime;

		// calculates which buffer belongs to which machine
		int indexBuffer = 3;
		if(e.machineNum == 0 || e.machineNum == 1) {
			indexBuffer = 0;
		} else {
			indexBuffer = 1;
		}
		if(!m1Repairing[e.machineNum]) { 
			if(bufferList.get(indexBuffer).size() <20) {
				DVD new_dvd = new DVD(currentTime, 0);
				Event m1Finished = new Event(eventTimeM1(),1,e.machineNum,new_dvd);
				eventList.add(m1Finished);
				dvdsStarted++;
				if (bufferList.get(indexBuffer).isEmpty() && !m2Busy[indexBuffer]) {
					
					Event m2Finished = new Event(eventTimeM2(),4,indexBuffer,e.dvd);	
					eventList.add(m2Finished);
					m2Busy[indexBuffer] = true;  
				} else {
					bufferList.get(indexBuffer).add(e.dvd);
				}	
			} else {
				m1Idle[e.machineNum] = true;
				m1DVDWaiting.set(e.machineNum, e.dvd);
			}
		} else {
			m1RestTime[e.machineNum] = currentTime - m1StartRepairTime[e.machineNum];
			m1DVDWaiting.set(e.machineNum, e.dvd);
			
		}
		// Machines go from 4 to 2, so we need to change machineNum accordingly
		e.machineNum = indexBuffer;
	}
	
	private static void m1StartRepairing(Event e){
		currentTime = e.eventTime;
		m1Repairing[e.machineNum] = true;
		m1StartRepairTime[e.machineNum] = currentTime;
		Event m1FinishedRepairing = new Event(eventTimeM1FinishedRepair(),3,e.machineNum,null);
		eventList.add(m1FinishedRepairing);
		m1Idle[e.machineNum] = false;
	}
	
	private static void m1FinishedRepairing(Event e){
		currentTime = e.eventTime;
		if(!m1Idle[e.machineNum]) {
			double eventM1time = (currentTime+m1RestTime[e.machineNum]);
			Event m1Finished = new Event(eventM1time,1,e.machineNum,m1DVDWaiting.get(e.machineNum));
			m1RestTime[e.machineNum] = 0;
			eventList.add(m1Finished);
		}
		m1Repairing[e.machineNum] = false;
		totalRepairTime = m1StartRepairTime[e.machineNum] + totalRepairTime;  // testing
		repairNumber++; // testing
		Event m1StartRepairEvent = new Event(eventTimeStartRepairM1(),2,e.machineNum,null);
		eventList.add(m1StartRepairEvent);
	}
	
	private static void m2ScheduledFinished(Event e) { 
		currentTime = e.eventTime;

		// Again, we still need to check this PRNG.
		double dvdBrokenRand;
		Random rand = new Random();
		dvdBrokenRand = rand.nextDouble();

		if (!cbIdle[e.machineNum]){
			m2Busy[e.machineNum] = false;
			if (dvdBrokenRand > .02) { // DVDs to conveyor belt
				Event cbScheduledFinished = new Event((currentTime+(5*60)),5,e.machineNum,e.dvd);
				eventList.add(cbScheduledFinished);
			} else { 
				// delete DVD
				// e.dvd = null; // Jiske: volgens mij niet nodig 
				brokenDVDs++;
			}
			// In case any M1 of this M2 is Idle	
			int option1 = 5;
			int option2 = 5;
			if(e.machineNum == 0){
				option1 = 0;
				option2 = 1;
			} else {
				option1 = 2;
				option2 = 3;
			}
			if (m1Idle[option1]){
				Event m1Finished = new Event(currentTime,1,option1,m1DVDWaiting.get(option1));
				eventList.add(m1Finished);
				m1Idle[option1] = false;
			}
			if (m1Idle[option2]){
				Event m1Finished = new Event(currentTime,1,option2,m1DVDWaiting.get(option2));
				eventList.add(m1Finished);
				m1Idle[option2] = false;
			}
			// If buffer not empty schedule new m2Finished
			if(!bufferList.get(e.machineNum).isEmpty()){
				DVD new_dvd = bufferList.get(e.machineNum).remove(); 
				Event m2Finished = new Event(eventTimeM2(),4,e.machineNum,new_dvd);
				eventList.add(m2Finished);
				m2Busy[e.machineNum] = true;
			}
		// If conveyor belt is idle set true
		} else {
			m2Idle[e.machineNum] = true;
			m2WaitingDVD.set(e.machineNum, e.dvd);
		}
	}
	
	private static void cbScheduledFinished(Event e) {
		currentTime = e.eventTime;
		if(!cbIdle[e.machineNum]){
			// If not 20 put DVD in crate
			if(crateFrontList.get(e.machineNum).size() < 20){ 
				crateFrontList.get(e.machineNum).add(e.dvd);
				// If it becomes 20 by doing so create swap crates event. 
				if(crateFrontList.get(e.machineNum).size() == 20){
					Event swapCrates = new Event(currentTime,6,e.machineNum,null);
					eventList.add(swapCrates);
					cbWaitingForSwap[e.machineNum] = true; // Jiske: Denk dat dit toch nodig is. 
				}
			// If 20 
			} else {
				cbIdle[e.machineNum] = true;
				cbIdleTime[e.machineNum] = currentTime;
				cbWaitingTime.get(e.machineNum).add(0.0);
				cbWaitingDVD.get(e.machineNum).add(e.dvd);
			}
		// if cb idle
		} else {
			double waitingTime = currentTime - cbIdleTime[e.machineNum];
			cbWaitingTime.get(e.machineNum).add(waitingTime);
			cbWaitingDVD.get(e.machineNum).add(e.dvd);
		}
	}
	
	private static void cratesScheduledSwap(Event e) {
		currentTime = e.eventTime;
		
		ArrayList<DVD> tempCrateFront = new ArrayList<DVD>();
		ArrayList<DVD> tempCrateIn = new ArrayList<DVD>();
		
		for(int i = 0; i < cbWaitingForSwap.length; i++ ){
			for(int j = 0; j < m3_3WaitingForSwap.length; j++ ) {
				for(int k = 0; k < m4Idle.length; k++) {
					if(cbWaitingForSwap[i] && m3_3WaitingForSwap[j] && m4Idle[k]){
						tempCrateFront = (ArrayList<DVD>) crateFrontList.get(i).clone();
						tempCrateIn = (ArrayList<DVD>) crateInList.get(j).clone();
						
						crateBackList.set(k,tempCrateIn);
						crateInList.set(j,tempCrateFront);
						crateFrontList.get(i).clear();
						
						Event m3_12ScheduledFinished = new Event(eventTimeM3_12(),7,j,null);
						eventList.add(m3_12ScheduledFinished);
						
						
						cbWaitingForSwap[i] = false;
						m3_3WaitingForSwap[j] = false;
						m4Idle[k] = false;
						cbIdle[i] = false;
						
						while(!cbWaitingDVD.get(i).isEmpty()){
							DVD this_DVD = cbWaitingDVD.get(i).remove();
							Event CBfinished = new Event((currentTime + cbWaitingTime.get(i).remove()),5,i,this_DVD);
							eventList.add(CBfinished);
						}
						
						if(m2Busy[i]) {
							m2Idle[i] = false;
							Event m2ScheduledFinished = new Event(currentTime,4,i,m2WaitingDVD.get(i));
							eventList.add(m2ScheduledFinished);
							
						} else if(!bufferList.get(i).isEmpty()){
							m2Idle[i] = false;
							Event m2ScheduledFinished = new Event(eventTimeM2(),4,i,bufferList.get(i).remove());
							eventList.add(m2ScheduledFinished);
						}
						
						if(!m4Repairing[k]) {
							Event m4ScheduledFinished = new Event(eventTimeM4(),9,k,null);
							eventList.add(m4ScheduledFinished);
						}
					}	
				}
			}
		}
	}
	
    private static void m3_12ScheduledFinished(Event e) {
    	currentTime = e.eventTime;
           
        //Delay in seconds
        int delay = 0;
       
        // Make a random object
        Random rand = new Random();
        double nozzleBlockChance;
       
        // Make temporary Arraylist for easy access.
        ArrayList<DVD> tempCrateIn = new ArrayList<DVD>();
        tempCrateIn = crateInList.get(e.machineNum);
       
        // Loop over the crate that is currently in the machine
        // check for each dvd if the nozzle gets blocked.
            for(int i = 0; i < tempCrateIn.size(); i++){
                    nozzleBlockChance = rand.nextDouble();
                    if (nozzleBlockChance < .03) {
                            delay += 300;
                    }
            }
           
            Event m3_3Finished = new Event((eventTimeM3_3()+delay),8,e.machineNum,null);
            eventList.add(m3_3Finished);
       
        }

	private static void m3_3ScheduledFinished(Event e) {
        currentTime = e.eventTime;
        m3_3WaitingForSwap[e.machineNum] = true;
        Event cratesScheduledSwap = new Event(currentTime,6,e.machineNum,null);
        eventList.add(cratesScheduledSwap);
    }
	
	private static void m4ScheduledFinished(Event e) {
		currentTime = e.eventTime;
		m4Repairing[e.machineNum] = false;

		if(!crateBackList.get(e.machineNum).isEmpty()){
			if (countDVDs[e.machineNum] < cartridge[e.machineNum] ) {
				countDVDs[e.machineNum]++;
				
				DVD this_dvd = crateBackList.get(e.machineNum).remove(0);
				producedDVDQueue.add(this_dvd);
				
				if(crateBackList.get(e.machineNum).isEmpty()){
					m4Idle[e.machineNum] = true;
					Event crateScheduledSwap = new Event(currentTime,6,e.machineNum,null);
					eventList.add(crateScheduledSwap);
				} else {
					Event m4ScheduledFinished = new Event(eventTimeM4(),9,e.machineNum,null);
					eventList.add(m4ScheduledFinished);
				}
				
			} else {
				m4Repairing[e.machineNum] = true;
				cartridge[e.machineNum] = getCartridgeSize();
				countDVDs[e.machineNum] = 0;
				Event m4ScheduledFinished = new Event(eventTimeM4()+eventTimeM4Refill(),9,e.machineNum,null);
				eventList.add(m4ScheduledFinished);
			}
		} else {
			m4Idle[e.machineNum] = true;
		}
	}

	
/////------------------------------------ Checking --------------------------------------\\\\\\\\\


	private static void hourCheck(Event e){
		currentTime = e.eventTime;
		hour++;
		System.out.println("Week: " + hour);
		System.out.println("Total number of DVDs started machine 1: " + dvdsStarted);
		for(int i = 0; i<amountM2; i++) {
			System.out.println("DVDs in buffer " + (i) + ": " + bufferList.get(i).size());
		}
		System.out.println("Total number of DVDs broken in machine 2: " + brokenDVDs);	
		//System.out.println("Total number of DVDs finished in machine 2: " + m2DVDsFinished);
		//System.out.println("Total number of DVDs finished cb: " + cbFinish);
		System.out.println("DVDs on conveyor belt 0: " + cbWaitingDVD.get(0).size());
		System.out.println("DVDs on conveyor belt 1: " + cbWaitingDVD.get(1).size());

		for(int i = 0; i<amountM2; i++) {
			System.out.println("DVDs in crateFront " + (i) + ": " + crateFrontList.get(i).size());
		}
		for(int i = 0; i<amountM2; i++) {
			System.out.println("DVDs in crateIn " + (i) + ": " + crateInList.get(i).size());
		}
		for(int i = 0; i<amountM2; i++) {
			System.out.println("DVDs in crateBack " + (i) + ": " + crateBackList.get(i).size());
		}
		System.out.println("Calls to method m2Finished: " + m2Int);
		System.out.println("Calls to method cbFinished: " + cbInt);
		System.out.println("Calls to method cbSwap: " + cSwapInt);
		System.out.println("Calls to method m3_12: " + m3_12Int);
		System.out.println("Calls to method m3_3: " + m3_3Int);
		System.out.println("Calls to method m4: " + m4Int);
		System.out.println("Total DVD's produced: " + producedDVDQueue.size());
		System.out.print("Idle machines are: ");
		for(int i = 0; i<amountM1; i++) { 
			if (m1Idle[i]){
				System.out.print("M1." + (i) + ", ");
			}
		}	
		for(int i = 0; i<amountM2; i++){
			if (m2Idle[i]){
				System.out.print("M2." + (i) + ", ");
			}
		}
		for(int i = 0; i<amountM2; i++){
			if (cbIdle[i]){
				System.out.print("CB." + (i) + ", ");
			}
		}
		for(int i = 0; i<amountM3; i++){
			if (m3_3WaitingForSwap[i]){
				System.out.print("M3." + (i) + ", ");
			}
		}
		for(int i = 0; i<amountM4; i++){
			if (m4Idle[i]){
				System.out.print("M4." + (i) + ", ");
			}
		}
		
		System.out.println();
		System.out.print("Repairing machines are: ");
		for(int i = 0; i<amountM1; i++) {
			if (m1Repairing[i]){
				System.out.print("M1." + (i) + ", ");
			} 
		}
		System.out.println();
		System.out.print("Busy machines are: ");
		for(int i = 0; i<amountM2; i++) {
			if (m2Busy[i]){
				System.out.print("M2." + (i) + ", ");
			}
		}
		System.out.println();
		System.out.println("---");
		Event newHourCheck = new Event(currentTime+(7*24*60*60),10,0,null);
		eventList.add(newHourCheck);
	}


/////------------------------------------ Event time calculations --------------------------\\\\\\\\\
	

	private static double eventTimeM1(){
		double scale=3.51;
		double shape=1.23;
		LogNormalDistribution log = new LogNormalDistribution(scale, shape);
		
		return currentTime + log.sample();
	}
	
	private static double eventTimeStartRepairM1() {
		ExponentialDistribution exp = new ExponentialDistribution(8*60*60);
		return currentTime + exp.sample();
	}
	
	private static double eventTimeM1FinishedRepair() {
		ExponentialDistribution exp = new ExponentialDistribution(2*60*60);
		return currentTime + exp.sample();
	}
	
	private static double eventTimeM2() {
		double scale=2.91;
		double shape=0.822;
		LogNormalDistribution log = new LogNormalDistribution(scale, shape);
		return currentTime + log.sample();
	}
	
	private static double eventTimeM3_12() {
		ExponentialDistribution exp1 = new ExponentialDistribution(10);
		ExponentialDistribution exp2 = new ExponentialDistribution(6);
		double m3_12Time = 0;
		for(int i = 0; i < crateSize; i++) {
			m3_12Time += exp1.sample() + exp2.sample();
		}
		
		
		return currentTime + m3_12Time;
	}
	
	// This is always exactly 3 minutes
	private static double eventTimeM3_3() {
		return currentTime + 180;
	}
	
	private static double eventTimeM4() {
		Random rng = new Random();
		double m4Time = rng.nextInt(10) + rng.nextDouble() + 20;
		return currentTime + m4Time;
	}
	
	private static int getCartridgeSize(){
		double rand1, rand2;
		
		// I think this one has a period of 2^48, gotta check though.
		Random random = new Random();
		rand1 = random.nextDouble();
		rand2 = random.nextDouble();

		if (rand1 <= .6){
			return 200;
		} else if ( rand1 <= .8 ) {
			if (rand2 <= .5) return 201; else return 199;
		} else {
			if (rand2 <= .5) return 202; else return 198;
		}
	}
	
	
	private static double eventTimeM4Refill() {
		NormalDistribution normalDis = new NormalDistribution(900, 60);
		return  normalDis.sample();
	}


	
	
/////------------------------------------ Main loop --------------------------\\\\\\\\\

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Factory window = new Factory();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
/////------------------------------------  GUI  --------------------------------\\\\\\\\\

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		Image simImage = new Image(display,
				   "/Users/barendpoot/Pictures/simImage.png");
		dvdFactoryImage.setImage(simImage);
		init();

		
		
		
		while (!shell.isDisposed()) {		
			Integer producedDVDs = new Integer(producedDVDQueue.size());
			Integer	dvdsInBuffer0 = new Integer(bufferList.get(0).size());
			Integer	dvdsInBuffer1 = new Integer(bufferList.get(1).size());
			Integer	dvdsInCrateFront0 = new Integer(crateFrontList.get(0).size());
			Integer	dvdsInCrateFront1 = new Integer(crateFrontList.get(1).size());
			Integer	dvdsInCrateIn0 = new Integer(crateInList.get(0).size());
			Integer	dvdsInCrateIn1 = new Integer(crateInList.get(1).size());
			Integer	dvdsInCrateBack0 = new Integer(crateBackList.get(0).size());
			Integer	dvdsInCrateBack1 = new Integer(crateBackList.get(1).size());
			Integer brokenDVDsint = new Integer(brokenDVDs);
			Integer cartridgeSize_0 = new Integer(cartridge[0]-countDVDs[0]);
			Integer cartridgeSize_1 = new Integer(cartridge[1]-countDVDs[1]);
			Double currentTime0 = new Double(currentTime);
			
			lblBuffer0.setText(dvdsInBuffer0.toString());
			lblBuffer1.setText(dvdsInBuffer1.toString());
			lblCrateFront0.setText(dvdsInCrateFront0.toString());
			lblCrateFront1.setText(dvdsInCrateFront1.toString());
			lblCrateIn0.setText(dvdsInCrateIn0.toString());
			lblCrateIn1.setText(dvdsInCrateIn1.toString());
			lblCrateBack0.setText(dvdsInCrateBack0.toString());
			lblCrateBack1.setText(dvdsInCrateBack1.toString());
			cbtnIdleM1_0.setSelection(m1Idle[0]);
			cbtnIdleM1_1.setSelection(m1Idle[1]);
			cbtnIdleM1_2.setSelection(m1Idle[2]);
			cbtnIdleM1_3.setSelection(m1Idle[3]);
			cbtnRepairingM1_0.setSelection(m1Repairing[0]);
			cbtnRepairingM1_1.setSelection(m1Repairing[1]);
			cbtnRepairingM1_2.setSelection(m1Repairing[2]);
			cbtnRepairingM1_3.setSelection(m1Repairing[3]);
			cbtnIdleM2_0.setSelection(m2Idle[0]);
			cbtnIdleM2_1.setSelection(m2Idle[1]);
			cbtnBusyM2_0.setSelection(m2Busy[0]);
			cbtnBusyM2_1.setSelection(m2Busy[1]);
			lblBrokenDVDs.setText("Broken DVDs: " + brokenDVDsint.toString());			
			dvdProduced.setText("Dvd's produced: " + producedDVDs.toString());
			if(m4Repairing[0]){
				lblCartridgeSize_0.setText("DVDs left before refill: 0");
			} else {
				lblCartridgeSize_0.setText("DVDs left before refill: " + cartridgeSize_0.toString());
			}
			if(m4Repairing[1]){
				lblCartridgeSize_1.setText("DVDs left before refill: 0");
			} else {
				lblCartridgeSize_1.setText("DVDs left before refill: " + cartridgeSize_1.toString());
			}
			//cbtnCleaningM4_0.setSelection(m3);
			//cbtnCleaningM4_1.setSelection(m3);
			
			cbtnRefillM4_0.setSelection(m4Repairing[1]);
			cbtnRefillM4_1.setSelection(m4Repairing[0]);
			lblCurrentTime.setText("Current time: " + currentTime0.toString());
			
			
			// An eventstep 11 is the "End Simulation" event. If this is the next event, the simulation should stop.
			//while(eventList.peek().eventStep != 11 ) {
				Event e = eventList.remove();
				switch(e.eventStep) {
				case 1: m1ScheduledFinished(e); 
						break;
				case 2: m1StartRepairing(e);
						break;
				case 3: m1FinishedRepairing(e);
						break;
				case 4: m2ScheduledFinished(e);
						m2Int++;
						break;
				case 5: cbScheduledFinished(e);
						cbInt++;
						break;
				case 6: cratesScheduledSwap(e);
						cSwapInt++;
						break;
				case 7: m3_12ScheduledFinished(e);
						m3_12Int++;
						break;
				case 8: m3_3ScheduledFinished(e);
						m3_3Int++;
						break;
				case 9: m4ScheduledFinished(e);
						m4Int++;
						break;
				case 10:hourCheck(e);
						break;
				default: System.out.println("What's happening?!?!");
				
				}
			
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(1096, 570);
		shell.setText("DVD Factory");
		
		dvdProduced = new Label(shell, SWT.NONE);
		dvdProduced.setBounds(882, 298, 182, 25);
		dvdProduced.setText("DVD's produced: 0");
		
		lblBuffer1 = new Label(shell, SWT.NONE);
		lblBuffer1.setBounds(259, 395, 85, 14);
		lblBuffer1.setText("0");
		
		lblBuffer0 = new Label(shell, SWT.NONE);
		lblBuffer0.setBounds(262, 146, 59, 14);
		lblBuffer0.setText("0");
		
		lblCrateFront0 = new Label(shell, SWT.NONE);
		lblCrateFront0.setBounds(551, 146, 59, 14);
		lblCrateFront0.setText("0");
		
		lblCrateFront1 = new Label(shell, SWT.NONE);
		lblCrateFront1.setBounds(551, 438, 59, 14);
		lblCrateFront1.setText("0");
		
		lblCrateIn0 = new Label(shell, SWT.NONE);
		lblCrateIn0.setBounds(693, 93, 59, 14);
		lblCrateIn0.setText("0");
		
		lblCrateIn1 = new Label(shell, SWT.NONE);
		lblCrateIn1.setBounds(693, 339, 59, 14);
		lblCrateIn1.setText("0");
		
		lblCrateBack0 = new Label(shell, SWT.NONE);
		lblCrateBack0.setBounds(838, 146, 59, 14);
		lblCrateBack0.setText("0");
		
		lblCrateBack1 = new Label(shell, SWT.NONE);
		lblCrateBack1.setBounds(838, 438, 59, 14);
		lblCrateBack1.setText("0");
		
		cbtnIdleM1_0 = new Button(shell, SWT.CHECK);
		cbtnIdleM1_0.setBounds(49, 60, 48, 18);
		cbtnIdleM1_0.setText("Idle");
		
		cbtnIdleM1_1 = new Button(shell, SWT.CHECK);
		cbtnIdleM1_1.setBounds(49, 184, 48, 18);
		cbtnIdleM1_1.setText("Idle");
		
		cbtnIdleM1_2 = new Button(shell, SWT.CHECK);
		cbtnIdleM1_2.setBounds(49, 309, 48, 18);
		cbtnIdleM1_2.setText("Idle");
		
		cbtnIdleM1_3 = new Button(shell, SWT.CHECK);
		cbtnIdleM1_3.setBounds(49, 435, 48, 18);
		cbtnIdleM1_3.setText("Idle");
		
		cbtnRepairingM1_0 = new Button(shell, SWT.CHECK);
		cbtnRepairingM1_0.setBounds(93, 60, 85, 18);
		cbtnRepairingM1_0.setText("Repairing");
		
		cbtnRepairingM1_1 = new Button(shell, SWT.CHECK);
		cbtnRepairingM1_1.setBounds(93, 184, 85, 18);
		cbtnRepairingM1_1.setText("Repairing");
		
		cbtnRepairingM1_2 = new Button(shell, SWT.CHECK);
		cbtnRepairingM1_2.setBounds(93, 309, 85, 18);
		cbtnRepairingM1_2.setText("Repairing");
		
		cbtnRepairingM1_3 = new Button(shell, SWT.CHECK);
		cbtnRepairingM1_3.setBounds(93, 435, 85, 18);
		cbtnRepairingM1_3.setText("Repairing");
		
		cbtnIdleM2_0 = new Button(shell, SWT.CHECK);
		cbtnIdleM2_0.setBounds(359, 120, 48, 14);
		cbtnIdleM2_0.setText("Idle");
		
		cbtnIdleM2_1 = new Button(shell, SWT.CHECK);
		cbtnIdleM2_1.setBounds(359, 368, 48, 14);
		cbtnIdleM2_1.setText("Idle");
		
		cbtnBusyM2_0 = new Button(shell, SWT.CHECK);
		cbtnBusyM2_0.setBounds(419, 120, 52, 14);
		cbtnBusyM2_0.setText("Busy");
		
		cbtnBusyM2_1 = new Button(shell, SWT.CHECK);
		cbtnBusyM2_1.setBounds(419, 368, 52, 14);
		cbtnBusyM2_1.setText("Busy");
		
		lblCartridgeSize_0 = new Label(shell, SWT.NONE);
		lblCartridgeSize_0.setBounds(910, 471, 200, 14);
		lblCartridgeSize_0.setText("DVDs left before refill: 0");
		
		lblCartridgeSize_1 = new Label(shell, SWT.NONE);
		lblCartridgeSize_1.setBounds(910, 221, 200, 14);
		lblCartridgeSize_1.setText("DVDs left before refill: 0");
		
		lblBrokenDVDs = new Label(shell, SWT.NONE);
		lblBrokenDVDs.setBounds(359, 298, 116, 14);
		lblBrokenDVDs.setText("Broken DVDs: 0");
		
		/*
		cbtnCleaningM4_0 = new Button(shell, SWT.CHECK);
		cbtnCleaningM4_0.setBounds(648, 218, 94, 18);
		cbtnCleaningM4_0.setText("Cleaning");
		
		cbtnCleaningM4_1 = new Button(shell, SWT.CHECK);
		cbtnCleaningM4_1.setBounds(648, 468, 94, 18);
		cbtnCleaningM4_1.setText("Cleaning");
		*/
		cbtnRefillM4_0 = new Button(shell, SWT.CHECK);
		cbtnRefillM4_0.setBounds(938, 118, 94, 18);
		cbtnRefillM4_0.setText("Refill");
		
		cbtnRefillM4_1 = new Button(shell, SWT.CHECK);
		cbtnRefillM4_1.setBounds(938, 366, 94, 18);
		cbtnRefillM4_1.setText("Refill");
		
		lblCurrentTime = new Label(shell, SWT.NONE);
		lblCurrentTime.setBounds(211, 60, 200, 14);
		lblCurrentTime.setText("Current Time: 0");
		
		dvdFactoryImage = new Label(shell, SWT.NONE);
		dvdFactoryImage.setBounds(0, 0, 1096, 550);

	}
	public Label getDvdProduced() {
		return dvdProduced;
	}
	
	public Label getDvdFactoryImage() {
		return dvdFactoryImage;
	}
	public Label getLblBuffer1() {
		return lblBuffer1;
	}
	public Label getLblBuffer0() {
		return lblBuffer0;
	}
	public Label getLblCrateFront0() {
		return lblCrateFront0;
	}
	public Label getLblCrateFront1() {
		return lblCrateFront1;
	}
	public Label getLblCrateIn0() {
		return lblCrateIn0;
	}
	public Label getLblCrateIn1() {
		return lblCrateIn1;
	}
	public Label getLblCrateBack0() {
		return lblCrateBack0;
	}
	public Label getLblCrateBack1() {
		return lblCrateBack1;
	}
	public Button getCbtnIdleM1_0() {
		return cbtnIdleM1_0;
	}
	public Button getCbtnIdleM1_1() {
		return cbtnIdleM1_1;
	}
	public Button getCbtnIdleM1_2() {
		return cbtnIdleM1_2;
	}
	public Button getCbtnIdleM1_3() {
		return cbtnIdleM1_3;
	}
	
	public Button getCbtnRepairingM1_0() {
		return cbtnRepairingM1_0;
	}
	public Button getCbtnRepairingM1_1() {
		return cbtnRepairingM1_1;
	}
	public Button getCbtnRepairingM1_2() {
		return cbtnRepairingM1_2;
	}
	public Button getCbtnRepairingM1_3() {
		return cbtnRepairingM1_3;
	}
	public Button getCbtnIdleM2_0() {
		return cbtnIdleM2_0;
	}
	public Button getCbtnIdleM2_1() {
		return cbtnIdleM2_1;
	}
	public Button getCbtnBusyM2_0() {
		return cbtnBusyM2_0;
	}
	public Button getCbtnBusyM2_1() {
		return cbtnBusyM2_1;
	}
	public Label getLblBrokenDVDs() {
		return lblBrokenDVDs;
	}
	public Label getLblCartridgeSize_0() {
		return lblCartridgeSize_0;
	}
	public Label getLblCartridgeSize_1() {
		return lblCartridgeSize_1;
	}
	public Button getCbtnCleaningM4_0() {
		return cbtnCleaningM4_0;
	}
	public Button getCbtnCleaningM4_1() {
		return cbtnCleaningM4_1;
	}
	public Button getCbtnRefillM4_0() {
		return cbtnRefillM4_0;
	}
	public Button getCbtnRefillM4_1() {
		return cbtnRefillM4_1;
	}
	public Label getLblCurrentTime() {
		return lblCurrentTime;
	}
}
