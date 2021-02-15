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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class Main extends Application {
    private GraphicsContext graphicsContext;
    private Canvas canvas;
    private ArrayList<Particle> particles = new ArrayList<Particle>();
    private Layer[] layers;
    private Double pWidth = 2.0;
    private int width = 1000;
    private int height = 750;
    private Double[] dims =new Double[]{Double.valueOf(width),Double.valueOf(height)};
    @Override
    public void start(Stage stage) throws Exception {
        this.canvas = new Canvas(width,height);
        this.graphicsContext = canvas.getGraphicsContext2D();
        layers = new Layer[2];
        layers[0] = new PointSourceLayer(dims, 0.0006);
        layers[1] = new ForceField(dims, 0.0001);
//        layers[2] = new DragLayer(dims, 0.000001);
        for (int i = 0; i<20000; i++){
            Particle particle = new Particle(dims, 1.0,pWidth);
            particles.add(particle);

        }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Long time = new Date().getTime();
                graphicsContext.setFill(Color.BLACK);
                graphicsContext.fillRect(0,0,width,height);

                tick();


                for (Particle particle: particles){
                    Double[] pos = particle.getPos();
                    Double velo = Mat.sigmoid(Mat.magnitude(particle.getVelocity()));
                    graphicsContext.setFill(Color.hsb(Math.pow(velo,8)*255*10, 1.0,1.0));
                    graphicsContext.fillOval(pos[0]-(pWidth*0.5),pos[1]-(pWidth*0.5),pWidth,pWidth);
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
                    for (int a = 0; a < 10; a++) {
                        particle.tick(0.1, dims, layers);

                    }

                }
            });
            thread.setPriority(10);
            thread.start();
            prev = finish;
        }
    }


}
