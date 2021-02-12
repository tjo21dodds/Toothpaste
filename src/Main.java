import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;

public class Main extends Application {
    private GraphicsContext graphicsContext;
    private Canvas canvas;
    private ArrayList<Particle> particles = new ArrayList<Particle>();
    private Layer[] layers;
    private Double pWidth = 2.0;
    private int width = 400;
    private int height = 300;
    private Double[] dims =new Double[]{Double.valueOf(width),Double.valueOf(height)};

    @Override
    public void start(Stage stage) throws Exception {


        this.canvas = new Canvas(width,height);
        this.graphicsContext = canvas.getGraphicsContext2D();
        layers = new Layer[1];
        layers[0] = new Layer(dims, 0.01);
        for (int i = 0; i<1000; i++){
            particles.add(new Particle(dims, 1.0,pWidth));
        }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (int i = 0; i<10; i++){
                    tick();
                }
                graphicsContext.setFill(Color.WHEAT);
                graphicsContext.fillRect(0,0,width,height);
                graphicsContext.setFill(Color.BLUE);
                for (Particle particle: particles){
                    Double[] pos = particle.getPos();
                    graphicsContext.fillOval(pos[0]-(pWidth*0.5),pos[1]-(pWidth*0.5),pWidth,pWidth);
                }
            }
        };
        Pane pane = new Pane(this.canvas);
        Scene scene = new Scene(pane,width,height);
        stage.setScene(scene);
        stage.show();
        timer.start();
    }

    public static void main(String[] args){
        launch(args);
    }

    public void tick(){
        for (Particle particle: particles){
            particle.tick(0.1,dims,layers);
        }
    }


}
