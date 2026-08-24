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

public class ModifySettingsPane {

    private int width;
    private int height;

    private Pane root;

    public ModifySettingsPane(Window window, Scene scene) {
        this.width = 100;
        this.height = 100;

        root = new Pane();

        Button exit = new Button("Exit settings");

        prepareExitSettings(exit, window);

        this.root.getChildren().add(exit);

        setRootCharacteristics(exit, window, scene);
    }

    private void setRootCharacteristics(Button exit, Window window, Scene scene) {
        this.root.prefWidthProperty().bind(scene.widthProperty());

        this.root.prefHeightProperty().bind(scene.heightProperty());
		this.root.widthProperty().addListener((width, prevWidth, nextWidth) -> {
			this.width = nextWidth.intValue();
			addButtons(exit, window);

		});		this.root.heightProperty().addListener((height, prevHeight, nextHeight) -> {
			this.height = nextHeight.intValue();
			addButtons(exit, window);
		});
		this.root.setStyle("-fx-background-color: rgba(0, 0, 0, 0)");
    }

    private int getButtonTextFontSize() {
		int fontSize = (this.width > this.height) ?
					(int) (0.038 * this.height) :
					(int) (0.038 * this.width);

		return fontSize;
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

    private void prepareExitSettings(Button exit, Window window) {
        int fontSize = getButtonTextFontSize();
        String textStyling = getModifySettingsStyling(fontSize);

        exit.setStyle(textStyling);

        exit.setLayoutX(0.042 * this.width);
		exit.setLayoutY(0.025 * this.height);
		exit.setMinWidth(0.220 * this.width);
		exit.setMinHeight(0.050 * this.height);

		exit.setOnMouseClicked(e -> window.changePane("menuPane"));
    }

    private void addButtons(Button exit, Window window) {
        this.root.getChildren().clear();

        prepareExitSettings(exit, window);

        this.root.getChildren().add(exit);
    }

    public Pane getRoot() {
        return this.root;
    }

}
