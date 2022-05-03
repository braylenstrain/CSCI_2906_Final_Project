package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.util.Duration;

public class MathMinigame extends BorderPane {
	//Numbers used to determine arithmetic operations (set to -1 to show code error)
	private int firstNum = -1;
	private int secondNum = -1;
	private int operatorNum = -1;
	//For displaying the equation to the user
	private char operatorChar;
	//Constants to represent which operation is being performed
	private static final int ADD = 0;
	private static final int SUBTRACT = 1;
	private static final int MULTIPLY = 2;
	private static final int DIVIDE= 3;
	//Counter for how many problems have been answered correctly vs total
	private int counter;
	private Text counterText;
	private int total;
	//Timer animation
	private Timeline countdownAnimation;
	
	public MathMinigame(int level) {
		//Button to start the game
		Button btStart = new Button("Let's Go!");
		
		if (level == 1) {
			//Instructions explaining the minigame
			Text instructions = new Text("\t\t\t\t\tLevel 1: 10 problems, 15 seconds\n\n"
					+ "This minigame is Simple Arithmetic.\n"
					+ "You have to answer all the math problems in the designated time.\n"
					+ "Once you have pressed a number key, your answer will be submitted automatically.\n"
					+ "Every answer will be a single digit answer.\n"
					+ "Be wary! If you input the wrong answer, you'll lose the minigame.");
			instructions.setFont(Font.font(15));

			//Put instructions and btStart into top and center respectively
			setTop(instructions);
			BorderPane.setAlignment(instructions, Pos.CENTER);
			setCenter(btStart);
			BorderPane.setAlignment(btStart, Pos.CENTER);
			
			//Starts the minigame when btStart is clicked
			btStart.setOnAction(e -> {
				getChildren().clear();
				playGame(10, 15);
			});
		} else if (level == 2) {
			//Intro
			Text text = new Text("Simple Arithmetic: Level 2\n12 problems, 12 seconds");
			text.setFont(Font.font(30));
			setTop(text);
			BorderPane.setAlignment(text, Pos.CENTER);
			setCenter(btStart);
			BorderPane.setAlignment(btStart, Pos.CENTER);
			
			//Start the minigame when btStart is clicked
			btStart.setOnAction(e -> {
				getChildren().clear();
				playGame(12, 12);
			});
		} else {
			//Intro
			Text text = new Text("Simple Arithmetic: Level 3\n15 problems, 10 seconds");
			text.setFont(Font.font(30));
			setTop(text);
			BorderPane.setAlignment(text, Pos.CENTER);
			setCenter(btStart);
			BorderPane.setAlignment(btStart, Pos.CENTER);
			
			//Start the minigame when btStart is clicked
			btStart.setOnAction(e -> {
				getChildren().clear();
				playGame(15, 10);
			});
		}
	}
	
