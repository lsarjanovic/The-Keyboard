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

	private Pane root;
	private RootPaneElements rootPaneElem;

	@Override
	public void start(Stage stage) {

		this.root = new Pane();

		this.width = 450;
		this.height = 1050;

		Scene scene = new Scene(root);

		root.prefWidthProperty().bind(scene.widthProperty());
		root.prefHeightProperty().bind(scene.heightProperty());

		Image icon = getIcon();
		stage.getIcons().add(icon);

		stage.setFullScreen(true);
		stage.setTitle("The Keyboard");


		this.rootPaneElem = new RootPaneElements(this, scene);

		root.getChildren().add(this.rootPaneElem.getBackground().getRoot());
		root.getChildren().add(this.rootPaneElem.getMenuPane().getRoot());

//		keyPressEscape(stage); //Uncomment code to enable screen rotation on ESCAPE
							   //keypress.

		stage.setScene(scene);
		stage.show();
	}

	private Image getIcon() {
		Image icon = new Image(getClass().getResourceAsStream("resources/TheKeyboard.jpg"));

		return icon;
	}

	//Uncomment code to enable screen rotation on ESCAPE keypress.
	/*
	private void keyPressEscape(Stage stage) {
		scene.setOnKeyPressed(e -> {
			if (e.getCode() == ESCAPE) changeRotation(stage);
		});
	}


	private void changeRotation(Stage stage) { //For screen rotation.
		changeDimensions();
		this.rootPaneElem.getBackground().changeRotation();
		this.rootPaneElem.getMenuPane().changeRotation();
		this.rootPaneElem.getTheKeyboardPane().changeRotation();
		stage.sizeToScene();
		return;
	}

	private void changeDimensions() {
		int temp = this.width;
		this.width= this.height;
		this.height = temp;
		return;
	}
	*/

	public void changePane(String paneRemove) {
		boolean jeMenuPane = (paneRemove.equals("menuPane")) ? true : false;
		//boolean jeMenuPane = paneRemove.equals("menuPane"); //Works the same way.

		if (jeMenuPane) { //Remove menuPane and add theKeyboardPane.
			root.getChildren().remove(this.rootPaneElem.getMenuPane().getRoot());
			root.getChildren().add(this.rootPaneElem.getTheKeyboardPane().getRoot());
		} else { //Remove theKeyboardPane and menuPane.
			root.getChildren().remove(this.rootPaneElem.getTheKeyboardPane().getRoot());
			root.getChildren().add(this.rootPaneElem.getMenuPane().getRoot());
		}
		return;
	}


	private static class RootPaneElements {
		private MenuPane menuPane;
		private TheKeyboardPane theKeyboardPane;
		private Background background;

		public RootPaneElements(Window window, Scene scene) {
			this.menuPane = new MenuPane(window, scene);
			this.theKeyboardPane = new TheKeyboardPane(window, scene);
			this.background = new Background(scene);
		}

		private MenuPane getMenuPane() {
			return this.menuPane;
		}

		private TheKeyboardPane getTheKeyboardPane() {
			return this.theKeyboardPane;
		}

		private Background getBackground() {
			return this.background;
		}
	}

}
