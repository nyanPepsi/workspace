package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
 
public class Main extends Application {
	
 
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Planar Graph");
        Group root = new Group();
        Canvas canvas = new Canvas(1280, 720);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    
    void calcOffset (double A[][], double B[][]) {
    	
    }

    private void drawShapes(GraphicsContext gc) {
    	graphTransform pGr = new graphTransform();
    	pGr.read("input.txt");
    	pGr.transform();
    	
    	
    	
    	double scale = 300;
    	double offset = 300;
    	
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.ORANGE);
        gc.setLineWidth(3);
        
        
        for (int i = 0; i < pGr.adjMatrix.length; ++i) {
        	for (int j = i + 1; j < pGr.adjMatrix.length; ++j) {
        		if (pGr.adjMatrix[i][j] != 0) {
        			gc.strokeLine(pGr.x[i] * scale + offset, pGr.y[i] * scale + offset, pGr.x[j] * scale + offset, pGr.y[j] * scale + offset);
        			
        		}
        	}
        }
        
        gc.setFill(Color.WHITE);
        
        for (int i = 0; i < pGr.x.length; ++i) {
        	gc.fillOval(pGr.x[i] * scale + offset -10, pGr.y[i] * scale + offset -10, 20, 20);
        }
        
        gc.setFill(Color.BLACK);
        for (int i = 0; i < pGr.x.length; ++i) {
        	gc.strokeOval(pGr.x[i] * scale + offset -10, pGr.y[i] * scale + offset -10, 20, 20);
        	gc.fillText(Integer.toString(i + 1), pGr.x[i] * scale + offset - 3, pGr.y[i] * scale + offset + 3);
        }
        
    }
}