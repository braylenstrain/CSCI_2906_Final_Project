import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class CirclesMinigame extends BorderPane {
	
	public CirclesMinigame(int level) {
		if (level == 1) {
			level1();
		} else if (level == 2) {
			level2();
		} else {
			level3();
		}
	}
	
	public void level1() {
		Text instructions = new Text("Welcome to the first minigame. When the game starts, you will begin to see circles appear.\nYou must click the circles with your mouse before they shrink away entirely.\nWhen you are ready, click the button below.");
		Button btStart = new Button("Let's Go!");
		setTop(instructions);
		BorderPane.setAlignment(instructions, Pos.CENTER);
		setCenter(btStart);
		BorderPane.setAlignment(btStart, Pos.CENTER);
	}
	
	public void level2() {
		
	}
	
	public void level3() {
		
	}

}
