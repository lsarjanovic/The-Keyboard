import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import java.util.*;
import java.lang.Math;
import javafx.scene.text.Font;
import java.lang.StringBuilder;
import javafx.geometry.Bounds;

public class TheKeyboardPane {
	private int width;
	private int height;

	private List <Button> keyboardButtons = new ArrayList <Button>();
	//Of method addKeyboardButtons().

	private List <Button> otherButtons = new ArrayList <Button>();
	//.get(0) = home, .get(1) = discard, .get(2) = enter, .get(3) = delete,
	//.get(4) = space, .get(5) = shift. Of method addOtherButtons().

	private Pane root;
	private Trie trie;
	private Scene scene;

	public TheKeyboardPane(Window window, Scene scene) {
		this.width = 100;
		this.height = 100;
		this.scene = scene;
		this.root = new Pane();

		TextField textField = getTextField();

		addText(textField);


		SuggestElem suggestElem = getSuggestElem();

		addSuggestButtons(textField, suggestElem);

		ArrayList<Button> suggestButtons = suggestElem.getSuggestButtons();
		ArrayList <String> suggestions = new ArrayList <String>();

		addOtherButtons(textField, suggestButtons, suggestions, window);
		addKeyboardButtons(textField, suggestButtons, suggestions);

		this.trie = new Trie();

		setRootCharacteristics(textField, suggestElem);
	}

	private SuggestElem getSuggestElem() {
		ArrayList <Button> suggestButtons = new ArrayList <Button>();
		Rectangle suggestBackground = new Rectangle();

		SuggestElem suggestElem = new SuggestElem(suggestButtons, suggestBackground);

		return suggestElem;
	}

	private TextField getTextField() {
		Text mainText = new Text("eeeeeeee");
		Rectangle textBackground = new Rectangle();

		byte caps = 0;

		TextField textField = new TextField(mainText, textBackground, caps);

		return textField;
	}

	private void setRootCharacteristics(TextField textField, SuggestElem suggestElem) {

		ArrayList<Button> suggestButtons = suggestElem.getSuggestButtons();

		this.root.prefWidthProperty().bind(this.scene.widthProperty());
		this.root.prefHeightProperty().bind(this.scene.heightProperty());
		this.root.widthProperty().addListener((widthProperty, prevWidth, nextWidth) -> {
			this.width = nextWidth.intValue();
			refreshAllButtons(textField, suggestElem);
		});
		this.root.heightProperty().addListener((heightProperty, prevHeight, nextHeight) -> {
			this.height = nextHeight.intValue();
			refreshAllButtons(textField, suggestElem);
		});

		this.root.setStyle("-fx-background-color: rgba(0, 0, 0, 0)");
	}

	private void createSuggestButtons(ArrayList<Button> suggestButtons) {
		Button suggest1 = new Button("suggest1");
		Button suggest2 = new Button("suggest2");
		Button suggest3 = new Button("suggest3");

		suggestButtons.add(suggest1);
		suggestButtons.add(suggest2);
		suggestButtons.add(suggest3);
	}

	//Sets position of suggestion buttons background.
	private void setSuggestButtonsBackgroundPosition(Rectangle suggestBackground) {
		suggestBackground.setLayoutX(0.223 * this.width);
		suggestBackground.setLayoutY(0.480 * this.height);
		suggestBackground.setWidth(0.734 * this.width);
		suggestBackground.setHeight(0.100 * this.height);
	}

	//Sets properties of suggestion buttons background.
	private void setSuggestButtonsBackground(Rectangle suggestBackground) {

		setSuggestButtonsBackgroundPosition(suggestBackground);
		suggestBackground.setFill(Color.rgb(255, 255, 255, 0.5));
		suggestBackground.setStroke(Color.BLACK);

		root.getChildren().add(suggestBackground);
	}

