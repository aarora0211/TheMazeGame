import java.awt.EventQueue;

/*Main for the MazeRunner game. 
 * Finished? */
public class TriviaMaze {
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
