package edu.wm.cs.cs301.soleonv.falstad;



import edu.wm.cs.cs301.soleonv.falstad.Constants.StateGUI;
import edu.wm.cs.cs301.soleonv.falstad.MazeController.DriverList;
import edu.wm.cs.cs301.soleonv.generation.Order;

/**
 * Implements the screens that are displayed whenever the game is not in 
 * the playing state. The screens shown are the title screen, 
 * the generating screen with the progress bar during maze generation,
 * and the final screen when the game finishes.
 * @author pk
 *
 */
public class MazeView extends DefaultViewer {

	// need to know the maze model to check the state 
	// and to provide progress information in the generating state
	public MazeController controller ;
	public NewMazePanel mazepanel;
	
	///added to figure out how to generate what is selected from the menu
	public RobotDriver driver; 
	
	public MazeView(MazeController c) {
		super() ;
		controller = c ;
	}

	@Override
	public void redraw(NewMazePanel mazepanel, StateGUI state, int px, int py, int view_dx,
			int view_dy, int walk_step, int view_offset, RangeSet rset, int ang) {
		//dbg("redraw") ;
		//Graphics gc = mazepanel.getBufferGraphics();
		switch (state) {
		case STATE_TITLE:
			//redrawTitle(gc);
			break;
		case STATE_GENERATING:
			//redrawGenerating(gc);
			break;
		case STATE_PLAY:
			// skip this one
			break;
		case STATE_FINISH:
			//redrawFinish(gc);
			break;
		case STATE_FAILURE:

			break;

		}
	}
	
	private void dbg(String str) {
		System.out.println("MazeView:" + str);
	}


}
