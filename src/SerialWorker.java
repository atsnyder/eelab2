import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;


public class SerialWorker extends SwingWorker
{

	@Override
	protected Object doInBackground() throws Exception 
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				while(true)
				{
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("q");
					try {
						SerialTest.output.write("y".getBytes());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
			
		});
	
		// TODO Auto-generated method stub
		return null;
	}

}
