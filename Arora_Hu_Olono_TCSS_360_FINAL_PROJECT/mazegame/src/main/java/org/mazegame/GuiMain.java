/*
 * Group Project Trivia Maze
 * Fall 2020
 */
package org.mazegame;

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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import java.util.Iterator;

/**
 * The GUI displaying the Game.
 * @author olono_arora_zixuan
 *https://stackoverflow.com/questions/19784628/saving-game-state#:~:text=You%20can%20use%20ObjectOutputStream%20and,readObject%20to%20load%20game%20states.
 */
public class GuiMain extends JFrame implements ActionListener, Runnable, Serializable {

	/**
	 * The default version ID.
	 */
	private static final long serialVersionUID = 6781575158234540112L;
			
	/**
	 * Current directory location for relative path.
	 */
	private static final String CURRENT_DIR_LOCATION = System.getProperty("user.dir");
	
	/**
	 * Crown image location.
	 */
	private static final String FINAL_ROOM_CROWN_IMG = "/src/main/java/org/mazegame/crown.png";
	
	/**
	 * Cross image location.
	 */
	private static final String FINAL_ROOM_CROSS_IMG = "/src/main/java/org/mazegame/Cross.png";
	
	
	/**
	 * Current Button.
	 */
	private int buttonPressed = 1;
	
	/**
	 * Maze object.
	 */
	private Maze myMaze;
	
	/**
	 * Image at the goal.
	 */
	private String myGoalImg = FINAL_ROOM_CROWN_IMG;
		
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
	private static final Dimension WINDOW_SIZE = new Dimension(700, 800);
	
