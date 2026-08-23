import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import java.util.*;
import java.io.IOException;
import java.lang.Math;
import javafx.scene.text.Font;
import java.lang.StringBuilder;
import javafx.geometry.Bounds;

public class TheKeyboardPane {
	private int width;
	private int height;

	private Trie trie;
	private Pane root;

	public TheKeyboardPane(Window window, Scene scene) {
		this.width = 100;
		this.height = 100;
		this.root = new Pane();

		TextField textField = getTextField();

		addText(textField);

		SuggestElem suggestElem = getSuggestElem();

		addSuggestButtons(textField, suggestElem);

		Buttons buttons = getButtons(suggestElem);
		trie = new Trie();

		addNavigationButtons(textField, buttons, trie, window);


		addKeyboardButtons(textField, buttons, trie);

		setRootCharacteristics(textField, buttons, scene);
	}

	private Buttons getButtons(SuggestElem suggestElem) {
		ArrayList<Button> navigationButtons = new ArrayList <Button>();
		ArrayList<Button> keyboardButtons = new ArrayList <Button>();

		Buttons buttons = new Buttons(suggestElem,
									  navigationButtons,
									  keyboardButtons);

		return buttons;
	}

	private SuggestElem getSuggestElem() {
		ArrayList <Button> suggestButtons = new ArrayList <Button>();
		ArrayList <String> suggestions = new ArrayList <String>();
		Rectangle suggestBackground = new Rectangle();

		SuggestElem suggestElem = new SuggestElem(suggestButtons, suggestions, suggestBackground);

		return suggestElem;
	}

	private TextField getTextField() {
		Text mainText = new Text("eeeeeeee");
		Rectangle textBackground = new Rectangle();

		byte caps = 0;

		TextField textField = new TextField(mainText, textBackground, caps);

		return textField;
	}