	private void setSuggestButtonsFunctions(TextField textField, ArrayList<Button> suggestButtons) {

		Text mainText = textField.getMainText();

		//Set button dynamic properties.
		suggestButtons.get(0).setOnMouseClicked(e -> {

			//If suggest1 is not empty, complete the word to suggest1.
			if (!suggestButtons.get(0).getText().isEmpty())
					mainText.setText(
								useSuggestion(mainText, suggestButtons.get(0))
							);

			setSuggestionsToEmpty(suggestButtons); //Set suggestions to empty.
			refreshText(textField); //Refresh mainText size.
		});
		suggestButtons.get(1).setOnMouseClicked(e -> {

			//If suggest2 is not empty, complete the word to suggest2
			if (!suggestButtons.get(1).getText().isEmpty())
				mainText.setText(
								useSuggestion(mainText, suggestButtons.get(1))
							);

			setSuggestionsToEmpty(suggestButtons); //Set suggestions to empty.
			refreshText(textField); //Refresh mainText size.
		});
		suggestButtons.get(2).setOnMouseClicked(e -> {

			//If suggest3 is not empty, complete the word to suggest3.
			if (!suggestButtons.get(2).getText().isEmpty())
				mainText.setText(
								useSuggestion(mainText, suggestButtons.get(2))
							);

			setSuggestionsToEmpty(suggestButtons); //Set suggestions to empty.
			refreshText(textField); //Refresh text size.
		});
	}

	private int getButtonTextFontSize() {
		int fontSize = (this.width > this.height) ?
					(int) (0.038 * this.height) :
					(int) (0.038 * this.width);

		return fontSize;
	}

	private String getSuggestButtonsStyling() {
		int fontSize = getButtonTextFontSize();
		StringBuilder textStyling = new StringBuilder("");

		textStyling.append("-fx-background-color: rgba(255, 255, 255, 0);");
		textStyling.append("-fx-border-color: black;");
		textStyling.append("-fx-border-width: 2px;");
		textStyling.append("-fx-text-fill: black;");
		textStyling.append("-fx-font-family: Comfortaa;");
		textStyling.append("-fx-font-size: ");
		textStyling.append(fontSize);
		textStyling.append("px;");

		return textStyling.toString();
	}

	private void setSuggestButtonsStyle(ArrayList<Button> suggestButtons) {
		int fontSize = getButtonTextFontSize();
		String textStyling = getSuggestButtonsStyling();

		suggestButtons.get(0).setStyle(textStyling); 	//suggestButtons.get(0) = suggest1
		suggestButtons.get(1).setStyle(textStyling); 	//suggestButtons.get(1) = suggest2
		suggestButtons.get(2).setStyle(textStyling);	//suggestButtons.get(2) = suggest3
	}

	//Adds the whole suggestions box and ads all buttons.
	private void addSuggestButtons(TextField textField, SuggestElem suggestElem) {

		ArrayList<Button> suggestButtons = suggestElem.getSuggestButtons();
		createSuggestButtons(suggestButtons);

		Rectangle suggestBackground = suggestElem.getSuggestBackground();
		setSuggestButtonsBackground(suggestBackground);

		setSuggestButtonsFunctions(textField, suggestButtons);
		setSuggestButtonsStyle(suggestButtons);
		setSuggestButtonsPosition(suggestButtons);

		for (Button button : suggestButtons) this.root.getChildren().add(button);
	}


	private int getMainTextFontSize(Text mainText) {
		int textLength = Math.max(1, mainText.getText().length());
		int fontSize = (int) (0.050 * this.height - textLength / 5);
		if (fontSize < 30) fontSize = 30;

		return fontSize;
	}

	private String getMainTextStyling(int fontSize) {
		StringBuilder textStyling = new StringBuilder("");

		textStyling.append("-fx-background-color: rgba(255, 255, 255, 0);");
		textStyling.append("-fx-border-color: black;");
		textStyling.append("-fx-border-width: 2px;");
		textStyling.append("-fx-text-fill: black;");
		textStyling.append("-fx-font-family: Comfortaa;");
		textStyling.append("-fx-font-size: ");
		textStyling.append(fontSize);
		textStyling.append("px;");

		return textStyling.toString();
	}

