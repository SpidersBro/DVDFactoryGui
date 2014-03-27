package dvd;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Test {
	
	public static void testRun(int bufferSize, int batchSize, double endTime){
		
		
		DVDFactory.init(bufferSize,batchSize);

		// This is the ending event. It is currently set at endTime.
		Event endSimulationEvent = new Event(endTime,11,0,null);
		DVDFactory.eventList.add(endSimulationEvent);
		
		while(!DVDFactory.endOfSimulation){
			DVDFactory.nextEvent();
		}
	}
	
	
	
	public static void testLogNormalm1() {
		
		Writer writer = null; 
		for(int i = 0; i < 500; i++){
			try { 
				writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("./files/" + "logNormalm1.csv",true), "utf-8")); 
				writer.write( DVDFactory.eventTimeM1() + "\n"); } catch (IOException ex) { 
					// report 
					} 
			finally { 
				try {
					writer.close();
				} catch (Exception ex) {
				} 
			}
			
		}
		

	}
	
	public static void testLogNormalm2() {
		
		Writer writer = null; 
		for(int i = 0; i < 500; i++){
			try { 
				writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("./files/" + "logNormalm2.csv",true), "utf-8")); 
				writer.write( DVDFactory.eventTimeM2() + "\n"); } catch (IOException ex) { 
					// report 
					} 
			finally { 
				try {
					writer.close();
				} catch (Exception ex) {
				} 
			}
			
		}
		

	}
	
	public static void uniformDism4() {
		
		Writer writer = null; 
		for(int i = 0; i < 500; i++){
			try { 
				writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("./files/" + "uniformDism4.csv",true), "utf-8")); 
				writer.write( DVDFactory.eventTimeM4() + "\n"); } catch (IOException ex) { 
					// report 
					} 
			finally { 
				try {
					writer.close();
				} catch (Exception ex) {
				} 
			}
			
		}
		

	}
	
	
	
	
	
}