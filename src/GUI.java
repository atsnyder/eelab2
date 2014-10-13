
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
    
	public GUI()
	{
		super("Temperature graph");//add title to frame
		setLayout(null);
		
		temperatures = new ArrayList<Integer>();
		rand = new Random();
		
		remoteOn = false;
				
		setSize(830, 830);
	
		
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

		add(graph);
				
		graph.setVisible(true);
		
		addComponentListener(new ComponentAdapter() 
		{
			@Override
			public void componentResized(ComponentEvent e)
			{
				//allow resizing of the graph
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
				
				try 
				{
					SerialTest.output.write("y".getBytes());
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			}
		});
		
		
		
	}
	
	
	public void start()
	{
		int x = -123;
		int serialTimer = 1000;
		int readTimer = 1000;
		
		SerialTest tester = new SerialTest();
		
		timer = Calendar.getInstance();
				
		while(true)//infinite loops produces a random integer each second and adds it to the arraylist
		{
			if(temperatures.size() > 0)
			{
				//if(temperatures.get(0) == -1000 || temperatures.get(0) == -123 || temperatures.get(0) == -600) readTimer = 250;
				//else readTimer = 1000;
			}
			while(timer.getTimeInMillis() + readTimer > Calendar.getInstance().getTimeInMillis());//get data every second


			
			x = Integer.parseInt(SerialTest.inputLine);
			SerialTest.inputLine = "-123";//assume error code
			
			graph.setRealTime(x);//update real time display
			
			if(x == -123)
			{
				//reinitialize serial connection every 2 seconds to fix no serial input error
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
			else if(x > 50) x = 50;//too high input gets rounded
			else if(x < 10) x = 10;//too low input gets rounded
			
			if(remoteOn)//if remote on is enabled, send "y" to the arduino to keep the leds on
			{
				try 
				{
					SerialTest.output.write("y".getBytes());
				}
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		    temperatures.add(0, x);//add newest temperature to arraylist
		    
			graph.updateTemperatures(temperatures);//then the graph is updated
			

			timer = Calendar.getInstance();
			
		}
	}
	


}
