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
import java.io.PrintStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class Main extends Application {
    private GraphicsContext graphicsContext;
    private Canvas canvas;
    private ArrayList<Particle> particles = new ArrayList<Particle>();
    private Layer[] layers;
    private Double pWidth = 4.0;
    private int width = 500;
    private int height = 1000;
    private Double[] dims =new Double[]{Double.valueOf(width),Double.valueOf(height)};
    @Override
    public void start(Stage stage) throws Exception {
        this.canvas = new Canvas(width,height);
        this.graphicsContext = canvas.getGraphicsContext2D();
        layers = new Layer[3];
        layers[0] = new PointSourceLayer(dims, 0.003);
        layers[1] = new ForceField(dims, 0.001);
        layers[2] = new PressureLayer(dims, 0.02);
        for (int i = 0; i<10000; i++){
            Particle particle = new Particle(dims, 1.0,pWidth);
            particles.add(particle);

        }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Long time = new Date().getTime();
                for (Layer layer: layers){
                    if (layer instanceof PressureLayer){
                        PressureLayer pL = ((PressureLayer)layer);
                        pL.wipeCells();
                    }
                }
                tick();
                graphicsContext.setFill(Color.BLACK);
                graphicsContext.fillRect(0,0,width,height);
                graphicsContext.setFill(Color.WHITE);
                for (Particle particle: particles){
                    Double[] pos = particle.getPos();
                    Double velo = Mat.sigmoid(Mat.magnitude(particle.getVelocity()));
                    if (particle.getDensity() != 99.0) {
                        graphicsContext.setFill(Color.color((particle.mass -particle.getDensity())/particle.mass, 0.0, (particle.getDensity())/ particle.mass));
                    }
                    else{
                        graphicsContext.setFill(Color.WHITE);
                    }
                    graphicsContext.fillOval((pos[0]-(pWidth*0.5)),pos[1]-(pWidth*0.5),pWidth,pWidth);
                }
                Long nTime = new Date().getTime();
                System.out.println(nTime - time);
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

//        for (int i = 0; i < particles.size(); i++){
//            Particle particle = particles.get(i);
//            for (int a = 0; a<100; a++){
//                particle.tick(0.01, dims, layers);
//            }
//        }
//        System.out.println(Instant.now().getNano()-time);
//        time = Instant.now().getNano();
        int w = 24;
        int prev = 0;
        for (int t = 0; t < w; t++) {
            int start = prev;
            int finish = (t+1)*(particles.size()/w);
            if ((t+1) == w){
                finish = particles.size();
            }
            int finalFinish = finish;
            Thread thread = new Thread(() -> {
                for (int i = start; i < finalFinish; i++) {
                    Particle particle = particles.get(i);
                    for (int a = 0; a < 5; a++) {
                        particle.tick(0.2, dims, layers);
                    }

                }
            });
            thread.setPriority(10);
            thread.start();
            prev = finish;
        }
    }


}