	private int [ ] findChars(Text mainText) {
		char [ ] znaki = {'a', 'b', 'c', 'd', 'e', ',', 'u', 'v', //The buttons in order
								   'f', 'g', 'h', 'i', 'j', '!', 'w', 'x',
								   'k', 'l', 'm', 'n', 'o', '?', 'y', 'z',
								   'p', 'q', 'r', 's', 't', '.', 	//End of buttons in order
								   ' ', 'A', 'B', 'C', 'D', 'E', 'U', 'V', //The beggining of additional characters.
									'F', 'G', 'H', 'I', 'J', 'W', 'X',
									'K', 'L', 'M', 'N', 'O', 'Y', 'Z',
									'P', 'Q', 'R', 'S', 'T'}; //The end of additional characters.
		int [ ] occurences = new int [znaki.length];
		for (int i = 0; i < znaki.length; i++)
			for (char c : mainText.getText().toCharArray())
				if (c == znaki[i]) occurences[i]++;

		return occurences;
	}

	private double [ ] getRatios() {
		double [ ] ratios = {3.3140, 3.170, 3.752, 3.17, 3.652, //Vse vrednosti, po 5 vrednosti na vrstico.
										9.0, 3.302, 4.052, 6.1, 3.3,
										3.3, 7.82, 7.88, 8.467, 3.02,
										4.1, 3.9, 7.92, 2.375, 3.3,
										3.3, 4.1, 4.09, 4.07, 3.17,
										3.17, 4.868, 4.15, 5.57, 10.4,
										7.57, 3.18, 3.21, 2.9, 2.9,
										3.22, 2.74, 3.12, 3.58, 2.90,
										2.65, 7.98, 3.64, 2.4, 3.16,
										3.42, 3.52, 2.28, 2.7, 2.43,
										3.46, 3.11, 3.64, 2.434, 3.63,
										3.38, 3.4};

		return ratios;
	}

	private void moveMainText(Text mainText, int fontSize) {
		int [ ] occurences = findChars(mainText);
		double [ ] ratios = getRatios();

		double textLayoutX = 0.500 * this.width;
		for (int i = 0; i < occurences.length; i++)
			if (occurences[i] > 0) textLayoutX -= occurences[i] * (fontSize / ratios[i]);

		mainText.setLayoutX(textLayoutX);
		mainText.setLayoutY(0.340 * this.height);
	}

	private void addNewlineMainText(Text mainText) {
		String mainTextNext = mainText.getText() + "\n";

		mainText.setText(mainTextNext);
	}

	private void refreshText(TextField textField) {

		Text mainText = textField.getMainText();

		int fontSize = getMainTextFontSize(mainText);

		String mainTextStyling = getMainTextStyling(fontSize);
		mainText.setStyle(mainTextStyling);


		Rectangle textBackground = textField.getTextBackground();
		double centerX = textBackground.getLayoutX() + textBackground.getWidth() / 2;
		double centerY = textBackground.getLayoutY() + textBackground.getHeight() / 2;
		
		Bounds textBounds = mainText.getBoundsInLocal();

		mainText.setLayoutX(centerX - textBounds.getWidth() / 2);
		mainText.setLayoutY(centerY + textBounds.getHeight() / 4); // adjust for font baseline

		moveMainText(mainText, fontSize);

//		if (textOutOfBounds()) addNewlineMainText(mainText); //Add newline character "\n".
	}

	private boolean textOutOfBounds(TextField textField) {
		Text mainText = textField.getMainText();
		Rectangle textBackground = textField.getTextBackground();

		Bounds textBounds = mainText.getBoundsInLocal(); //Nešto ne radi kako treba. Pogledaj getBoundsInParent() kako radi.
		Bounds backgroundBounds = textBackground.getBoundsInLocal();

		if ((textBounds.getMinX() < backgroundBounds.getMinX()) 
				|| (textBounds.getMaxX() > backgroundBounds.getMaxX())) return false;

		return true;
	}

	//Sets position of main text background.
	private void setMainTextBackgroundPosition(Rectangle textBackground) {
		textBackground.setLayoutX(0.130 * this.width);
		textBackground.setLayoutY(0.141 * this.height);
		textBackground.setWidth(0.827 * this.width);
		textBackground.setHeight(0.322 * this.height);
	}


