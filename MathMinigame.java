import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class MathMinigame extends BorderPane {
	private int firstNum = -1;
	private int secondNum = -1;
	private int operatorNum = -1;
	
	public MathMinigame(int level) {
		//Button to start the game
		Button btStart = new Button("Let's Go!");
		
		if (level == 1) {
			//Instructions explaining the minigame
			Text instructions = new Text("This minigame is Simple Arithmetic.\n"
					+ "You have to answer all the math problems in the designated time.\n"
					+ "Once you have pressed a number key, your answer will be submitted automatically.\n"
					+ "Every answer will be a single digit answer, so I recommend using the keypad.\n"
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
				playGame(10, 10);
			});
		} else if (level == 2) {
			Text text = new Text("Simple Arithmetic: Level 2\n15 problems, 12 seconds");
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
		} else {
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
		
		//Create problem and put at center
		for (int i = 0; i < 100; i++) {
			//Get numbers
			firstNum = (int)(Math.random() * 10);
			secondNum = (int)(Math.random() * 10);
			
			//Get operation
			operatorNum = (int)(Math.random() * 4);
			//Addition
			if (operatorNum == 0) {
				if (firstNum + secondNum < 10);
				break;
			//Subtraction	
			} else if (operatorNum == 1) {
				if (firstNum - secondNum < 10);
				break;
			//Multiplication
			} else if (operatorNum == 2) {
				if (firstNum * secondNum < 10);
				break;
			//Division
			} else if (operatorNum == 3) {
				if (firstNum / secondNum < 10);
				break;
			} else {
				System.out.println("ERROR");
			}
		}
		
		//Check for correct answer when key is pressed
		Capstone.scene.setOnKeyTyped(e -> {
			String answer = e.getCharacter();
			if (operatorNum == 0) {
				if (firstNum + secondNum == Integer.parseInt(answer)) correctAnswer();
				else wrongAnswer();
			} else if (operatorNum == 1) {
				if (firstNum - secondNum == Integer.parseInt(answer)) correctAnswer();
				else wrongAnswer();
			}
		});
		
		//Create counter of problems vs total and put at bottom
	}
	
	private void correctAnswer() {
		
	}
	
	private void wrongAnswer() {
		
	}
}
