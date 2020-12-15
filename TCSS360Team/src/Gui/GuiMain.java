/**
 * The GuiMain creates a Graphical User Interface for the trivia maze game.
 */
package Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 * @author aeora44
 *
 */
public class GuiMain extends JFrame implements ActionListener, Runnable {

	/**
	 * The default version ID.
	 */
	private static final long serialVersionUID = 1L;
			
	/**
	 * Current directory location for relative path.
	 */
	private static final String CURRENT_DIR_LOCATION = System.getProperty("user.dir");
	
	/**
	 * Crown image location.
	 */
	private static final String FINAL_ROOM_CROWN_IMG = "/src/Gui/crown.png";
	
	/**
	 * Cross image location.
	 */
	private static final String FINAL_ROOM_CROSS_IMG = "/src/Gui/Cross.png";
	
	
	/**
	 * Current Button.
	 */
	private int buttonPressed = 1;
	
	/**
	 * Image at the goal.
	 */
	private String myGoalImg = FINAL_ROOM_CROWN_IMG;
	/**
	 * Map containing questions and possible answers.
	 */
	private Map<String, String[]> myQuestionMap = new HashMap<String, String[]>();
	
	/**
	 * Map containing questions and the correct answer.
	 */
	private Map<String, String> myAnswerMap = new HashMap<String, String>();
		
	/**
	 * JPanel.
	 */
	private MyPanel mainPanel = new MyPanel();
	
	/**
	 * Delay for Splash Screens.
	 */
	private static final int DELAY = 4000;
	
	/**
	 * Dimensions of the Window.
	 */
	private static final Dimension WINDOW_SIZE = new Dimension(800, 900);
	
	/**
	 * Map of Blocked Rooms.
	 */
	private Map<Integer,List<Integer>> myBlockedRooms = new HashMap<>();
	
	
	/**
	 * Map of Blocked Buttons.
	 */
	private Map<JButton, List<JButton>> myBlockedButtons = new HashMap<>();
	
	/**
	 * Current Button.
	 */
	private JButton myCurrentButton = new JButton();
	
	/**
	 * All the buttons for the rooms.
	 */
	private JButton[] myMovementButtons = new JButton[30];
	

