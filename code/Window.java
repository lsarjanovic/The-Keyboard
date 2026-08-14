import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

public class Window extends Application {

	private int width;
	private int height;
	private Background background;
	private MenuPane menuPane;
	private TheKeyboardPane theKeyboardPane;
	private Pane root;

	@Override
	public void start(Stage stage) {

		root = new Pane();
		this.width = 450; // 16:9 aspect ratio sa 720 i 1280
		this.height = 1050;	//21 : 9 aspect ratio sa 540 i 1260
		//4:3 1140 i 855
		Scene scene = new Scene(root);

		root.prefWidthProperty().bind(scene.widthProperty());
		root.prefHeightProperty().bind(scene.heightProperty());

		Image icon1 = new Image(getClass().getResourceAsStream("resources/TheKeyboard.jpg"));
		stage.getIcons().add(icon1);

		stage.setFullScreen(true);
		stage.setTitle("The Keyboard");

		background = new Background(scene);
		root.getChildren().add(background.getRoot());

		menuPane = new MenuPane(this, scene);
		root.getChildren().add(menuPane.getRoot());

		theKeyboardPane = new TheKeyboardPane(this, scene);

			//Prilagodi na obratanje ekrana. Jedan panel od jednom pokušaj pošto može, da se stvar zezne.
			/*scene.setOnKeyPressed(e -> {
				switch (e.getCode()) {
					case ESCAPE:
						changeRotation(stage);
						break;
				}
			});*/
		stage.setScene(scene);
		stage.show();
	}

		/*private void changeRotation(Stage stage) { //For screen rotation.
			changeDimensions();
			background.changeRotation();
			menuPane.changeRotation();
			theKeyboardPane.changeRotation();
			stage.sizeToScene();
			return;
		}

		private void changeDimensions() {
			int temp = this.width;
			this.width= this.height;
			this.height = temp;
			return;
		}*/


	public void changePane(String paneRemove) {
		boolean jeMenuPane;
		if (paneRemove.equals("menuPane")) jeMenuPane = true;
		else jeMenuPane = false;

		if (jeMenuPane) { //Remove menuPane and add theKeyboardPane.
			root.getChildren().remove(menuPane.getRoot());
			root.getChildren().add(theKeyboardPane.getRoot());
		} else { //Remove theKeyboardPane and menuPane.
			root.getChildren().remove(theKeyboardPane.getRoot());
			root.getChildren().add(menuPane.getRoot());
		}
		return;
	}
}
