import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class FastTapMinigame extends BorderPane{
	private Pane pane = new Pane();
	private final int PANE_SIZE = 700; //Width of playable game area
	
	public FastTapMinigame(int level) {
		if (level == 1) {
			level1();
		} else if (level == 2) {
			level2();
		} else {
			level3();
		}
	}
	
	//Starts level1 version of Fast Tap Minigame
	private void level1() {
		//Instructions explaining the minigame
		Text instructions = new Text("This minigame is Fast Tap.\nYou must tap the key shown on the screen as fast possible.\nPay attention! The key could switch midgame.\nThe counter below the key shows how many times you've tapped the key vs how many times you must tap total.");
		instructions.setFont(Font.font(15));

		Button btStart = new Button("Let's Go!");//Button to start the game
		
		//Put instructions and btStart into this
		setTop(instructions);
		BorderPane.setAlignment(instructions, Pos.CENTER);
		setCenter(btStart);
		BorderPane.setAlignment(btStart, Pos.CENTER);
	}
	
	private void level2() {
		
	}
	
	private void level3() {
		
	}
}
