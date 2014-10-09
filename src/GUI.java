
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;



@SuppressWarnings("serial")
public class GUI extends JFrame
{
	private final int C = 0;
	private final int F = 1;
	
	private JMenuBar menu;
	
	private JMenu timeScaleMenu;
	private JMenuItem seconds60;
	private JMenuItem seconds300;
	
	private JMenu temperatureScaleMenu;
	private JMenuItem celsius;
	private JMenuItem fahrenheit;
	
	private JMenu remoteOnMenu;
	private JMenuItem on;
	private JMenuItem off;
	
    private GraphPanel graph;
    
    private Random rand;
    private Calendar timer;
    private ArrayList<Integer> temperatures;
    
    private JLabel realTime;
      
    private boolean remoteOn;
    
	private SerialWorker serialWorker;

	public GUI()
	{
		super("Temperature graph");//add title to frame
		setLayout(null);
		
		temperatures = new ArrayList<Integer>();
		rand = new Random();
		
		remoteOn = false;
		
		serialWorker = new SerialWorker();
		
		setSize(830, 830);
		

		//realTime = new JLabel("30 °C");
		//realTime.setFont(new Font("Verdana", Font.BOLD, 50));
		//realTime.setBounds(350, 40, 300, 50);
		//add(realTime);
		
		//set up menu bar and sub-menus
		menu = new JMenuBar();
		
		timeScaleMenu = new JMenu("Time Scale");
		seconds60 = new JMenuItem("60 seconds");
		seconds300 = new JMenuItem("300 seconds");
		timeScaleMenu.add(seconds60);
		timeScaleMenu.add(seconds300);
		
		temperatureScaleMenu = new JMenu("Temperature Scale");
		celsius = new JMenuItem("Celsius");
		fahrenheit = new JMenuItem("Fahrenheit");
		temperatureScaleMenu.add(celsius);
		temperatureScaleMenu.add(fahrenheit);
		
		remoteOnMenu = new JMenu("Remote On");
		on = new JMenuItem("On");
		off = new JMenuItem("Off");
		remoteOnMenu.add(on);
		remoteOnMenu.add(off);
		
		
		menu.add(timeScaleMenu);
		menu.add(temperatureScaleMenu);
		menu.add(remoteOnMenu);
		
		add(menu);
		
		setJMenuBar(menu);
		
		//set up graph panel
		graph = new GraphPanel();
		//graph.setBounds(0, 0, (int)(getWidth()*.5), (int)(getHeight()*.5));
		//graph.setPreferredSize(new Dimension(getWidth(), getHeight()));
		
		add(graph);
				
		graph.setVisible(true);
		
		addComponentListener(new ComponentAdapter() 
		{
			@Override
			public void componentResized(ComponentEvent e)
			{
				//System.out.println(e.getComponent().getWidth());
				//e.getComponent().setSize(getWidth(), getWidth());
				//setSize(new Dimension(getWidth(), getWidth()));
				if(getWidth() < getHeight()) 
				{
					
 					graph.setScale((double)(getWidth() - 50) / 830, getWidth(), getHeight());
				}
				else 
				{	
					graph.setScale((double)(getHeight() - 50) / 830, getWidth(), getHeight());
			    }

			}
		});
		
		
		//menu actionListeners
		seconds60.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				graph.setTimeScale(60);
			}
		});
		
		seconds300.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				graph.setTimeScale(300);
			}
		});
		
		celsius.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				graph.setTempScale(C);
			}
		});
		
		fahrenheit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				graph.setTempScale(F);
			}
		});
		
		on.addActionListener(new ActionListener()
		{			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				remoteOn = true;
				
				serialWorker.run();

				/*try 
				{
					SerialTest.output.write("y".getBytes());
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				System.out.println("REMOTE ON");
			}
		});
		
		off.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				remoteOn = false;

				try 
				{
					SerialTest.output.write("n".getBytes());
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
				System.out.println("REMOTE OFF");
			}
		});
		
		
		
	}
	
	
	public void start()
	{
		int x = -123;
		int serialTimer = 0;
		int sTimer = 0;
		
		SerialTest tester = new SerialTest();
		
		timer = Calendar.getInstance();
		
		//temperatures.add(0, 30);
		
		while(true)//infinite loops produces a random integer each second and adds it to the arraylist
		{
			while(timer.getTimeInMillis() + 1000 > Calendar.getInstance().getTimeInMillis());//get data every second

			
		
		//	try {
		
							//x = Integer.getInteger(SerialTest.input.readLine());
			//} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
		//	}//

		//	x = temperatures.get(0) + (rand.nextInt(3) - 1); //send in temperature here
		
			
			x = Integer.parseInt(SerialTest.inputLine);
			SerialTest.inputLine = "-123";
			
			System.out.println(x);
			graph.setRealTime(x);
			//SerialTest.inputLine = "-123";
			
			if(x == -123)
			{
				sTimer = 1;
				//tester.close();
				//tester = new SerialTest();
					
				serialTimer++;
				if(serialTimer % 2 == 0) 
				{
					tester.close();
					tester.initialize();
				}
				
			}
			else if(x == -1000)
			{
				
			}
			else if(x == -600)
			{
				
			}
			else if(x > 50) x = 50;
			else if(x < 10) x = 10;
		
			
			
		    temperatures.add(0, x);
		    
			graph.updateTemperatures(temperatures);//then the graph is updated
			
			//drawCurrentTemperature();

			
			/*try 
			{
			//	System.out.println(SerialTest.serialPort);
				
				if(remoteOn)
				{	
					sTimer++;
					if(sTimer % 10 == 0 && temperatures.get(0) != -123) 
					{
						sTimer = 1;
						SerialTest.output.write("y".getBytes());
					}
			    }
				//else SerialTest.output.write("n".getBytes());
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch(NullPointerException n)
			{
				n.printStackTrace();
			} 
			finally
			{
				try
				{
				    if(SerialTest.output != null) SerialTest.output.close();
				}
				catch(IOException e2)
				{
				   e2.printStackTrace();	
				}
			}*/
			
			
			
			
			timer = Calendar.getInstance();
			
		}
	}
	


}
