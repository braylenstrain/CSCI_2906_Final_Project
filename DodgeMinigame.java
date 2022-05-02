package application;

import javafx.animation.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class DodgeMinigame extends BorderPane {
	private static final int avatarSize = 50;

	public DodgeMinigame(int level) {
		if (level == 1) {
			//Instructions explaining the minigame
			Text instructions = new Text("Instructions go here");
			instructions.setFont(Font.font(15));
			
			//Button to start the game
			Button btStart = new Button("Let's Go!");
			
			//Put instructions and btStart into this
			setTop(instructions);
			BorderPane.setAlignment(instructions, Pos.CENTER);
			setCenter(btStart);
			BorderPane.setAlignment(btStart, Pos.CENTER);
			
			//Starts the minigame when btStart is clicked
			btStart.setOnAction(e -> {
				getChildren().clear();
				level1();
			});
		} else if (level == 2) {
			level2();
		} else {
			level3();
		}
	}
	
	private void level1() {
		//Create player avatar, put into pane, put pane in center
		ImageView playerAvatar = new ImageView("2022_05_01_0ue_Kleki.png");
		playerAvatar.setPreserveRatio(true);
		playerAvatar.setFitHeight(avatarSize);
		playerAvatar.setX(getWidth() / 2);
		playerAvatar.setY(getHeight() / 2);
		Pane pane = new Pane(playerAvatar);
		setCenter(pane);
		
		
		//Animations that run when key is pressed on stopped when key is released
		Timeline upAnimation = new Timeline(new KeyFrame(Duration.millis(1), e -> {
			if (playerAvatar.getY() > 0)
				playerAvatar.setY(playerAvatar.getY() - 0.5);
		}));
		upAnimation.setCycleCount(Timeline.INDEFINITE);
		
		Timeline downAnimation = new Timeline(new KeyFrame(Duration.millis(1), e-> {
			if (playerAvatar.getY() < getHeight() - avatarSize)
				playerAvatar.setY(playerAvatar.getY() + 0.5);
		}));
		downAnimation.setCycleCount(Timeline.INDEFINITE);
		
		Timeline leftAnimation = new Timeline(new KeyFrame(Duration.millis(1), e-> {
			if (playerAvatar.getX() > 0)
				playerAvatar.setX(playerAvatar.getX() - 0.5);
		}));
		leftAnimation.setCycleCount(Timeline.INDEFINITE);
		
		Timeline rightAnimation = new Timeline(new KeyFrame(Duration.millis(1), e-> {
			if (playerAvatar.getX() < getWidth() - avatarSize)
				playerAvatar.setX(playerAvatar.getX() + 0.5);
		}));
		rightAnimation.setCycleCount(Timeline.INDEFINITE);
		
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
	}
	
	private void level2() {
		
	}
	
	private void level3() {
		
	}
}
