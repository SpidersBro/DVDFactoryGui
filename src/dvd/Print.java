package dvd;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;


public class Print {
	static public DecimalFormat df = new DecimalFormat("##########.##");
	
	public static void printDVDThroughputTime(double data){
		Writer writer = null; 
		try { 
			writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("./files/DVDThroughputTime.csv",true), "utf-8")); 
			writer.write(df.format(data) + "\n"); } catch (IOException ex) { 
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
			writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("./files/DVDProductionPerHour.csv",true), "utf-8")); 
			writer.write(df.format(data) + "\n"); } catch (IOException ex) { 
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
			case 1: fileName = (number + "M1IdleTime.csv");
			break;
			case 2: fileName = (number + "M2IdleTime.csv");
			break;
			case 20: fileName = (number + "M2FIdleTime.csv");
			break;
			case 21: fileName = (number + "M2BIdleTime.csv");
			break;
			case 3: fileName = (number + "M3IdleTime.csv");
			break;
			case 30: fileName = (number + "M3FIdleTime.csv");
			break;
			case 31: fileName = (number + "M3BIdleTime.csv");
			break;
			case 4: fileName = (number + "M4IdleTime.csv");
			break;
			case 5: fileName = (number + "CBIdleTime.csv");
			break;
			default: fileName = ("printIdleTimeFailing.txt");
		}
		Writer writer = null; 
		try { 
			writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("./files/" + fileName,true), "utf-8")); 
			writer.write(df.format(data) + ";"); 
		} 
		catch (IOException ex) { 
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
			case 1: fileName = (number + "M1IdleTime.csv");
			break;
			case 2: fileName = (number + "M2IdleTime.csv");
			break;
			case 20: fileName = (number + "M2FIdleTime.csv");
			break;
			case 21: fileName = (number + "M2BIdleTime.csv");
			break;
			case 3: fileName = (number + "M3IdleTime.csv");
			break;
			case 30: fileName = (number + "M3FIdleTime.csv");
			break;
			case 31: fileName = (number + "M3BIdleTime.csv");
			break;
			case 4: fileName = (number + "M4IdleTime.csv");
			break;
			case 5: fileName = (number + "CBIdleTime.csv");
			break;
			default: fileName = ("printEndIdleTimeFailing.txt");
		}
		Writer writer = null; 
		try { 
			writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("./files/" + fileName,true), "utf-8")); 
			writer.write(df.format(data) + "\n"); } catch (IOException ex) { 
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

