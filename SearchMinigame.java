
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.control.ScrollPane;

public class SearchMinigame extends BorderPane {
	//TODO node mouse enter/leave, arraylist of nodes shuffled, randomly choose node to find, difficulty increases by number of nodes and time
	private List<ImageView> imageList = Arrays.asList(
			new ImageView(new Image("images/2022_04_27_0m8_Kleki.png")),
			new ImageView(new Image("images/2022_04_27_0mb_Kleki.png")),
			new ImageView(new Image("images/2022_04_27_0md_Kleki.png")),
			new ImageView(new Image("images/2022_04_27_0mi_Kleki.png")),
			new ImageView(new Image("images/2022_04_27_0mk_Kleki.png")),
			new ImageView(new Image("images/2022_04_27_0ml_Kleki.png")),
			new ImageView(new Image("images/2022_04_27_0mn_Kleki.png")),
			new ImageView(new Image("images/2022_04_27_0mo_Kleki.png")),
			new ImageView(new Image("images/2022_04_27_0mp_Kleki.png")),
			new ImageView(new Image("images/2022_04_27_0ms_Kleki.png")),
			new ImageView(new Image("images/23710-8-legolas-transparent-image.png")),
			new ImageView(new Image("images/388-3880363_captain-jack-sparrow-png.png")),
			new ImageView(new Image("images/8e6b064c27cdda51e81589dc11ac68bf.jpeg")),
			new ImageView(new Image("images/Avatar_Aang.png")),
			new ImageView(new Image("images/Master-Chief-Transparent-Background.png"))
			);
	
	public SearchMinigame(int level) {
		Collections.shuffle(imageList);
		if (level == 1) {
			level1();
		} else if (level == 2) {
			level2();
		} else {
			level3();
		}
	}

	private void level1() {
		for (ImageView image: imageList) {
			image.setPreserveRatio(true);
			image.setFitHeight(100);
		}
		FlowPane test = new FlowPane();
		test.getChildren().addAll(imageList);
		setCenter(test);
		
	}
	
	private void level2() {
		// TODO Auto-generated method stub
		
	}
	
	private void level3() {
		// TODO Auto-generated method stub
		
	}
}
