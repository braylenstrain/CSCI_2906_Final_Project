package application;

import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

public class CirclesMinigame extends BorderPane {
	private Pane pane = new Pane();
	private final int PANE_WIDTH = 700; //Width of playable game area after introduction
	private final int PANE_HEIGHT = 700; //Height of playable game area after introduction
	private final double CIRCLE_RADIUS = 50.0; //Predetermined radius of the circles when spawned
	
	public CirclesMinigame(int level) {
		if (level == 1) {
			level1();
		} else if (level == 2) {
			level2();
		} else {
			level3();
		}
	}
	
	//Starts level 1 version of Click The Circles minigame
	private void level1() {
		//Instructions explaining the minigame
		Text instructions = new Text("The first minigame is Click The Circles. When the game starts, you will begin to see circles appear.\nYou must click the circles with your mouse before they shrink away entirely.\nWhen you are ready, click the button below.");
		instructions.setFont(Font.font(15));

		//Button to start the game
		Button btStart = new Button("Let's Go!");
		
		//Set instructions and btStart into top and center
		setTop(instructions);
		BorderPane.setAlignment(instructions, Pos.CENTER);
		setCenter(btStart);
		BorderPane.setAlignment(btStart, Pos.CENTER);
		
		//Starts the minigame when btStart is clicked
		btStart.setOnAction(e -> {
			getChildren().clear();
			setCenter(pane);
			playLevel1();
		});
	}
	
	//Continues the level 1 version of this game when btStart is clicked
	private void playLevel1() {
		new Thread(() -> {
			try {
				int lives = Capstone.getLives();
				for (int i = 0; i < 0; i++) { //TODO fix back to 10
					//Check to see if they've lost the minigame
					if (Capstone.getLives() != lives) break;
					
					//Else, continue the game
					makeACircle();
					Thread.sleep(1000);
				}
				
				//Give last spawned circle time to shrink entirely, if needed (Time to shrink a circle - sleep time between each circle + a little extra)
				Thread.sleep(0);//TODO Put back to 1550
				
				//Sometimes another circle will have spawned before lostALife() is called. In this case, set lives to correct amount.
				if (Capstone.getLives() < lives - 1) Capstone.setLives(lives - 1);
				//Display this text if user beats minigame
				else if (Capstone.getLives() == lives) Platform.runLater(() -> {
					Text winText = new Text("Yipee for you, you got them all.");
					winText.setFont(Font.font(20));
					setCenter(winText);
				});
				
				//Add Button to go to next minigame
				Platform.runLater(() -> {
					setBottom(Capstone.btNextMinigame);
					setAlignment(Capstone.btNextMinigame, Pos.CENTER_RIGHT);
					setMargin(Capstone.btNextMinigame, new Insets(20));
					});
			} catch (InterruptedException e) {
					e.printStackTrace();
			}
		}).start();
	}
	
	private void level2() {
		
	}
	
	private void level3() {
		
	}
	
	//Creates a circle, sets setOnMouseClick action for circle, puts into pane, calls shrinkCircle in a TimeLine to shrink it
	private void makeACircle() {
		//Makes sure circles are spawned entirely in the pane
		double randomX = Math.random() * PANE_WIDTH;
		randomX = randomX > PANE_WIDTH - CIRCLE_RADIUS ? randomX -= CIRCLE_RADIUS : randomX < CIRCLE_RADIUS ? randomX += CIRCLE_RADIUS : randomX;
		double randomY = Math.random() * PANE_HEIGHT;
		randomY = randomY > PANE_HEIGHT - CIRCLE_RADIUS ? randomY -= CIRCLE_RADIUS : randomY < CIRCLE_RADIUS ? randomY += CIRCLE_RADIUS : randomY;
		
		//Create circle, add to pane, add clicking functionality
		Circle circle = new Circle(randomX, randomY, CIRCLE_RADIUS);
		circle.setOnMouseClicked(e -> {
			circle.setRadius(0);
		});
		Platform.runLater(() -> pane.getChildren().add(circle));
		
		//Shrink animation for the circles
		Timeline circleShrink = new Timeline(new KeyFrame(Duration.millis(50), e -> shrinkCircle(circle)));
		circleShrink.setCycleCount(50);
		circleShrink.play();
		circleShrink.setOnFinished(e -> {
			if (circle.getRadius() == 0.0) {
				loseALife();
			}
		});
	}
	
	//This method is called inside the animation to shrink a circle
	private void shrinkCircle(Circle circle) {
		circle.setRadius(circle.getRadius() - 1);
	}
	
	//This is called if a circle shrinks all the way without being clicked
	private void loseALife() {
		new Thread(() -> {
			Capstone.setLives(Capstone.getLives() - 1);
			Platform.runLater(() -> {
				getChildren().clear();
				setCenter(new Text("YOU DIED SUCKA!"));
			});
		}).start();
	}

}