	/**
	 * The parameterless constructor.
	 */
	public GuiMain() {
		super("Trivia Maze");
		System.out.println(CURRENT_DIR_LOCATION);
		SwingUtilities.invokeLater((Runnable) this);		
		setSize(800, 900);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.BLACK);
		myQuestionMap.put("What is the smallest odd prime number?", new String[] {"1", "3", "5", "7"});
		myQuestionMap.put("What is the only even prime number?", new String[] {});
		myAnswerMap.put("What is the smallest odd prime number?", "3");
		myAnswerMap.put("What is the only even prime number?", "2");
	}
	
	/**
	 * Creates a frame with all the components for the trivia maze game. 
	 */
	public void start() {
		mainPanel.setLayout(null);
		mainPanel.setBounds(150, 150, 500, 700);
		add(mainPanel);
		setResizable(false);
		mainPanel.setBackground(Color.BLACK);
		setJMenuBar(createMenuBar());
		
				
		//Display the frame.
		int count = 0;
				
        for (int i = 0; i < 6; i++) {
        	for(int j = 0; j < 5; j++) {
        		myMovementButtons[count] = new JButton();
        		myMovementButtons[count].setActionCommand(""+(count+1));   
        		myMovementButtons[count].setOpaque(false);
        		myMovementButtons[count].setContentAreaFilled(false);
        		myMovementButtons[count].setBorderPainted(false);
    			mainPanel.add(myMovementButtons[count]);
    			myMovementButtons[count].addActionListener(this);
    			myMovementButtons[count].setBounds(50 + 88*j, 0 + 88*i, 50, 50);
    			count++;
        	}
        }
        myCurrentButton = myMovementButtons[0];
        this.setLocationRelativeTo(null);
	}
	
	/**
	 * actionPerformed instructs what actions to perform when the rooms in the Trivia Maze are clicked. 
	 */
    public void actionPerformed(ActionEvent a) {
        JButton button = (JButton) a.getSource();
        int newButton = Integer.parseInt(button.getActionCommand());
        if ((Math.abs(newButton - buttonPressed) == 1 || Math.abs(newButton - buttonPressed) == 5) && !(buttonPressed % 5 == 0 && newButton - buttonPressed == 1)) {
    	    UIManager.put("OptionPane.background",Color.gray);  
    	    UIManager.put("Panel.background",Color.gray);
        	JPanel panel = new JPanel();
        	panel.setLayout(new BorderLayout());
            String questionString = (String) myQuestionMap.keySet().toArray()[buttonPressed % 2];
            JLabel questionLabel = new JLabel("Question: " + questionString);
            panel.add(questionLabel, BorderLayout.NORTH);
            questionLabel.setForeground(Color.WHITE);
            questionLabel.setHorizontalAlignment(JLabel.CENTER);
            questionLabel.setVerticalAlignment(JLabel.CENTER);
            questionLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
            panel.setBackground(Color.GRAY);

            ImageIcon img = new ImageIcon(new ImageIcon(CURRENT_DIR_LOCATION + "/src/Gui/questionIcon3.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            Object response;
            if (myQuestionMap.get(myQuestionMap.keySet().toArray()[buttonPressed % 2]).length > 0) {
                response = JOptionPane.showInputDialog(this,panel 
                		, "Question room #" + newButton,
                        JOptionPane.QUESTION_MESSAGE, img, myQuestionMap.get(myQuestionMap.keySet().toArray()[buttonPressed % 2]),
                        myQuestionMap.get(myQuestionMap.keySet().toArray()[buttonPressed % 2])
                );

            } else {
                response = JOptionPane.showInputDialog(this,panel);
            }
            

            if ( myAnswerMap.get(myQuestionMap.keySet().toArray()[buttonPressed % 2]).compareTo((String) response) == 0) {
            	buttonPressed = newButton;
            	myCurrentButton = button;
            	if (buttonPressed == 30) {

    	        	congratulations.run();
	
            	}
                System.out.println("Button #" + newButton);
            } else {
            	if (myBlockedRooms.containsKey(buttonPressed)) {
            		myBlockedRooms.get(buttonPressed).add(newButton);
            	} else {
            		//
            		List<Integer> list=new ArrayList<Integer>();
            		list.add(newButton);
            		myBlockedRooms.put(buttonPressed, list);
            		System.out.println("Current Button: " + buttonPressed + "Array: " + list.toString());
            	}
            	
            	if (myBlockedButtons.containsKey(myCurrentButton)) {
            		myBlockedButtons.get(myCurrentButton).add(button);
            	} else {
            		List<JButton> list=new ArrayList<JButton>();
            		list.add(button);
            		myBlockedButtons.put(myCurrentButton, list);
            	}
            }
        	List<JButton> list = new ArrayList<JButton>();
            if (myBlockedButtons.containsKey(myCurrentButton)) {
            	list = myBlockedButtons.get(myCurrentButton);
            }
        	for (int i = 0; i < myMovementButtons.length; i++) {
        		if (list.contains(myMovementButtons[i])) {
        			System.out.println("Button" + Integer.parseInt(myCurrentButton.getActionCommand()) + "contains Button" + (i+1));
        			myMovementButtons[i].setVisible(false);
        		} else {
        			myMovementButtons[i].setVisible(true);
        		}
        		if (list.contains(myMovementButtons[29])) {
        			myGoalImg = FINAL_ROOM_CROSS_IMG;
        		} else {
        			myGoalImg = FINAL_ROOM_CROWN_IMG;
        		}
        	}
        } else {
        	
        	System.out.println("Illegal move");
        }
        revalidate();
        repaint();
    }

	/**
	 * Creates the JMenuBar with all the sub-menu components. 
	 * @return JMenuBar with all the components added. 
	 */
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
        menuBar.add(buildFileMenu());
        menuBar.add(buildAboutUsMenu());
        menuBar.add(buildColorMenu());
		return menuBar;
	}
	
	/**
	 * getter for the current state of the GuiMain. 
	 * @return GuiMain instance for inner classes. 
	 */
	private GuiMain getMyClass() {
		return this;
	}
	
	/**
	 * Creates the "File" menu for the JMenuBar.
	 * @return JMenu containing all the components for File. 
	 */
	private JMenu buildFileMenu() {
		JMenu fileMenu = new JMenu("File");
		JMenuItem open = new JMenuItem("Open");
		JMenuItem saveAs = new JMenuItem("Save As");

		open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
	            final JFileChooser chooser = new JFileChooser(".");
	            final int result = chooser.showOpenDialog(getMyClass());
	            if (result == JFileChooser.APPROVE_OPTION)
	            {
	              final File selectedFile = chooser.getSelectedFile();
	              String fileName = selectedFile.getAbsolutePath();
	              System.out.println(fileName);
	            }
			}
			
		});
		
		saveAs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
	            final JFileChooser chooser = new JFileChooser(".");
	            final int result = chooser.showSaveDialog(getMyClass());
	            if (result == JFileChooser.APPROVE_OPTION)
	            {
	                final File selectedFile = chooser.getSelectedFile();
	              String fileName = selectedFile.getAbsolutePath();
	              System.out.println(fileName);
	            }
			}
		});
		fileMenu.add(open);
		fileMenu.add(saveAs);
		return fileMenu;
	}
	
	/**
	 * Creates the "About Us" menu for the JMenuBar.
	 * @return JMenu containing all the components for "About Us" feature.
	 */
	private JMenu buildAboutUsMenu() {
		JMenu aboutUsMenu = new JMenu("About Us");
		JMenuItem instructionsMenu = new JMenuItem("Instructions");
		JMenuItem aboutTriviaMaze = new JMenuItem("About TriviaMaze");
		aboutUsMenu.add(instructionsMenu);
		aboutUsMenu.add(aboutTriviaMaze);
		
		instructionsMenu.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
            	JOptionPane.showMessageDialog(getMyClass(), "Instructions for Trivia Maze");
            }
        });
        
		aboutTriviaMaze.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
            	JOptionPane.showMessageDialog(getMyClass(), "Created by Arora, Hu, Olono");
            }
        });

		
		return aboutUsMenu;
	}
	
	/**
	 * Creates the "Background Color" menu for the JMenuBar.
	 * @return JMenu containing all the components for "Background Color" feature. 
	 */
	private JMenu buildColorMenu() {
		JMenu colorMenu = new JMenu("Background Color");
        final ButtonGroup group = new ButtonGroup();                
        final JMenuItem chooseColorItem = new JMenuItem("Choose Color");
        chooseColorItem.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final Color result = JColorChooser.showDialog(null, "A Color Chooser", null);
                if (result != null) {
                	getMyClass().getContentPane().setBackground(result);
                    mainPanel.setBackground(result);
                    group.clearSelection();
                    
                }
           }
        });
        
        colorMenu.add(chooseColorItem);
        colorMenu.add(createColorItems("Gray", Color.GRAY, group));
        colorMenu.add(createColorItems("Green", Color.GREEN, group));
        colorMenu.add(createColorItems("Black", Color.BLACK, group));
        return colorMenu;
	}
	
	/**
	 * Creates the RadioButton for some default color options. 
	 * @param theName Title of the RadioButton
	 * @param theColor Color the radioButton shall change.
	 * @param theGroup The group which the color is associated with.
	 * @return
	 */
    private JRadioButtonMenuItem createColorItems(final String theName, final Color theColor, final ButtonGroup theGroup) {
        
        final JRadioButtonMenuItem colorItem = new JRadioButtonMenuItem(theName);
        theGroup.add(colorItem);
        colorItem.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
            	getMyClass().getContentPane().setBackground(theColor);
                mainPanel.setBackground(theColor);
            }
        });
        return colorItem;
    }

	
	// Class for creating the Panel with the specified background features. 
	class MyPanel extends JPanel {

		/**
		 * The default serial version ID.
		 */
		private static final long serialVersionUID = 1L;
		
		//Instructs the GUI how the panel's background should be painted. 
		@Override
		public void paintComponent(Graphics g) {
			if (buttonPressed == 1) {
				super.paintComponent(g);
			}
			int count = 1;
			List<Integer> currentRoomsLocked = new ArrayList<>();
	        for (int i = 0; i < 6; i++) {
	        	for(int j = 0; j < 5; j++) {
	        		if (count != 30) {
		        		if (count == buttonPressed) {
		        			if (myBlockedRooms.containsKey(buttonPressed)) {
		        				currentRoomsLocked = myBlockedRooms.get(buttonPressed);
		        			}
		        		}
	        		}
	        		count++;
	        	}
	        }
	        
	        count = 1;
			
	        for (int i = 0; i < 6; i++) {
	        	for(int j = 0; j < 5; j++) {
	        		if (count != 30) {
		        		if (count == buttonPressed) {
		        			g.setColor(Color.YELLOW);
		        		} else {
		        			if (currentRoomsLocked.contains(count)) {
				    	        g.setColor(Color.RED);

		        			} else {
				    	        g.setColor(Color.CYAN);
		        			}
		        		}
		    	        g.fillOval(50 + 88*j, 0 + 88*i, 50, 50);
		    	        g.setColor(Color.BLUE);
		    	        g.drawOval(50 + 88*j, 0 + 88*i, 50, 50);
		    	        if (j < 4) {
			    	        g.drawLine(50 + 88*j + 50, 0 + 88*i + 25, 50 + 88*j + 50 + 44, 0 + 88*i + 25);
		    	        }
		    	        if (i < 5) {
			    	        g.drawLine(50 + 88*j + 25, 0 + 88*i + 50, 50 + 88*j + 25, 0 + 88*i + 50 + 44);
		    	        }
		    	        FontMetrics fm = g.getFontMetrics();
		    	        String roomNo = "" + count;
		    	        double textWidth = fm.getStringBounds(roomNo, g).getWidth();
		    	        g.setColor(Color.BLACK);
		    	        g.drawString(roomNo, (int) (50 + 88*j + 25 - textWidth / 2),(int) (0 + 88*i + 25 + fm.getMaxAscent() / 2));
		    	        count++;
		        	} else {
		        	    BufferedImage img = null;
		        	    try {
		        	        img = ImageIO.read(new File(CURRENT_DIR_LOCATION + myGoalImg));
		        	    } catch (IOException e) {
		        	    	System.out.println("Image Not Found");
		        	    }
		        	    g.drawImage(img, 50 + 88*j, 0 + 88*i, 50, 50, this);
		        	}
			    }
	       }
	        
	        // Current room information.
	        int fontSize = 20;
	        Font f = new Font("Comic Sans MS", Font.BOLD, fontSize);
	        g.setColor(Color.WHITE);
	        g.setFont(f);
	        g.drawString("You are currently at Room #" + buttonPressed, 100, 600);
	        
	        // Legend at the bottom of the Panel.
	        g.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
	        g.setColor(Color.YELLOW);
	        g.drawString("YELLOW = CURRENT ROOM", 0, 680);
	        g.setColor(Color.RED);
	        g.drawString("RED = ROOM LOCKED", 200, 680);
	        g.setColor(Color.CYAN);
	        g.drawString("CYAN = ROOM OPEN", 370, 680);
	    }		
	}

	// Creates a welcome splash screen window with a loading bar.
	@Override
	public void run() {
		System.out.println("Runnable");
		if (buttonPressed == 1) {
			final JWindow w = new JWindow();
			w.getContentPane().setBackground(Color.BLACK);
			ImageIcon imageIcon = new ImageIcon(new ImageIcon(CURRENT_DIR_LOCATION + "/src/Gui/loading4.gif").getImage().getScaledInstance(800, 900, Image.SCALE_DEFAULT));
			JLabel gifLabel = new JLabel(imageIcon, SwingConstants.CENTER);
			gifLabel.setForeground(Color.WHITE);
		    w.getContentPane().add(gifLabel);
		    w.getContentPane().setPreferredSize(WINDOW_SIZE);
	        w.pack();
	        w.setBackground(Color.BLACK);
	        w.setLocationRelativeTo(null);
	        w.setVisible(true);
	        Timer timer = new Timer(DELAY, new ActionListener() {
	    	    @Override
	        	public void actionPerformed(ActionEvent e) {
	    	        w.setVisible(false);
	    			setVisible(true);
	    	    }
	        });
	        timer.start();
		}
	}
	
	// Runnable for displaying the congratulations splash screen
	Runnable congratulations = new Runnable() {
	     public void run() {
	 		final JWindow w = new JWindow();
	 		w.setAlwaysOnTop(true);
			w.getContentPane().setBackground(Color.BLACK);
			ImageIcon imageIcon = new ImageIcon(new ImageIcon(CURRENT_DIR_LOCATION + "/src/Gui/Congratulations3.gif").getImage().getScaledInstance(800, 900, Image.SCALE_DEFAULT));
			JLabel gifLabel = new JLabel(imageIcon, SwingConstants.CENTER);
			imageIcon.setImageObserver(gifLabel);
			gifLabel.setForeground(Color.WHITE);
		    w.getContentPane().add(gifLabel);
		    w.getContentPane().setPreferredSize(WINDOW_SIZE);
	        w.pack();
	        w.setLocationRelativeTo(getMyClass());
	    	getMyClass().setVisible(false);
	        w.setBackground(Color.BLACK);
	        w.setVisible(true);
	        
	        Timer timer = new Timer(DELAY, new ActionListener() {
	    	    @Override
	        	public void actionPerformed(ActionEvent e) {
	    	    	gifLabel.setVisible(true);
	    	    	w.setVisible(false);
	    	    	setVisible(true);
	    	    	w.dispose();
	    	    	
	    	    }
	        });
	        timer.start();
	     }
	 };

	/**
	 * 
	 * @param theArgs
	 */
	public static void main(String[] theArgs) {
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GuiMain frame = new GuiMain();
            	frame.start();
            }
        });
	}
}