	private void addText(TextField textField) {
		Rectangle textBackground = textField.getTextBackground();

		textBackground.setFill(Color.rgb(255, 255, 255, 0.5));
		textBackground.setStroke(Color.BLACK);

		setMainTextBackgroundPosition(textBackground);

		Text mainText = textField.getMainText();
		refreshText(textField);

		root.getChildren().addAll(textBackground, mainText);
	}

/*	private void setHDEButtonsText(Button home, Button discard,
			Button enter) {
		if (this.width > this.height) {
			enter.setText("-Enter-"); 
			discard.setText("-Discard-");
			home.setText("-H");
		} else {
			enter.setText("-E-"); 
			discard.setText("-X-");
			home.setText("-H"); //Morda bom text do home.
		}
	}
*/

	private void setAllOtherButtonsFunctions(TextField textField, ArrayList<Button> suggestButtons,
											 ArrayList<String> suggestions, Window window) {
		Text mainText = textField.getMainText();

		//Set home button function.
		otherButtons.get(0).setOnMouseClicked(e -> {
			window.changePane("TheKeyboardPane");
			mainText.setText("");
			setSuggestionsToEmpty(suggestButtons); //Set all displayed suggestions to empty.
		});

		//Set discard button function.
		otherButtons.get(1).setOnMouseClicked(e -> {
			mainText.setText("");
			setSuggestionsToEmpty(suggestButtons); //Set all displayed suggestions to empty.
		});
		
		//Set enter button function.
		otherButtons.get(2).setOnMouseClicked(e -> {
			String sentence = mainText.getText();
			if(!sentence.isEmpty()) {
				trie.insertSentence(sentence);
				mainText.setText("");
				setSuggestionsToEmpty(suggestButtons); //Set all displayed suggestions to empty.
			}
		});

		//Set delete button function.
		otherButtons.get(3).setOnMouseClicked(e -> {
			String sentence = mainText.getText();

			if (!sentence.isEmpty()) {
				mainText.setText(
						deleteChar(mainText)
						);
				refreshSuggestions(suggestButtons, suggestions);
			} 
			sentence = mainText.getText();
			if(sentence.isEmpty()) setSuggestionsToEmpty(suggestButtons);
		});
		
		otherButtons.get(4).setOnMouseClicked(e -> {
			mainText.setText(mainText.getText() + " "); //Dodaj črko v poved.
			refreshText(textField); //Refresh text size.
			setSuggestionsToEmpty(suggestButtons); //Set all displayed suggestions to empty.
		});

		otherButtons.get(5).setOnMouseClicked(e -> shift(textField));
	}

	private String getButtonTextStyling() {
		int fontSize = getButtonTextFontSize();

		StringBuilder textStyling = new StringBuilder("");

		textStyling.append("-fx-background-color: rgba(255, 255, 255, 0.5);");
		textStyling.append("-fx-border-color: black;");
		textStyling.append("-fx-border-width: 2px;");
		textStyling.append("-fx-text-fill: black;");
		textStyling.append("-fx-font-family: Comfortaa;");
		textStyling.append("-fx-font-size: ");
		textStyling.append(fontSize);
		textStyling.append("px;");

		return textStyling.toString();
	}

	private void setAllOtherButtonsStyle() {
		//Set styling of text to all otherButtons.
		String textStyling = getButtonTextStyling();

		otherButtons.get(0).setStyle(textStyling);
		otherButtons.get(1).setStyle(textStyling);
		otherButtons.get(2).setStyle(textStyling);
		otherButtons.get(3).setStyle(textStyling);
		otherButtons.get(4).setStyle(textStyling);
		otherButtons.get(5).setStyle(textStyling);
	}

	private void refreshAllButtons(TextField textField, SuggestElem suggestElem) {
		refreshOtherButtons();

		Rectangle suggestBackground = suggestElem.getSuggestBackground();
		setSuggestButtonsBackgroundPosition(suggestBackground);

		ArrayList<Button> suggestButtons = suggestElem.getSuggestButtons();
		setSuggestButtonsStyle(suggestButtons);
		setSuggestButtonsPosition(suggestButtons);

		refreshKeyboardButtons();

		Rectangle textBackground = textField.getTextBackground();
		setMainTextBackgroundPosition(textBackground);

		refreshText(textField);
	}

