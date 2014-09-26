
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		

		realTime = new JLabel("30 �C");
		realTime.setFont(new Font("Verdana", Font.BOLD, 50));
		realTime.setBounds(350, 40, 300, 50);
		add(realTime);
		
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
		graph.setBounds(0, 0, getWidth(), getHeight());
		
		add(graph);
				
		graph.setVisible(true);
		
		
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
				drawCurrentTemperature();
			}
		});
		
		fahrenheit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				graph.setTempScale(F);
				drawCurrentTemperature();
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
		int x;
		
		timer = Calendar.getInstance();
		
		temperatures.add(0, 30);
		
		while(true)//infinite loops produces a random integer each second and adds it to the arraylist
		{
			while(timer.getTimeInMillis() + 1000 > Calendar.getInstance().getTimeInMillis());//get data every second
		
			x = temperatures.get(0) + (rand.nextInt(3) - 1);

			if(x > 50) x = 50;
			if(x < 10) x = 10;
			
		    temperatures.add(0, x);
		    
			graph.updateTemperatures(temperatures);//then the graph is updated
			
			drawCurrentTemperature();

			
			timer = Calendar.getInstance();
			
		}
	}
	
	public void drawCurrentTemperature()
	{
		if(graph.getTempScale() == C) realTime.setText(temperatures.get(0) + " �C");
		if(graph.getTempScale() == F) realTime.setText(CtoF(temperatures.get(0)) + " �F");
	}

    public float CtoF(float x)
    {
    	return (float)(x * 1.8) + 32;
    }

}
