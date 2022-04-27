
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.scene.layout.*;
import javafx.scene.image.*;

//TODO node mouse enter/leave, randomly choose node to find, difficulty increases by number of nodes and time
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
	private void level1() {
		for (int i = 0; i < 8; i++) {
			ImageView image = imageList.get(i);
			image.setPreserveRatio(true);
			image.setFitHeight(100);
			image.setX((int)(Math.random() * (PANE_SIZE - 100)));
			image.setY((int)(Math.random() * (PANE_SIZE - 200)));
			pane.getChildren().add(image);
		}
		setCenter(pane);
	}
	
	private void level2() {
		// TODO Auto-generated method stub
		
	}
	
	private void level3() {
		// TODO Auto-generated method stub
		
	}
}