	private void refreshKeyboardButtons() {
		int wButton = (int) (0.042 * this.width); //Width position button.
		int hButton = (int) (0.590 * this.height); //Height position button.

		String textStyling = getButtonTextStyling();

		for (Button button : keyboardButtons) {
			setKeyboardButtonStatic(button, wButton, hButton);
			button.setStyle(textStyling);

			wButton += (int) (0.117 * this.width);
			if (wButton > this.width - 0.10 * this.width) {
				hButton += (int) (0.10 * this.height); //0.10 * this.height
				wButton = (int) (0.042 * this.width); //0.033 * this.width
			}	
		}
	}

	private void refreshOtherButtons() {
		setHomeButtonStatic();
		setDiscardButtonStatic();
		setEnterButtonStatic();
		setDeleteButtonStatic();
		setSpaceButtonStatic();
		setShiftButtonStatic();
		setAllOtherButtonsStyle();
		return;
	}

	private void setHomeButtonStatic() {
		otherButtons.get(0).setLayoutX(0.042 * this.width);
		otherButtons.get(0).setLayoutY(0.025 * this.height);
		otherButtons.get(0).setPrefWidth(0.325 * this.width);
		otherButtons.get(0).setPrefHeight(0.100 * this.height);
		otherButtons.get(0).setMinWidth(0.250* this.width);
		otherButtons.get(0).setMinHeight(0.050 * this.height);
	}

	private void setDiscardButtonStatic() {
		otherButtons.get(1).setLayoutX(0.610 * this.width);
		otherButtons.get(1).setLayoutY(0.025 * this.height);
		otherButtons.get(1).setPrefWidth(0.167 * this.width);
		otherButtons.get(1).setPrefHeight(0.100 * this.height);
		otherButtons.get(1).setMinWidth(0.167 * this.width);
		otherButtons.get(1).setMinHeight(0.100 * this.height);
	}

	private void setEnterButtonStatic() {
		otherButtons.get(2).setLayoutX(0.790 * this.width);
		otherButtons.get(2).setLayoutY(0.025 * this.height); //0.370 * this.heigth
		otherButtons.get(2).setPrefWidth(0.167 * this.width);
		otherButtons.get(2).setPrefHeight(0.100 * this.height);
		otherButtons.get(2).setMinWidth(0.167 * this.width);
		otherButtons.get(2).setMinHeight(0.100 * this.height);
	}

	private void setDeleteButtonStatic() {
		otherButtons.get(3).setLayoutX(0.042 * this.width);
		otherButtons.get(3).setLayoutY(0.140 * this.height); //0.370 * this.heigth
		otherButtons.get(3).setPrefWidth(0.075 * this.width);
		otherButtons.get(3).setPrefHeight(0.325 * this.height);
		otherButtons.get(3).setMinWidth(0.075 * this.width);
		otherButtons.get(3).setMinHeight(0.270 * this.height);
	}

	private void setSpaceButtonStatic() {
		otherButtons.get(4).setLayoutX(0.042 * this.width);
		otherButtons.get(4).setLayoutY(0.480 * this.height);
		otherButtons.get(4).setPrefWidth(0.167 * this.width);
		otherButtons.get(4).setPrefHeight(0.100 * this.height);
		otherButtons.get(4).setMinWidth(0.167 * this.width);
		otherButtons.get(4).setMinHeight(0.100 * this.height);
	}

	private void setShiftButtonStatic() {
		otherButtons.get(5).setLayoutX(0.742 * this.width);
		otherButtons.get(5).setLayoutY(0.889 * this.height);
		otherButtons.get(5).setPrefWidth(0.217 * this.width);
		otherButtons.get(5).setPrefHeight(0.090 * this.height);
		otherButtons.get(5).setMinWidth(0.217 * this.width);
		otherButtons.get(5).setMinHeight(0.090 * this.height);
	}

