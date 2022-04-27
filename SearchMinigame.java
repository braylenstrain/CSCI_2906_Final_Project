
import java.util.ArrayList;

import javafx.scene.layout.*;
import javafx.scene.Node;

public class SearchMinigame extends BorderPane {
	//TODO node mouse enter/leave, arraylist of nodes shuffled, randomly choose node to find, difficulty increases by number of nodes and time
	ArrayList<Node> nodeList = new ArrayList<>();
	
	public SearchMinigame(int level) {
		if (level == 1) {
			level1();
		} else if (level == 2) {
			level2();
		} else {
			level3();
		}
	}

	private void level1() {
		// TODO Auto-generated method stub
		
	}
	
	private void level2() {
		// TODO Auto-generated method stub
		
	}
	
	private void level3() {
		// TODO Auto-generated method stub
		
	}
}
