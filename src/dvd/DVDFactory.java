package dvd;



import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

import org.apache.commons.math3.distribution.*;



public class DVDFactory {
	
/////-------------------------------------------- Testing	-------------------------------\\\\\\\\\\	
	public static int printTime = 0;
	public static int m1Int=0;
	public static int m2Int=0;
	public static int m3Int=0;
	public static int CbInt=0;
	public static int m1FRInt=0;
	public static int m4Int=0;
	public static int swapInt=0;
	public static int dvdAfterStable=0;
/////-------------------------------------------- Testing	-------------------------------\\\\\\\\\\	
	public static double totalRepairTime = 0;
	public static int repairNumber = 0;
	public static int dvdsM2 = 0;
	public static int brokenDVDs = 0;
	public static long randCalls;
	public static boolean endOfSimulation = false;

	
	
/////------------------------------------ Declarations and init() --------------------------\\\\\\\\\
	
	
	

	// These are the variables we can increase or decrease and may  the production.
	public static double currentTime;
	public static int bufferSize;
	public static int crateSize;
	public static int amountM1 = 6;
	public static int amountM2 = 2;
	public static int amountM3 = 2;
	public static int amountM4 = 2;
	public static Random random = new Random();
	
	
	public static PriorityQueue<Event> eventList = new PriorityQueue<Event>();
	
	
	// states for all machines 1
	public static boolean[] m1Repairing = new boolean[amountM1];
	public static boolean[] m1Idle = new boolean[amountM1];
	public static double[] m1RestTime = new double[amountM1];
	public static ArrayList<DVD> m1DVDWaiting = new ArrayList<DVD>();
	public static double[] m1StartRepairTime = new double[amountM1];
	public static double[] m1totalRepairTime = new double[amountM1];
	public static boolean firstPrint1;
	public static boolean firstPrint2;
	public static boolean firstPrint3;
	public static boolean firstPrint4;
	public static boolean firstPrint5;
	
	// states for all buffers 
	public static ArrayList<Queue<DVD>> bufferList = new ArrayList<Queue<DVD>>();

	// state for all machines 2
	public static boolean[] m2IdleFront = new boolean[amountM2];
	public static boolean[] m2IdleBack = new boolean[amountM2];
	public static ArrayList<DVD> m2WaitingDVD = new ArrayList<DVD>();
	public static boolean[] m2Busy = new boolean[amountM2];
	
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
	public static int[] m3_numBlockedNozzles = new int[amountM3];
	
	// state for all machine 4
	public static boolean[] m4Idle = new boolean[amountM4];
	public static boolean[] m4Repairing = new boolean[amountM4];
	public static int[] cartridge = new int[amountM4]; 
	public static int[] countDVDs = new int[amountM4];


	// variables needed for Performance measures
	public static double totalThroughputTime;
	public static double averageThroughputTime;
	
	public static double avgDVDPerHour;
	public static ArrayList<DVD> producedDVDList = new ArrayList<DVD>();
	public static int lastDVD;
	
	public static double totalM1IdleTime;
	public static double totalM2IdleTime;
	public static double totalM2IdleFrontTime;
	public static double totalM2IdleBackTime;
	public static double totalCBIdleTime;
	public static double totalM3IdleTime;
	public static double totalM3IdleFrontTime;
	public static double totalM3IdleBackTime;
	public static double totalM4IdleTime;
	
	public static double[] aTotalM1IdleTime = new double[amountM1];
	public static double[] aTotalM2IdleTime = new double[amountM2];
	public static double[] aTotalM2IdleFrontTime = new double[amountM2];
	public static double[] aTotalM2IdleBackTime = new double[amountM2];
	public static double[] aTotalCBIdleTime = new double[amountM2];
	public static double[] aTotalM3IdleTime = new double[amountM3];
	public static double[] aTotalM3IdleFrontTime = new double[amountM3];
	public static double[] aTotalM3IdleBackTime = new double[amountM3];
	public static double[] aTotalM4IdleTime = new double[amountM4];
	
	public static double[] m1IdleTime = new double[amountM1];
	public static double[] m2IdleTime = new double[amountM2];
	public static double[] m2IdleFrontTime = new double[amountM2];
	public static double[] m2IdleBackTime = new double[amountM2];
	public static double[] m3IdleTime = new double[amountM3];
	public static double[] m3IdleFrontTime = new double[amountM3];
	public static double[] m3IdleBackTime = new double[amountM3];
	public static double[] m4IdleTime = new double[amountM4];
	public static double[] pmcbIdleTime = new double[amountM2];

	
	public static boolean[] m3Idle = new boolean[amountM3];
	
