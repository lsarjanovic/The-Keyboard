import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.shape.Ellipse;
import java.lang.Math;

public class MenuPane {
	private int width;
	private int height;
	private Scene scene;
	private Pane root;
	private Window window;
	private Text author;
	private Text playText;
	private Ellipse play;
	private Ellipse playHitbox;

	public MenuPane(Window window, Scene scene) {
		this.scene = scene;
		this.window = window;
		this.height = 100;
		this.width = 100;

		//Make the pane.
		root = new Pane();
		addElements();
		root.prefWidthProperty().bind(this.scene.widthProperty());
		root.prefHeightProperty().bind(this.scene.heightProperty());
		root.widthProperty().addListener((obs, oldVal, newVal) -> {
			this.width = newVal.intValue();
			addElements();
		});
		root.heightProperty().addListener((obs, oldVal, newVal) -> {
			this.height = newVal.intValue();
			addElements();
		});
		root.setStyle("-fx-background-color: rgba(0, 0, 0, 0)");
	}

	private int getFontSize() {
		int fontSize;
		if (this.height > this.width) fontSize = (int) (0.016f * this.height);
		else fontSize = (int) (0.016f * this.width);
		return fontSize;
	}

	private boolean je21_9Height() {
//		int screenSize = (int) (scene.getWidth() +  scene.getHeight());
//		int enota = screenSize / (21 + 9);
//		if (Math.round(this.height / enota) == 21) return true;
		return false;
	}

	private int getFontSizePlay() {
		int fontSizePlay;
		if (je21_9Height()) fontSizePlay = (int) (0.027f * this.width);
		else if (this.height > this.width) fontSizePlay = (int) (0.016f * this.width);
		else fontSizePlay = (int) (0.027f * this.width);
		return fontSizePlay;
	}

	private void setPlayAndPlayHitbox() {

		play.setCenterX(0.333 * this.width);
		play.setCenterY(0.250 * this.height);
		play.setRadiusX(0.117 * this.width);
		play.setRadiusY(0.120 * this.height);

		playHitbox.setCenterX(0.333 * this.width);
		playHitbox.setCenterY(0.250 * this.height);
		playHitbox.setRadiusX(0.125 * this.width);
		playHitbox.setRadiusY(0.127 * this.height);
	}

	private float getWidthKPlayText() {
		float widthKPlayText;

		if (this.height > this.width) widthKPlayText = 0.233f;
		else widthKPlayText = 0.237f;

		return widthKPlayText;
	}

	private float getHeightKPlayText() {
		float heightKPlayText;

		if (this.height > this.width) heightKPlayText = 0.250f;
		else heightKPlayText = 0.265f;

		return heightKPlayText;
	}

	//Prepares text in play ellipse.
	private void prepareTextPlay() {
		float widthKPlayText = getWidthKPlayText();
		float heightKPlayText = getHeightKPlayText();

		playText = new Text("The Keyboard");
		playText.setLayoutX(widthKPlayText * this.width);
		playText.setLayoutY(heightKPlayText * this.height);
		
		int fontSizePlay = getFontSizePlay();
		playText.setStyle("-fx-background-color: #F0FFFF;"
			+ "-fx-text-fill: black;"
			+ "-fx-font-family: Comfortaa;"
			+ "-fx-font-size:" + fontSizePlay + "px;"           
			+ "-fx-font-weight: bold;");
	}

	//Add the elements.
	private void addElements() {
		//Clear old elements.
		root.getChildren().clear();


		//Prepare the author text.
		int fontSize = getFontSize();
		author = new Text("by Luka Sarjanović");
		author.setLayoutX(0.083 * this.width);
		author.setLayoutY(0.7 * this.height);

		author.setStyle("-fx-font-size:" + fontSize + "px; -fx-fill: black; -fx-font-weight: bold; -fx-font-family: Comfortaa;");

		//Prepare play button static properties.
		play = new Ellipse();
		play.setFill(Color.AZURE);
		play.setStrokeWidth(3);
		play.setStroke(Color.BROWN);

		playHitbox = new Ellipse();
		playHitbox.setFill(Color.TRANSPARENT);

		setPlayAndPlayHitbox();

		//Set play button dynamic properties.
		playHitbox.setOnMouseEntered(e -> {
			play.setStrokeWidth(8); 
			play.setStroke(Color.BROWN);
		});
		playHitbox.setOnMouseExited(e -> {
			play.setStrokeWidth(3);
			play.setStroke(Color.BROWN);
//			play.setStroke(Color.TRANSPARENT)); //To make the button border transparent.
		});

		//Spremeni da bi delal keyboardPane.
		playHitbox.setOnMouseClicked (e -> this.window.changePane("menuPane"));


		//Prepare text in button.
		prepareTextPlay();

		//Add all elements
		root.getChildren().addAll(author, play, playText, playHitbox);
	}

	public Pane getRoot() {
		return root;
	}

	private void rotateTextPlay() {
		float widthKPlayText = getWidthKPlayText();
		float heightKPlayText = getHeightKPlayText();
		playText.setLayoutX(widthKPlayText * this.width);
		playText.setLayoutY(heightKPlayText * this.height);

		int fontSizePlay = getFontSizePlay();
		playText.setStyle("-fx-background-color: #F0FFFF;"
			+ "-fx-text-fill: black;"
			+ "-fx-font-family: Comfortaa;"
			+ "-fx-font-size:" + fontSizePlay + "px;"           
			+ "-fx-font-weight: bold;");
	}

	private void rotateTextAuthor() {
		int fontSize = getFontSize();
		author.setLayoutX(0.083 * this.width);
		author.setLayoutY(0.7 * this.height);
		
		author.setStyle("-fx-font-size:" + fontSize + "px; -fx-fill: black; -fx-font-weight: bold; -fx-font-family: Comfortaa;");
	}

	private void changeDimensions() {
		int temp = this.width;
		this.width= this.height;
		this.height = temp;
	}

	/*public void changeRotation() {
		changeDimensions();
		setPlayAndPlayHitbox();
		rotateTextPlay();
		rotateTextAuthor();
	}*/
}