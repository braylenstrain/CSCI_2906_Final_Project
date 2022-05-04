package application;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
	private static final int PANE_WIDTH = 700; //Width of playable game area after introduction
	private static final int PANE_HEIGHT = 700; //Height of playable game area after introduction
	private static final int CIRCLE_RADIUS = 50; //Predetermined radius of the circles when spawned
	
	public CirclesMinigame(int level) {
		Button btStart = new Button("Let's Go!"); //Button to start the game

		if (level == 1) {
			//Instructions explaining the minigame
			Text instructions = new Text("The first minigame is Click The Circles. When the game starts, you will begin to see circles appear.\n"
					+ "You must click the circles with your mouse before they shrink away entirely.\n"
					+ "When you are ready, click the button below.");
			instructions.setFont(Font.font(15));

			//Set instructions and btStart into top and center
			setTop(instructions);
			BorderPane.setAlignment(instructions, Pos.CENTER);
			setCenter(btStart);
			BorderPane.setAlignment(btStart, Pos.CENTER);
			
			//Starts the minigame when btStart is clicked
			btStart.setOnAction(e -> {
				getChildren().clear();
				playGame(10, 1000);
			});
		} else if (level == 2) {
			//Title of minigame
			Text instructions = new Text("Click The Circles: Level 2");
			instructions.setFont(Font.font(30));

			//Set instructions and btStart into top and center
			setTop(instructions);
			BorderPane.setAlignment(instructions, Pos.CENTER);
			setCenter(btStart);
			BorderPane.setAlignment(btStart, Pos.CENTER);
			
			//Starts the minigame when btStart is clicked
			btStart.setOnAction(e -> {
				getChildren().clear();
				playGame(15, 500);
			});
		} else {
			//Title of minigame
			Text instructions = new Text("Click The Circles: Level 3");
			instructions.setFont(Font.font(30));

			//Set instructions and btStart into top and center
			setTop(instructions);
			BorderPane.setAlignment(instructions, Pos.CENTER);
			setCenter(btStart);
			BorderPane.setAlignment(btStart, Pos.CENTER);
			
			//Starts the minigame when btStart is clicked
			btStart.setOnAction(e -> {
				getChildren().clear();
				playGame(20, 400);
			});
		}
	}

	//Continues the level 1 version of this game when btStart is clicked
	private void playGame(int numOfCircles, int millisBetweenCircles) {
		setCenter(pane);
		new Thread(() -> {
			try {
				int lives = Capstone.getLives();
				for (int i = 0; i < numOfCircles; i++) {
					//Check to see if they've lost the minigame
					if (Capstone.getLives() != lives) break;
					
					//Else, continue the game
					makeACircle(i);
					Thread.sleep(millisBetweenCircles);
				}
				
				//Give last spawned circle time to shrink entirely, if needed (Time to shrink a circle - sleep time between each circle + a little extra)
				Thread.sleep(50 * CIRCLE_RADIUS - millisBetweenCircles + 50);
				
				//Sometimes another circle will have spawned before lostALife() is called. In this case, set lives to correct amount.
				if (Capstone.getLives() < lives - 1) Capstone.setLives(lives - 1);
				//Display this text if user beats minigame
				else if (Capstone.getLives() == lives) Platform.runLater(() -> {
					Text winText = new Text("Nice work player, you got them all!");
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
	
	//Creates a circle, sets setOnMouseClick action for circle, puts into pane, calls shrinkCircle in a TimeLine to shrink it
	private void makeACircle(int viewOrder) {
		//Makes sure circles are spawned entirely in the pane
		double randomX = Math.random() * PANE_WIDTH;
		randomX = randomX > PANE_WIDTH - CIRCLE_RADIUS ? randomX -= CIRCLE_RADIUS : randomX < CIRCLE_RADIUS ? randomX += CIRCLE_RADIUS : randomX;
		double randomY = Math.random() * PANE_HEIGHT;
		randomY = randomY > PANE_HEIGHT - CIRCLE_RADIUS ? randomY -= CIRCLE_RADIUS : randomY < CIRCLE_RADIUS ? randomY += CIRCLE_RADIUS : randomY;
		
		//Create circle, set stroke to white and set viewOrder in case of overlap (newer circles spawn behind)
		Circle circle = new Circle(randomX, randomY, CIRCLE_RADIUS);
		circle.setStroke(Color.WHITE);
		circle.setViewOrder(viewOrder);
		
		//Add clicking functionality to circle
		circle.setOnMouseClicked(e -> {
			circle.setRadius(0);
		});
		
		//Add circle to pane
		Platform.runLater(() -> pane.getChildren().add(circle));
		
		//Shrink animation for the circles
		Timeline circleShrink = new Timeline(new KeyFrame(Duration.millis(50), e -> shrinkCircle(circle)));
		circleShrink.setCycleCount(CIRCLE_RADIUS);
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