	public static double totalM1RepairTime;
	public static double totalM3CleaneTime;
	public static double totalM4RefillTime;

	
	
	//Initial state is declared here
	public static void init(int bSize, int cSize){
		printTime++;
		
		bufferSize = bSize;
		crateSize = cSize;
		currentTime = 0;
		
		firstPrint1 = true;
		firstPrint2 = true;
		firstPrint3 = true;
		
		totalThroughputTime = 0.0;
		averageThroughputTime = 0.0;
		
		avgDVDPerHour = 0.0;
		lastDVD = 0;
		
		totalM1IdleTime = 0.0;
		totalM2IdleTime = 0.0;
		totalM2IdleFrontTime = 0.0;
		totalM2IdleBackTime = 0.0;
		totalCBIdleTime = 0.0;
		totalM3IdleTime = 0.0;
		totalM3IdleFrontTime = 0.0;
		totalM3IdleBackTime = 0.0;
		totalM4IdleTime = 0.0;
		
		totalM1RepairTime = 0.0;
		totalM3CleaneTime = 0.0;
		totalM4RefillTime = 0.0;
		
		
		// Production Step 1 is running and needs initial events for the process to begin
		// We need to create m1Amount*2 initial events, 
		// One event for each machine in production Step 1, 
		// and a scheduled breaking time for each machine in production step 1.
		for ( int i = 0; i < amountM1; i++){
			m1Repairing[i] = false;
			m1Idle[i] = false;
			m1IdleTime[i] = 0;
			m1RestTime[i] = 0;
			aTotalM1IdleTime[i] = 0;
			DVD dvd = new DVD(0,0);
			Event m1FinishedEvent = new Event((currentTime+eventTimeM1()),1,i,dvd);
			Event m1StartRepairEvent = new Event((currentTime+eventTimeStartRepairM1()),2,i,null);
			eventList.add(m1FinishedEvent);
			eventList.add(m1StartRepairEvent);
			m1DVDWaiting.add(i,null);
		}
		
		// ProductionStep 2 is running, and all buffers are empty
		for ( int i = 0; i < amountM2; i++){
			cbIdle[i] = false;
			m2IdleFront[i] = false;
			m2IdleBack[i] = false;
			cbIdleTime[i] = 0;
			pmcbIdleTime[i] = 0;
			m2IdleFrontTime[i] = 0;
			m2IdleBackTime[i] = 0;
			m2Busy[i] = false;
			aTotalM2IdleTime[i] = 0;
			aTotalM2IdleFrontTime[i] = 0;
			aTotalM2IdleBackTime[i] = 0;
			aTotalCBIdleTime[i] = 0;
			
			
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
			m3Idle[i] = false;
			m3IdleTime[i] = 0;
			m3IdleFrontTime[i] = 0;
			m3IdleBackTime[i] = 0;
			aTotalM3IdleTime[i] = 0;
			aTotalM3IdleFrontTime[i] = 0;
			aTotalM3IdleBackTime[i] = 0;
			m3_3WaitingForSwap[i] = false; // Same as IdleBack
			ArrayList<DVD> crateFront = new ArrayList<DVD>();
			ArrayList<DVD> crateIn = new ArrayList<DVD>();
			ArrayList<DVD> crateBack = new ArrayList<DVD>();
			
			crateFrontList.add(crateFront);
			crateInList.add(crateIn);
			crateBackList.add(crateBack);
		}
		
		// ProductionStep 4 is running, and cartridgeSize is initialized
		for ( int i = 0; i < amountM4; i++){
			m4Idle[i] = false;
			m4Repairing[i] = false;
			aTotalM4IdleTime[i] = 0;
			cartridge[i] = getCartridgeSize();
			countDVDs[i] = 0;
		}
		
		
		Event pmDVDProductionPerHour = new Event((60*60),12,0,null);
		eventList.add(pmDVDProductionPerHour);
		
	}
	
	
	
/////------------------------------------ Event Handlers--------------------------------------\\\\\\\\\