	//Add all buttons above suggestion buttons.
	private void addOtherButtons(TextField textField, ArrayList<Button> suggestButtons,
								 ArrayList<String> suggestions, Window window) {

		//Add home button and set static and dynamic properties,
		//home button returns to homepage.
		Button home = new Button("-Home");

		otherButtons.add(home);
		setHomeButtonStatic();

		//Add discard button and set static and dynamic properties.
		Button discard = new Button("-Discard-");

		otherButtons.add(discard);
		setDiscardButtonStatic();

		//Add enter button and set static and dynamic properties.
		Button enter = new Button("-Enter-");

		otherButtons.add(enter);
		setEnterButtonStatic();

		//Add delete button and set static and dynamic properties,
		//delete button returns to deletepage.
		Button delete = new Button("--D");

		otherButtons.add(delete);
		setDeleteButtonStatic();

		//Add space button and set static and dynamic properties.
		Button space = new Button("______");

		otherButtons.add(space);
		setSpaceButtonStatic();

		//Add shift button and set static and dynamic properties.
		Button shift = new Button("-->");

		otherButtons.add(shift);
		setShiftButtonStatic();

		setAllOtherButtonsFunctions(textField, suggestButtons, suggestions, window);
		setAllOtherButtonsStyle();

		//Add all buttons to theKeyboardPane.
		for (Button button : otherButtons) root.getChildren().add(button);
		return;
	}

	//Add all butttons below suggestion buttons.
	private void addKeyboardButtons(TextField textField, ArrayList<Button> suggestButtons, ArrayList<String> suggestions) {
		int wButton = (int) (0.042 * this.width); //Width position button.
		int hButton = (int) (0.590 * this.height); //Height position button.

		String textStyling = getButtonTextStyling();

		char [ ] znaki = {'a', 'b', 'c', 'd', 'e', ',', 'u', 'v', 'f', 'g', 'h', 'i', 'j', '!', 'w',
			'x', 'k', 'l', 'm', 'n', 'o', '?', 'y', 'z', 'p', 'q', 'r', 's', 't', '.'};

		Text mainText = textField.getMainText();

		byte caps = textField.getCaps();


		//Add keyboard buttons.
		for (int i = 0; i < znaki.length; i++) {
			String znak = String.valueOf(znaki[i]);

			Button button = new Button(znak);
			//Set keyboardButton static properties.
			setKeyboardButtonStatic(button, wButton, hButton);
			button.setStyle(textStyling);

			//Set keyboardButton dynamic properties.
			button.setOnMouseClicked(e -> {
				Button clickedButton = (Button) e.getSource();

				//Get new text.
				String mainTextNext = mainText.getText() + clickedButton.getText();

				suggestions.clear(); //Clear all suggestions.
				trie.setSuggestions(suggestions, mainTextNext);

				refreshSuggestions(suggestButtons, suggestions);

				mainText.setText(mainTextNext);

				refreshText(textField);

				if (caps == 1) noCaps(textField);
			});

			keyboardButtons.add(button);
			root.getChildren().add(button);

			wButton += (int) (0.117 * this.width);
			if (wButton > this.width - 0.10 * this.width) {
				hButton += (int) (0.10 * this.height); //0.10 * this.height
				wButton = (int) (0.042 * this.width); //0.033 * this.width
			}	
		}
		return;
	}

	private void setKeyboardButtonStatic(Button button, int wButton, int hButton) {
		button.setLayoutX(wButton);
		button.setLayoutY(hButton);
		button.setPrefWidth((int) (0.10 * this.width)); //0.10 * this.width
		button.setPrefHeight((int) (0.09 * this.height)); //0.09 * this.height
		button.setMinWidth((int) (0.10 * this.width)); //0.10 * this.width
		button.setMinHeight((int) (0.09 * this.height)); //0.09 * this.height
	}


