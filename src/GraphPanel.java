import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GraphPanel extends JPanel
{
	private final int C = 0;
	private final int F = 1;
	
	private int timeScale;
	private int tempScale;
		
	private ArrayList<Integer> temperatures;
	
	public GraphPanel()
	{
		timeScale = 60;
		tempScale = C;
		
		temperatures = new ArrayList<Integer>();
    	temperatures.add(30);

		setVisible(true);
		setSize(512, 512);
		
		addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent ME)
			{
				System.out.println(ME.getX() + " " + ME.getY());
			}
		});
	}
    
    @Override
    public void paintComponent(Graphics g)//initial draw
    {	
    	g.setColor(Color.black);

    	drawAxes(g);
    	
    	Font font = new Font("Verdana", Font.BOLD, 50);
    	g.setFont(font);

    	drawString(g, "30 °C", 350, 50);
    	
    	g.setColor(Color.red);
		if(timeScale == 60) g.fillOval(Math.round((697)), (847 - 15 * 30), 6, 6);
		
    	//drawTimeAxis(g, 60);
    	//drawTempAxis(g, C);
    	//draw temperature axis

    }
    
    public void drawString(Graphics g, String s, int x, int y)
    {    	    	
    	g.drawString(s, x, y);
    }
    
    public void drawLine(Graphics g, int x1, int y1, int x2, int y2)
    {
    	g.drawLine(x1, y1, x2, y2);
    }
    
    public void setTimeScale(int x)
    {
    	timeScale = x;
    	Graphics g = this.getGraphics();
    	redraw(g); 
    }
    
    public void setTempScale(int x)
    {
    	tempScale = x;
    	Graphics g = this.getGraphics();
    	redraw(g);
    }
    
    public void redraw(Graphics g)
    {
    	clear();
    	
    	drawAxes(g);
    	drawTemperatures();
    }
    
    public void drawAxes(Graphics g)
    {
    	drawTimeAxis(g);
    	drawTempAxis(g);
    }
    
    public void drawTimeAxis(Graphics g)
    {
    	drawString(g, "Time", 385, 760);
        drawLine(g, 100, 700, 700, 700);//time axis

        drawString(g, "Now", 685, 720);
        drawLine(g, 700, 690, 700, 710);//now tick
        
        drawLine(g, 100, 690, 100, 710);//longest time tick
        drawLine(g, 400, 690, 400, 710);//middle time tick
        

    	if(timeScale == 60)
    	{
        	drawString(g, "60 seconds ago", 60, 720);
        	drawString(g, "30 seconds ago", 357, 720);
    	}
    	if(timeScale == 300)
    	{
        	drawString(g, "300 seconds ago", 60, 720);
        	drawString(g, "150 seconds ago", 357, 720);
    	}
    }
    
    public void drawTempAxis(Graphics g)
    {
    	drawLine(g, 700, 700, 700, 100);//temp axis
    	drawString(g, "Temperature", 740, 330);
    	
    	drawLine(g, 690, 100, 710, 100);//highest  tick
        drawLine(g, 690, 400, 710, 400);//middle tick
    	drawLine(g, 690, 700, 710, 700);//lowest tick

    	drawString(g, "(°C)", 765, 350);
        drawString(g, "10", 720, 705);
        drawString(g, "50", 720, 105);
        drawString(g, "30", 720, 405);

    	
    }
    public void updateTemperatures(ArrayList<Integer> temps)
    {
    	temperatures = temps;
    	redraw(this.getGraphics());
    	
    }
    public void drawTemperatures()
    {
    	Graphics g = this.getGraphics();
    	g.setColor(Color.red);
    	
    	//draw dots
    	for(int i = 0; i < temperatures.size() && i <= timeScale; i++)
    	{
    		if(timeScale == 60) g.fillOval(Math.round((697 - 10 * i)), (847 - 15 * temperatures.get(i)), 6, 6);
    		if(timeScale == 300) g.fillOval(Math.round((int)(698 - 2 * i)), (848 - 15 * temperatures.get(i)), 4, 4);
    	}
    	
    	//draw lines connecting dots
    	for(int i = 0; i < temperatures.size() - 1 && i <= timeScale - 1; i++)
    	{
    		if(timeScale == 60) g.drawLine(Math.round(700 - 10 * i), (850 - 15 * temperatures.get(i)), Math.round(700 - 10 * (i + 1)), (850 - 15 * temperatures.get(i + 1)));
    		if(timeScale == 300) g.drawLine(Math.round(700 - 2 * i), (850 - 15 * temperatures.get(i)), Math.round(700 - 2 * (i + 1)), (850 - 15 * temperatures.get(i + 1)));
    	}

    	drawCurrentTemperature(g);
    	
    }
    
    public void drawCurrentTemperature(Graphics g)
    {
    	Font font = new Font("Verdana", Font.BOLD, 50);
    	g.setFont(font);
    	g.setColor(Color.black);
    	
    	if(tempScale == C) drawString(g, temperatures.get(0) + " °C", 350, 50);
    	if(tempScale == F) drawString(g, CtoF(temperatures.get(0)) + " °F", 350, 50);
    }
	
    public float CtoF(float x)
    {
    	return (float)(x * 1.8) + 32;
    }
    
    public void clear()//"clear" panel by drawing a solid rectangle
    {
    	Graphics g = this.getGraphics();
    	g.setColor(this.getBackground());
    	g.fillRect(0, 90, this.getWidth(), this.getHeight());//clear graph
    	
    	g.fillRect(350, 0, 300, 100);//clear current temperature
    }
    
    public int getTempScale()
    {
    	return tempScale;
    }
    public int getTimeScale()
    {
    	return timeScale;
    }
    
}