	private static void m1ScheduledFinished(Event e){
		currentTime = e.eventTime;
		if (e.dvd == null) {
			System.out.println("Im not real in M1");
		}
		
		// calculates which buffer belongs to which machine
		int indexBuffer;
		if(e.machineNum == 0 || e.machineNum == 1 || e.machineNum ==2 ) {
			indexBuffer = 0;
		} else {
			indexBuffer = 1;
		}
		if(!m1Repairing[e.machineNum] && m1totalRepairTime[e.machineNum] == 0) { 
			if(bufferList.get(indexBuffer).size() < bufferSize) {
				DVD new_dvd = new DVD(currentTime, 0);
				Event m1Finished = new Event(eventTimeM1(),1,e.machineNum,new_dvd);
				eventList.add(m1Finished);
				if (bufferList.get(indexBuffer).isEmpty() && !m2Busy[indexBuffer]) {	
					Event m2Finished = new Event(eventTimeM2(),4,indexBuffer,e.dvd);	
					eventList.add(m2Finished);
					m2Busy[indexBuffer] = true;  
				} else {
					bufferList.get(indexBuffer).add(e.dvd);
				}	
				// Machines go from 4 to 2, so we need to change machineNum accordingly
				e.machineNum = indexBuffer;
				m1Int++;
			} else {
				m1Idle[e.machineNum] = true;
				if(e.dvd == null){
					System.out.println("im not real in m1 check 2");
				}
				m1DVDWaiting.set(e.machineNum, e.dvd);
			}
		} else if (m1Repairing[e.machineNum] && m1totalRepairTime[e.machineNum] == 0){
			m1RestTime[e.machineNum] = currentTime - m1StartRepairTime[e.machineNum];
			if(e.dvd == null){
				System.out.println("im not real in m1 check 3");
			}
			m1DVDWaiting.set(e.machineNum, e.dvd);
		} else {			
			Event m1Finished = new Event(currentTime+m1totalRepairTime[e.machineNum],1,e.machineNum,e.dvd);
			eventList.add(m1Finished);
			m1totalRepairTime[e.machineNum] = 0;
		}
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
		double eventM1time = (currentTime+m1RestTime[e.machineNum]);
		
		/*
		for(int i = 0; i < m1DVDWaiting.size(); i++){
			if (m1DVDWaiting.get(i)==null){
				System.out.println("De dvd " + i +  " is null");
				System.out.println(e.machineNum);
				//System.out.println("ArrayList size is: " + m1DVDWaiting.size());
			}
		}
		*/
		if (!(m1DVDWaiting.get(e.machineNum)==null)){
			Event m1Finished = new Event(eventM1time,1,e.machineNum,m1DVDWaiting.get(e.machineNum));
			m1DVDWaiting.set(e.machineNum,null);
			m1RestTime[e.machineNum] = 0;
			eventList.add(m1Finished);
		} else{
			m1totalRepairTime[e.machineNum] = currentTime - m1StartRepairTime[e.machineNum];
		}
		m1Repairing[e.machineNum] = false;
		Event m1StartRepairEvent = new Event(eventTimeStartRepairM1(),2,e.machineNum,null);
		eventList.add(m1StartRepairEvent);
	}
	
	private static void m2ScheduledFinished(Event e) { 
		currentTime = e.eventTime;
		if (e.dvd == null) {
			System.out.println(" Im not real");
		}
		// Again, we still need to check this PRNG.
		double dvdBrokenRand;
		dvdBrokenRand = random.nextDouble();
		randCalls++;
		
		if (!cbIdle[e.machineNum]){
			m2Busy[e.machineNum] = false;
			if (dvdBrokenRand > .02) { // DVDs to conveyor belt
				Event cbScheduledFinished = new Event((currentTime+(5*60)),5,e.machineNum,e.dvd);
				eventList.add(cbScheduledFinished);
			} else { // Broken DVDs
				brokenDVDs++;
			}
			
			// In case any M1 of this M2 is Idle	
			int option1;
			int option2;
			int option3;
			if(e.machineNum == 0){
				option1 = 0;
				option2 = 1;
				option3 = 2;
			} else {
				option1 = 3;
				option2 = 4;
				option3 = 5;
			} 
			if (m1Idle[option1]){
				Event m1Finished = new Event(currentTime,1,option1,m1DVDWaiting.get(option1));
				eventList.add(m1Finished);
				m1DVDWaiting.set(option1,null);
				m1Idle[option1] = false;
			}
			if (m1Idle[option2]){
				Event m1Finished = new Event(currentTime,1,option2,m1DVDWaiting.get(option2));
				eventList.add(m1Finished);
				m1DVDWaiting.set(option2,null);
				m1Idle[option2] = false;
			}
			if (m1Idle[option3]){
				Event m1Finished = new Event(currentTime,1,option3,m1DVDWaiting.get(option3));
				eventList.add(m1Finished);
				m1DVDWaiting.set(option3,null);
				m1Idle[option3] = false;
			}
			
			// If buffer not empty schedule new m2Finished
			if(!bufferList.get(e.machineNum).isEmpty()){
				m2IdleFront[e.machineNum] = false;
				DVD new_dvd = bufferList.get(e.machineNum).remove(); 
				if(new_dvd == null){
					System.out.println("The DVD in the buffer is null");
				}
				Event m2Finished = new Event(eventTimeM2(),4,e.machineNum,new_dvd);
				eventList.add(m2Finished);
				m2Busy[e.machineNum] = true;
			} else {
				m2IdleFront[e.machineNum] = true;
			}
		// If conveyor belt is idle set true
		} else {
			m2IdleBack[e.machineNum] = true;
			m2WaitingDVD.set(e.machineNum, e.dvd);
		}
	}
	
