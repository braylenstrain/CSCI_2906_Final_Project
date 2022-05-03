package application;

import java.util.ArrayList;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class DodgeMinigame extends BorderPane {
	private static final int AVATAR_SIZE = 25; //Size of player avatar image
	private static final int SQUARE_SIZE = 50; //width and height of square
	private ArrayList<Timeline> animationList = new ArrayList<>(); //A list of all animations in the minigame. Used to stop them all at once if player loses.
	
	//Create a new minigame with the pregame text and number of squares per side changed per level
	public DodgeMinigame(int level) {
		//Button to start the game
		Button btStart = new Button("Let's Go!");
		
		if (level == 1) {
			//Instructions explaining the minigame
			Text instructions = new Text("This minigame is called Dodge.\n"
					+ "You are the little smiley face in the black box. Use the arrow keys to move.\n"
					+ "Avoid all the other larger boxes as they come flying towards you to win!\n\n"
					+ "Tips:\n"
					+ "1. The black box is a part of you. Don't let it touch anything!\n"
					+ "2. If you get touched, everything freezes. In some cases,\n"
					+ "    it may look like you weren't actually hit.\n"
					+ "    This is just due to a delay stopping the animations.\n"
					+ "    You were hit, I promise.");
			instructions.setFont(Font.font(15));

			//Put instructions and btStart into top and center respectively
			setTop(instructions);
			BorderPane.setAlignment(instructions, Pos.CENTER);
			setCenter(btStart);
			BorderPane.setAlignment(btStart, Pos.CENTER);
			
			//Starts the minigame when btStart is clicked
			btStart.setOnAction(e -> {
				getChildren().clear();
				playGame(6);
			});
		} else if (level == 2) {
			Text text = new Text("Dodge: Level 2");
			text.setFont(Font.font(30));
			setTop(text);
			BorderPane.setAlignment(text, Pos.CENTER);
			setCenter(btStart);
			BorderPane.setAlignment(btStart, Pos.CENTER);
			
			//Start the minigame when btStart is clicked
			btStart.setOnAction(e -> {
				getChildren().clear();
				playGame(11);
			});
		} else {
			Text text = new Text("Dodge: Level 3");
			text.setFont(Font.font(30));
			setTop(text);
			BorderPane.setAlignment(text, Pos.CENTER);
			setCenter(btStart);
			BorderPane.setAlignment(btStart, Pos.CENTER);
			
			//Start the minigame when btStart is clicked
			btStart.setOnAction(e -> {
				getChildren().clear();
				playGame(16);
			});
		}
	}
	
	//Start the minigame, with the argument being the number of squares that spawn on each edge of the pane
	private void playGame(int squareCountPerSide) {
		ImageView playerAvatar = new ImageView("images/2022_05_01_115_Kleki.png");
		playerAvatar.setPreserveRatio(true);
		playerAvatar.setFitHeight(AVATAR_SIZE);
		playerAvatar.setX(getWidth() / 2);
		playerAvatar.setY(getHeight() / 2);
		Pane pane = new Pane(playerAvatar);
		setCenter(pane);
		
		
		//Animations that run when certain key is pressed on stopped when certain key is released
		Timeline upAnimation = new Timeline(new KeyFrame(Duration.millis(1), e -> {
			if (playerAvatar.getY() > 0)
				playerAvatar.setY(playerAvatar.getY() - 0.5);
		}));
		upAnimation.setCycleCount(Timeline.INDEFINITE);
		animationList.add(upAnimation);
		
		Timeline downAnimation = new Timeline(new KeyFrame(Duration.millis(1), e-> {
			if (playerAvatar.getY() < getHeight() - AVATAR_SIZE)
				playerAvatar.setY(playerAvatar.getY() + 0.5);
		}));
		downAnimation.setCycleCount(Timeline.INDEFINITE);
		animationList.add(downAnimation);
		
		Timeline leftAnimation = new Timeline(new KeyFrame(Duration.millis(1), e-> {
			if (playerAvatar.getX() > 0)
				playerAvatar.setX(playerAvatar.getX() - 0.5);
		}));
		leftAnimation.setCycleCount(Timeline.INDEFINITE);
		animationList.add(leftAnimation);
		
		Timeline rightAnimation = new Timeline(new KeyFrame(Duration.millis(1), e-> {
			if (playerAvatar.getX() < getWidth() - AVATAR_SIZE)
				playerAvatar.setX(playerAvatar.getX() + 0.5);
		}));
		rightAnimation.setCycleCount(Timeline.INDEFINITE);
		animationList.add(rightAnimation);
		
		//Play certain animation when certain key is pressed
		Capstone.scene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.UP) {
				upAnimation.play();	
			} else if (e.getCode() == KeyCode.DOWN) {
				downAnimation.play();
			} else if (e.getCode() == KeyCode.LEFT) {
				leftAnimation.play();
			} else if (e.getCode() == KeyCode.RIGHT) {
				rightAnimation.play();
			}
		});
		
		//Stop certain animation when certain key is released
		Capstone.scene.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.UP) {
				upAnimation.stop();
			} else if (e.getCode() == KeyCode.DOWN) {
				downAnimation.stop();
			} else if (e.getCode() == KeyCode.LEFT) {
				leftAnimation.stop();
			} else if (e.getCode() == KeyCode.RIGHT) {
				rightAnimation.stop();
			}
		});
		
		//Add Squares to top area
		for (int i = 0; i < squareCountPerSide; i++) {
			Rectangle rectangle = new Rectangle((int)(Math.random() * (getWidth() - SQUARE_SIZE)), 0, SQUARE_SIZE, SQUARE_SIZE);
			squareColors(rectangle);
			pane.getChildren().add(rectangle);
			
			//Move square downward and check for collison
			Timeline squareAnimation = new Timeline(new KeyFrame(Duration.millis(2), e -> {
				//Move square downward
				rectangle.setY(rectangle.getY() + 0.5);
				//Check for collision with player avatar
				if (rectangle.getBoundsInParent().intersects(playerAvatar.getBoundsInParent())) {
					playerLoses(pane);
				}
			}));
			squareAnimation.setDelay(Duration.millis((long)(Math.random() * 5000) + 1000)); //Squares start moving between 1-6 seconds after start
			squareAnimation.setCycleCount((int)getHeight() * 2  + SQUARE_SIZE); //Once squares are out of frame, no more cycles needed
			animationList.add(squareAnimation);
			squareAnimation.play();
		}
		
		//Add Squares to left area
		for (int i = 0; i < squareCountPerSide; i++) {
			Rectangle rectangle = new Rectangle(0, (int)(Math.random() * (getHeight() - SQUARE_SIZE)), SQUARE_SIZE, SQUARE_SIZE);
			squareColors(rectangle);
			pane.getChildren().add(rectangle);
			
			//Move square right and check for collision
			Timeline squareAnimation = new Timeline(new KeyFrame(Duration.millis(2), e -> {
				//Move square right
				rectangle.setX(rectangle.getX() + 0.5);
				//Check for collision with player avatar
				if (rectangle.getBoundsInParent().intersects(playerAvatar.getBoundsInParent())) {
					playerLoses(pane);
				}
			}));
			squareAnimation.setDelay(Duration.millis((long)(Math.random() * 5000) + 1000)); //Squares start moving between 1-6 seconds after start
			squareAnimation.setCycleCount((int)getHeight() * 2  + SQUARE_SIZE); //Once squares are out of frame, no more cycles needed
			animationList.add(squareAnimation);
			squareAnimation.play();
		}
		
		//Add squares to right area
		for (int i = 0; i < squareCountPerSide; i++) {
			Rectangle rectangle = new Rectangle(getWidth() - SQUARE_SIZE, (int)(Math.random() * (getHeight() - SQUARE_SIZE)), SQUARE_SIZE, SQUARE_SIZE);
			squareColors(rectangle);
			pane.getChildren().add(rectangle);
			
			//Move square left and check for collision
			Timeline squareAnimation = new Timeline(new KeyFrame(Duration.millis(2), e -> {
				//Move square left
				rectangle.setX(rectangle.getX() - 0.5);
				//Check for collision with player avatar
				if (rectangle.getBoundsInParent().intersects(playerAvatar.getBoundsInParent())) {
					playerLoses(pane);
				}
			}));
			squareAnimation.setDelay(Duration.millis((long)(Math.random() * 5000) + 1000)); //Squares start moving between 1-6 seconds after start
			squareAnimation.setCycleCount((int)getHeight() * 2  + SQUARE_SIZE); //Once squares are out of frame, no more cycles needed
			animationList.add(squareAnimation);
			squareAnimation.play();
		}
		
		//Create a timeline that marks the game as won if it doesn't get stopped by playerLoses()
		Timeline winCheck = new Timeline(new KeyFrame(Duration.millis(10000)));
		winCheck.setOnFinished(e -> playerWins());
		animationList.add(winCheck);
		winCheck.play();
		
		//Add squares to bottom area
		for (int i = 0; i < squareCountPerSide; i++) {
			Rectangle rectangle = new Rectangle((int)(Math.random() * (getWidth() - SQUARE_SIZE)), getHeight() - SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
			squareColors(rectangle);
			pane.getChildren().add(rectangle);
			
			//Move square upward and check for collision
			Timeline squareAnimation = new Timeline(new KeyFrame(Duration.millis(2), e -> {
				//Move square upward
				rectangle.setY(rectangle.getY() - 0.5);
				//Check for collision with player avatar
				if (rectangle.getBoundsInParent().intersects(playerAvatar.getBoundsInParent())) {
					playerLoses(pane);
				}
			}));
			squareAnimation.setDelay(Duration.millis((long)(Math.random() * 5000) + 1000)); //Squares start moving between 1-6 seconds after start
			squareAnimation.setCycleCount((int)getHeight() * 2  + SQUARE_SIZE); //Once squares are out of frame, no more cycles needed
			animationList.add(squareAnimation);
			squareAnimation.play();
		}
	}
	
	//The colors of choice for the enemy squares
	private void squareColors(Rectangle rectangle) {
		rectangle.setStroke(Color.BLACK);
		rectangle.setFill(Color.DARKSLATEBLUE);
	}
	
	//Stop all current animations and events, add a flashing loseText, and put up next button after short delay
	private void playerLoses(Pane pane) {
		//Stop all key events
		Capstone.scene.setOnKeyPressed(null);
		Capstone.scene.setOnKeyReleased(null);
		
		//Stop all animations
		for (Timeline animation: animationList) {
			animation.stop();
		}

		//Create loseText with fade animation and add to pane
		Text loseText = new Text(getWidth() / 4, getHeight() / 2, "Oh no, you were splattered!");
		loseText.setFont(Font.font(30));
		
		FadeTransition loseTextAnimation = new FadeTransition(Duration.millis(1000), loseText);
		loseTextAnimation.setAutoReverse(true);
		loseTextAnimation.setCycleCount(Timeline.INDEFINITE);;
		loseTextAnimation.setFromValue(1);
		loseTextAnimation.setToValue(0);
		loseTextAnimation.play();
		
		pane.getChildren().add(loseText);
		
		//Take a life
		Capstone.setLives(Capstone.getLives() - 1);
		
		//After a small delay, show the next button
		new Thread(() -> {
			try {
				Thread.sleep(2500);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			
			Platform.runLater(() -> {
				setBottom(Capstone.btNextMinigame);
				setAlignment(Capstone.btNextMinigame, Pos.CENTER_RIGHT);
				setMargin(Capstone.btNextMinigame, new Insets(20));
			});
		}).start();
	}
	
	//Player never touched a square, AKA the winCheck animation was never stopped
	private void playerWins() {
		HBox winBox = new HBox(100);
		winBox.setAlignment(Pos.CENTER);
		Text winText = new Text("You won!");
		winText.setFont(Font.font(30));
		winBox.getChildren().addAll(winText, Capstone.btNextMinigame);
		setBottom(winBox);
		this.requestFocus();
	}
	
}
