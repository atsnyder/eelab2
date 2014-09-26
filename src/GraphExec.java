import javax.swing.JFrame;


public class GraphExec
{
	public static void main(String[] args)
	{
		GUI gui = new GUI();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//gui.setResizable(false);
		gui.setSize(830, 830);
		gui.setLocationRelativeTo(null);
		gui.setVisible(true);
		
		gui.start();
	}

}