	private void setSuggestButtonsPosition(ArrayList<Button> suggestButtons) {
		if (this.width > this.height) {
//		root.getChildren().add(suggestButtons.get(0)); //If rotation.

		//Set suggest buttons position.
		suggestButtons.get(0).setLayoutX(0.227 * this.width); //suggestButtons.get(0) = suggest1
		suggestButtons.get(1).setLayoutX(0.397 * this.width);	//suggestButtons.get(1) = suggest2
		suggestButtons.get(2).setLayoutX(0.679 * this.width);	//suggestButtons.get(2) = suggest3
		suggestButtons.get(0).setLayoutY(0.491 * this.height);
		suggestButtons.get(1).setLayoutY(0.491 * this.height);
		suggestButtons.get(2).setLayoutY(0.491 * this.height);

		//Set suggest buttons dimension.
		suggestButtons.get(0).setPrefWidth(0.161 * this.width);
		suggestButtons.get(1).setPrefWidth(0.274 * this.width);
		suggestButtons.get(2).setPrefWidth(0.274 * this.width);
		suggestButtons.get(0).setPrefHeight(0.083 * this.height);
		suggestButtons.get(1).setPrefHeight(0.083 * this.height);
		suggestButtons.get(2).setPrefHeight(0.083 * this.height);

		suggestButtons.get(0).setMinWidth(0.161 * this.width);
		suggestButtons.get(1).setMinWidth(0.274 * this.width);
		suggestButtons.get(2).setMinWidth(0.274 * this.width);
		suggestButtons.get(0).setMinHeight(0.083 * this.height);
		suggestButtons.get(1).setMinHeight(0.083 * this.height);
		suggestButtons.get(2).setMinHeight(0.083 * this.height);
		} else {

//		root.getChildren().remove(suggestButtons.get(0)); //If rotation.

		//Set buttons position.
		suggestButtons.get(1).setLayoutX(0.250 * this.width);
		suggestButtons.get(2).setLayoutX(0.618 * this.width);
		suggestButtons.get(1).setLayoutY(0.487 * this.height);
		suggestButtons.get(2).setLayoutY(0.487 * this.height);

		//Set buttons dimension.
		suggestButtons.get(1).setPrefWidth(0.314 * this.width);
		suggestButtons.get(2).setPrefWidth(0.314 * this.width);
		suggestButtons.get(1).setPrefHeight(0.079 * this.height);
		suggestButtons.get(2).setPrefHeight(0.079 * this.height);

		suggestButtons.get(1).setMinWidth(0.314 * this.width);
		suggestButtons.get(2).setMinWidth(0.314 * this.width);
		suggestButtons.get(1).setMinHeight(0.079 * this.height);
		suggestButtons.get(2).setMinHeight(0.079 * this.height);
		}
	}

	private String useSuggestion(Text mainText, Button suggest) {
		String [ ] sentenceWords = mainText.getText().split("\\s+"); //Split the current sentence into words.
		String word = sentenceWords[sentenceWords.length - 1]; //Get the last word in the sentence.

		//Build the new sentence for text all but the last word.
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < sentenceWords.length - 1; j++) { //Add all words other than the last and space between them.
			sb.append(sentenceWords[j]); //Dodaj vse besede razen zadnje. Zadnjo preuredi na mestih kjer so velike črke z boolean tabelo.
			sb.append(" ");
		}
		System.out.println(sb.toString());
		//Make suggestion array of letters.
		char [ ] suggestion = suggest.getText().toCharArray();

		//Find all upperCase letters in last word.
		boolean [ ] isUpperCase = new boolean [suggestion.length];
		int i = 0;
		for (char crka : word.toCharArray()) {
			if (crka >= 'A' && crka <= 'Z') isUpperCase[i] = true;
			i++;
		}