	private static void cbScheduledFinished(Event e) {
		currentTime = e.eventTime;
		if(!cbIdle[e.machineNum]){
			// If not 20 put DVD in crate
			if(crateFrontList.get(e.machineNum).size() < crateSize){ 
				crateFrontList.get(e.machineNum).add(e.dvd);
				// If it becomes 20 by doing so create swap crates event. 
				if(crateFrontList.get(e.machineNum).size() == crateSize){
					Event swapCrates = new Event(currentTime,6,e.machineNum,null);
					eventList.add(swapCrates);
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
		for(int j = 0; j < amountM3; j++ ) {
			for(int k = 0; k < amountM4; k++) {
				ArrayList<DVD> tempCrateIn = new ArrayList<DVD>();
				/*
				 * 
				 * A crate must be swapped from Back to In when:
				 * 		crateBack.size() = 0 AND crateIn.size() = 20. 
				 * 		For any machines in the line
				 * 
				 * OR
				 */			
				if(  crateBackList.get(k).isEmpty() && crateInList.get(j).size() == crateSize && m3_3WaitingForSwap[j]) {
					//Swap crateBack and CrateIn
					tempCrateIn = (ArrayList<DVD>) crateInList.get(j).clone();
					crateBackList.set(k,tempCrateIn);
					crateInList.get(j).clear();
					
					//If M4 is not repairing, it should now start again.
					if(!m4Repairing[k]) {
						Event m4ScheduledFinished = new Event(eventTimeM4(),9,k,null);
						eventList.add(m4ScheduledFinished);
					}
					m3_3WaitingForSwap[j] = false;
					m4Idle[k] = false;
				}
			}
		}
		/* 
		 * 
		 *
		 * A crate must be swapped from In to Front when:
		 * 		crateIn.size() = 0  AND crateFront.size() = 20.
		 * 		(this is the case in the initial state and otherwise only when 2 and 3 already have been swapped)
		 * 
		 */	
		for(int i = 0; i < amountM2; i++ ){
			for(int j = 0; j < amountM3; j++ ) {	
				ArrayList<DVD> tempCrateFront = new ArrayList<DVD>();		
				if (crateInList.get(j).isEmpty() && crateFrontList.get(i).size() == crateSize ) {
					//Swap crateIn with crateFront
					tempCrateFront = (ArrayList<DVD>) crateFrontList.get(i).clone();
					crateInList.set(j,tempCrateFront);
					crateFrontList.get(i).clear();
					// If the front crate is now empty, start up the conveyor belt in front of the crate again.
					while(!cbWaitingDVD.get(i).isEmpty()){
						DVD this_DVD = cbWaitingDVD.get(i).remove();
						Event CBfinished = new Event((currentTime + cbWaitingTime.get(i).remove()),5,i,this_DVD);
						eventList.add(CBfinished);
					}
					cbIdle[i] = false;
					if(m2IdleBack[i]) {
						m2IdleBack[i] = false;
						if ( m2WaitingDVD.get(i) == null) {
							System.out.println("The dvd from m2WaitingDVD is null");
						}
						Event m2ScheduledFinished = new Event(currentTime,4,i,m2WaitingDVD.get(i));
						m2WaitingDVD.set(i,null);
						eventList.add(m2ScheduledFinished);
					} else if(!bufferList.get(i).isEmpty()){
						//m2IdleBack[i] = false;
						Event m2ScheduledFinished = new Event(eventTimeM2(),4,i,bufferList.get(i).remove());
						eventList.add(m2ScheduledFinished);
					}
					// And if crateIn is now full, M3 should also start again.
					Event m3_12ScheduledFinished = new Event(eventTimeM3_12(),7,j,null);
					eventList.add(m3_12ScheduledFinished);
				}
			}
		}
	}
	
    private static void m3_12ScheduledFinished(Event e) {
    	currentTime = e.eventTime;
           
        //Delay in seconds
        int delay = 0;
        m3_numBlockedNozzles[e.machineNum] = 0;
        
        // Make a random object
        double nozzleBlockChance;
       
        // Make temporary Arraylist for easy access.
        ArrayList<DVD> tempCrateIn = new ArrayList<DVD>();
        tempCrateIn = crateInList.get(e.machineNum);
       
        // Loop over the crate that is currently in the machine
        // check for each dvd if the nozzle gets blocked.
            for(int i = 0; i < tempCrateIn.size(); i++){
                    nozzleBlockChance = random.nextDouble();
                    randCalls++;
                    if (nozzleBlockChance < .03) {
                            delay += 300;
                            m3_numBlockedNozzles[e.machineNum]++;
                    }
            }
            //TODO
            //totalM3CleaneTime =+ delay; // for perfomance measure 
            
            Event m3_3Finished = new Event(eventTimeM3_3()+delay,8,e.machineNum,null);
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

				crateBackList.get(e.machineNum).trimToSize();

				this_dvd.endTime = currentTime;
				producedDVDList.add(this_dvd);
				dvdThroughputTime(this_dvd);
				
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

	private static void finishedSimulation(Event e){
		currentTime = e.eventTime;
		bufferList.clear();
		m1DVDWaiting.clear();
		m2WaitingDVD.clear();
		cbWaitingTime.clear();
		cbWaitingDVD.clear();
		crateFrontList.clear();
		crateInList.clear();
		crateBackList.clear();
		firstPrint1 = true;
		firstPrint2 = true;
		firstPrint3 = true;
		eventList.clear();
		endOfSimulation = true;
	}



/////------------------------------------ Event time calculations --------------------------\\\\\\\\\
	

	public static double eventTimeM1(){
		double scale=3.51;
		double shape=1.23;
		LogNormalDistribution log = new LogNormalDistribution(scale, shape);
		
		return currentTime + log.sample();
	}
	
	private static double eventTimeStartRepairM1() {
		ExponentialDistribution exp = new ExponentialDistribution(8*60*60);
		double repairM1 = exp.sample();
		totalM1RepairTime =+ repairM1;
		return currentTime + repairM1;
	}
	
	private static double eventTimeM1FinishedRepair() {
		ExponentialDistribution exp = new ExponentialDistribution(2*60*60);
		return currentTime + exp.sample();
	}
	
	public static double eventTimeM2() {
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
	
	public static double eventTimeM4() {
		double m4Time = random.nextInt(10) + random.nextDouble() + 20;
		randCalls += 2;
		return currentTime + m4Time;
		
	}
	
	private static int getCartridgeSize(){
		double rand1, rand2;
		
		// I think this one has a period of 2^48, gotta check though.
		rand1 = random.nextDouble();
		rand2 = random.nextDouble();
		randCalls += 2;

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
		double m4RefillTime = normalDis.sample();
		totalM4RefillTime += m4RefillTime;
		return  normalDis.sample();
	}

/////------------------------------------ Performance Measures --------------------------\\\\\\\\\
	
	public static void dvdThroughputTime(DVD dvd) {
        double throughputTime;
        throughputTime = dvd.endTime - dvd.startTime;

        
        if(currentTime >= 24*60*60) {
                dvdAfterStable++;
                totalThroughputTime += throughputTime;
        //        if(dvdAfterStable%100 == 0) {
        //                averageThroughputTime = totalThroughputTime/ 100;
        //                Print.printDVDThroughputTime(averageThroughputTime);
        //                totalThroughputTime = 0;
        //        }
        }
}
	
    public static void dvdProductionPerHour(Event e){
        
        currentTime = e.eventTime;
        /*
        System.out.println("M1 " +m1Int);
		System.out.println("M2 " +m2Int);
		m2Int=0;		
		System.out.println("M3 " +m3Int);
		m3Int=0;
		System.out.println("CB " +CbInt);
		CbInt=0;
		System.out.println("mFR " +m1FRInt);
		m1FRInt=0;
		System.out.println("m4 " +m4Int);
		m4Int=0;
		System.out.println("swap " +swapInt);
		swapInt=0;
		System.out.println();

        */
        Event pmDVDProductionPerHour = new Event(currentTime+(60*60),12,0,null);
        eventList.add(pmDVDProductionPerHour);
       // avgDVDPerHour = (producedDVDList.size() - lastDVD);
        
        
        if(currentTime <= 24*60*60) {
        	lastDVD = producedDVDList.size();    
        }
        m1Int=0;
}

    
	public static double idleTime(double[] startIdleTime, double[] totalIdleTime, boolean[] mIdle, int machine ){
		double ttotalIdleTime = 0;
		for(int i = 0; i < mIdle.length;i++){
			if(mIdle[i] && startIdleTime[i] == 0) {
				startIdleTime[i] = currentTime;
				//Print.printStartIdleTime(currentTime, machine, i);
			} else if (mIdle[i] && startIdleTime[i] != 0) {
				totalIdleTime[i] += (currentTime - startIdleTime[i]);
				startIdleTime[i] = currentTime;
			} else if (!mIdle[i] && startIdleTime[i] != 0) {
				totalIdleTime[i] += (currentTime - startIdleTime[i]);
				startIdleTime[i] = 0;
				//Print.printEndIdleTime(currentTime, machine, i);
			}
		ttotalIdleTime += totalIdleTime[i];
		}
		return ttotalIdleTime;
	}
	
	private static boolean[] mIdle(boolean[] front, boolean[] back) {
		boolean[] idle = new boolean[front.length];
		for(int i = 0; i < front.length; i++){
			idle[i] |= (front[i] || back[i]);
		}
		return idle;
	}
	
	private static boolean[] m3IdleFront(){
		boolean[] front = new boolean[amountM3];
		for(int i = 0; i< amountM3; i++) {
			front[i] |= crateInList.get(i).isEmpty();
		}
		return front;
	}
	
	
/////------------------------------------ nextEvent() --------------------------\\\\\\\\\

	public static void nextEvent(){
		Event e = eventList.remove();
		switch(e.eventStep) {
		case 1: m1ScheduledFinished(e); 
				//m1Int++;
				break;
		case 2: m1StartRepairing(e);
				break;
		case 3: m1FinishedRepairing(e);
				m1FRInt++;
				break;
		case 4: m2ScheduledFinished(e);
				m2Int++;
				break;
		case 5: cbScheduledFinished(e);
				CbInt++;
				break;
		case 6: cratesScheduledSwap(e);
				swapInt++;
				break;
		case 7: m3_12ScheduledFinished(e);
				m3Int++;
				break;
		case 8: m3_3ScheduledFinished(e);
				break;
		case 9: m4ScheduledFinished(e);
				m4Int++;
				break;
		case 11:finishedSimulation(e);
				break;
		case 12:dvdProductionPerHour(e); // Only here for output of performance measures, not a real event.
				break;
		default: System.out.println("This is not an event. Eventstep is: " + e.eventStep);
		}
		
		
		if(!endOfSimulation && currentTime > 24*60*60 ){
			totalM1IdleTime =  idleTime(m1IdleTime, aTotalM1IdleTime, m1Idle,1);
			totalM2IdleFrontTime = idleTime(m2IdleFrontTime, aTotalM2IdleFrontTime, m2IdleFront,20);
			totalM2IdleBackTime = idleTime(m2IdleBackTime, aTotalM2IdleBackTime, m2IdleBack,21);
			totalM2IdleTime = idleTime(m2IdleTime, aTotalM2IdleTime, mIdle(m2IdleFront,m2IdleBack),2);
			totalCBIdleTime = idleTime(pmcbIdleTime, aTotalCBIdleTime, cbIdle,5);
			totalM3IdleFrontTime = idleTime(m3IdleFrontTime, aTotalM3IdleFrontTime, m3IdleFront(),30);
			totalM3IdleBackTime = idleTime(m3IdleBackTime, aTotalM3IdleBackTime, m3_3WaitingForSwap,31);
			totalM3IdleTime = idleTime(m3IdleTime, aTotalM3IdleTime, mIdle(m3IdleFront(),m3_3WaitingForSwap),3);
			totalM4IdleTime = idleTime(m4IdleTime, aTotalM4IdleTime, m4Idle,4); 
		}
	}
}




