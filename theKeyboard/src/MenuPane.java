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

	private WindowCharacteristics windowCharact;

	private TextElem textElem;

	private EllipseElem ellipseElem;

	public MenuPane(Window window, Scene scene) {
		this.height = 100; //Default value, changes at full_sceen resize.
		this.width = 100;  //Default value, changes at full_sceen resize.

		setWindowCharact(window, scene);

		addElements();
	}

	private void setRootCharacteristics() {
		this.windowCharact.getRoot().prefWidthProperty().bind(this.windowCharact.getScene().widthProperty());
		this.windowCharact.getRoot().prefHeightProperty().bind(this.windowCharact.getScene().heightProperty());
		this.windowCharact.getRoot().widthProperty().addListener((width, prevWidth, nextWidth) -> {
			this.width = nextWidth.intValue();
			addElements();
		});
		this.windowCharact.getRoot().heightProperty().addListener((height, prevHeight, nextHeight) -> {
			this.height = nextHeight.intValue();
			addElements();
		});
		this.windowCharact.getRoot().setStyle("-fx-background-color: rgba(0, 0, 0, 0)");
	}


	private void setWindowCharact(Window window, Scene scene) {

		//Make the pane.
		Pane root = new Pane();

		windowCharact = new WindowCharacteristics(window, scene, root);

		setRootCharacteristics();
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

		this.textElem.getPlayText().setLayoutX(widthKPlayText * this.width);
		this.textElem.getPlayText().setLayoutY(heightKPlayText * this.height);

		//Testing of the layout placement of textPlay.
//		System.out.println(widthKPlayText * this.width);
//		System.out.println(heightKPlayText * this.height);

		int fontSizePlay = getFontSizePlay();
		this.textElem.getPlayText().setStyle("-fx-background-color: #F0FFFF;"
			+ "-fx-text-fill: black;"
			+ "-fx-font-family: Comfortaa;"
			+ "-fx-font-size:" + fontSizePlay + "px;"           
			+ "-fx-font-weight: bold;");
	}

	private void prepareAuthor() { //Prepare the author text.
		int fontSize = getFontSize();

		this.textElem.getAuthor().setStyle("-fx-font-size:" + fontSize + "px; -fx-fill: black; -fx-font-weight: bold; -fx-font-family: Comfortaa;");

		this.textElem.getAuthor().setLayoutX(0.083 * this.width);
		this.textElem.getAuthor().setLayoutY(0.7 * this.height);
	}


	private void prepareTextElem() {
		String author = "by Luka Sarjanović";
		String playText = "The Keyboard";

		this.textElem = new TextElem(author, playText);

		prepareAuthor();
		prepareTextPlay();
	}

	private void setEllipseElemPosition() {

		this.ellipseElem.getPlay().setCenterX(0.333 * this.width);
		this.ellipseElem.getPlay().setCenterY(0.250 * this.height);
		this.ellipseElem.getPlay().setRadiusX(0.117 * this.width);
		this.ellipseElem.getPlay().setRadiusY(0.120 * this.height);

		this.ellipseElem.getPlayHitbox().setCenterX(0.333 * this.width);
		this.ellipseElem.getPlayHitbox().setCenterY(0.250 * this.height);
		this.ellipseElem.getPlayHitbox().setRadiusX(0.125 * this.width);
		this.ellipseElem.getPlayHitbox().setRadiusY(0.127 * this.height);
	}

	private void setEllipseElemCharacteristics() {
		this.ellipseElem.getPlay().setFill(Color.AZURE);
		this.ellipseElem.getPlay().setStrokeWidth(3);
		this.ellipseElem.getPlay().setStroke(Color.BROWN);
		//			this.ellipseElem.getPlay().setStroke(Color.TRANSPARENT)); //To make the button border transparent.

		this.ellipseElem.getPlayHitbox().setFill(Color.TRANSPARENT);
	}

	private void setEllipseElemDynamic() {

		this.ellipseElem.getPlayHitbox().setOnMouseEntered(e -> {
			this.ellipseElem.getPlay().setStrokeWidth(8);
			this.ellipseElem.getPlay().setStroke(Color.BROWN);
		});
		this.ellipseElem.getPlayHitbox().setOnMouseExited(e -> {
			this.ellipseElem.getPlay().setStrokeWidth(3);
			this.ellipseElem.getPlay().setStroke(Color.BROWN);
		});

		//keyboardPane function on button_click.
		this.ellipseElem.getPlayHitbox().setOnMouseClicked (e -> this.windowCharact.getWindow().changePane("menuPane"));
	}


	private void prepareEllipseElem() {
		this.ellipseElem = new EllipseElem();

		setEllipseElemCharacteristics();
		setEllipseElemPosition();
		setEllipseElemDynamic();
	}

	//Add the elements.
	private void addElements() {

		//Clear all elements.
		this.windowCharact.getRoot().getChildren().clear();

		prepareTextElem();

		prepareEllipseElem();

		//Add all elements to root Pane.
		this.getRoot().getChildren().addAll(this.textElem.getAuthor(),
								  this.ellipseElem.getPlay(),
								  this.textElem.getPlayText(),
								  this.ellipseElem.getPlayHitbox());
	}

	public Pane getRoot() {
		return this.windowCharact.getRoot();
	}

	private void rotateTextPlay() {
		float widthKPlayText = getWidthKPlayText();
		float heightKPlayText = getHeightKPlayText();

		this.textElem.getPlayText().setLayoutX(widthKPlayText * this.width);
		this.textElem.getPlayText().setLayoutY(heightKPlayText * this.height);

		int fontSizePlay = getFontSizePlay();
		this.textElem.getPlayText().setStyle("-fx-background-color: #F0FFFF;"
			+ "-fx-text-fill: black;"
			+ "-fx-font-family: Comfortaa;"
			+ "-fx-font-size:" + fontSizePlay + "px;"           
			+ "-fx-font-weight: bold;");
	}

	private void rotateTextAuthor() {
		int fontSize = getFontSize();
		this.textElem.getAuthor().setLayoutX(0.083 * this.width);
		this.textElem.getAuthor().setLayoutY(0.7 * this.height);
		
		this.textElem.getAuthor().setStyle("-fx-font-size:" + fontSize + "px; -fx-fill: black; -fx-font-weight: bold; -fx-font-family: Comfortaa;");
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

	private static class WindowCharacteristics {
		private Window window;
		private Scene scene;
		private Pane root;

		public WindowCharacteristics(Window window,
								   Scene scene,
								   Pane root) {
			this.window = window;
			this.scene = scene;
			this.root = root;
		}

		public Window getWindow() {
			return this.window;
		}

		public Scene getScene() {
			return this.scene;
		}

		public Pane getRoot() {
			return this.root;
		}
	}


	private static class TextElem {
		private Text author;
		private Text playText;

		public TextElem(String author, String playText) {
			this.author = new Text(author);
			this.playText = new Text(playText);
		}

		public Text getAuthor() {
			return this.author;
		}

		public Text getPlayText() {
			return this.playText;
		}
	}


	private static class EllipseElem {
		private Ellipse play;
		private Ellipse playHitbox;

		public EllipseElem() {
			this.play = new Ellipse();
			this.playHitbox = new Ellipse();
		}

		public Ellipse getPlay() {
			return this.play;
		}

		public Ellipse getPlayHitbox() {
			return this.playHitbox;
		}
	}

}