	/**
	 * Map of Blocked Rooms.
	 */
	private Map<Integer, List<Integer>> myBlockedRooms = new HashMap<>();
	
	
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
		myMaze = new Maze();
		SwingUtilities.invokeLater((Runnable) this);		
		setSize(700, 800);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.BLACK);
	}
	
	/**
	 * Creates a frame with all the components for the trivia maze game. 
	 */
	public void start() {
		mainPanel.setLayout(null);
		mainPanel.setBounds(100, 100, 500, 700);
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
		serialize(CURRENT_DIR_LOCATION + "newGameTriviaMazeDoNotReplace.xyz");
	}
	
	/**
	 * actionPerformed instructs what actions to perform when the rooms in the Trivia Maze are clicked. 
	 */
    public void actionPerformed(ActionEvent a) {
        JButton button = (JButton) a.getSource();
        int newButton = Integer.parseInt(button.getActionCommand());
        if ((Math.abs(newButton - buttonPressed) == 1 || 
        		Math.abs(newButton - buttonPressed) == 5) && 
        		!(buttonPressed % 5 == 0 && newButton - buttonPressed == 1)) {
        	Map<String, String[]> myQs = myMaze.displayCoords(buttonPressed, newButton);
        	UIManager.put("OptionPane.background",Color.gray);  
    	    UIManager.put("Panel.background",Color.gray);
        	JPanel panel = new JPanel();
        	panel.setLayout(new BorderLayout());
        	String questionString = "";
        	if (((String) myQs.keySet().toArray()[0]).compareTo("Help") != 0) {
        		questionString = (String) myQs.keySet().toArray()[0];
        	}
            JLabel questionLabel = new JLabel("Question: " + questionString);
            panel.add(questionLabel, BorderLayout.NORTH);
            questionLabel.setForeground(Color.WHITE);
            questionLabel.setHorizontalAlignment(JLabel.CENTER);
            questionLabel.setVerticalAlignment(JLabel.CENTER);
            questionLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
            panel.setBackground(Color.GRAY);
            if (myQs.size() == 1 && questionString.compareTo("") != 0) {
	            ImageIcon img = new ImageIcon(new ImageIcon(CURRENT_DIR_LOCATION + 
	            		"/src/main/java/org/mazegame/questionIcon3.png").
	            		getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
	            Object response;
	            if (myQs.get(myQs.keySet().toArray()[0]).length > 0) {
	                response = JOptionPane.showInputDialog(this,panel 
	                		, "Question room #" + newButton,
	                        JOptionPane.QUESTION_MESSAGE, img, myQs.get(myQs.keySet().toArray()[0]),
	                        myQs.get(myQs.keySet().toArray()[0])
	                );
	            } else {
	                response = JOptionPane.showInputDialog(this,panel);
	            }
	            if(response != null) {
		            if (myMaze.isCorrect(response)) {
		            	buttonPressed = newButton;
		            	myCurrentButton = button;
		            	if (buttonPressed == 30) {
		
		    	        	congratulations.run();
		    	        	int restart = JOptionPane.showConfirmDialog(this,"Congratulations you won, would you like to restart the game?",
			            			"Congraturations",JOptionPane.YES_NO_CANCEL_OPTION);
		    	        	if(restart == JOptionPane.YES_OPTION) {
		    	                try {
									deserialize(CURRENT_DIR_LOCATION + "newGameTriviaMazeDoNotReplace.xyz");
									return;
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		    	        	} else if (restart == JOptionPane.CANCEL_OPTION || restart == JOptionPane.NO_OPTION) {
		    	        		setVisible(false);
		    	        		dispose();
		    	        		System.exit(0);
		    	        	}
		            	}
		            } else {		            
		            	JOptionPane.showMessageDialog(this,"Incorrect answer, door is now locked.",
		            			"Incorrect",JOptionPane.WARNING_MESSAGE);
		            	if (!myMaze.canContinue()) {
		            		int restart = JOptionPane.showConfirmDialog(this,"There are no paths remaining, game is over! Would you like to restart the game?",
			            			"Incorrect",JOptionPane.YES_NO_CANCEL_OPTION);
		    	        	if(restart == JOptionPane.YES_OPTION) {
		    	                try {
									deserialize(CURRENT_DIR_LOCATION + "newGameTriviaMazeDoNotReplace.xyz");
									return;
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		    	        	} else if (restart == JOptionPane.CANCEL_OPTION || restart == JOptionPane.NO_OPTION) {
		    	        		setVisible(false);
		    	        		dispose();
		    	        		System.exit(0);
		    	        	}
		            	}
		            	if (myBlockedRooms.containsKey(buttonPressed)) {
		                    myBlockedRooms.get(buttonPressed).add(newButton);
		                } else {
		                    //
		                    List<Integer> list=new ArrayList<Integer>();
		                    list.add(newButton);
		                    myBlockedRooms.put(buttonPressed, list);
		                }
		                if (myBlockedRooms.containsKey(newButton)) {
		                    myBlockedRooms.get(newButton).add(buttonPressed);
		                } else {
		                    //
		                    List<Integer> list=new ArrayList<Integer>();
		                    list.add(buttonPressed);
		                    myBlockedRooms.put(newButton, list);
		                }
		                
		                if (myBlockedButtons.containsKey(myCurrentButton)) {
		                    myBlockedButtons.get(myCurrentButton).add(button);
		                } else {
		                    List<JButton> list=new ArrayList<JButton>();
		                    list.add(button);
		                    myBlockedButtons.put(myCurrentButton, list);
		                }
		                if (myBlockedButtons.containsKey(button)) {
		                    myBlockedButtons.get(button).add(myCurrentButton);
		                } else {
		                    List<JButton> list=new ArrayList<JButton>();
		                    list.add(myCurrentButton);
		                    myBlockedButtons.put(button, list);
		                }
		            }

	            }
	        	List<JButton> list = new ArrayList<JButton>();
	            if (myBlockedButtons.containsKey(myCurrentButton)) {
	            	list = myBlockedButtons.get(myCurrentButton);
	            }
	        	for (int i = 0; i < myMovementButtons.length; i++) {
	        		if (list.contains(myMovementButtons[i])) {
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
            } else if (questionString.compareTo("") == 0) {
            	buttonPressed = newButton;
            } else if (myQs.size() == 2) {
            	JOptionPane.showMessageDialog(this,"Door is locked","Inaccessible",
            			JOptionPane.WARNING_MESSAGE);
            }
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
		JMenuItem exit = new JMenuItem("Exit");

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
	              try {
					deserialize(fileName);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

	            }
			}
		});
		
		saveAs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
	            final JFileChooser chooser = new JFileChooser(".");
	            final int result = chooser.showSaveDialog(getMyClass());
	            if (result == JFileChooser.APPROVE_OPTION)
	            {
	                final File selectedFile = chooser.getSelectedFile();
	              String fileName = selectedFile.getAbsolutePath();
	              serialize(fileName);
	            }
			}
		});
		
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		fileMenu.add(open);
		fileMenu.add(saveAs);
		fileMenu.add(exit);

		return fileMenu;
	}
	
	/**
	 * This method serializes the state of the current game.
	 * @param location, the location for saving the game.
	 */
	 @SuppressWarnings("resource")
	private void serialize(String location) {

         try
         {    
             //Saving of object in a file 
             FileOutputStream file = new FileOutputStream(location); 
             ObjectOutputStream out = new ObjectOutputStream(file); 
               
             // Method for serialization of object 
             
             out.writeObject("" + buttonPressed);
             out.writeObject(myMaze);
             out.writeObject(myGoalImg);
             out.writeObject(myBlockedRooms);
                          
             out.close();
             file.close();
               
             FileChannel sourceChannel = null;
             FileChannel destChannel = null;
             
             Path src = Paths.get(CURRENT_DIR_LOCATION + "/java-sqlite.db");
             Path dest = Paths.get(location + ".db");

             try {
                 sourceChannel = new FileInputStream(src.toFile()).getChannel();
                 destChannel = new FileOutputStream(dest.toFile()).getChannel();
                 destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
                }finally{
                    sourceChannel.close();
                    destChannel.close();
            }
         }
         catch(IOException ex) 
         { 
             ex.printStackTrace();
         } 
	}
	 
	 /**
	  * this method deserializes the state of the current game.
	  * @param fileName the path of the file to be deserialized.
	  * @throws ClassNotFoundException
	  */
     @SuppressWarnings({ "unchecked", "resource" })
	private void deserialize(String fileName) throws ClassNotFoundException {
         try
         {    
             //Saving of object in a file 
             FileInputStream file = new FileInputStream(fileName); 
             ObjectInputStream in = new ObjectInputStream(file);
               
             // Method for serialization of object 
             
             String buttonString = (String) in.readObject();
             buttonPressed = Integer.parseInt(buttonString);
             myMaze = (Maze) in.readObject();
             myGoalImg = (String) in.readObject();
             myBlockedRooms = (Map<Integer, List<Integer>>) in.readObject();
             
             myCurrentButton = myMovementButtons[buttonPressed - 1];
             in.close();
             file.close();
             FileChannel sourceChannel = null;
             FileChannel destChannel = null;

             Path dest = Paths.get(CURRENT_DIR_LOCATION + "/java-sqlite.db");
             Path src = Paths.get(fileName + ".db");

             try {
                 sourceChannel = new FileInputStream(src.toFile()).getChannel();
                 destChannel = new FileOutputStream(dest.toFile()).getChannel();
                 destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
                }finally{
                    sourceChannel.close();
                    destChannel.close();
            }

             restoreButtonsMap(myBlockedRooms);
             
               
             revalidate();
             repaint();
         } 
         catch(IOException ex) 
         { 
             ex.printStackTrace();
         } 
     }
     
     /**
      * restores the buttonMap for the recently deserialized game.
      * @param theBlockedRooms the Map containing all the room numbers that should be blocked. 
      */
     private void restoreButtonsMap(Map<Integer, List<Integer>> theBlockedRooms) {
    	 Iterator<Integer> roomItr = theBlockedRooms.keySet().iterator();
		 Map<JButton, List<JButton>> blockedButtons = new HashMap<>();
    	 while(roomItr.hasNext()) {
    		 int room = roomItr.next();
    		 List<Integer> roomList = theBlockedRooms.get(room);
    		 JButton roomButton = myMovementButtons[room - 1];
    		 for (int i = 0; i < roomList.size(); i++) {
    			 int blockedRoom = roomList.get(i);
    			 if (blockedButtons.containsKey(roomButton)) {
    				 blockedButtons.get(roomButton).add(myMovementButtons[blockedRoom - 1]);
    			 } else {
    				 List<JButton> buttonList = new ArrayList<>();
    				 buttonList.add(myMovementButtons[blockedRoom - 1]);
    				 blockedButtons.put(roomButton, buttonList);
    			 }
    		 }
    	 }
    	 myBlockedButtons = blockedButtons;
         restoreButtons(myCurrentButton);
     }
     
     /**
      * restores all the buttons and sets the as visible or not visible.
      * @param myCurrentButton the current room button.
      */
     private void restoreButtons(JButton myCurrentButton) {
    	 if (myBlockedButtons.containsKey(myCurrentButton)) {
        	 List<JButton> myBlockedButtonList = myBlockedButtons.get(myCurrentButton);
        	 for (int i = 0; i < myMovementButtons.length; i++) {
        		 if (myBlockedButtonList.contains(myMovementButtons[i])) {
        			 myMovementButtons[i].setVisible(false);
        		 } else {
        			 myMovementButtons[i].setVisible(true);
        		 }
         		if (myBlockedButtonList.contains(myMovementButtons[29])) {
        			myGoalImg = FINAL_ROOM_CROSS_IMG;
        		} else {
        			myGoalImg = FINAL_ROOM_CROWN_IMG;
        		}
        	 }
    	 } else {
        	 for (int i = 0; i < myMovementButtons.length; i++) {
    			 myMovementButtons[i].setVisible(true);
        	 }
    	 }
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
            	JOptionPane.showMessageDialog(getMyClass(), "To play the Trivia Maze, follow the given instructions:\n\n "
            			+ "1.	Simply click on the room you want to enter. \n You can move up, down, left, or right from the current room to the next room, \n"
            			+ "given that the rooms are present in those directions. \n\n"
            			+ "2.   You need to answer the given question correctly in order to go to some other room.\n"
            			+ "If answered incorrectly, the door between the rooms would become permanently locked from that direction. \n"
            			+ "You can still access it from other directions though.\n"
            			+ " Once answered correctly, the door between the rooms becomes opened for the whole duration of the game.\n\n" 
            			+ "3.	You need to reach the crown in order to win the game.\n"
            			+ "4.	To save or load an existing game, you need to go to the file menu and press save/load.");
            }
        });
        
		aboutTriviaMaze.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
            	JOptionPane.showMessageDialog(getMyClass(), "Trivia Maze is a game developed by Arora, Hu, Olono. \n"
            			+ "The game consists of some randomly generated doors and rooms that we need to answer correctly in order to win the game. \n"
            			+ "The theme of the Trivia Maze is Math, Soccer, and Video Game. Sounds exiting? It is.\n\n\n"
            			+ "It’s really fun, do try it!");
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
	 * @return JRadioButtonMenuItem for the color menu.
	 */
    private JRadioButtonMenuItem createColorItems(final String theName, 
    		final Color theColor, final ButtonGroup theGroup) {
        
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

	
    /**
     * Class for creating the Panel with the specified background features.
     * @author arora_olono_hu
     *
     */
	class MyPanel extends JPanel implements Serializable {

		/**
		 * The default serial version ID.
		 */
		private static final long serialVersionUID = 2663373888044429766L;

		/**
		 * Instructs the GUI how the panel's background should be painted. 
		 */
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
			    	        g.drawLine(50 + 88*j + 50, 0 + 88*i + 25, 50 + 88*j + 
			    	        		50 + 44, 0 + 88*i + 25);
		    	        }
		    	        if (i < 5) {
			    	        g.drawLine(50 + 88*j + 25, 0 + 88*i + 50, 50 + 88*j + 25, 
			    	        		0 + 88*i + 50 + 44);
		    	        }
		    	        FontMetrics fm = g.getFontMetrics();
		    	        String roomNo = "" + count;
		    	        double textWidth = fm.getStringBounds(roomNo, g).getWidth();
		    	        g.setColor(Color.BLACK);
		    	        g.drawString(roomNo, (int) (50 + 88*j + 25 - textWidth / 2),(int) 
		    	        		(0 + 88*i + 25 + fm.getMaxAscent() / 2));
		    	        count++;
		        	} else {
		        	    BufferedImage img = null;
		        	    try {
		        	        img = ImageIO.read(new File(CURRENT_DIR_LOCATION + myGoalImg));
		        	    } catch (IOException e) {
		        	    	e.printStackTrace();
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
	        g.drawString("You are currently at Room #" + buttonPressed, 100, 560);
	        
	        // Legend at the bottom of the Panel.
	        g.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
	        g.setColor(Color.YELLOW);
	        g.drawString("YELLOW = CURRENT ROOM", 0, 630);
	        g.setColor(Color.RED);
	        g.drawString("RED = ROOM LOCKED", 200, 630);
	        g.setColor(Color.CYAN);
	        g.drawString("CYAN = ROOM OPEN", 370, 630);
	    }		
	}

	/**
	 * Creates a welcome splash screen window with a loading bar.
	 */
	@Override
	public void run() {
		if (buttonPressed == 1) {
			final JWindow w = new JWindow();
			w.getContentPane().setBackground(Color.BLACK);
			ImageIcon imageIcon = new ImageIcon(new ImageIcon(CURRENT_DIR_LOCATION + 
					"/src/main/java/org/mazegame/loading4.gif").getImage().
					getScaledInstance(800, 900, Image.SCALE_DEFAULT));
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
	
	
	/**
	 * Runnable for displaying the congratulations splash screen.
	 */
	Runnable congratulations = new Runnable() {
	     public void run() {
	 		final JWindow w = new JWindow();
	 		w.setAlwaysOnTop(true);
			w.getContentPane().setBackground(Color.BLACK);  
			ImageIcon imageIcon = new ImageIcon(new ImageIcon(CURRENT_DIR_LOCATION + 
					"/src/main/java/org/mazegame/Congratulations3.gif").
					getImage().getScaledInstance(800, 900, Image.SCALE_DEFAULT));
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
}