package dvd;

public class Test {
	
	public static void testRun(int bufferSize, int batchSize, double endTime){
		
		DVDFactory.init(bufferSize,batchSize);
		
		// This is the ending event. It is currently set at 7 days.
		Event endSimulationEvent = new Event(endTime,11,0,null);
		DVDFactory.eventList.add(endSimulationEvent);
		
		while(!DVDFactory.eventList.isEmpty()){
			DVDFactory.nextEvent();
		}
				
		
	}
	

}