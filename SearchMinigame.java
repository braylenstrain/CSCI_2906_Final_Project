
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.text.Text;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;

public class SearchMinigame extends BorderPane {
	private static final int PANE_SIZE = 700;
	private static final int GOAL_IMAGE_SIZE = 150;
	private static final int SEARCH_IMAGE_SIZE = 200; //TODO Get rid of when parameters are create for runGame() method
	private static final int NUMBER_OF_IMAGES = 8; //TODO Same as above^^^^
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
	//TODO Add Timer and ability to lose(show where image was)
	//TODO Move everything into a single method with number of images and size of images parameters?
	private void level1() {
		//TODO Instructions and start button (Tips:Slow and steady wins the race, be precise especially with stickmen, check around/under legolas' bow)
		
		//At the top, show a copy of the goal image that the user is trying to find
		Image glImg = imageList.get(0).getImage();
		ImageView goalImage = new ImageView(glImg);
		goalImage.setPreserveRatio(true);
		goalImage.setFitHeight(GOAL_IMAGE_SIZE);
		setTop(goalImage);
		setAlignment(goalImage, Pos.CENTER);
		
		//Put the images into pane at random places
		ArrayList<Point2D> imagePlacement = new ArrayList<>();
		for (int i = 0; i < NUMBER_OF_IMAGES; i++) { 
			ImageView image = imageList.get(i);
			
			//Set size of image
			image.setPreserveRatio(true);
			image.setFitHeight(SEARCH_IMAGE_SIZE);
			
			//Set coordinates of image
			findImageCoordinates(image);
			
			//If image overlaps another image, find a different spot (100 tries)
			boolean foundSpot = true;
			for (int j = 0; j < 100; j++) {
				foundSpot = true;
				for (Point2D point: imagePlacement) {
					//If distance between image midpoint and point midpoint is less than the height of the images / 1.5, set a new x and y for image's midpoint
					if (point.distance(image.getX(), image.getY()) < SEARCH_IMAGE_SIZE / 1.5) {
						findImageCoordinates(image);
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
				image.setOpacity(1); //TODO Change back to 0
				pane.getChildren().add(image);
				
				//Images are revealed when mouse is hovered over them
				image.setOnMouseEntered(e -> {
					image.setOpacity(1);
				});
				
				//Images disappear when mouse is no longer hovered over
				image.setOnMouseExited(e -> {
					image.setOpacity(1); //TODO change back to 0
				});
				
				//User wins if they click the correct image before timer hits 0 (Correct image is first image in imageList after it's been shuffled)
				if (i == 0) {
					image.setOnMouseClicked(e -> {
						image.setOnMouseEntered(null); //Image stays shown after it's been clicked
						image.setOnMouseExited(null); //Same as above ^^^
						setTop(new Text("You're a winner!"));
						//TODO approriate top text and Next button on bottom
					});
				}
			}
		}
		
		setCenter(pane);
	}
	
	private void level2() {
		//TODO difficulty increases by number of nodes and time
		
	}
	
	private void level3() {
		// TODO Don't have stickmen be a choice for level 3?(Too small)
		
	}
	
	//Set coordinates of image
	private void findImageCoordinates(ImageView image) {
		image.setX(Math.random() * (PANE_SIZE - SEARCH_IMAGE_SIZE));
		image.setY(Math.random() * (PANE_SIZE - SEARCH_IMAGE_SIZE - 150));
	}
	
}
