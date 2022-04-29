
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.shape.Line;

public class SearchMinigame extends BorderPane {
	private static final int WINDOW_SIZE = 700; //Size of entire window
	private static final int TOP_SIZE = 150; //Size of top border
	private List<ImageView> imageList = Arrays.asList(
			new ImageView(new Image("images/2022_04_27_0m8_Kleki-removebg-preview.png")),
			new ImageView(new Image("images/2022_04_27_0mb_Kleki-removebg-preview.png")),
			new ImageView(new Image("images/2022_04_27_0md_Kleki-removebg-preview.png")),
			new ImageView(new Image("images/2022_04_27_0mi_Kleki-removebg-preview.png")),
			new ImageView(new Image("images/2022_04_27_0mk_Kleki-removebg-preview.png")),
			new ImageView(new Image("images/2022_04_27_0ml_Kleki-removebg-preview.png")),
			new ImageView(new Image("images/2022_04_27_0mn_Kleki-removebg-preview.png")),
			new ImageView(new Image("images/2022_04_27_0mo_Kleki-removebg-preview.png")),
			new ImageView(new Image("images/2022_04_27_0mp_Kleki-removebg-preview.png")),
			new ImageView(new Image("images/2022_04_27_0ms_Kleki-removebg-preview.png")),
			new ImageView(new Image("images/23710-8-legolas-transparent-image.png")),
			new ImageView(new Image("images/388-3880363_captain-jack-sparrow-png-removebg-preview.png")),
			new ImageView(new Image("images/8e6b064c27cdda51e81589dc11ac68bf-removebg-preview.png")),
			new ImageView(new Image("images/Avatar_Aang.png")),
			new ImageView(new Image("images/Master-Chief-Transparent-Background.png")),
			new ImageView(new Image("images/tumblr_orvfd8FdK51rxkqbso1_500.png"))
			);
	private Pane pane = new Pane();
	
	public SearchMinigame(int level) {
		Collections.shuffle(imageList); //Shuffle imageList so each SearchMinigame has different images/image locations
		pane.setStyle("-fx-background-color: white; -fx-border-style: solid;");
		if (level == 1) {
			level1();
		} else if (level == 2) {
			level2();
		} else {
			level3();
		}
	}

