import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

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
		
		
		GUI gui = new GUI();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				
				System.out.println("close gui");
				/*try 
				{
					//SerialTest.input.close();
					//SerialTest.output.close();
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
			}
			
		});
		//gui.setResizable(false);
		gui.setSize(830, 830);
		gui.setLocationRelativeTo(null);
		gui.setVisible(true);
		
		
		
		gui.start();
	}

}
