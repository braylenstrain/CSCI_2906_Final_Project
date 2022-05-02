import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class MathMinigame extends BorderPane {
	//Numbers used to determine arithmetic operations (set to -1 to show code error)
	private int firstNum = -1;
	private int secondNum = -1;
	private int operatorNum = -1;
	private char operatorChar; //For displaying the equation to the user
	//Constants to represent which operation is being performed
	private static final int ADD = 0;
	private static final int SUBTRACT = 1;
	private static final int MULTIPLY = 2;
	private static final int DIVIDE= 3;
	
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
		//Create timer and put at top
		String timerString = Integer.toString(timer);
		Text timerText = new Text(timerString);
		
		//Get the equation to put at center
		findEquation();
		
		//Create counter of problems vs total and put at bottom
		
		//Check for correct answer when key is pressed
		Capstone.scene.setOnKeyTyped(e -> {
			String answer = e.getCharacter();
			if (answer.compareTo("0") >= 0 && answer.compareTo("9") <= 0) {
				if (operatorNum == ADD) {
					if (firstNum + secondNum == Integer.parseInt(answer)) correctAnswer();
					else wrongAnswer();
				} else if (operatorNum == SUBTRACT) {
					if (firstNum - secondNum == Integer.parseInt(answer)) correctAnswer();
					else wrongAnswer();
				} else if (operatorNum == MULTIPLY) {
					if (firstNum * secondNum == Integer.parseInt(answer)) correctAnswer();
					else wrongAnswer();
				} else if (operatorNum == DIVIDE) {
					if (firstNum / secondNum == Integer.parseInt(answer)) correctAnswer();
					else wrongAnswer();
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
	
	private void correctAnswer() {
		findEquation();
	}
	
	private void wrongAnswer() {
		System.out.println("Incorrect");
	}
	

}
