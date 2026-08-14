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
	private Window window;

	private List <Text> texts = new ArrayList <Text>();

	private Text text;
	//The main text.

	private Rectangle textBackground;
	//The main text background.

	private List <Button> keyboardButtons = new ArrayList <Button>();
	//Of method addKeyboardButtons().

	private List <Button> otherButtons = new ArrayList <Button>();
	//.get(0) = home, .get(1) = discard, .get(2) = enter, .get(3) = delete,
	//.get(4) = space, .get(5) = shift. Of method addOtherButtons().

	private List <Button> suggestButtons = new ArrayList <Button>();
	//Of method addSuggestionButtons().

	private List <String> suggestions = new ArrayList <String>();
	//Communicates with Trie.java file to get suggestions.

	private Rectangle MFWBackground;
	//Background of displayed suggestions.

	private Pane root;
	private Trie trie;
	private byte caps;
	private Scene scene;

	public TheKeyboardPane(Window window, Scene scene) {
		this.width = 100;
		this.height = 100;
		this.window = window;
		this.scene = scene;

		root = new Pane();
		root.prefWidthProperty().bind(this.scene.widthProperty());
		root.prefHeightProperty().bind(this.scene.heightProperty());
		root.widthProperty().addListener((obs, oldVal, newVal) -> {
			this.width = newVal.intValue();
			refreshAllButtons();
		});
		root.heightProperty().addListener((obs, oldVal, newVal) -> {
			this.height = newVal.intValue();
			refreshAllButtons();
		});

		root.setStyle("-fx-background-color: rgba(0, 0, 0, 0)");
		addText();
		addButtons();
		trie = new Trie();
	}

	//Adds the whole suggestions box and ads all buttons.
	private void addSuggestionButtons() {
		createSuggestButtons();
		setSuggestionButtonsBackground();
		setSuggestionButtons();
		for (Button button : suggestButtons) root.getChildren().add(button);
	}

	//Sets properties of suggestion buttons background.
	private void setSuggestionButtonsBackground() {
		MFWBackground = new Rectangle();
				//most frequent words background.
		setSuggestionButtonsBackgroundStatic();
		MFWBackground.setFill(Color.rgb(255, 255, 255, 0.5));
		MFWBackground.setStroke(Color.BLACK);

		root.getChildren().add(MFWBackground);
	}

	//Sets position of suggestion buttons background.
	private void setSuggestionButtonsBackgroundStatic() {
		MFWBackground.setLayoutX(0.223 * this.width);
		MFWBackground.setLayoutY(0.480 * this.height);
		MFWBackground.setWidth(0.734 * this.width);
		MFWBackground.setHeight(0.100 * this.height);
	}

	private void createSuggestButtons() {
		Button suggest1 = new Button("suggest1");
		Button suggest2 = new Button("suggest2");
		Button suggest3 = new Button("suggest3");

		suggestButtons.add(suggest1);
		suggestButtons.add(suggest2);
		suggestButtons.add(suggest3);
	}

	private void setSuggestionButtons() {
		setSuggestButtonsFunctions();
		setSuggestButtonsStyle();

		//Set button static properties.
		setSuggestButtonsStatic();
		return;
	}

	private void setSuggestButtonsFunctions() {
		//Set button dynamic properties.
		suggestButtons.get(0).setOnMouseClicked(e -> {

			//If suggest1 is not empty, complete the word to suggest1.
			if (!suggestButtons.get(0).getText().isEmpty()) 
					text.setText(useSuggestion(suggestButtons.get(0))); 

			setSuggestionsToEmpty(); //Set suggestions to empty.
			refreshText(); //Refresh text size.
		});
		suggestButtons.get(1).setOnMouseClicked(e -> {

			//If suggest2 is not empty, complete the word to suggest2
			if (!suggestButtons.get(1).getText().isEmpty()) 
				text.setText(useSuggestion(suggestButtons.get(1)));

			setSuggestionsToEmpty(); //Set suggestions to empty.
			refreshText(); //Refresh text size.
		});
		suggestButtons.get(2).setOnMouseClicked(e -> {

			//If suggest3 is not empty, complete the word to suggest3.
			if (!suggestButtons.get(2).getText().isEmpty()) 
				text.setText(useSuggestion(suggestButtons.get(2)));

			setSuggestionsToEmpty(); //Set suggestions to empty.
			refreshText(); //Refresh text size.
		});
	}

	private void setSuggestButtonsStyle() {
		int fontSize = getFontSize();
		String textStyling = "-fx-background-color: rgba(255, 255, 255, 0);"
				+ "-fx-border-color: black;"
				+ "-fx-border-width: 2px;"
				+ "-fx-text-fill: black;"
				+ "-fx-font-family: Comfortaa;"
				+ "-fx-font-size: " + fontSize + "px;";

		//Set style to buttons.
		suggestButtons.get(0).setStyle(textStyling); //suggestButtons.get(0) = suggest1
		suggestButtons.get(1).setStyle(textStyling); //suggestButtons.get(1) = suggest2
		suggestButtons.get(2).setStyle(textStyling);	//suggestButtons.get(2) = suggest3
	}

	private void addText() {
		addMainTextBackground();
		addMainText();
	}

	private void addMainText() {
		text = new Text ("eeeeeeeeee");
//								+ "                                                                     f");
//								+ ",,,,,,,,,,,,");
		refreshText();
		root.getChildren().add(text);
		return;
	}

	private void refreshText() {
		int textLength = Math.max(1, text.getText().length());
		int fontSize = (int) (0.050 * this.height - textLength / 5);
		if (fontSize < 30) fontSize = 30;

		String textStyling = "-fx-background-color: rgba(255, 255, 255, 0);"
				+ "-fx-border-color: black;"
				+ "-fx-border-width: 2px;"
				+ "-fx-text-fill: black;"
				+ "-fx-font-family: Comfortaa;"
				+ "-fx-font-size: " + fontSize + "px;";
		text.setStyle(textStyling);

		Bounds textBounds = text.getBoundsInLocal(); 

		double centerX = textBackground.getLayoutX() + textBackground.getWidth() / 2;
		double centerY = textBackground.getLayoutY() + textBackground.getHeight() / 2;
		
		text.setLayoutX(centerX - textBounds.getWidth() / 2);
		text.setLayoutY(centerY + textBounds.getHeight() / 4); // adjust for font baseline

		int [ ] occurences = findChars();
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

		double textLayoutX = 0.500 * this.width;
		for (int i = 0; i < occurences.length; i++) 
			if (occurences[i] > 0) textLayoutX -= occurences[i] * (fontSize / ratios[i]);

		text.setLayoutX(textLayoutX);
		text.setLayoutY(0.340 * this.height);

//		if (textOutOfBounds()) newText();

		/*
			When bounds of Text text reaches bounds of Rectangle textBackground,
			insert newline('\n') character in the back of the second last word.
				Hello, I am Luka. <-- . reaches Bounds of textBackground
										, so the text changes to Hello, I am\n Luka and
										is split into:
													Hello, I am\n 
										and 
												Luka 
										which becomes a part of a new Text. The old text goes to List<Text>
										and the global Text text changes to a new Text.
			When the text reaches lower bounds of textBackground, the last Text (the oldest)
			is removed from textBackground. All texts are included in a container with a 
				scrollwheel which stores the full converstation (the user can click on it while typing).
			If the KeyboardPane is exited or the window is closed, the conversation is stored into
			the library where it can be deleted if one chooses to. The library will be added later
			when the main functionalities are finished.
		*/
	}

	private boolean textOutOfBounds() {
		Bounds textBounds = text.getBoundsInLocal(); //Nešto ne radi kako treba. Pogledaj getBoundsInParent() kako radi.
		Bounds backgroundBounds = textBackground.getBoundsInLocal();

		if ((textBounds.getMinX() < backgroundBounds.getMinX()) 
				|| (textBounds.getMaxX() > backgroundBounds.getMaxX())) return false;

		return true;
	}

	private void newText() {
		//Remove the last word from the old text you are going to save.
		String [ ] words = text.getText().split("\\s+"); //For later use

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < words.length - 1; i++) {
			sb.append(words[i]);
			sb.append(" ");
		}

		text.setText(sb.toString());

		//Save the old text.
		Text text1 = text;
		texts.add(text1);

		//Get the new text ready.
		String lastWord = words[words.length - 1];
		text = new Text(lastWord);
	}


	private int [ ] findChars() {
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
			for (char c : text.getText().toCharArray())
				if (c == znaki[i]) occurences[i]++;

		return occurences;
	}


	private void addMainTextBackground() {
		textBackground = new Rectangle();
		
		setMainTextBackgroundStatic();

		textBackground.setFill(Color.rgb(255, 255, 255, 0.5));
		textBackground.setStroke(Color.BLACK);

		root.getChildren().add(textBackground);
	}

	//Sets position of main text background.
	private void setMainTextBackgroundStatic() {
		textBackground.setLayoutX(0.130 * this.width);
		textBackground.setLayoutY(0.141 * this.height);
		textBackground.setWidth(0.827 * this.width);
		textBackground.setHeight(0.322 * this.height);
	}

	private void addButtons() {
		addSuggestionButtons();
		addOtherButtons();
		addKeyboardButtons();
		return;
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

	private void setAllOtherButtonsFunctionsAndStyle() {
		setAllOtherButtonsFunctions();
		setAllOtherButtonsStyle();
	}

	private void setAllOtherButtonsFunctions() {
		//Set home button function.
		otherButtons.get(0).setOnMouseClicked(e -> {
			this.window.changePane("TheKeyboardPane");
			text.setText(""); 				//Set main text to empty.
			setSuggestionsToEmpty(); //Set all displayed suggestions to empty.
		});

		//Set discard button function.
		otherButtons.get(1).setOnMouseClicked(e -> {
			text.setText(""); 				//Set main text to empty.
			setSuggestionsToEmpty(); //Set all displayed suggestions to empty.
		});
		
		//Set enter button function.
		otherButtons.get(2).setOnMouseClicked(e -> {
			String sentence = text.getText();
			if(!sentence.isEmpty()) {
				trie.insertSentence(sentence);
				text.setText(""); 				//Set main text to empty.
				setSuggestionsToEmpty(); //Set all displayed suggestions to empty.
			}
		});

		//Set delete button function.
		otherButtons.get(3).setOnMouseClicked(e -> {
			String sentence = text.getText(); //Get sentence in main text.
			if (!sentence.isEmpty()) {
				text.setText(deleteChar()); //If the sentence has atleast 1 letter, 
								//delete last character from main text and set new text.
				refreshSuggestions(); 		//Refreshes suggestions.
			} 
			sentence = text.getText(); 	//Is needed because one character might
								//have been deleted in between.
			if(sentence.isEmpty()) setSuggestionsToEmpty(); //If the sentence is empty
								//don't set any suggestions.
		});
		
		otherButtons.get(4).setOnMouseClicked(e -> {
			text.setText(text.getText() + " "); //Dodaj črko v poved.
			refreshText(); //Refresh text size.
			setSuggestionsToEmpty(); //Set all displayed suggestions to empty.
		});

		otherButtons.get(5).setOnMouseClicked(e -> shift());
	}

	private void setAllOtherButtonsStyle() {
		//Set styling of text to all otherButtons.
		String textStyling = getTextStyling();

		otherButtons.get(0).setStyle(textStyling);
		otherButtons.get(1).setStyle(textStyling);
		otherButtons.get(2).setStyle(textStyling);
		otherButtons.get(3).setStyle(textStyling);
		otherButtons.get(4).setStyle(textStyling);
		otherButtons.get(5).setStyle(textStyling);
	}

	private void refreshAllButtons() {
		refreshOtherButtons();
		setSuggestionButtonsBackgroundStatic();
		refreshSuggestButtons();
		refreshKeyboardButtons();
		setMainTextBackgroundStatic();
		refreshText();
	}

	private void refreshSuggestButtons() {
		setSuggestButtonsStyle();
		setSuggestButtonsStatic();
	}
	private void refreshKeyboardButtons() {
		int wButton = (int) (0.042 * this.width); //Width position button.
		int hButton = (int) (0.590 * this.height); //Height position button.

		String textStyling = getTextStyling();

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
	private void addOtherButtons() {
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

		//Set text of enter, discard, home button and add them to Pane.
//		setHDEButtonsText(home, discard, enter);

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

		setAllOtherButtonsFunctionsAndStyle();

		//Add all buttons to theKeyboardPane.
		for (Button button : otherButtons) root.getChildren().add(button);
		return;
	}

	//Add all butttons below suggestion buttons.
	private void addKeyboardButtons() {
		int wButton = (int) (0.042 * this.width); //Width position button.
		int hButton = (int) (0.590 * this.height); //Height position button.

		String textStyling = getTextStyling();

		char [ ] znaki = {'a', 'b', 'c', 'd', 'e', ',', 'u', 'v', 'f', 'g', 'h', 'i', 'j', '!', 'w',
			'x', 'k', 'l', 'm', 'n', 'o', '?', 'y', 'z', 'p', 'q', 'r', 's', 't', '.'};

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
				String newText = text.getText() + clickedButton.getText();

				//Get suggestions and set suggestions for new text and set new text.
				suggestions = trie.getSuggestions(newText);
				refreshSuggestions();	//Predlagaj dopolnjene besede.
//				System.out.println(suggestions.size());
				text.setText(newText); //Add the character to the current words.

				refreshText();

				//If shift clicked only once, remove caps.
				if (caps == 1) noCaps();
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

	//Set suggest buttons static properties.
	private void setSuggestButtonsStatic() {
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

	//Beggining of functional methods.

	private String useSuggestion(Button suggest) {
		String [ ] sentenceWords = text.getText().split("\\s+"); //Split the current sentence into words.
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
	private void refreshSuggestions() {
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
	private void setSuggestionsToEmpty() {
		suggestButtons.get(0).setText(""); //suggestButtons.get(0) = suggest1
		suggestButtons.get(1).setText(""); //suggestButtons.get(1) = suggest2
		suggestButtons.get(2).setText(""); //suggestButtons.get(2) = suggest3
	}

	//Removes last character from text.
	private String deleteChar() {
		StringBuilder sb = new StringBuilder();
		sb.append(text.getText());
		sb.deleteCharAt(sb.toString().length() - 1);
		return sb.toString();
	}

	//Method for capsLock and shift via the same button, the button shift.
	//Activates only when shift is clicked.
	private void shift() {
		if (caps == 0) {
			allCaps();
			caps++;
		} else if (caps == 1) {
			caps++;
		} else if (caps == 2) {
			caps = 0;
			noCaps();
		}
		return;
	}

	//Submethod of shift(). Makes all letters upperCase.
	private void allCaps() {
		char znak;
		for (Button button : keyboardButtons) {
			znak = button.getText().charAt(0);
			if (znak >= 'a' && znak <= 'z' && znak != '-'
					&& znak != '_') {
				znak -= 32;		
				button.setText(String.valueOf(znak));
			}
		}
		return;
	}

	//Submethod of shift(). Makes all letter lowerCase.
	private void noCaps() {
		char znak;
		for (Button button : keyboardButtons) {
			znak = button.getText().charAt(0);
			if (znak >= 'A' && znak <= 'Z' && znak != '-'
				&& znak != '_') {
				znak += 32;
				button.setText(String.valueOf(znak));
			}
		}
		caps = 0;
		return;
	}

	private int getFontSize() {
		int fontSize;
		if (this.width > this.height) fontSize = (int) (0.038 * this.height);
		else fontSize = (int) (0.038 * this.width);
		return fontSize;
	}

	private String getTextStyling() {
		int fontSize = getFontSize();
		String textStyling = "-fx-background-color: rgba(255, 255, 255, 0.5);"
				+ "-fx-border-color: black;"
				+ "-fx-border-width: 2px;"
				+ "-fx-text-fill: black;"
				+ "-fx-font-family: Comfortaa;"
				+ "-fx-font-size: " + fontSize + "px;";
		return textStyling;
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
		return root;
	}
}
