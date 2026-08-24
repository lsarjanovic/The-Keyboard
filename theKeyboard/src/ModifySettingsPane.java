import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;
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

public class ModifySettingsPane {

    private int width;
    private int height;

    private Pane root;
    private ScrollPane scrollPane;

    public ModifySettingsPane(Window window, Scene scene) {
        this.width = 100;
        this.height = 100;

        root = new Pane();

        Button exit = new Button("Exit settings");

        scrollPane = new ScrollPane();
        Text textScrollPane = new Text ("Delete any limited number of words.");

        addButtons(exit, scrollPane, textScrollPane, window);

        setRootCharacteristics(exit, scrollPane, textScrollPane, window, scene);
    }

    private void setRootCharacteristics(Button exit, ScrollPane scrollPane, Text textScrollPane, Window window, Scene scene) {
        this.root.prefWidthProperty().bind(scene.widthProperty());

        this.root.prefHeightProperty().bind(scene.heightProperty());
		this.root.widthProperty().addListener((width, prevWidth, nextWidth) -> {
			this.width = nextWidth.intValue();
			addButtons(exit, scrollPane, textScrollPane, window);

		});		this.root.heightProperty().addListener((height, prevHeight, nextHeight) -> {
			this.height = nextHeight.intValue();
			addButtons(exit, scrollPane, textScrollPane, window);
		});
		this.root.setStyle("-fx-background-color: rgba(0, 0, 0, 0)");
    }

    private Button getWordButton(int xPosition, int yPosition, String word) {
        Button wordButton = new Button(word);

        wordButton.setLayoutX(xPosition);
        wordButton.setLayoutY(yPosition);

        return wordButton;
    }

    private Button getDeleteWordButton(double xPosition, int yPosition, String word, Trie trie) {
        Button deleteWordButton = new Button("-");

        deleteWordButton.setLayoutX(xPosition);
        deleteWordButton.setLayoutY(yPosition);

//        deleteWordButton.setOnMouseClicked(e -> {
//            trie.removeWord(word);
//        });

        return deleteWordButton;
    }

    private void addButtons(Pane wordsPane, ArrayList<String> words, Trie trie) {

        int fontSize = getButtonTextFontSize();
        String textStyling = getButtonStyling(fontSize);

        int xPosition = 0;
        int yPosition = 0;

        double xPositionDelete = xPosition + 0.30 * this.width;

        int size1 = words.size();
        for (int i = 0; i < size1; i++) {
            String word = words.get(i);

            Button wordButton = getWordButton(xPosition,
                                              yPosition,
                                              word);

            Button deleteWordButton =  getDeleteWordButton(xPositionDelete,
                                yPosition,
                                word,
                                trie);


            wordButton.setStyle(textStyling);
            deleteWordButton.setStyle(textStyling);

            yPosition += (0.086 * this.height);

            wordsPane.getChildren().addAll(wordButton, deleteWordButton);
        }
    }

    private Pane getWordsPane(Trie trie) {
        Pane wordsPane = new Pane();

        ArrayList<String> words = trie.getWords();

        Collections.sort(words); //Sort alphabetically.

        addButtons(wordsPane, words, trie);

        return wordsPane;
    }

    private String getScrollPaneStyling() {
        StringBuilder style = new StringBuilder();

        style.append("-fx-background-color: rgba(255, 255, 255, 0.24);");
        style.append("-fx-background: transparent;");
        style.append("-fx-control-inner-background: transparent;");

        return style.toString();
    }

    private ScrollPane getScrollPane() {
        return this.scrollPane;
    }

    public void setDeleteButtons(Trie trie) {
        ScrollPane scrollPane = getScrollPane();

        ArrayList<String> words = trie.getWords();

        Collections.sort(words); //Sort alphabetically.

        Pane wordsPane = (Pane) scrollPane.getContent();
        wordsPane.getChildren().clear();

        addButtons(wordsPane, words, trie);

        scrollPane.setContent(wordsPane);
    }

    private void prepareScrollPane(ScrollPane scrollPane, Window window) {
        Trie trie = window.getTrie();

        Pane wordsPane = getWordsPane(trie);
        wordsPane.setPrefSize(0.30 * this.width, 6.00 * this.height);

        scrollPane.setContent(wordsPane);

        scrollPane.setLayoutX(0.615 * this.width);
        scrollPane.setLayoutY(0.056 * this.height);

        String style = getScrollPaneStyling();
        scrollPane.setStyle(style);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollPane.setPrefSize(0.38 * this.width, 0.55 * this.height);
    }

    private String getTextScrollPaneStyling(int fontSize) {
		StringBuilder textStyling = new StringBuilder();

		textStyling.append("-fx-font-size:");
		textStyling.append(fontSize);
		textStyling.append("px; -fx-fill: black;");
		textStyling.append("-fx-font-weight: bold;");
		textStyling.append("-fx-font-family: Comfortaa;");

		return textStyling.toString();
	}

	private int getFontSize() {
		int fontSize;
		if (this.height > this.width) fontSize = (int) (0.016f * this.height);
		else fontSize = (int) (0.016f * this.width);
		return fontSize;
	}

    private void prepareTextScrollPane(Text textScrollPane) {
        int fontSize = getFontSize();
        String textStyling = getTextScrollPaneStyling(fontSize);

        textScrollPane.setStyle(textStyling);

        textScrollPane.setLayoutX(0.635 * this.width);
        textScrollPane.setLayoutY(0.045 * this.height);
    }

    private int getButtonTextFontSize() {
		int fontSize = (this.width > this.height) ?
					(int) (0.038 * this.height) :
					(int) (0.038 * this.width);

		return fontSize;
	}

    private String getButtonStyling(int fontSize) {
		StringBuilder textStyling = new StringBuilder();

		textStyling.append("-fx-background-color: rgba(255, 255, 255, 1);");
		textStyling.append("-fx-border-color: black;");
		textStyling.append("-fx-border-width: 2px;");
		textStyling.append("-fx-text-fill: black;");
		textStyling.append("-fx-font-family: Comfortaa;");
		textStyling.append("-fx-font-size: ");
		textStyling.append(fontSize);
		textStyling.append("px;");

		return textStyling.toString();
	}

    private void prepareExitSettings(Button exit, Window window) {
        int fontSize = getButtonTextFontSize();
        String textStyling = getButtonStyling(fontSize);

        exit.setStyle(textStyling);

        exit.setLayoutX(0.042 * this.width);
		exit.setLayoutY(0.025 * this.height);
		exit.setMinWidth(0.220 * this.width);
		exit.setMinHeight(0.050 * this.height);

		exit.setOnMouseClicked(e -> window.changePane("menuPane"));
    }

    private void addButtons(Button exit, ScrollPane scrollPane, Text textScrollPane, Window window) {
        this.root.getChildren().clear();

        prepareExitSettings(exit, window);
        prepareScrollPane(scrollPane, window);

        prepareTextScrollPane(textScrollPane);

        this.root.getChildren().addAll(exit, textScrollPane, scrollPane);
    }

    public Pane getRoot() {
        return this.root;
    }

}
