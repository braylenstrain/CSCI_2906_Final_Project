package application;

/*
 * Author: Braylen Strain
 * Date: TODO Make sure you can beat the game before being done
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
	private Pane pane; //Each minigame pane will get set to this variable
	private static final int WINDOW_SIZE = 700; //Used to designate the size of the scenes
	private static int level = 1; //Current level the game is on
	private static int lives = 3; //How many lives the user has remaining
	private static int preMinigameLives = 3; //How many lives the user had before the current minigame started
	public static Button btNextMinigame = new Button("Next"); //Used to move on after a minigame is over
	public static Button btReset = new Button("Play again?"); //Used to reset the game back to level 1 with 3 lives
	public static Scene scene; // Set as a public static data field so that I can register key events in the minigames

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
		//Make buttons bigger so they are more noticeable
		btNextMinigame.setPrefSize(200, 100);
		btNextMinigame.setFont(Font.font(40));
		
		btReset.setPrefSize(200, 100);
		btReset.setFont(Font.font(20));
		
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
		
		//After a minigame ends, btNextMinigame will be displayed. The user clicks it to move on.
		btNextMinigame.setOnAction(e -> {
			if (pane instanceof CirclesMinigame) {
				runSecondMinigame(primaryStage);
			} else if (pane instanceof FastTapMinigame) {
				runThirdMinigame(primaryStage);
			} else if (pane instanceof SearchMinigame) {
				runFourthMinigame(primaryStage);
			} else if (pane instanceof DodgeMinigame) {
				runFifthMinigame(primaryStage);
			} else if (pane instanceof MathMinigame) {
				endOfLevel(primaryStage);
			}
		});
		
		//Offer a restart button when the game is over. If clicked, reset lives, preminigame lives, and level. Then runFirstMinigame().
		btReset.setOnAction(e -> {
			lives = 3;
			preMinigameLives = 3;
			level = 1;
			runFirstMinigame(primaryStage);
		});

		//Create a thread to introduce the game, changing the contents of introductionText periodically, then calling runFirstMinigame()
		new Thread(() -> {
			try {
				Thread.sleep(3000);
				introductionText.setText("Do you think you can beat it?");
				Thread.sleep(3000);
				introductionText.setText("We will see.");
				Thread.sleep(3000);
				introductionText.setText("You have 3 lives to beat 3 levels.\nThere are 5 minigames per level.\nLose a minigame, lose a life.\nLose all your lives, game over.");
				Thread.sleep(6000);
				Platform.runLater(() -> pane.getChildren().clear());
				Platform.runLater(() -> runFirstMinigame(primaryStage));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	//Run the first minigame after the intro is done
	private void runFirstMinigame(Stage primaryStage) {
		pane = new CirclesMinigame(level);
		scene = new Scene(pane, WINDOW_SIZE, WINDOW_SIZE);
		primaryStage.setScene(scene);
	}
	
	//Run the second minigame
	private void runSecondMinigame(Stage primaryStage) {
		new Thread(() -> {
			try {
				//Display win/loss page after last minigame for 2 seconds
				if (preMinigameLives != lives) {
					Platform.runLater(() -> lostALife(primaryStage));
					preMinigameLives = lives;
				} else {
					Platform.runLater(() -> wonAMinigame(primaryStage));
				}
				Thread.sleep(2500);
				
				//If user lost all lives, call endGame()
				if (lives == 0) {
					Platform.runLater(() -> endGame());
				} else {
					//Put next minigame into stage
					Platform.runLater(() -> {
						pane = new FastTapMinigame(level);
						scene = new Scene(pane, WINDOW_SIZE, WINDOW_SIZE);
						primaryStage.setScene(scene);
					});
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	//Run the third minigame
	private void runThirdMinigame(Stage primaryStage) {
		new Thread(() -> {
			try {
				//Display win/loss page after last minigame for 2 seconds
				if (preMinigameLives != lives) {
					Platform.runLater(() -> lostALife(primaryStage));
					preMinigameLives = lives;
				} else {
					Platform.runLater(() -> wonAMinigame(primaryStage));
				}
				Thread.sleep(2500);
				
				//If user lost all lives, call endGame()
				if (lives == 0) {
					Platform.runLater(() -> endGame());
				} else {
					//Put next minigame into stage
					Platform.runLater(() -> {
						pane = new SearchMinigame(level);
						scene = new Scene(pane, WINDOW_SIZE, WINDOW_SIZE);
						primaryStage.setScene(scene);
					});
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	private void runFourthMinigame(Stage primaryStage) {
		new Thread(() -> {
			try {
				//Display win/loss page after last minigame for 2 seconds
				if (preMinigameLives != lives) {
					Platform.runLater(() -> lostALife(primaryStage));
					preMinigameLives = lives;
				} else {
					Platform.runLater(() -> wonAMinigame(primaryStage));
				}
				Thread.sleep(2500);
				
				//If user lost all lives, call endGame()
				if (lives == 0) {
					Platform.runLater(() -> endGame());
				} else {
					//Put next minigame into stage
					Platform.runLater(() -> {
						pane = new DodgeMinigame(level);
						scene = new Scene(pane, WINDOW_SIZE, WINDOW_SIZE);
						primaryStage.setScene(scene);
					});
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	private void runFifthMinigame(Stage primaryStage) {
		new Thread(() -> {
			try {
				//Display win/loss page after last minigame for 2 seconds
				if (preMinigameLives != lives) {
					Platform.runLater(() -> lostALife(primaryStage));
					preMinigameLives = lives;
				} else {
					Platform.runLater(() -> wonAMinigame(primaryStage));
				}
				Thread.sleep(2500);
				
				//If user lost all lives, call endGame()
				if (lives == 0) {
					Platform.runLater(() -> endGame());
				} else {
					//Put next minigame into stage
					Platform.runLater(() -> {
						pane = new MathMinigame(level);
						scene = new Scene(pane, WINDOW_SIZE, WINDOW_SIZE);
						primaryStage.setScene(scene);
					});
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	private void endOfLevel(Stage primaryStage) {
		new Thread(() -> {
			try {
				//Display win/loss page after last minigame for 2 seconds
				if (preMinigameLives != lives) {
					Platform.runLater(() -> lostALife(primaryStage));
					preMinigameLives = lives;
				} else {
					Platform.runLater(() -> wonAMinigame(primaryStage));
				}
				Thread.sleep(2500);
				
				//If user lost all lives, call endGame()
				if (lives == 0) {
					Platform.runLater(() -> endGame());
				} else {
					//Show end of level screen
					Platform.runLater(() -> {
						Text levelUpText = new Text("You have beaten level " + level + "! Keep up the great work!");
						levelUpText.setFont(Font.font(30));
						pane = new StackPane(levelUpText);
						scene = new Scene(pane, WINDOW_SIZE, WINDOW_SIZE);
						primaryStage.setScene(scene);
						
						//Increase level
						level++;
					});
					
					//Pause so player can read levelUpText
					Thread.sleep(3000);
					Platform.runLater(() -> {
						//Start new level or end game
						if (level < 4) runFirstMinigame(primaryStage);
						else endGame();
					});
				}
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
		case 0: winText.setText("Alright! You won one!"); break;
		case 1: winText.setText("Stay focused."); break;
		case 2: winText.setText("You made that look easy!"); break;
		case 3: winText.setText("You can win."); break;
		case 4: winText.setText("Good Job!"); break;
		case 5: winText.setText("You deserve a cookie for that one!"); break;
		case 6: winText.setText("Trust your feelings."); break;
		case 7: winText.setText("You're pretty good!"); break;
		case 8: winText.setText("I am so impressed!"); break;
		case 9: winText.setText("My grandma says you're really good."); break;
		case 10: winText.setText("Beep Beep Boop, major cool person detected."); break;
		case 11: winText.setText("Don't stop believing!"); break;
		case 12: winText.setText("Your shoes are really cool!"); break;
		case 13: winText.setText("I have faith in you!"); break;
		case 14: winText.setText("You're better at this then most."); break;
		case 15: winText.setText("I sense your confidence increasing..."); break;
		case 16: winText.setText("Don't give up!"); break;
		case 17: winText.setText("Nice! I bet you'll win the next one too!"); break;
		case 18: winText.setText("=D"); break;
		case 19: winText.setText("Nice work!"); break;
		default: winText.setText("ERROR");
		}
		
		//Put text into StackPane and display
		pane = new StackPane(winText);
		Scene scene = new Scene(pane, WINDOW_SIZE, WINDOW_SIZE);
		primaryStage.setScene(scene);
	}
	
	private void endGame() {

		//If level < 4, Display lost scene. Else display win scene.
		if (level < 4) {
			pane.getChildren().clear();
			Text lostGameText = new Text("You have lost all your lives.\nThe gods weep for your failure :(\n You may try again if you like.");
			lostGameText.setFont(Font.font(30));
			pane.getChildren().add(lostGameText);
		} else {
			//Display win scene
			pane.getChildren().clear();
			Text beatGameText = new Text("Actually, scratch that.\nYou've beaten the whole game!\nYou're unstoppable.\n...I love you.");
			beatGameText.setFont(Font.font(30));
			pane.getChildren().add(beatGameText);
		}
		
		//Pause so player can read before switching to btReset
		new Thread(() -> {
			try {
				Thread.sleep(5000);

			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			//Display btReset
			Platform.runLater(() -> {
				pane.getChildren().clear();
				pane.getChildren().add(btReset);
			});
		}).start();
	}

}