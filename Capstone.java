//TODO display the minigames (if it's level 1, display instructions first), have a countdown
//TODO how to deal with losses in both minigames and overall. Need a restart option.
//TODO final end game win
/*
 * Author: Braylen Strain
 * Date: TODO
 * 
 * This program is a game of minigames. The user has to beat each minigame to move on to the next level. After beating 3 levels, they win.
 */
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class Capstone extends Application{
	int level = 1;
	int lives = 3;
	int introCount = 0;


	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		StackPane pane = new StackPane(); // The main pane that will hold all the minigame panes
		Text introductionText = new Text("Welcome to my game."); //Intro text explaining the game
		
		//Increase size of intro text and put into pane
		introductionText.setFont(Font.font(40));
		pane.getChildren().add(introductionText);
		
		//Set the scene and stage
		Scene scene = new Scene(pane, 800, 800);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Can You Beat My Game?");
		primaryStage.show();
		primaryStage.setResizable(false);
		
		//Handler for changing introductionText
		EventHandler<ActionEvent> introEventHandler = e -> {
			switch(introCount) {
			case 0: introductionText.setText("Do you think you can beat it?");
					introCount = 1;
					break;
			case 1: introductionText.setText("We will see.");
					introCount = 2;
					break;
			case 2: introductionText.setText("You have 3 lives to beat 3 levels.\nThere are 5 minigames per level.\nLose a minigame, lose a life.\nLose all your lives, game over.\n\nPress any key on the keyboard to continue...");
					introCount = 3;
			}
		};
		
		//Animation that changes introductionText contents
		Timeline introAnimation = new Timeline(new KeyFrame(Duration.millis(3000), introEventHandler));
		introAnimation.setCycleCount(3);
		introAnimation.play();
		
		//Create a game intro thread, then change the contents of introductionText periodically
//		new Thread( () -> {
//			try {
//				Thread.sleep(3000);
//				introductionText.setText("Do you think you can beat it?");
//				Thread.sleep(3000);
//				introductionText.setText("We will see.");
//				Thread.sleep(3000);
//				introductionText.setText("You have 3 lives to beat 3 levels.\nThere are 5 minigames per level.\nLose a minigame, lose a life.\nLose all your lives, game over.\n\nPress any key to continue...");
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}).start();

		//After the introduction, user presses a key to start the game.
		scene.setOnKeyPressed(e -> {
			pane.getChildren().clear();
		});
		
		//Game plays until user beats level 3, or loses all lives
	}

}
