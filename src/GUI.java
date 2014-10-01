
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
      
	public GUI()
	{
		super("Temperature graph");//add title to frame
		setLayout(null);
		
		temperatures = new ArrayList<Integer>();
		rand = new Random();
		
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
		graph.setBounds(0, 0, (int)(getWidth()*.5), (int)(getHeight()*.5));
		graph.setPreferredSize(new Dimension(getWidth(), getHeight()));
		
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
					
 					graph.setScale((double)(getWidth() - 15) / 815, getWidth(), getHeight());
				}
				else 
				{	
					graph.setScale((double)(getHeight() - 60) / 770, getWidth(), getHeight());
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
				System.out.println("REMOTE ON");
			}
		});
		
		off.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				System.out.println("REMOTE OFF");
			}
		});
		
		
		
	}
	
	
	public void start()
	{
		int x = 0;
		
		timer = Calendar.getInstance();
		
		temperatures.add(0, 30);
		
		while(true)//infinite loops produces a random integer each second and adds it to the arraylist
		{
			while(timer.getTimeInMillis() + 1000 > Calendar.getInstance().getTimeInMillis());//get data every second
		
	//		try {
				
		//		x = Integer.parseInt(SerialTest.input.readLine());
				//x = Integer.getInteger(SerialTest.input.readLine());
		//	} catch (IOException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
		//	}//

			x = temperatures.get(0) + (rand.nextInt(3) - 1); //send in temperature here
			
			if(x > 50) x = 50;
			if(x < 10) x = 10;
			
		    temperatures.add(0, x);
		    
			graph.updateTemperatures(temperatures);//then the graph is updated
			
			//drawCurrentTemperature();

			
			timer = Calendar.getInstance();
			
		}
	}
	


}
