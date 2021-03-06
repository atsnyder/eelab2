
import javax.swing.JFrame;


public class GraphExec
{
	public static void main(String[] args)
	{
		SerialTest main = new SerialTest();
		main.initialize();
		Thread t=new Thread() {
			public void run() {
				//the following line will keep this app alive for 1000 seconds,
				//waiting for events to occur and responding to them (printing incoming messages to console).
				try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
			}
		};
		t.start();
		
		//set up GUI window
		GUI gui = new GUI();
		gui.setSize(830, 830);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setLocationRelativeTo(null);
		gui.setVisible(true);
		
		
		
		gui.start();
	}

}
