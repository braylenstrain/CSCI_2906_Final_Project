
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.text.Text;

public class SearchMinigame extends BorderPane {
	private static final int PANE_SIZE = 700;
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
			new ImageView(new Image("images/Master-Chief-Transparent-Background.png"))
			);
	private Pane pane = new Pane();
	
	public SearchMinigame(int level) {
		Collections.shuffle(imageList); //Shuffle imageList so each SearchMinigame has different images/image locations
		pane.setStyle("-fx-backgorund-color: white");
		if (level == 1) {
			level1();
		} else if (level == 2) {
			level2();
		} else {
			level3();
		}
	}

	//Put 8 pictures behind pane to search through
	//TODO Add Timer and ability to lose
	private void level1() {
		//TODO Instructions and start button
		int random = (int)(Math.random() * 8);
		ImageView desiredImage = imageList.get(random);
		//TODO how to copy desired image to it's own image and set to top of screen?
		for (int i = 0; i < 8; i++) { //TODO how to make sure images don't overlap?
			ImageView image = imageList.get(i);
			image.setPreserveRatio(true);
			image.setFitHeight(200);
			image.setX((int)(Math.random() * (PANE_SIZE - 200)));//TODO How to calcualte center after image has been added to top?
			image.setY((int)(Math.random() * (PANE_SIZE - 200)));//TODO same as above
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
			if (image == desiredImage) {
				image.setOnMouseClicked(e -> {
					image.setOnMouseEntered(null); //Image stays shown after it's been clicked
					image.setOnMouseExited(null); //Same as above ^^^
					setTop(new Text("You're a winner!"));
					//TODO approriate top text and Next button on bottom
				});
			}
		}
		
		setCenter(pane);
	}
	
	private void level2() {
		//TODO difficulty increases by number of nodes and time
		
	}
	
	private void level3() {
		// TODO Auto-generated method stub
		
	}
}
