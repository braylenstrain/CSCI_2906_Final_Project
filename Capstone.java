
//TODO display the minigames (if it's level 1, display instructions first), have a countdown
//TODO how to deal with losses in both minigames and overall. Need a restart option.
//TODO final end game win
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

		//Create a thread to introduce the game, changing the contents of introductionText periodically, then calling runTheGame()
		new Thread(() -> {
			try {
				Thread.sleep(000);
				introductionText.setText("Do you think you can beat it?");
				Thread.sleep(000);
				introductionText.setText("We will see.");
				Thread.sleep(000);
				introductionText.setText("You have 3 lives to beat 3 levels.\nThere are 5 minigames per level.\nLose a minigame, lose a life.\nLose all your lives, game over.");
				Thread.sleep(000);
				Platform.runLater(() -> pane.getChildren().clear());
				Platform.runLater(() -> startTheGame(primaryStage));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		
		btNextMinigame.setOnAction(e -> {
			if (pane instanceof CirclesMinigame) {
				runSecondMinigame(primaryStage);
			}
		});
	}
	
	
	
	//Run the rest of the game after the intro
	private void startTheGame(Stage primaryStage) {
		//Run the first minigame
		pane = new CirclesMinigame(level);
		Scene scene = new Scene(pane, WINDOW_SIZE, WINDOW_SIZE);
		primaryStage.setScene(scene);
		//TODO Measure if last minigame was won or lost based on lives remaining vs started, have an inbetween pane with text
	}
	
	private void runSecondMinigame(Stage primaryStage) {
		if (preMinigameLives != lives) {
			lostALife(primaryStage);
			preMinigameLives = lives;
		} else {
			System.out.println("Won");
		}
	}
	
	private void lostALife(Stage primaryStage) {
		Text loseText = new Text("You lost a life.\nYou have " + lives + " remaining.");
		loseText.setFont(Font.font(30));
		pane = new StackPane(loseText);
		Scene scene = new Scene(pane, WINDOW_SIZE, WINDOW_SIZE);
		primaryStage.setScene(scene);
	}

}