		//Apply the upperCase prefix to the suggested word.
		for(int j = 0; j < suggestion.length; j++) {
			if (isUpperCase[j] && suggestion[j]  >= 'a' && suggestion[j] <= 'z') //If the char in last word should be upperCase 
				suggestion[j] -= 32;									//according to prefix, then make it upperCase.
				sb.append(suggestion[j]);
		}
		return sb.toString(); //Return the new sentence with the suggested word with correct prefix.
	}

	//Refreshes suggestions.
	private void refreshSuggestions(ArrayList<Button> suggestButtons, ArrayList<String> suggestions) {
		if (this.width > this.height) { //If there are 3 suggestion boxes on the screen.
			if (suggestions.size() >= 1) suggestButtons.get(0).setText(suggestions.get(0)); //suggestButtons.get(0) = suggest1
			if (suggestions.size() >= 2) suggestButtons.get(1).setText(suggestions.get(1)); //suggestButtons.get(1) = suggest2
			if (suggestions.size() >= 3) suggestButtons.get(2).setText(suggestions.get(2)); //suggestButtons.get(2) = suggest3
		} else { //If there are 2 suggestion boxes on the screen.
			if (suggestions.size() >= 1) suggestButtons.get(1).setText(suggestions.get(0));	//suggestButtons.get(1) = suggest2
			if (suggestions.size() >= 2) suggestButtons.get(2).setText(suggestions.get(1));	//suggestButtons.get(2) = suggest3
		}
	}

	//Returns displayed suggestion to empty state,
	//happens only when enter or space clicked.
	private void setSuggestionsToEmpty(ArrayList<Button> suggestButtons) {
		suggestButtons.get(0).setText(""); //suggestButtons.get(0) = suggest1
		suggestButtons.get(1).setText(""); //suggestButtons.get(1) = suggest2
		suggestButtons.get(2).setText(""); //suggestButtons.get(2) = suggest3
	}

	//Removes last character from text.
	private String deleteChar(Text mainText) {
		StringBuilder sb = new StringBuilder();
		sb.append(mainText.getText());
		sb.deleteCharAt(sb.toString().length() - 1);
		return sb.toString();
	}

	//Submethod of shift(). Makes all letters upperCase.
	private void allCaps() {
		char znak;
		for (Button button : keyboardButtons) {
			znak = button.getText().charAt(0);
			if (znak >= 'a'
				&& znak <= 'z'
				&& znak != '-'
				&& znak != '_') {
				znak -= 32;
				button.setText(String.valueOf(znak));
			}
		}
		return;
	}

	//Method for capsLock and shift via the same button, the button shift.
	private void shift(TextField textField) {
		textField.incrementCaps();

		byte caps = textField.getCaps();

		if (caps == 0) noCaps(textField);
		else if (caps == 1) allCaps();

		return;
	}

	//Submethod of shift(). Makes all letter lowerCase.
	private void noCaps(TextField textField) {
		char znak;
		for (Button button : keyboardButtons) {
			znak = button.getText().charAt(0);
			if (znak >= 'A'
				&& znak <= 'Z'
				&& znak != '-'
				&& znak != '_') {
				znak += 32;
				button.setText(String.valueOf(znak));
			}
		}

		textField.setCapsToZero();
		return;
	}

	/*private void changeDimensions() {
		int temp = this.width;
		this.width= this.height;
		this.height = temp;
	}

	private void rotateKeyboardButtons() {
		int wButton = (int) (0.042 * this.width); //Width position button.
		int hButton = (int) (0.590 * this.height); //Height position button.

		for (Button button : keyboardButtons) {
			setKeyboardButtonStatic(button, wButton, hButton);

			wButton += (int) (0.117 * this.width);
			if (wButton > this.width - 0.10 * this.width) {
				hButton += (int) (0.10 * this.height); //0.10 * this.height
				wButton = (int) (0.042 * this.width); //0.033 * this.width
			}
		}
	}


	public void changeRotation() {
		changeDimensions();
		rotateKeyboardButtons();
		setSuggestionButtonsBackgroundStatic();
		setSuggestButtonsStatic();
		setHomeButtonStatic();
		setDiscardButtonStatic();
		setEnterButtonStatic();
		setDeleteButtonStatic();
		setSpaceButtonStatic();
		setShiftButtonStatic();
	}*/

	public Pane getRoot() {
		return this.root;
	}

	private static class SuggestElem {
		private ArrayList<Button> suggestButtons;
		private Rectangle suggestBackground;

		public SuggestElem(ArrayList<Button> suggestButtons, Rectangle suggestBackground) {
			this.suggestButtons = suggestButtons;
			this.suggestBackground = suggestBackground;
		}

		public ArrayList<Button> getSuggestButtons() {
			return this.suggestButtons;
		}

		public Rectangle getSuggestBackground() {
			return this.suggestBackground;
		}
	}

	private static class TextField {
		private Text mainText;
		private Rectangle textBackground;
		private byte caps;

		public TextField(Text mainText, Rectangle textBackground, byte caps) {
			this.mainText = mainText;
			this.textBackground = textBackground;
			this.caps = caps;
		}

		public Text getMainText() {
			return this.mainText;
		}

		public Rectangle getTextBackground() {
			return this.textBackground;
		}

		public byte getCaps() {
			return this.caps;
		}

		public void incrementCaps() {

			if (this.caps > 2) this.setCapsToZero();
			else this.caps++;
		}

		public void setCapsToZero() {
			this.caps = 0;
		}
	}
}
