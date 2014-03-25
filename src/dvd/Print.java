package dvd;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;


public class Print {
	
	public static void printDVDThroughputTime(double data){
		Writer writer = null; 
		try { 
			writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("./files/DVDThroughputTime.txt",true), "utf-8")); 
			writer.write(data + ","); } catch (IOException ex) { 
				// report 
				} 
		finally { 
			try {
				writer.close();
			} catch (Exception ex) {
			} 
		}
	}
	
	public static void printDVDProductionPerHour(double data){
		Writer writer = null; 
		try { 
			writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("./files/DVDProductionPerHour.txt",true), "utf-8")); 
			writer.write(data + ","); } catch (IOException ex) { 
				// report 
				} 
		finally { 
			try {
				writer.close();
			} catch (Exception ex) {
			} 
		}
	}
	
	public static void printStartIdleTime(double data, int type, int number){
		String fileName;
		switch(type){
			case 1: fileName = (number + "M1StartIdleTime.txt");
			break;
			case 2: fileName = (number + "M2StartIdleTime.txt");
			break;
			case 20: fileName = (number + "M2StartFIdleTime.txt");
			break;
			case 21: fileName = (number + "M2StartBIdleTime.txt");
			break;
			case 3: fileName = (number + "M3StartIdleTime.txt");
			break;
			case 30: fileName = (number + "M3StartFIdleTime.txt");
			break;
			case 31: fileName = (number + "M3StartBIdleTime.txt");
			break;
			case 4: fileName = (number + "M4StartIdleTime.txt");
			break;
			case 5: fileName = (number + "CBStartIdleTime.txt");
			break;
			default: fileName = ("printIdleTimeFailing.txt");
		}
		Writer writer = null; 
		try { 
			writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("./files/" + fileName,true), "utf-8")); 
			writer.write(data + ","); } catch (IOException ex) { 
				// report 
				} 
		finally { 
			try {
				writer.close();
			} catch (Exception ex) {
			} 
		}
	}
	
	public static void printEndIdleTime(double data, int type, int number){
		String fileName;
		switch(type){
			case 1: fileName = (number + "M1EndIdleTime.txt");
			break;
			case 2: fileName = (number + "M2EndIdleTime.txt");
			break;
			case 20: fileName = (number + "M2EndFIdleTime.txt");
			break;
			case 21: fileName = (number + "M2EndBIdleTime.txt");
			break;
			case 3: fileName = (number + "M3EndIdleTime.txt");
			break;
			case 30: fileName = (number + "M3EndFIdleTime.txt");
			break;
			case 31: fileName = (number + "M3EndBIdleTime.txt");
			break;
			case 4: fileName = (number + "M4EndIdleTime.txt");
			break;
			case 5: fileName = (number + "CBEndIdleTime.txt");
			break;
			default: fileName = ("printEndIdleTimeFailing.txt");
		}
		Writer writer = null; 
		try { 
			writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("./files/" + fileName,true), "utf-8")); 
			writer.write(data + ","); } catch (IOException ex) { 
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