	private void setRootCharacteristics(TextField textField,
										Buttons buttons,
										Scene scene) {

		this.root.prefWidthProperty().bind(scene.widthProperty());
		this.root.prefHeightProperty().bind(scene.heightProperty());
		this.root.widthProperty().addListener((widthProperty, prevWidth, nextWidth) -> {
			this.width = nextWidth.intValue();
			refreshAllButtons(textField, buttons);
		});
		this.root.heightProperty().addListener((heightProperty, prevHeight, nextHeight) -> {
			this.height = nextHeight.intValue();
			refreshAllButtons(textField, buttons);
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

		for (int i = 0; i < 3; i++) {

			Button suggest = suggestButtons.get(i);
			suggest.setOnMouseClicked(e -> {

				if (!suggest.getText().isEmpty())
						mainText.setText(
									useSuggestion(mainText, suggest)
								);

				setSuggestionsToEmpty(suggestButtons);
				refreshText(textField);
			});
		}
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

	private char [ ] getZnaki() {
		char [ ] znaki = {'a', 'b', 'c', 'd', 'e', ',', 'u', 'v', //The buttons in order
								   'f', 'g', 'h', 'i', 'j', '!', 'w', 'x',
								   'k', 'l', 'm', 'n', 'o', '?', 'y', 'z',
								   'p', 'q', 'r', 's', 't', '.', 	//End of buttons in order
								   ' ', 'A', 'B', 'C', 'D', 'E', 'U', 'V', //The beggining of additional characters.
									'F', 'G', 'H', 'I', 'J', 'W', 'X',
									'K', 'L', 'M', 'N', 'O', 'Y', 'Z',
									'P', 'Q', 'R', 'S', 'T'}; //The end of additional characters.

		return znaki;
	}

	private int [ ] findChars(Text mainText) {
		char [ ] znaki = getZnaki();

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

	private void setNavigationButtonsFunctions(TextField textField,
											   Buttons buttons,
											   Trie trie,
											   Window window
											   ) {


		ArrayList<Button> suggestButtons = buttons.getSuggestElem().getSuggestButtons();
		ArrayList<String> suggestions = buttons.getSuggestElem().getSuggestions();
		Text mainText = textField.getMainText();

		ArrayList<Button> navigationButtons = buttons.getNavigationButtons();

		Button home = navigationButtons.get(0);
		home.setOnMouseClicked(e -> {
			window.changePane("TheKeyboardPane");
			mainText.setText("");
			setSuggestionsToEmpty(suggestButtons); //Set all displayed suggestions to empty.
		});

		Button discard = navigationButtons.get(1);
		discard.setOnMouseClicked(e -> {
			mainText.setText("");
			setSuggestionsToEmpty(suggestButtons); //Set all displayed suggestions to empty.
		});
		
		Button enter = navigationButtons.get(2);
		enter.setOnMouseClicked(e -> {
			String sentence = mainText.getText();
			if(!sentence.isEmpty()) {
				trie.addSentence(sentence);
				mainText.setText("");
				setSuggestionsToEmpty(suggestButtons); //Set all displayed suggestions to empty.
			}
		});

		Button delete = navigationButtons.get(3);
		delete.setOnMouseClicked(e -> {
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
		
		Button space = navigationButtons.get(4);
		space.setOnMouseClicked(e -> {
			mainText.setText(mainText.getText() + " ");
			refreshText(textField);
			setSuggestionsToEmpty(suggestButtons);
		});

		ArrayList<Button> keyboardButtons = buttons.getKeyboardButtons();
		Button shift = navigationButtons.get(5);
		shift.setOnMouseClicked(e -> shift(textField, keyboardButtons));
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

	private void setNavigationButtonsStyle(ArrayList<Button> navigationButtons) {
		//Set styling of text to all otherButtons.
		String textStyling = getButtonTextStyling();

		navigationButtons.get(0).setStyle(textStyling);
		navigationButtons.get(1).setStyle(textStyling);
		navigationButtons.get(2).setStyle(textStyling);
		navigationButtons.get(3).setStyle(textStyling);
		navigationButtons.get(4).setStyle(textStyling);
		navigationButtons.get(5).setStyle(textStyling);
	}

	private void setHomeButtonPosition(Button home) {
		home.setLayoutX(0.042 * this.width);
		home.setLayoutY(0.025 * this.height);
		home.setPrefWidth(0.325 * this.width);
		home.setPrefHeight(0.100 * this.height);
		home.setMinWidth(0.250* this.width);
		home.setMinHeight(0.050 * this.height);
	}

	private void setDiscardButtonPosition(Button discard) {
		discard.setLayoutX(0.610 * this.width);
		discard.setLayoutY(0.025 * this.height);
		discard.setPrefWidth(0.167 * this.width);
		discard.setPrefHeight(0.100 * this.height);
		discard.setMinWidth(0.167 * this.width);
		discard.setMinHeight(0.100 * this.height);
	}

	private void setEnterButtonPosition(Button enter) {
		enter.setLayoutX(0.790 * this.width);
		enter.setLayoutY(0.025 * this.height); //0.370 * this.heigth
		enter.setPrefWidth(0.167 * this.width);
		enter.setPrefHeight(0.100 * this.height);
		enter.setMinWidth(0.167 * this.width);
		enter.setMinHeight(0.100 * this.height);
	}

	private void setDeleteButtonPosition(Button delete) {
		delete.setLayoutX(0.042 * this.width);
		delete.setLayoutY(0.140 * this.height); //0.370 * this.heigth
		delete.setPrefWidth(0.075 * this.width);
		delete.setPrefHeight(0.325 * this.height);
		delete.setMinWidth(0.075 * this.width);
		delete.setMinHeight(0.270 * this.height);
	}

	private void setSpaceButtonPosition(Button space) {
		space.setLayoutX(0.042 * this.width);
		space.setLayoutY(0.480 * this.height);
		space.setPrefWidth(0.167 * this.width);
		space.setPrefHeight(0.100 * this.height);
		space.setMinWidth(0.167 * this.width);
		space.setMinHeight(0.100 * this.height);
	}

	private void setShiftButtonPosition(Button shift) {
		shift.setLayoutX(0.742 * this.width);
		shift.setLayoutY(0.889 * this.height);
		shift.setPrefWidth(0.217 * this.width);
		shift.setPrefHeight(0.090 * this.height);
		shift.setMinWidth(0.217 * this.width);
		shift.setMinHeight(0.090 * this.height);
	}

	private void setNavigationButtonsPosition(ArrayList<Button> navigationButtons) {
		Button home = navigationButtons.get(0);
		setHomeButtonPosition(home);

		Button discard = navigationButtons.get(1);
		setDiscardButtonPosition(discard);

		Button enter = navigationButtons.get(2);
		setEnterButtonPosition(enter);

		Button delete = navigationButtons.get(3);
		setDeleteButtonPosition(delete);

		Button space = navigationButtons.get(4);
		setSpaceButtonPosition(space);

		Button shift = navigationButtons.get(5);
		setShiftButtonPosition(shift);
		return;
	}

	private void addNavigationButtons(TextField textField,
									  Buttons buttons,
									  Trie trie,
									  Window window
									  ) {

		ArrayList<Button> navigationButtons = buttons.getNavigationButtons();

		Button home = new Button("-Home");
		navigationButtons.add(home);

		Button discard = new Button("-Discard-");
		navigationButtons.add(discard);

		Button enter = new Button("-Enter-");
		navigationButtons.add(enter);

		Button delete = new Button("--D");
		navigationButtons.add(delete);

		Button space = new Button("______");
		navigationButtons.add(space);

		Button shift = new Button("-->");
		navigationButtons.add(shift);

		setNavigationButtonsPosition(navigationButtons);
		setNavigationButtonsStyle(navigationButtons);

		setNavigationButtonsFunctions(textField,
									  buttons,
									  trie,
									  window);

		for (Button button : navigationButtons) root.getChildren().add(button);
	}

	//Add all butttons below suggestion buttons.
	private void addKeyboardButtons(TextField textField,
									Buttons buttons,
									Trie trie
									) {

		int wButton = (int) (0.042 * this.width); //Width position button.
		int hButton = (int) (0.590 * this.height); //Height position button.

		String textStyling = getButtonTextStyling();

		char [ ] znaki = {'a', 'b', 'c', 'd', 'e', ',', 'u', 'v', 'f', 'g', 'h', 'i', 'j', '!', 'w',
			'x', 'k', 'l', 'm', 'n', 'o', '?', 'y', 'z', 'p', 'q', 'r', 's', 't', '.'};

		Text mainText = textField.getMainText();
		ArrayList<Button> suggestButtons = buttons.getSuggestElem().getSuggestButtons();
		ArrayList<String> suggestions = buttons.getSuggestElem().getSuggestions();
		ArrayList<Button> keyboardButtons = buttons.getKeyboardButtons();

		//Add keyboard buttons.
		for (int i = 0; i < znaki.length; i++) {

			String znak = String.valueOf(znaki[i]);

			Button button = new Button(znak);
			//Set keyboardButton static properties.
			setKeyboardButtonPosition(button, wButton, hButton);
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

				byte caps = textField.getCaps();
				if (caps == 1) noCaps(textField, keyboardButtons);
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

	private void setKeyboardButtonPosition(Button button, int wButton, int hButton) {
		button.setLayoutX(wButton);
		button.setLayoutY(hButton);
		button.setPrefWidth((int) (0.10 * this.width)); //0.10 * this.width
		button.setPrefHeight((int) (0.09 * this.height)); //0.09 * this.height
		button.setMinWidth((int) (0.10 * this.width)); //0.10 * this.width
		button.setMinHeight((int) (0.09 * this.height)); //0.09 * this.height
	}


	private void setSuggestButtonsPosition(ArrayList<Button> suggestButtons) {

		Button suggest1 = suggestButtons.get(0);
		Button suggest2 = suggestButtons.get(1);
		Button suggest3 = suggestButtons.get(2);

		if (this.width > this.height) {

		if (!root.getChildren().contains(suggest1))
				root.getChildren().add(suggest1);

			suggest1.setLayoutX(0.227 * this.width);
			suggest2.setLayoutX(0.397 * this.width);
			suggest3.setLayoutX(0.679 * this.width);

			suggest1.setLayoutY(0.491 * this.height);
			suggest2.setLayoutY(0.491 * this.height);
			suggest3.setLayoutY(0.491 * this.height);

			suggest1.setPrefWidth(0.161 * this.width);
			suggest2.setPrefWidth(0.274 * this.width);
			suggest3.setPrefWidth(0.274 * this.width);

			suggest1.setPrefHeight(0.083 * this.height);
			suggest2.setPrefHeight(0.083 * this.height);
			suggest3.setPrefHeight(0.083 * this.height);

			suggest1.setMinWidth(0.161 * this.width);
			suggest2.setMinWidth(0.274 * this.width);
			suggest3.setMinWidth(0.274 * this.width);

			suggest1.setMinHeight(0.083 * this.height);
			suggest2.setMinHeight(0.083 * this.height);
			suggest3.setMinHeight(0.083 * this.height);
		} else {

			if (root.getChildren().contains(suggest1))
				root.getChildren().remove(suggest1);


			suggest2.setLayoutX(0.250 * this.width);
			suggest3.setLayoutX(0.618 * this.width);
			suggest2.setLayoutY(0.487 * this.height);
			suggest3.setLayoutY(0.487 * this.height);

			suggest2.setPrefWidth(0.314 * this.width);
			suggest3.setPrefWidth(0.314 * this.width);
			suggest2.setPrefHeight(0.079 * this.height);
			suggest3.setPrefHeight(0.079 * this.height);

			suggest2.setMinWidth(0.314 * this.width);
			suggest3.setMinWidth(0.314 * this.width);
			suggest2.setMinHeight(0.079 * this.height);
			suggest3.setMinHeight(0.079 * this.height);
		}
	}

	private void refreshKeyboardButtons(ArrayList<Button> keyboardButtons) {
		int wButton = (int) (0.042 * this.width); //Width position button.
		int hButton = (int) (0.590 * this.height); //Height position button.

		String textStyling = getButtonTextStyling();

		for (Button button : keyboardButtons) {
			setKeyboardButtonPosition(button, wButton, hButton);
			button.setStyle(textStyling);

			wButton += (int) (0.117 * this.width);
			if (wButton > this.width - 0.10 * this.width) {
				hButton += (int) (0.10 * this.height); //0.10 * this.height
				wButton = (int) (0.042 * this.width); //0.033 * this.width
			}
		}
	}

	private void refreshAllButtons(TextField textField,
								   Buttons buttons) {

		ArrayList<Button> navigationButtons = buttons.getNavigationButtons();
		setNavigationButtonsPosition(navigationButtons);
		setNavigationButtonsStyle(navigationButtons);

		Rectangle suggestBackground = buttons.getSuggestElem().getSuggestBackground();
		setSuggestButtonsBackgroundPosition(suggestBackground);

		ArrayList<Button> suggestButtons = buttons.getSuggestElem().getSuggestButtons();
		setSuggestButtonsStyle(suggestButtons);
		setSuggestButtonsPosition(suggestButtons);

		ArrayList<Button> keyboardButtons = buttons.getKeyboardButtons();
		refreshKeyboardButtons(keyboardButtons);

		Rectangle textBackground = textField.getTextBackground();
		setMainTextBackgroundPosition(textBackground);

		refreshText(textField);
	}


	private String useSuggestion(Text mainText, Button suggest) {
		String sentence = mainText.getText();

		String [ ] words = sentence.split("\\s+");

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < words.length - 1; i++) {
			sb.append(words[i]);
			sb.append(" ");
		}

		sb.append(suggest.getText());


		return sb.toString();
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
	private void allCaps(ArrayList<Button> keyboardButtons) {
		char znak;
		for (Button button : keyboardButtons) {
			znak = button.getText().charAt(0);
			if (znak >= 'a'
				&& znak <= 'z'
				&& znak != '-'
				&& znak != '_') {
				znak += 'A' - 'a';
				button.setText(String.valueOf(znak));
			}
		}
		return;
	}

	//Method for capsLock and shift via the same button, the button shift.
	private void shift(TextField textField, ArrayList<Button> keyboardButtons) {
		textField.incrementCaps();

		byte caps = textField.getCaps();

		if (caps == 0) noCaps(textField, keyboardButtons);
		else if (caps == 1) allCaps(keyboardButtons);

		return;
	}

	public void loadWords() {
		try {
			this.trie.loadWords();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void saveWords() {
		this.trie.saveWords();
	}


	//Submethod of shift(). Makes all letter lowerCase.
	private void noCaps(TextField textField, ArrayList<Button> keyboardButtons) {
		char znak;
		for (Button button : keyboardButtons) {
			znak = button.getText().charAt(0);
			if (znak >= 'A'
				&& znak <= 'Z'
				&& znak != '-'
				&& znak != '_') {
				znak -= 'A' - 'a';
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

	public static class Buttons {
		private SuggestElem suggestElem;
		private ArrayList<Button> navigationButtons;
		private ArrayList<Button> keyboardButtons;

		public Buttons(SuggestElem suggestElem,
					   ArrayList<Button> navigationButtons,
					   ArrayList<Button> keyboardButtons) {

			this.suggestElem = suggestElem;
			this.navigationButtons = navigationButtons;
			this.keyboardButtons = keyboardButtons;
		}

		public SuggestElem getSuggestElem() {
			return this.suggestElem;
		}

		public ArrayList<Button> getNavigationButtons() {
			return this.navigationButtons;
		}

		public ArrayList<Button> getKeyboardButtons() {
			return this.keyboardButtons;
		}
	}

	private static class SuggestElem {
		private ArrayList<Button> suggestButtons;
		private ArrayList<String> suggestions;
		private Rectangle suggestBackground;

		public SuggestElem(ArrayList<Button> suggestButtons,
						   ArrayList<String> suggestions,
						   Rectangle suggestBackground) {

			this.suggestButtons = suggestButtons;
			this.suggestions = suggestions;
			this.suggestBackground = suggestBackground;
		}

		public ArrayList<Button> getSuggestButtons() {
			return this.suggestButtons;
		}

		public ArrayList<String> getSuggestions() {
			return this.suggestions;
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
			this.caps++;

			if (this.caps > 2) this.setCapsToZero();
		}

		public void setCapsToZero() {
			this.caps = 0;
		}
	}
}
