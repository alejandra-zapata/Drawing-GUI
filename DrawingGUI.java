import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import java.awt.geom.Point2D;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.stage.*;

public class RectangleDrawingGUI extends Application {

	private final static int WIDTH = 600, HEIGHT = 600;
	private BorderPane borderPane;
	private Pane pane;
	private RadioButton blueRadioButton, greenRadioButton, brownRadioButton, thinBorderRadioButton, thickBorderRadioButton,
						rectangleRadioButton, circleRadioButton;
	private CheckBox fillCheckBox;
	private Button clearButton;
	private int clickCount;
	private Rectangle rectangle;
	private double rectangleStartingX;
	private double rectangleStartingY;
	private double rectangleEndingX;
	private double rectangleEndingY;
	private Circle circle;
	private double circleX;
	private double circleY;
	private double circleX2;
	private double circleY2;
	private double circleRadius;
	
	public void start(Stage primaryStage) {
		borderPane = new BorderPane();
		
		//Shapes group:
	  ToggleGroup shapesGroup = new ToggleGroup();
	  rectangleRadioButton = new RadioButton("Rectangle");
		rectangleRadioButton.setSelected(true);
		rectangleRadioButton.setToggleGroup(shapesGroup);
		circleRadioButton = new RadioButton("Circle");
		circleRadioButton.setToggleGroup(shapesGroup);
		
		//Colors group:
		ToggleGroup colorGroup = new ToggleGroup();
		blueRadioButton = new RadioButton("Blue");
		blueRadioButton.setSelected(true);
		blueRadioButton.setToggleGroup(colorGroup);
		greenRadioButton = new RadioButton("Green");
		greenRadioButton.setToggleGroup(colorGroup);
		brownRadioButton = new RadioButton("Brown");
		brownRadioButton.setToggleGroup(colorGroup);
		
		//Thickness group: 
		ToggleGroup borderGroup = new ToggleGroup();
		thinBorderRadioButton = new RadioButton("Thin Border");
		thinBorderRadioButton.setSelected(true);
		thinBorderRadioButton.setToggleGroup(borderGroup);
		thickBorderRadioButton = new RadioButton("Thick Border");
		thickBorderRadioButton.setToggleGroup(borderGroup);
		
		//Filled?
		fillCheckBox = new CheckBox("Fill");
			
		//Clear button:
		clearButton = new Button("Clear");
		clearButton.setOnAction(this::handleClearButton);
	  clearButton.setPadding(new Insets(10, 10, 10, 10));
	    
		//Menu (bottom of border pane):
		HBox shapesBox = new HBox(rectangleRadioButton, circleRadioButton);
		shapesBox.setAlignment(Pos.CENTER);
		shapesBox.setPadding(new Insets(10, 10, 10, 10));
		shapesBox.setSpacing(10);
		
		HBox colorBox = new HBox(blueRadioButton, greenRadioButton, brownRadioButton);
		colorBox.setAlignment(Pos.CENTER);
		colorBox.setPadding(new Insets(10, 10, 10, 10));
		colorBox.setSpacing(10);
		
		HBox controlBox = new HBox(thinBorderRadioButton, thickBorderRadioButton, fillCheckBox, clearButton);
		controlBox.setAlignment(Pos.CENTER);
		controlBox.setPadding(new Insets(10, 10, 10, 10));
		controlBox.setSpacing(10);
		
		VBox menuBox = new VBox(shapesBox, colorBox, controlBox);
		
		
		//Drawing screen (center of border pane):
		pane = new Pane();
		pane.setStyle("-fx-border-color: black");
		borderPane.setCenter(pane);
		borderPane.setBottom(menuBox);
		
		clickCount = 0;
		
		//Drawing either a rectangle or a circle::
		pane.setOnMouseClicked(this::handleMouseClicks);
				
		Scene scene = new Scene(borderPane, WIDTH, HEIGHT, Color.TRANSPARENT);
		primaryStage.setTitle("Drawing Project");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	private void handleMouseClicks(MouseEvent event) {
		rectangle = new Rectangle();
		circle = new Circle();
		clickCount++;
		
		if(clickCount % 2 == 1) {
			if(rectangleRadioButton.isSelected()) {
				rectangleStartingX = event.getX();
				rectangleStartingY = event.getY();
				pane.setOnMouseMoved(this::handleMouseMotion);
			}else if(circleRadioButton.isSelected()) {
				circleX = event.getX();
				circleY = event.getY();
				pane.setOnMouseMoved(this::handleMouseMotion);
			}
		}
		else if(clickCount % 2 == 0) {
			if(rectangleRadioButton.isSelected()) {
				rectangleEndingX = event.getX();
				rectangleEndingY = event.getY();	
				pane.getChildren().add(createRectangle(rectangleStartingX, rectangleStartingY, rectangleEndingX, rectangleEndingY, rectangle));
			}else if(circleRadioButton.isSelected()) {
				circleX2 = event.getX();
				circleY2 = event.getY();
				pane.getChildren().add(createCircle(circleX, circleY, circleX2, circleY2, circle));
			}
		}
	}
	
	private void handleMouseMotion(MouseEvent event) {
		int listSize = pane.getChildren().size();
		if(clickCount % 2 == 1) {
			double currentMousePositionX = event.getSceneX();
			double currentMousePositionY = event.getSceneY();
			
			
			//removing before adding it. 
			if(listSize > 0) {
				pane.getChildren().remove(listSize -1);
			}
			if(rectangleRadioButton.isSelected()) {
				pane.getChildren().add(createRectangle(rectangleStartingX, rectangleStartingY, currentMousePositionX, currentMousePositionY, rectangle));
				listSize ++;
			}else if(circleRadioButton.isSelected()) {
				pane.getChildren().add(createCircle(circleX, circleY, currentMousePositionX, currentMousePositionY, circle));
				listSize ++;
			}	
		}
		
	}
	
	private Rectangle createRectangle(double startX, double startY, double finalX, double finalY, Rectangle givenRectangle) {
		givenRectangle.setX(startX);
		givenRectangle.setY(startY);
		givenRectangle.setWidth(finalX - startX);
		givenRectangle.setHeight(finalY - startY);
		
	
		if(givenRectangle.getWidth() < 0) {
			givenRectangle.setWidth(- givenRectangle.getWidth() );
			givenRectangle.setX(givenRectangle.getX() - givenRectangle.getWidth() );		
		}
		if(givenRectangle.getHeight() < 0 ) {
			givenRectangle.setHeight( - givenRectangle.getHeight());
			givenRectangle.setY(givenRectangle.getY() - givenRectangle.getHeight());
		}
		
		givenRectangle.setStroke(currentColor());
		
		if(fillCheckBox.isSelected()) {
			givenRectangle.setFill(currentColor());
		} else {
			givenRectangle.setFill(Color.TRANSPARENT);
		}
		
		if(thinBorderRadioButton.isSelected()) {
			givenRectangle.setStrokeWidth(1);
		}else if(thickBorderRadioButton.isSelected()){
			givenRectangle.setStrokeWidth(6);
		}
		return givenRectangle;	
	}
	

	private Circle createCircle(double centerX, double centerY, double radiusX, double radiusY, Circle givenCircle) {
		circleRadius = Point2D.distance(centerX, centerY, radiusX, radiusY);
		givenCircle = new Circle(centerX, centerY,circleRadius);
		
		givenCircle.setStroke(currentColor());
		
		if(fillCheckBox.isSelected()) {
			givenCircle.setFill(currentColor());
		} else {
			givenCircle.setFill(Color.TRANSPARENT);
		}
		
		if(thinBorderRadioButton.isSelected()) {
			givenCircle.setStrokeWidth(1);
		}else if(thickBorderRadioButton.isSelected()){
			givenCircle.setStrokeWidth(6);
		}
		return givenCircle;
	}

	private Color currentColor() {
		Color currentColor = null;
		if(blueRadioButton.isSelected()) {
			currentColor = Color.DODGERBLUE;
		}else if(greenRadioButton.isSelected()) {
			currentColor = Color.OLIVEDRAB;
		}else if(brownRadioButton.isSelected()) {
			currentColor = Color.SIENNA;
		}	
		return currentColor;
	}
	
	private void handleClearButton(ActionEvent event) {
		pane.getChildren().clear();	
	}
		
	public static void main(String[] args) {
		launch(args);
	}
}
