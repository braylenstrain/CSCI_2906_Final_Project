package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.Duration;
import javafx.scene.shape.Rectangle;

public class FastTapMinigame extends BorderPane {
	private String key; //The name of the key that needs to be tapped
	private Enum<KeyCode> keyCode; //The keycode of the key that needs to be tapped
	private int currentTaps; //How many times the user has tapped the correct key
	private static boolean notHeldDown = true; //Used to make sure player isn't holding down a key to count taps
	
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
		Text instructions = new Text("This minigame is Fast Tap.\n"
				+ "You must tap the displayed key enough times before the timer reaches 0.\n"
				+ "Pay attention! The key could switch midgame.\n"
				+ "The counter below the key shows how many times you've tapped the key\n"
				+ "vs how many times you must tap total.");
		instructions.setFont(Font.font(15));
		
		//Button to start the game
		Button btStart = new Button("Let's Go!");
		
		//Put instructions and btStart into this
		setTop(instructions);
		BorderPane.setAlignment(instructions, Pos.CENTER);
		setCenter(btStart);
		BorderPane.setAlignment(btStart, Pos.CENTER);
		
		//Starts the minigame when btStart is clicked
		btStart.setOnAction(e -> {
			getChildren().clear();
			playGame(10, 30);
		});
	}
	
	private void level2() {
		//TODO Just like level 1 with different parameters when playGame is called
	}
	
	private void level3() {
		//TODO
	}
	
	//Play the minigame using the given time and number of taps
	private void playGame(int time, int numberOfTaps) {
		//Set time as Text to top
		String timeString = Integer.toString(time);
		Text timeText = new Text(timeString);
		timeText.setFont(Font.font(80));
		setTop(timeText);
		setAlignment(timeText, Pos.CENTER);
		
		//Create Timeline animation that counts down to 0. Each pass changes timeText and calls keySwitch
		Timeline countdownAnimation = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
			int countdown = Integer.parseInt(timeText.getText());
			timeText.setText(Integer.toString(--countdown));
			keySwitch(Integer.parseInt(timeText.getText()), time);
		}));
		countdownAnimation.setCycleCount(time);
		countdownAnimation.play();
		
		//If countdown gets to 0, set loseText and btNextMinigame, and take a life
		countdownAnimation.setOnFinished(e -> {
			Text loseText = new Text("TOO BAD SO SAD YOU WERE TOO SLOW HA!");
			loseText.setFont(Font.font(30));
			setCenter(loseText);
			setBottom(Capstone.btNextMinigame);
			setAlignment(Capstone.btNextMinigame, Pos.CENTER_RIGHT);
			setMargin(Capstone.btNextMinigame, new Insets(20));
			this.requestFocus(); //So that the user doesn't accidentally skip past if the last tapped key was ENTER
			Capstone.setLives(Capstone.getLives() - 1);
			Capstone.scene.setOnKeyPressed(null); //So that user doesn't get the win text after already losing if they keep tapping
		});
		
		//Set up counter and total at bottom of screen
		HBox tapCounterAndTotal = new HBox();
		tapCounterAndTotal.setSpacing(30);
		
		Text tapCounter = new Text("0");
		Text forwardSlash = new Text("/");
		Text totalTaps = new Text(Integer.toString(numberOfTaps));
		tapCounter.setFont(Font.font(20));
		forwardSlash.setFont(Font.font(20));
		totalTaps.setFont(Font.font(20));
		
		tapCounterAndTotal.getChildren().addAll(tapCounter, forwardSlash, totalTaps);
		setBottom(tapCounterAndTotal);
		BorderPane.setMargin(tapCounterAndTotal, new Insets(0, 0, 200, 295));
		
		//Add first key to center
		displayKey();
		
		//Register key presses and call checkForWin()
		Capstone.scene.setOnKeyPressed(e -> {
			if (e.getCode() == keyCode && notHeldDown) {
				currentTaps++;
				tapCounter.setText(Integer.toString(currentTaps));
				checkForWin(numberOfTaps, countdownAnimation);
				notHeldDown = false;
			}
		});
		
		//Changes notHeldDown to true to prevent holding down the key to count as tapping
		Capstone.scene.setOnKeyReleased(e -> notHeldDown = true);
	}
	
	//Checks if currents taps has hit or passed numberOfTaps. If so, displays winText.
	private void checkForWin(int numberOfTaps, Timeline countdownAnimation) {
		if (currentTaps >= numberOfTaps) {
			countdownAnimation.stop();
			Text winText = new Text("ALRIGHT ALRIGHT you can stop tapping now! You got it.");
			winText.setFont(Font.font(20));
			setCenter(winText);
			setBottom(Capstone.btNextMinigame);
			setAlignment(Capstone.btNextMinigame, Pos.CENTER_RIGHT);
			setMargin(Capstone.btNextMinigame, new Insets(20));
			this.requestFocus(); //So that the user doesn't accidentally skip past if the last tapped key was ENTER
		}
	}
	
	//Switches the key that needs to be tapped at specific intervals
	private void keySwitch(int currentTime, int time) {
		if (currentTime == 3 || currentTime == 6 || currentTime == 12 || currentTime == 16 || currentTime == 18) {
			displayKey();
		}
	}
	
	//Displays the key that needs to be tapped
	private void displayKey() {
		StackPane keyPane = new StackPane();
		Rectangle keyBorder = new Rectangle(150, 150, Color.WHITE);
		keyBorder.setStroke(Color.BLACK);
		getKey();
		Text keyText = new Text(key);
		keyText.setFont(Font.font(40));
		keyPane.getChildren().addAll(keyBorder, keyText);
		setCenter(keyPane);
	}
	
	//Randomely choose key to be tapped
	private void getKey() {
		switch((int)(Math.random() * 8)) {
		case 0:
			key = "ENTER";
			keyCode = KeyCode.ENTER;
			break;
		case 1:
			key = "SPACE";
			keyCode = KeyCode.SPACE;
			break;
		case 2:
			key = "T";
			keyCode = KeyCode.T;
			break;
		case 3:
			key = "D";
			keyCode = KeyCode.D;
			break;
		case 4:
			key = "M";
			keyCode = KeyCode.M;
			break;
		case 5:
			key = ";";
			keyCode = KeyCode.SEMICOLON;
			break;
		case 6:
			key = " DOWN\nARROW";
			keyCode = KeyCode.DOWN;
			break;
		case 7:
			key = "[";
			keyCode = KeyCode.OPEN_BRACKET;
			break;
		}
	}
	
}
