# Industry Projects 1175 Final Project *Checkers*

## Synopsis
This project is called "Can You Beat My Game?". It is a game with 3 levels and 5 minigames in each level. If you lose a minigame, you lose a life. You have 3 lives to beat all the levels to win.

## Motivation
I wanted to make this game because I thought it would be the perfect way to practice everything I've learned over the whole course, and it is a game that I would enjoy playing myself.

## How to Run
The program runs using JavaFX. You will need to put all of the java files and the images folder into the src file of a java project in order for it to run properly.

## Code Example
This code snippet shows how the program creates each circle for the first minigame. First, the x and y coordinates of the circle are set somewhere randomly within the bounds of the pane. Then a circle is created with those coordinates, a predetermined radius using a constant, and a stroke color of white. Then *setOnMouseClicked()* is called on the circle so that when it is clicked, an event is created that immediately sets it's radius to 0 to make it disappear. The circle is then added to the pane, and *toBack()* is called to make sure the newer circles don't cover the older circles that have already started shrinking. Finally, a shrinking animation is added to the circle. When the animation is finished, *setOnFinished()* calls *circle.getRadius()*. If the circle was clicked before fully shrinking *getRadius()* does not return 0 (it returns whatever the radius of the circle was when it was clicked). If the circle does close all the way, *loseALife()* is called, which ends the minigame and takes away a life.
```
	//Creates a circle, sets setOnMouseClick action for circle, puts into pane, calls shrinkCircle in a TimeLine to shrink it
	private void makeACircle() {
		//Makes sure circles are spawned entirely in the pane
		double randomX = Math.random() * PANE_WIDTH;
		randomX = randomX > PANE_WIDTH - CIRCLE_RADIUS ? randomX -= CIRCLE_RADIUS : randomX < CIRCLE_RADIUS ? randomX += CIRCLE_RADIUS : randomX;
		double randomY = Math.random() * PANE_HEIGHT;
		randomY = randomY > PANE_HEIGHT - CIRCLE_RADIUS ? randomY -= CIRCLE_RADIUS : randomY < CIRCLE_RADIUS ? randomY += CIRCLE_RADIUS : randomY;
		
		//Create circle, set stroke to white and call toBack() in case of overlap (newer circles spawn behind)
		Circle circle = new Circle(randomX, randomY, CIRCLE_RADIUS);
		circle.setStroke(Color.WHITE);
		
		//circle.setViewOrder(viewOrder); THIS DOESN't WORK IN JAVA 8. If you use this you need to add a viewOrder parameter that takes i as an argument from the parent method.
		
		//Add clicking functionality to circle
		circle.setOnMouseClicked(e -> {
			circle.setRadius(0);
		});
		
		//Add circle to pane and put behind all other current circles
		Platform.runLater(() -> {
			pane.getChildren().add(circle);
			circle.toBack();
		});
		
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
 ```