	//Put 8 pictures behind pane to search through
	private void level1() {
		//Instructions explaining the minigame
		Text instructions = new Text("This minigame is Find The Hidden Image. At the top is the image you are looking for.\n"
				+ "In the blank area below, move your mouse around to reveal hidden images.\n"
				+ "Click on the goal image before time runs out to win!\n\n"
				+ "Tips:\n"
				+ "1. Frantically moving the mouse around may not reveal the \"skinnier\" pictures (like stick figures).\n"
				+ "2. Be precise when clicking on the image. Don't click on whitespace, click on colors.\n"
				+ "3. Sometimes images like to hide under Legolas' bow (you'll understand when you see it),\n"
				+ "     check carefully around that area.");
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
			runGame(8, 200, 10);
		});
		
	}
	
	private void level2() {
		//TODO difficulty increases by number of nodes and time
		
	}
	
	private void level3() {
		// TODO Don't have stickmen be a choice for level 3?(Too small)
		
	}
	
	//Set coordinates of image
	private void findImageCoordinates(ImageView image, int imgSize) {
		image.setX(Math.random() * (WINDOW_SIZE - imgSize));
		image.setY(Math.random() * (WINDOW_SIZE - imgSize - TOP_SIZE));
	}
	
	private void runGame(int numOfImages, int imgSize, int timerLength) {
		//Create goalImage that player needs to find (first image in imageList after it's been shuffled)
				Image glImg = imageList.get(0).getImage();
				ImageView goalImage = new ImageView(glImg);
				goalImage.setPreserveRatio(true);
				goalImage.setFitHeight(TOP_SIZE);
				
				//Create Timeline animation that counts down to 0. Each pass changes timeText
				String timeString = Integer.toString(timerLength);
				Text timeText = new Text(timeString);
				timeText.setFont(Font.font(80));
				
				Timeline countdownAnimation = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
					int countdown = Integer.parseInt(timeText.getText());
					timeText.setText(Integer.toString(--countdown));
				}));
				countdownAnimation.setCycleCount(timerLength);
				countdownAnimation.play();
				
				//Put goalImage and Timer into HBox and set to top
				HBox imgAndTimer = new HBox();
				imgAndTimer.setSpacing(50);
				imgAndTimer.setPrefHeight(TOP_SIZE);
				imgAndTimer.getChildren().addAll(goalImage, timeText);
				imgAndTimer.setAlignment(Pos.CENTER);
				setTop(imgAndTimer);
				
				//Put the images into pane at random places
				ArrayList<Point2D> imagePlacement = new ArrayList<>();
				for (int i = 0; i < numOfImages; i++) { 
					ImageView image = imageList.get(i);
					
					//Set size of image
					image.setPreserveRatio(true);
					image.setFitHeight(imgSize);
					
					//Set coordinates of image
					findImageCoordinates(image, imgSize);
					
					//If image overlaps another image, find a different spot (100 tries)
					boolean foundSpot = true;
					for (int j = 0; j < 100; j++) {
						foundSpot = true;
						for (Point2D point: imagePlacement) {
							//If distance between image midpoint and point midpoint is less than the height of the images / 1.5, set a new x and y for image's midpoint
							if (point.distance(image.getX(), image.getY()) < imgSize / 1.5) {
								findImageCoordinates(image, imgSize);
								foundSpot = false;
								break;
							}
						}
						
						//If the midpoint did not have to be reset, end the loop
						if (foundSpot) break;
					}
					//If image doesn't overlap other images, add it to game
					if (foundSpot) {
						//Add image coordinates to imagePlacement, set Opacity to 0, and add it to pane
						imagePlacement.add(new Point2D(image.getX(), image.getY()));
						image.setOpacity(0);
						pane.getChildren().add(image);
						
						//Images are revealed when mouse is hovered over them
						image.setOnMouseEntered(e -> {
							image.setOpacity(1);
						});
						
						//Images disappear when mouse is no longer hovered over
						image.setOnMouseExited(e -> {
							image.setOpacity(0);
						});
						
						//User wins if they click the correct image before timer hits 0
						if (i == 0) {
							image.setOnMouseClicked(e -> {
								image.setOnMouseEntered(null); //Image stays shown after it's been clicked
								image.setOnMouseExited(null); //Same as above ^^^
								countdownAnimation.stop(); //Stop the timer
								
								//Show winText and next button using an HBox
								HBox winPane = new HBox();
								winPane.setSpacing(50);
								winPane.setPrefHeight(TOP_SIZE);
								Text winText = new Text("Congratulations, you found it.");
								winText.setFont(Font.font(30));
								winPane.getChildren().addAll(winText, Capstone.btNextMinigame);
								setTop(winPane);
								winPane.setAlignment(Pos.CENTER);
							});
						}
					}
				}
				
				//If timer reaches 0, player loses a life
				countdownAnimation.setOnFinished(e -> {
					//Stop all actions on the correct image and show it's location to player
					imageList.get(0).setOnMouseEntered(null);
					imageList.get(0).setOnMouseExited(null);
					imageList.get(0).setOnMouseClicked(null);
					imageList.get(0).setOpacity(1);
					
					//Create loseText with a PathTransimation haha ;)
					Text loseText = new Text("ooooooooo you didn't find it in time lol!");
					loseText.setFont(Font.font(30));
					pane.getChildren().add(loseText);
					PathTransition loseAnimation = new PathTransition(Duration.millis(3000), new Line(250, 525, 450, 25), loseText);
					loseAnimation.setCycleCount(Timeline.INDEFINITE);
					loseAnimation.setAutoReverse(true);
					loseAnimation.play();
					
					//Take a life
					Capstone.setLives(Capstone.getLives() - 1);
					
					//Add next button to top
					imgAndTimer.getChildren().add(Capstone.btNextMinigame);
				});
				
				//Set pane of images to center
				setCenter(pane);
	}
	
}
