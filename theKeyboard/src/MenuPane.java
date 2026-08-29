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

	private Pane root;

	public MenuPane(Window window, Scene scene, Trie trie) {
		this.height = 100; //Default value, changes at full_sceen resize.
		this.width = 100;  //Default value, changes at full_sceen resize.
		this.root = new Pane();

		EllipseElem ellipseElem = getEllipseElem();
		TextElem textElem = getTextElem();
		ModifySettingsElem modifySettingsElem = getModifySettingsElem(trie);

		setRootCharacteristics(ellipseElem, textElem, modifySettingsElem, window, scene);

		addElements(ellipseElem, textElem, modifySettingsElem, window);
	}

	private EllipseElem getEllipseElem() {
		EllipseElem ellipseElem = new EllipseElem();

		return ellipseElem;
	}

	private ModifySettingsElem getModifySettingsElem(Trie trie) {
		Button modifySettings = new Button("modifySettings");

		ModifySettingsElem modifySettingsElem = new ModifySettingsElem(modifySettings, trie);

		return modifySettingsElem;
	}

	private void setRootCharacteristics(EllipseElem ellipseElem,
										TextElem textElem,
										ModifySettingsElem modifySettingsElem,
										Window window,
										Scene scene) {
		this.root.prefWidthProperty().bind(scene.widthProperty());
		this.root.prefHeightProperty().bind(scene.heightProperty());
		this.root.widthProperty().addListener((width, prevWidth, nextWidth) -> {
			this.width = nextWidth.intValue();
			addElements(ellipseElem, textElem, modifySettingsElem, window);
		});
		this.root.heightProperty().addListener((height, prevHeight, nextHeight) -> {
			this.height = nextHeight.intValue();
			addElements(ellipseElem, textElem, modifySettingsElem, window);
		});
		this.root.setStyle("-fx-background-color: rgba(0, 0, 0, 0)");
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

	private String getPlayTextStyling(int fontSize) {
		StringBuilder textStyling = new StringBuilder();


		textStyling.append("-fx-background-color: #F0FFFF;");
		textStyling.append("-fx-text-fill: black;");
		textStyling.append("-fx-font-family: Comfortaa;");
		textStyling.append("-fx-font-size:");
		textStyling.append(fontSize);
		textStyling.append("px;");
		textStyling.append("-fx-font-weight: bold;");

		return textStyling.toString();
	}

	//Prepares text in play ellipse.
	private void prepareTextPlay(Text textPlay) {
		float widthKPlayText = getWidthKPlayText();
		float heightKPlayText = getHeightKPlayText();

		textPlay.setLayoutX(widthKPlayText * this.width);
		textPlay.setLayoutY(heightKPlayText * this.height);

		//Testing of the layout placement of textPlay.
//		System.out.println(widthKPlayText * this.width);
//		System.out.println(heightKPlayText * this.height);

		int fontSizePlay = getFontSizePlay();
		String textStyling = getPlayTextStyling(fontSizePlay);

		textPlay.setStyle(textStyling);
	}

	private String getAuthorTextStyling(int fontSize) {
		StringBuilder textStyling = new StringBuilder();

		textStyling.append("-fx-font-size:");
		textStyling.append(fontSize);
		textStyling.append("px; -fx-fill: black;");
		textStyling.append("-fx-font-weight: bold;");
		textStyling.append("-fx-font-family: Comfortaa;");

		return textStyling.toString();
	}

	private void prepareAuthor(Text author) { //Prepare the author text.
		int fontSize = getFontSize();

		String textStyling = getAuthorTextStyling(fontSize);

		author.setStyle(textStyling);

		author.setLayoutX(0.083 * this.width);
		author.setLayoutY(0.7 * this.height);
	}


	private TextElem getTextElem() {
		String author_string = "by Luka Sarjanović";
		String playText_string = "The Keyboard";

		TextElem textElem = new TextElem(author_string, playText_string);

		return textElem;
	}

	private void setEllipseElemCharacteristics(EllipseElem ellipseElem) {
		Ellipse play = ellipseElem.getPlay();

		play.setFill(Color.AZURE);
		play.setStrokeWidth(3);
		play.setStroke(Color.BROWN);
//		play.setStroke(Color.TRANSPARENT)); //Uncomment to make the button border transparent.

		Ellipse playHitbox = ellipseElem.getPlayHitbox();

		playHitbox.setFill(Color.TRANSPARENT);
	}

	private void setEllipseElemPosition(EllipseElem ellipseElem) {

		Ellipse play = ellipseElem.getPlay();

		play.setCenterX(0.333 * this.width);
		play.setCenterY(0.250 * this.height);
		play.setRadiusX(0.117 * this.width);
		play.setRadiusY(0.120 * this.height);

		Ellipse playHitbox = ellipseElem.getPlayHitbox();

		playHitbox.setCenterX(0.333 * this.width);
		playHitbox.setCenterY(0.250 * this.height);
		playHitbox.setRadiusX(0.125 * this.width);
		playHitbox.setRadiusY(0.127 * this.height);
	}

	private void setEllipseElemDynamic(EllipseElem ellipseElem, Window window) {

		Ellipse play = ellipseElem.getPlay();
		Ellipse playHitbox = ellipseElem.getPlayHitbox();

		playHitbox.setOnMouseEntered(e -> {
			play.setStrokeWidth(8);
			play.setStroke(Color.BROWN);
		});
		playHitbox.setOnMouseExited(e -> {
			play.setStrokeWidth(3);
			play.setStroke(Color.BROWN);
		});

		//keyboardPane function on button_click.
		playHitbox.setOnMouseClicked (e ->
		window.changePane("TheKeyboardPane"));
	}

	private String getModifySettingsStyling(int fontSize) {
		StringBuilder textStyling = new StringBuilder();

		textStyling.append("-fx-background-color: rgba(255, 255, 255, 0.24);");
		textStyling.append("-fx-border-color: black;");
		textStyling.append("-fx-border-width: 2px;");
		textStyling.append("-fx-text-fill: black;");
		textStyling.append("-fx-font-family: Comfortaa;");
		textStyling.append("-fx-font-size: ");
		textStyling.append(fontSize);
		textStyling.append("px;");

		return textStyling.toString();
	}

	private void prepareEllipseElem(EllipseElem ellipseElem, Window window) {

		setEllipseElemCharacteristics(ellipseElem);
		setEllipseElemPosition(ellipseElem);

		setEllipseElemDynamic(ellipseElem, window);
	}

	private Button prepareModifySettings(ModifySettingsElem modifySettingsElem, Window window) {

		Button modifySettings = modifySettingsElem.getModifySettings();
		Trie trie = modifySettingsElem.getTrie();

		int fontSize = getFontSize();
		String textStyling = getModifySettingsStyling(fontSize);
		modifySettings.setStyle(textStyling);

		modifySettings.setLayoutX(0.720 * this.width);
		modifySettings.setLayoutY(0.850 * this.height);

		modifySettings.setOnMouseClicked(e -> {
			window.setDeleteButtons(trie);
			window.changePane("modifySettingsPane");
		});


		return modifySettings;
	}

	//Add the elements.
	private void addElements(EllipseElem ellipseElem,
							 TextElem textElem,
							 ModifySettingsElem modifySettingsElem,
							 Window window) {

		//Clear all elements.
		this.root.getChildren().clear();

		Text author = textElem.getAuthor();
		prepareAuthor(author);

		Text playText = textElem.getPlayText();
		prepareTextPlay(playText);

		prepareEllipseElem(ellipseElem, window);

		Ellipse play = ellipseElem.getPlay();
		Ellipse playHitbox = ellipseElem.getPlayHitbox();

		prepareModifySettings(modifySettingsElem, window);

		Button modifySettings = modifySettingsElem.getModifySettings();

		//Add all elements to root Pane.
		this.root.getChildren().addAll(author,
								  play,
								  playText,
								  playHitbox,
								  modifySettings);
	}

	public Pane getRoot() {
		return this.root; //this.root.
	}


	//This code must be remodeled before it can be uncommented for screen-rotation.
	/*
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

	public void changeRotation() {
		changeDimensions();
		setPlayAndPlayHitbox();
		rotateTextPlay();
		rotateTextAuthor();
	}
	*/

	private static class ModifySettingsElem {
		private Button modifySettings;
		private Trie trie;

		private ModifySettingsElem(Button modifySettings, Trie trie) {
			this.modifySettings = modifySettings;
			this.trie = trie;
		}

		private Button getModifySettings() {
			return this.modifySettings;
		}

		private Trie getTrie() {
			return this.trie;
		}
	}

	private static class TextElem {
		private Text author;
		private Text playText;

		private TextElem(String author, String playText) {
			this.author = new Text(author);
			this.playText = new Text(playText);
		}

		private Text getAuthor() {
			return this.author;
		}

		private Text getPlayText() {
			return this.playText;
		}
	}


	private static class EllipseElem {
		private Ellipse play;
		private Ellipse playHitbox;

		private EllipseElem() {
			this.play = new Ellipse();
			this.playHitbox = new Ellipse();
		}

		private Ellipse getPlay() {
			return this.play;
		}

		private Ellipse getPlayHitbox() {
			return this.playHitbox;
		}
	}

}