	private void playGame(int numberOfProblems, int timer) {
		total = numberOfProblems; //For use later with correctAnswer()
		
		//Create timer and put at top
		String timerString = Integer.toString(timer);
		Text timerText = new Text(timerString);
		timerText.setFont(Font.font(80));
		setTop(timerText);
		setAlignment(timerText, Pos.CENTER);
		
		//Create Timeline animation that counts down to 0. Each pass changes timerText and calls keySwitch
		countdownAnimation = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
			int countdown = Integer.parseInt(timerText.getText());
			timerText.setText(Integer.toString(--countdown));
		}));
		countdownAnimation.setCycleCount(timer);
		countdownAnimation.play();
		
		//If countdown gets to 0, set loseText and btNextMinigame, and take a life
		countdownAnimation.setOnFinished(e -> {
			Text loseText = new Text("Out of time.");
			loseText.setFont(Font.font(30));
			setCenter(loseText);
			setBottom(Capstone.btNextMinigame);
			setAlignment(Capstone.btNextMinigame, Pos.CENTER_RIGHT);
			setMargin(Capstone.btNextMinigame, new Insets(20));
			Capstone.setLives(Capstone.getLives() - 1);
			Capstone.scene.setOnKeyTyped(null); //So that user can't keep inputting answers
		});
		
		//Get the equation to put at center
		findEquation();
		
		//Create counter of problems vs total and put at bottom
		counterText = new Text(counter + " / " + numberOfProblems);
		counterText.setFont(Font.font(30));
		setBottom(counterText);
		setAlignment(counterText, Pos.CENTER);
		setMargin(counterText, new Insets(20));
		
		//Check for correct answer when key is pressed
		Capstone.scene.setOnKeyTyped(e -> {
			String answer = e.getCharacter();
			if (answer.compareTo("0") >= 0 && answer.compareTo("9") <= 0) {
				if (operatorNum == ADD) {
					if (firstNum + secondNum == Integer.parseInt(answer)) correctAnswer();
					else wrongAnswer(answer);
				} else if (operatorNum == SUBTRACT) {
					if (firstNum - secondNum == Integer.parseInt(answer)) correctAnswer();
					else wrongAnswer(answer);
				} else if (operatorNum == MULTIPLY) {
					if (firstNum * secondNum == Integer.parseInt(answer)) correctAnswer();
					else wrongAnswer(answer);
				} else if (operatorNum == DIVIDE) {
					if (firstNum / secondNum == Integer.parseInt(answer)) correctAnswer();
					else wrongAnswer(answer);
				}
			}
		});
	}
	
	//Choose the numbers and operator for an equation and display in center
	private void findEquation() {
		for (int i = 0; i < 100; i++) {
			//Get numbers
			firstNum = (int)(Math.random() * 10);
			secondNum = (int)(Math.random() * 10);
			
			//Get operator
			operatorNum = (int)(Math.random() * 4);
			
			//Addition
			if (operatorNum == ADD) {
				if (firstNum + secondNum < 10) {
					operatorChar = '+';
					break;
				}
			//Subtraction	
			} else if (operatorNum == SUBTRACT) {
				if (firstNum - secondNum < 10 && firstNum - secondNum >= 0) {
					operatorChar = '-';
					break;
				}
			//Multiplication
			} else if (operatorNum == MULTIPLY) {
				if (firstNum * secondNum < 10) {
					operatorChar = '\u00D7';
					break;
				}
			//Division
			} else if (operatorNum == DIVIDE) {
				if (secondNum != 0 && firstNum / secondNum < 10 && firstNum % secondNum == 0) {
					operatorChar = '\u00F7';
					break;
				}
			} else {
				System.out.println("ERROR");
			}
			
			if (i == 99) {
				firstNum = 0;
				secondNum = 0;
				operatorNum = 0;
				operatorChar = '+';
			}
		}
		
		//Display equation
		String equation = String.format("%d %c %d", firstNum, operatorChar, secondNum);
		Text equationText = new Text(equation);
		equationText.setFont(Font.font(50));
		setCenter(equationText);
	}
	
	//Change equation when player inputs a correct answer. Check for win each call.
	private void correctAnswer() {
		//Increase counter and update counterText
		counter++;
		counterText.setText(counter + " / " + total);
		
		//Check for win
		if (counter == total) {
			countdownAnimation.stop(); //Stops the timer
			Capstone.scene.setOnKeyTyped(null); //Game no longer registers typed keys
			
			//Put winText in center
			Text winText = new Text("\t\t\tWrong!\nJust kidding you got them all right :).");
			winText.setFont(Font.font(30));
			setCenter(winText);
			
			//Put next button at bottom
			setBottom(Capstone.btNextMinigame);
			setAlignment(Capstone.btNextMinigame, Pos.CENTER_RIGHT);
			setMargin(Capstone.btNextMinigame, new Insets(20));
			
		} else {
			findEquation();
		}
	}
	
	//Display correct answer, take a life, and show next button
	private void wrongAnswer(String answer) {
		//Stop and clear timer
		countdownAnimation.stop();
		setTop(null);
		
		Capstone.scene.setOnKeyTyped(null); //Game stops registering typed keys
		
		//Save the answer to the equation in a variable
		int equationAnswer = -1;
		if (operatorNum == ADD) equationAnswer = firstNum + secondNum;
		if (operatorNum == SUBTRACT) equationAnswer = firstNum - secondNum;
		if (operatorNum == MULTIPLY) equationAnswer = firstNum * secondNum;
		if (operatorNum == DIVIDE) equationAnswer = firstNum / secondNum;
		
		//Create loseText and put in center
		Text loseText = new Text(String.format("\tIncorrect.\n\t%d %c %d = %d\nYour answer was %s.", firstNum, operatorChar, secondNum, equationAnswer, answer));
		loseText.setFont(Font.font(30));
		setCenter(loseText);
		
		//Take a life
		Capstone.setLives(Capstone.getLives() - 1);
		
		//Put next button at bottom
		setBottom(Capstone.btNextMinigame);
		setAlignment(Capstone.btNextMinigame, Pos.CENTER_RIGHT);
		setMargin(Capstone.btNextMinigame, new Insets(20));
	}

}
