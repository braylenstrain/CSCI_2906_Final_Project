
//TODO display the minigames (if it's level 1, display instructions first), have a countdown
//TODO how to deal with losses in both minigames and overall. Need a restart option.
//TODO final end game win "I never doubted you for a second"
/*
 * Author: Braylen Strain
 * Date: TODO
 * 
 * This program is a game of minigames. The user has to beat each minigame to move on to the next level. After beating 3 levels, they win.
 */
import javafx.application.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.scene.control.Button;

public class Capstone extends Application{
	private int level = 1;
	private static int lives = 3;
	private static int preMinigameLives = 3;
	public static Button btNextMinigame = new Button("Next");
	private Pane pane;
	private final int WINDOW_SIZE = 700;

	public static void main(String[] args) {
		launch(args);
	}

	public static int getLives() {
		return lives;
	}

	public static void setLives(int lives) {
		Capstone.lives = lives;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		btNextMinigame.setPrefSize(200, 100); //Make button bigger so it's more noticeable
		btNextMinigame.setFont(Font.font(40));
		pane = new StackPane(); //The pane that will show the introduction
		Text introductionText = new Text("Welcome to my game."); //Introduction text explaining the game
		
		//Increase size of intro text and put into pane
		introductionText.setFont(Font.font(30));
		pane.getChildren().add(introductionText);
		
		//Set the scene and stage
		Scene scene = new Scene(pane, WINDOW_SIZE, WINDOW_SIZE);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Can You Beat My Game?");
		primaryStage.show();
		primaryStage.setResizable(false);

		//Create a thread to introduce the game, changing the contents of introductionText periodically, then calling runFirstMinigame()
		new Thread(() -> {
			try {
				//TODO fix sleep numbers
				Thread.sleep(000);
				introductionText.setText("Do you think you can beat it?");
				Thread.sleep(000);
				introductionText.setText("We will see.");
				Thread.sleep(000);
				introductionText.setText("You have 3 lives to beat 3 levels.\nThere are 5 minigames per level.\nLose a minigame, lose a life.\nLose all your lives, game over.");
				Thread.sleep(000);
				Platform.runLater(() -> pane.getChildren().clear());
				Platform.runLater(() -> runFirstMinigame(primaryStage));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		
		//After a minigame ends, btNextMinigame will be displayed. The user clicks it to move on.
		btNextMinigame.setOnAction(e -> {
			if (pane instanceof CirclesMinigame) {
				runSecondMinigame(primaryStage);
			}
		});
	}
	
	//Run the first minigame after the intro is done
	private void runFirstMinigame(Stage primaryStage) {
		pane = new CirclesMinigame(level);
		Scene scene = new Scene(pane, WINDOW_SIZE, WINDOW_SIZE);
		primaryStage.setScene(scene);
	}
	
	//Run the second minigame
	private void runSecondMinigame(Stage primaryStage) {
		new Thread(() -> {
			try {
				if (preMinigameLives != lives) {
					Platform.runLater(() -> lostALife(primaryStage));
					preMinigameLives = lives;
				} else {
					Platform.runLater(() -> wonAMinigame(primaryStage));
				}
				Thread.sleep(3000);
				Platform.runLater(() -> pane.getChildren().clear());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	//Displayed if the user lost the last minigame
	private void lostALife(Stage primaryStage) {
		Text loseText = new Text("You lost a life.\nYou have " + lives + " remaining.");
		loseText.setFont(Font.font(30));
		pane = new StackPane(loseText);
		Scene scene = new Scene(pane, WINDOW_SIZE, WINDOW_SIZE);
		primaryStage.setScene(scene);
	}
	
	//Displayed if the user won the last minigame
	private void wonAMinigame(Stage primaryStage) {
		//Set a random text to be displayed
		int randomText = (int)(Math.random() * 20);
		Text winText = new Text();
		winText.setFont(Font.font(25));
		switch(randomText) {
		case 0: winText.setText("Big whoop, you won one."); break;
		case 1: winText.setText("Don't get cocky now."); break;
		case 2: winText.setText("That one was easy."); break;
		case 3: winText.setText("You actually think you can win?"); break;
		case 4: winText.setText("Ugh, good job I guess."); break;
		case 5: winText.setText("What, you want a medal or something?"); break;
		case 6: winText.setText("You'll choke, I just know it."); break;
		case 7: winText.setText("You're not as good as you think you are."); break;
		case 8: winText.setText("I could have done that with me eyes closed."); break;
		case 9: winText.setText("My grandma says you're trash."); break;
		case 10: winText.setText("Beep Beep Boop, major loser detected."); break;
		case 11: winText.setText("Why do you even try?"); break;
		case 12: winText.setText("Your shoes are ugly."); break;
		case 13: winText.setText("I don't have faith in you."); break;
		case 14: winText.setText("Anyone could have beaten that one."); break;
		case 15: winText.setText("I sense your confidence waining..."); break;
		case 16: winText.setText("Give up."); break;
		case 17: winText.setText("Whatever, it doesn't matter because you'll lose the next one."); break;
		case 18: winText.setText(">:("); break;
		case 19: winText.setText("Nice work!"); break;
		default: winText.setText("ERROR");
		}
		
		//Put text into StackPane and display
		pane = new StackPane(winText);
		Scene scene = new Scene(pane, WINDOW_SIZE, WINDOW_SIZE);
		primaryStage.setScene(scene);
	}

}