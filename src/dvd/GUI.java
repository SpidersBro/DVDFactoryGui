package dvd;



import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class GUI {
	
/////------------------------ Declare variables for the GUI ----------------------------\\\\\\\\\
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
		private Button cbtnRefillM4_0;
		private Button cbtnRefillM4_1;
		private Label lblCurrentTime;
		private Button cbtnBusyM3;
		private Button cbtnBusyM3_1;
		private Label lblNozzlesBlocked0;
		private Label lblNozzlesBlocked1;
		private Button btnNextEvent;
		private Button btnNewButton;
		private Label lblThroughputTime;
		private Label lblDvdsProducedPer;
		private Label lblIdleTimeM1;
		private Label lblIdleTimeFrontM2;
		private Label lblPerformanceMeasures;
		private Label lblIdleTimeBackM2;
		private Label lblIdleTimeMachineM3;
		private Label lblIdleTimeMachineM2;
		private Label lblIdleTimeMachineM4;
		private Label lblIdleTimeConveyor;
		private Label lblIdleTimeFrontM3;
		private Label lblIdleTimeBackM3;
		
		

/////---------------------------------------- Main loop ----------------------------------\\\\\\\\\

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		
		// For testing
		
		int bufferSize = 10000;
		int crateSize = 20;
		double endTime = 11*24*60*60;
		
		int[] crateSizes = {1,5,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,30,40,50,60,70,80,100};
		
		//for(int j = 0; j < crateSizes.length; j++){
			for(int i = 0; i < 40; i++){
				DVDFactory.endOfSimulation = false;
				Test.testRun(bufferSize, crateSize, endTime); 
				System.out.println("Code has ran for: " + endTime/(24*3600)*(i+1) + " days");
				System.out.println("Average throughput time in minutes: " + (DVDFactory.totalThroughputTime /DVDFactory.producedDVDList.size())/60);
				System.out.println("Current produced DVD's per hour: " + DVDFactory.producedDVDList.size()/(DVDFactory.currentTime/3600));
				System.out.println("Amount of DVD's produced: " + DVDFactory.producedDVDList.size());
				System.out.println("This is run number: " + (i+1) );
				DVDFactory.producedDVDList.clear();
			}
		//}
			
		
		// Comment the block below to disable GUI
		/*
		try {
			GUI window = new GUI();
			DVDFactory.init(20,20);
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		
	}
	
	
	
/////------------------------------------  Graphical User Interface  --------------------------------\\\\\\\\\

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		Image simImage = new Image(display,
				   "./img/simImage.png");
		dvdFactoryImage.setImage(simImage);
		
		
		
		
		
		
		while (!shell.isDisposed()) {	
			
			
			
			Integer producedDVDs = new Integer(DVDFactory.producedDVDList.size());
			Integer	dvdsInBuffer0 = new Integer(DVDFactory.bufferList.get(0).size());
			Integer	dvdsInBuffer1 = new Integer(DVDFactory.bufferList.get(1).size());
			Integer	dvdsInCrateFront0 = new Integer(DVDFactory.crateFrontList.get(0).size());
			Integer	dvdsInCrateFront1 = new Integer(DVDFactory.crateFrontList.get(1).size());
			Integer	dvdsInCrateIn0 = new Integer(DVDFactory.crateInList.get(0).size());
			Integer	dvdsInCrateIn1 = new Integer(DVDFactory.crateInList.get(1).size());
			Integer	dvdsInCrateBack0 = new Integer(DVDFactory.crateBackList.get(0).size());
			Integer	dvdsInCrateBack1 = new Integer(DVDFactory.crateBackList.get(1).size());
			Integer brokenDVDsint = new Integer(DVDFactory.brokenDVDs);
			Integer cartridgeSize_0 = new Integer(DVDFactory.cartridge[0]-DVDFactory.countDVDs[0]);
			Integer cartridgeSize_1 = new Integer(DVDFactory.cartridge[1]-DVDFactory.countDVDs[1]);
			DecimalFormat df = new DecimalFormat("###.##");
			
			lblBuffer0.setText(dvdsInBuffer0.toString());
			lblBuffer1.setText(dvdsInBuffer1.toString());
			lblCrateFront0.setText(dvdsInCrateFront0.toString());
			lblCrateFront1.setText(dvdsInCrateFront1.toString());
			lblCrateIn0.setText(dvdsInCrateIn0.toString());
			lblCrateIn1.setText(dvdsInCrateIn1.toString());
			lblCrateBack0.setText(dvdsInCrateBack0.toString());
			lblCrateBack1.setText(dvdsInCrateBack1.toString());
			cbtnIdleM1_0.setSelection(DVDFactory.m1Idle[0]);
			cbtnIdleM1_1.setSelection(DVDFactory.m1Idle[1]);
			cbtnIdleM1_2.setSelection(DVDFactory.m1Idle[2]);
			cbtnIdleM1_3.setSelection(DVDFactory.m1Idle[3]);
			cbtnRepairingM1_0.setSelection(DVDFactory.m1Repairing[0]);
			cbtnRepairingM1_1.setSelection(DVDFactory.m1Repairing[1]);
			cbtnRepairingM1_2.setSelection(DVDFactory.m1Repairing[2]);
			cbtnRepairingM1_3.setSelection(DVDFactory.m1Repairing[3]);
			cbtnIdleM2_0.setSelection(DVDFactory.m2IdleFront[0]||DVDFactory.m2IdleBack[0]);
			cbtnIdleM2_1.setSelection(DVDFactory.m2IdleFront[0]||DVDFactory.m2IdleBack[0]);
			cbtnBusyM2_0.setSelection(DVDFactory.m2Busy[0]);
			cbtnBusyM2_1.setSelection(DVDFactory.m2Busy[1]);
			cbtnBusyM3.setSelection(DVDFactory.m3_3WaitingForSwap[0]);
			cbtnBusyM3_1.setSelection(DVDFactory.m3_3WaitingForSwap[1]);
			lblNozzlesBlocked0.setText("Nozzles blocked: " + DVDFactory.m3_numBlockedNozzles[0]);
			lblNozzlesBlocked1.setText("Nozzles blocked: " + DVDFactory.m3_numBlockedNozzles[1]);
			lblThroughputTime.setText("Average throughput time in minutes: " + df.format(DVDFactory.averageThroughputTime/60));
			lblDvdsProducedPer.setText("Average DVD production per  hour: " + df.format(DVDFactory.producedDVDList.size()/(DVDFactory.currentTime/3600)));
			lblIdleTimeM1.setText("Average idle time machine 1: "+  df.format(DVDFactory.totalM1IdleTime/DVDFactory.currentTime*100/DVDFactory.amountM1) + "%");
			lblIdleTimeMachineM2.setText("Idle time machine 2: " + df.format(DVDFactory.totalM2IdleTime/DVDFactory.currentTime*100/DVDFactory.amountM2) + "%");
			lblIdleTimeFrontM2.setText("Idle time front machine 2: "+ df.format(DVDFactory.totalM2IdleFrontTime/DVDFactory.currentTime*100/DVDFactory.amountM2) + "%");
			lblIdleTimeBackM2.setText("Idle time back machine 2: "+ df.format(DVDFactory.totalM2IdleBackTime/DVDFactory.currentTime*100/DVDFactory.amountM2) + "%");
			lblIdleTimeConveyor.setText("Idle time conveyor belt: " + df.format(DVDFactory.totalCBIdleTime/DVDFactory.currentTime*100/DVDFactory.amountM2) + "%");
			lblIdleTimeMachineM3.setText("Idle time machine 3: "+ df.format(DVDFactory.totalM3IdleTime/DVDFactory.currentTime*100/DVDFactory.amountM3) + "%");
			lblIdleTimeFrontM3.setText("Idle time front machine 3: "+ df.format(DVDFactory.totalM3IdleFrontTime/DVDFactory.currentTime*100/DVDFactory.amountM3) + "%");
			lblIdleTimeBackM3.setText("Idle time Back machine 3: "+ df.format(DVDFactory.totalM3IdleBackTime/DVDFactory.currentTime*100/DVDFactory.amountM3) + "%");
			lblIdleTimeMachineM4.setText("Idle time machine 4: "+ df.format(DVDFactory.totalM4IdleTime/DVDFactory.currentTime*100/DVDFactory.amountM4) + "%");
			
			
			
			lblBrokenDVDs.setText("Broken DVDs: " + brokenDVDsint.toString());			
			dvdProduced.setText("Dvd's produced: " + producedDVDs.toString());
			if(DVDFactory.m4Repairing[0]){
				lblCartridgeSize_0.setText("DVDs left before refill: 0");
			} else {
				lblCartridgeSize_0.setText("DVDs left before refill: " + cartridgeSize_0.toString());
			}
			if(DVDFactory.m4Repairing[1]){
				lblCartridgeSize_1.setText("DVDs left before refill: 0");
			} else {
				lblCartridgeSize_1.setText("DVDs left before refill: " + cartridgeSize_1.toString());
			}
			
			 int day = (int)TimeUnit.SECONDS.toDays((long)DVDFactory.currentTime);        
			 long hours = TimeUnit.SECONDS.toHours((long)DVDFactory.currentTime) - (day *24);
			 long minute = TimeUnit.SECONDS.toMinutes((long)DVDFactory.currentTime) - (TimeUnit.SECONDS.toHours((long)DVDFactory.currentTime)* 60);
			 long second = TimeUnit.SECONDS.toSeconds((long)DVDFactory.currentTime) - (TimeUnit.SECONDS.toMinutes((long)DVDFactory.currentTime) *60);
			
			cbtnRefillM4_0.setSelection(DVDFactory.m4Repairing[0]);
			cbtnRefillM4_1.setSelection(DVDFactory.m4Repairing[1]);
			lblCurrentTime.setText("Current time: " + day + " days " + hours + " hours " +minute + " minutes and " + second+ " seconds");
			
			
			//DVDFactory.nextEvent();
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	
/////------------------------------------  createContents and getX's  --------------------------------\\\\\\\\\


	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(1096, 800);
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
		lblCartridgeSize_0.setBounds(910, 221, 200, 14);
		lblCartridgeSize_0.setText("DVDs left before refill: 0");
		
		lblCartridgeSize_1 = new Label(shell, SWT.NONE);
		lblCartridgeSize_1.setBounds(910, 471, 200, 14);
		lblCartridgeSize_1.setText("DVDs left before refill: 0");
		
		lblBrokenDVDs = new Label(shell, SWT.NONE);
		lblBrokenDVDs.setBounds(359, 270, 116, 14);
		lblBrokenDVDs.setText("Broken DVDs: 0");
	
		cbtnRefillM4_0 = new Button(shell, SWT.CHECK);
		cbtnRefillM4_0.setBounds(938, 118, 94, 18);
		cbtnRefillM4_0.setText("Refill");
		
		cbtnRefillM4_1 = new Button(shell, SWT.CHECK);
		cbtnRefillM4_1.setBounds(938, 366, 94, 18);
		cbtnRefillM4_1.setText("Refill");
		
		lblCurrentTime = new Label(shell, SWT.NONE);
		lblCurrentTime.setBounds(374, 10, 551, 14);
		lblCurrentTime.setText("Current Time: 0");
		
		cbtnBusyM3 = new Button(shell, SWT.CHECK);
		cbtnBusyM3.setBounds(647, 218, 150, 18);
		cbtnBusyM3.setText("Waiting for swap");
		
		cbtnBusyM3_1 = new Button(shell, SWT.CHECK);
		cbtnBusyM3_1.setBounds(647, 467, 150, 18);
		cbtnBusyM3_1.setText("Waiting for swap");
		
		lblNozzlesBlocked0 = new Label(shell, SWT.NONE);
		lblNozzlesBlocked0.setBounds(647, 235, 200, 14);
		lblNozzlesBlocked0.setText("Nozzles blocked: 0");
		
		lblNozzlesBlocked1 = new Label(shell, SWT.NONE);
		lblNozzlesBlocked1.setBounds(647, 485, 200, 14);
		lblNozzlesBlocked1.setText("Nozzles blocked: 0");
		
		btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				double stopTime;
				stopTime = DVDFactory.currentTime + (3600*24*7);
				while( stopTime >= DVDFactory.currentTime && !DVDFactory.eventList.isEmpty()){
					DVDFactory.nextEvent();
				}
			}
		});
		btnNewButton.setBounds(214, 3, 130, 28);
		btnNewButton.setText("Run for a week");
		
		Button btnRunAnHour = new Button(shell, SWT.NONE);
		btnRunAnHour.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				double stopTime;
				stopTime = DVDFactory.currentTime + 3600;
				while( stopTime >= DVDFactory.currentTime && !DVDFactory.eventList.isEmpty()){
					DVDFactory.nextEvent();
				}
			}
		});
		btnRunAnHour.setBounds(88, 3, 130, 28);
		btnRunAnHour.setText("Run for an hour");
		
		btnNextEvent = new Button(shell, SWT.NONE);
		btnNextEvent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (!DVDFactory.eventList.isEmpty()){
					DVDFactory.nextEvent();

				}
			}
		});
		btnNextEvent.setBounds(0, 3, 94, 28);
		btnNextEvent.setText("Next Event");
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(0, 30, 1096, 2);
		

		lblPerformanceMeasures = new Label(shell, SWT.NONE);
		lblPerformanceMeasures.setBounds(32, 561, 200, 14);
		lblPerformanceMeasures.setText("Performance measures:");	
		
		lblThroughputTime = new Label(shell, SWT.NONE);
		lblThroughputTime.setBounds(32, 601, 286, 14);
		lblThroughputTime.setText("Average throughput time in minutes: 0");
		
		lblDvdsProducedPer = new Label(shell, SWT.NONE);
		lblDvdsProducedPer.setBounds(32, 581, 271, 14);
		lblDvdsProducedPer.setText("Average DVD production per  hour: 0");
		
		lblIdleTimeM1 = new Label(shell, SWT.NONE);
		lblIdleTimeM1.setBounds(32, 621, 375, 14);
		lblIdleTimeM1.setText("Idle time machine 1: 0");
		
		lblIdleTimeFrontM2 = new Label(shell, SWT.NONE);
		lblIdleTimeFrontM2.setBounds(206, 641, 205, 14);
		lblIdleTimeFrontM2.setText("Idle time front machine 2: 0");

		lblIdleTimeBackM2 = new Label(shell, SWT.NONE);
		lblIdleTimeBackM2.setBounds(419, 641, 216, 14);
		lblIdleTimeBackM2.setText("Idle time back machine 2: 0");
		
		lblIdleTimeMachineM2 = new Label(shell, SWT.NONE);
		lblIdleTimeMachineM2.setBounds(32, 641, 174, 14);
		lblIdleTimeMachineM2.setText("Idle time machine 2: 0 ");
		
		lblIdleTimeMachineM3 = new Label(shell, SWT.NONE);
		lblIdleTimeMachineM3.setBounds(32, 681, 168, 14);
		lblIdleTimeMachineM3.setText("Idle time machine 3: 0");
		
		lblIdleTimeMachineM4 = new Label(shell, SWT.NONE);
		lblIdleTimeMachineM4.setBounds(32, 701, 168, 14);
		lblIdleTimeMachineM4.setText("Idle time machine 4: 0 ");
		
		lblIdleTimeConveyor = new Label(shell, SWT.NONE);
		lblIdleTimeConveyor.setBounds(32, 661, 200, 14);
		lblIdleTimeConveyor.setText("Idle time conveyor belt: 0");
		
		lblIdleTimeFrontM3 = new Label(shell, SWT.NONE);
		lblIdleTimeFrontM3.setBounds(206, 681, 201, 14);
		lblIdleTimeFrontM3.setText("Idle time front machine 3: 0");
		
		lblIdleTimeBackM3 = new Label(shell, SWT.NONE);
		lblIdleTimeBackM3.setBounds(419, 681, 191, 14);
		lblIdleTimeBackM3.setText("Idle time back machine 3: 0");
		
		
		
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
	public Button getCbtnRefillM4_0() {
		return cbtnRefillM4_0;
	}
	public Button getCbtnRefillM4_1() {
		return cbtnRefillM4_1;
	}
	public Label getLblCurrentTime() {
		return lblCurrentTime;
	}
	public Button getCbtnBusyM3() {
		return cbtnBusyM3;
	}
	public Button getCbtnBusyM3_1() {
		return cbtnBusyM3_1;
	}
	public Label getLblNozzlesBlocked0() {
		return lblNozzlesBlocked0;
	}
	public Label getLblNozzlesBlocked1() {
		return lblNozzlesBlocked1;
	}
	public Button getBtnNextEvent() {
		return btnNextEvent;
	}
	public Label getLblThroughputTime() {
		return lblThroughputTime;
	}
	public Label getLblDvdsProducedPer() {
		return lblDvdsProducedPer;
	}
	public Label getLblIdleTimeM1() {
		return lblIdleTimeM1;
	}
	public Label getLblIdleTimeFrontM2() {
		return lblIdleTimeFrontM2;
	}
	public Label getLblIdleTimeBackM2() {
		return lblIdleTimeBackM2;
	}
	public Label getLblIdleTimeMachineM2() {
		return lblIdleTimeMachineM2;
	}
	public Label getLblIdleTimeMachineM3() {
		return lblIdleTimeMachineM3;
	}
	public Label getLblIdleTimeMachineM4() {
		return lblIdleTimeMachineM4;
	}
	public Label getLblIdleTimeConveyor() {
		return lblIdleTimeConveyor;
	}
	public Label getLblIdleTimeBack() {
		return lblIdleTimeBackM3;
	}
	public Label getLblIdleTimeFront() {
		return lblIdleTimeFrontM3;
	}
}
