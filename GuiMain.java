/**
 * 
 */
package Gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

/**
 * @author aeora44
 *
 */
public class GuiMain extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private static GuiMain FRAME = new GuiMain();;

	
	/**
	 * 
	 */
	public GuiMain() {
		super("Trivia Maze");
		//this.setLayout(new GroupLayout(this));
		setSize(800, 800);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * 
	 */
	public void start() {
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(150, 150, 500, 500);
		add(mainPanel);
		mainPanel.setBackground(Color.GRAY);
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
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final JFileChooser chooser = new JFileChooser();
                final int result = chooser.showOpenDialog(FRAME);
                if (result == JFileChooser.APPROVE_OPTION)
                {
                    final File selectedFile = chooser.getSelectedFile();
                  String fileName = selectedFile.getAbsolutePath();
                  System.out.println(fileName);
                }
            }
        });
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
	
	/**
	 * 
	 * @param theArgs
	 */
	public static void main(String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	FRAME.start();
            }
        });
	}
}