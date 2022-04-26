package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class FastTapMinigame extends BorderPane{
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
		Text instructions = new Text("This minigame is Fast Tap.\nYou must tap the displayed key enough times before the timer reaches 0.\nPay attention! The key could switch midgame.\nThe counter below the key shows how many times you've tapped the key\nvs how many times you must tap total.");
		instructions.setFont(Font.font(15));

		Button btStart = new Button("Let's Go!");//Button to start the game
		
		//Put instructions and btStart into this
		setTop(instructions);
		BorderPane.setAlignment(instructions, Pos.CENTER);
		setCenter(btStart);
		BorderPane.setAlignment(btStart, Pos.CENTER);
		
		//Starts the minigame when btStart is clicked
		btStart.setOnAction(e -> {
			getChildren().clear();
			playGame(10, 20);
		});
	}
	
	//Play the minigame using the given time and number of taps
	private void playGame(int time, int numberOfTaps) {
		//Set time as Text to top and create countdown animation to 0
		String timeString = Integer.toString(time);
		Text timeText = new Text(timeString);
		timeText.setFont(Font.font(80));
		setTop(timeText);
		setAlignment(timeText, Pos.CENTER);
		Timeline countdownAnimation = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
			int countdown = Integer.parseInt(timeText.getText());
			timeText.setText(Integer.toString(--countdown));
		}));
		countdownAnimation.setCycleCount(time);
		countdownAnimation.play();
		
		//TODO Use while loop to keep game going while counter is greater than 0 and number of actual taps is less than total
		//TODO randomly choose key to display in center (with box around it) and add functionality to add to tap count when correct key is pressed. Allow for switching keys at random intervals
		//TODO Add tap counter to bottom (current / total)
	}
	
	private void level2() {
		//TODO Just like level 1 with different parameters when playGame is called
	}
	
	private void level3() {
		
	}
}
