/**
 * 
 */
package Gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 * @author aeora44
 *
 */
public class GuiMain extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	
	/**
	 * 
	 */
	public GuiMain() {
		super("Trivia Maze");
		//this.setLayout(new GroupLayout(this));
		setSize(800, 800);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.BLACK);
	}
	
	/**
	 * 
	 */
	public void start() {
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(150, 150, 500, 500);
		add(mainPanel);
		mainPanel.setBackground(Color.BLACK);
		setJMenuBar(createMenuBar());
				
		//Display the frame.
		setVisible(true);
		
	}
	
	/**
	 * 
	 * @return
	 */
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
        menuBar.add(buildFileMenu());
        menuBar.add(buildAboutUsMenu());
		return menuBar;
	}
	
	private GuiMain getMyClass() {
		return this;
	}
	
	/**
	 * 
	 * @return
	 */
	private JMenu buildFileMenu() {
		JMenu fileMenu = new JMenu("File");
		JMenu open = new JMenu("Open");
		JMenu saveAs = new JMenu("Save As");
		fileMenu.add(open);
		fileMenu.add(saveAs);
        open.addMenuListener(new OpenMenuListener());
		return fileMenu;
	}
	
	/**
	 * 
	 * @return
	 */
	private JMenu buildAboutUsMenu() {
		JMenu aboutUsMenu = new JMenu("About Us");
		JMenu instructionsMenu = new JMenu("Instructions");
		JMenu aboutTriviaMaze = new JMenu("About TriviaMaze");
		aboutUsMenu.add(instructionsMenu);
		aboutUsMenu.add(aboutTriviaMaze);
		return aboutUsMenu;
	}
	
	
	
	//Continue from here.
	@Override
	public void paint(Graphics g){
		super.paint(g);
		int count = 1;
	        for (int i = 0; i < 6; i++) {
	        	for(int j = 0; j < 5; j++) {
	        		if (count != 30) {
		        		if (count == 1) {
		        			g.setColor(Color.YELLOW);
		        		} else {
			    	        g.setColor(Color.CYAN);
		        		}
		    	        g.fillOval(200 + 88*j, 200 + 88*i, 50, 50);
		    	        g.setColor(Color.BLUE);
		    	        g.drawOval(200 + 88*j, 200 + 88*i, 50, 50);
		    	        if (j < 4) {
			    	        g.drawLine(200 + 88*j + 50, 200 + 88*i + 25, 200 + 88*j + 50 + 44, 200 + 88*i + 25);
		    	        }
		    	        if (i < 5) {
			    	        g.drawLine(200 + 88*j + 25, 200 + 88*i + 50, 200 + 88*j + 25, 200 + 88*i + 50 + 44);
		    	        }
		    	        FontMetrics fm = g.getFontMetrics();
		    	        String roomNo = "" + count;
		    	        double textWidth = fm.getStringBounds(roomNo, g).getWidth();
		    	        g.setColor(Color.RED);
		    	        g.drawString(roomNo, (int) (200 + 88*j + 25 - textWidth / 2),(int) (200 + 88*i + 25 + fm.getMaxAscent() / 2));
		    	        count++;
		        	} else {
		        	    BufferedImage img = null;
		        	    try {
		        	        img = ImageIO.read(new File("/Users/stlp/eclipse-workspace/TCSS360Team/src/Gui/crown.png"));
		        	    } catch (IOException e) {
		        	    	System.out.println("Image Not Found");
		        	    }
		        	    g.drawImage(img, 200 + 88*j, 200 + 88*i, 50, 50, this);
		        	}
			    }
	        }
    }
	
	//Dialog Box for menu
	class OpenMenuListener implements MenuListener {

	    @Override
	    public void menuSelected(MenuEvent e) {
            final JFileChooser chooser = new JFileChooser(".");
            final int result = chooser.showOpenDialog(getMyClass());
            if (result == JFileChooser.APPROVE_OPTION)
            {
                final File selectedFile = chooser.getSelectedFile();
              String fileName = selectedFile.getAbsolutePath();
              System.out.println(fileName);
            }
	    }

	    @Override
	    public void menuDeselected(MenuEvent e) {
	        System.out.println("menuDeselected");
	    }

	    @Override
	    public void menuCanceled(MenuEvent e) {
	        System.out.println("menuCanceled");
	    }	    
	}

	

	/**
	 * 
	 * @param theArgs
	 */
	public static void main(String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GuiMain frame = new GuiMain();
            	frame.start();
            }
        });
	}
}