import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

//ALICEBLUE & A SORT OF BLUE(Color.rgb(71, 69, 138))
//SNOW & DARKRED
//default: AZURE & BROWN

public class Background {
	private Scene scene;
	private int width;
	private int height;
	private Canvas canvas;
	private Pane root;
									//(600, 1000);
	public Background (Scene scene) {
		this.scene = scene;

		//Get the canvas ready.
		canvas = new Canvas();
		canvas.widthProperty().bind(this.scene.widthProperty());
        canvas.heightProperty().bind(this.scene.heightProperty());
		canvas.widthProperty().addListener((obs, oldVal, newVal) -> {
					this.width = (int) canvas.getWidth();
					drawBackground();
					});
        canvas.heightProperty().addListener((obs, oldVal, newVal) -> {
					this.height = (int) canvas.getHeight();
					drawBackground();
					});
//		this.width = (int) canvas.getWidth();
//		this.height = (int) canvas.getHeight();
		//Draw the curves.
		drawBackground();

		root = new Pane(canvas);
	}

	private void drawBackground() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		//Draw the background.
		gc.setFill(Color.AZURE);
		gc.fillRect(0, 0, this.width, this.height);

		//Set up the colour for the curves.
		gc.setFill(Color.BROWN);

		theTopSmallCurve(gc);
		theButtonCurve1(gc);
		theButtonCurve2(gc);
		theGreatCurve(gc);
		theBottomCurve(gc);
		oneBottomCurve(gc);
		theBottomSmallCurve(gc);
	}

	//The Curves metode so v kodi zapisane, tako kot so narisane od zgoraj navzdol.

	private void theTopSmallCurve(GraphicsContext gc) {
		gc.beginPath();
		gc.moveTo(0, 0.09 * this.height);
		gc.bezierCurveTo(0.216 * this.width, 0.10 * this.height, 0.266 * this.width, 0.02 * this.height, 0.503 * this.width, 0);
		gc.quadraticCurveTo(0.067 * this.width, 0.020 * this.height, 0, 0.244 * this.height);
		gc.lineTo(0, 0.09 * this.height);
		gc.closePath();
		gc.fill();
//		gc.stroke();
		return;
	}

	private void theButtonCurve1(GraphicsContext gc) { //Spoji dve cubic curves.
		gc.beginPath();
		gc.moveTo(0, 0.340 * this.height);
		gc.bezierCurveTo(0.233 * this.width, 0.250 * this.height, 0.233 * this.width, 0, 0.55 * this.width, 0.150 * this.height);
		gc.bezierCurveTo(0.167 * this.width, 0.240 * this.height, 0.50 * this.width, 0.600 * this.height, 0, 0.340 * this.height);
		gc.closePath();
		gc.fill();
//		gc.stroke();
		return;
	}

	private void theButtonCurve2(GraphicsContext gc) { //Spoji dve cubic curves.
		gc.beginPath();
		gc.moveTo(0.55 * this.width, 0.150 * this.height);
		gc.bezierCurveTo(0.666 * this.width, 0.130 * this.height, 0.733 * this.width, 0.030 * this.height, 0.85 * this.width, 0);
		gc.bezierCurveTo(0.617 * this.width, 0.010 * this.height, 0.667 * this.width, 0.260 * this.height, 0.55 * this.width, 0.150 * this.height);
		gc.closePath();
		gc.fill();
//		gc.stroke();
		return;
	}

	private void theGreatCurve(GraphicsContext gc) {
		gc.beginPath();
		gc.moveTo(0, 0.550 * this.height);
		gc.bezierCurveTo(0.333 * this.width, 0.4 * this.height, 0.5 * this.width, 0.71 * this.height, this.width, 0);
		gc.lineTo(this.width, 0);
		gc.bezierCurveTo(0.333 * this.width, 0.4 * this.height, 0.5 * this.width, 0.71 * this.height, 0, 0.55 * this.height);
		gc.lineTo(0, 0.550 * this.height);
		gc.closePath();
		gc.fill();
//		gc.stroke() //Outline.
		return;
	}
	
	private void theBottomCurve(GraphicsContext gc) {
		gc.beginPath();
		gc.moveTo(0, 0.980 * this.height);
		gc.bezierCurveTo(0.35 * this.width, 0.7 * this.height, 0.533 * this.width, 0.920 * this.height, this.width, 0.240 * this.height);
		gc.bezierCurveTo(0.417 * this.width, 0.7 * this.height, 0.51 * this.width, 0.920 * this.height, 0, 0.980 * this.height);
		gc.closePath();
		gc.fill();
//		gc.stroke() //Outline.
		return;
	}

	private void oneBottomCurve(GraphicsContext gc) {
		gc.beginPath();
		gc.moveTo(0.167 * this.width, this.height);
		gc.quadraticCurveTo(0.917 * this.width, 0.950 * this.height, this.width, 0.400 * this.height);
		gc.bezierCurveTo(0.583 * this.width, 0.980 * this.height, 0.75 * this.width, 0.950 * this.height, 0.167 * this.width, this.height);
		gc.closePath();
		gc.fill();
//		gc.stroke() //Outline.
		return;
	}

	private void theBottomSmallCurve(GraphicsContext gc) {
		gc.beginPath();
		gc.moveTo(this.width, 0.910 * this.height);
		gc.bezierCurveTo(0.783 * this.width, 0.920 * this.height, 0.733 * this.width, 0.980 * this.height, 0.497 * this.width, this.height);
		gc.quadraticCurveTo(0.933 * this.width, 0.980 * this.height, this.width, 0.766 * this.height);
		gc.lineTo(this.width, 0.910 * this.height);
		gc.fill();
//		gc.stroke() //Outline.
		return;
	}

	public Pane getRoot() {
		return root;
	}

	//Change dimensions and adapt canvas to new dimensions.
	private void changeDimensions() {
		int temp = this.width;
		this.width= this.height;
		this.height = temp;
//		canvas.setWidth(this.width);
//		canvas.setHeight(this.height);
	}

	public void changeRotation() {
		changeDimensions();
		drawBackground();
	}
}