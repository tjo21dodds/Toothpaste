import java.util.Random;

public class Particle {

    public boolean[] wall;

    private final Double restituion = 0.8;
    private final Double decay = 0.01;

    private Double mass = 1.0;

    private Double radius;

    private Double[] pos;
    private Double[] velocity;

    public Double[] getVelocity() {
        return velocity;
    }

    public void setVelocity(Double[] velocity) {
        this.velocity = velocity;
    }

    public Double[] getPos() {
        return pos;
    }

    public void setPos(Double[] pos) {
        this.pos = pos;
    }

    public void tick(Double time, Double[] dimensions, Layer[] layers){
        for (int i = 0; i<this.pos.length; i++){
            if (this.pos[i] < -10.0 || this.pos[i] > dimensions[i]+10.0){
                Random random = new Random();
                Double[] pos = new Double[dimensions.length];
                for (int a = 0; a < dimensions.length; a++){
                    pos[a] = random.nextDouble() * dimensions[a];
                }
                this.pos = pos;
                this.wall[i] = false;
            }
            else if (this.pos[i] > dimensions[i] || this.pos[i] < 0){
                if (!this.wall[i]){
                    this.velocity[i] = -1*this.velocity[i]* restituion;
                    this.wall[i] = true;
                }
            }
            else{
                this.wall[i] = false;
            }
            this.pos[i] = this.pos[i] + (this.velocity[i]*time);
        }
        for (Layer layer: layers){
            this.velocity = Mat.sum(this.velocity, layer.forceAt(this));
        }
        this.velocity = Mat.sum(this.velocity, Mat.negate(Mat.scale(Mat.pow(this.velocity,2.0),decay)));
    }

    public Particle(Double[] pos, Double[] velocity, Double radius){
        this.pos = pos;
        this.velocity = velocity;
        this.radius = radius;
    }

    public Particle(Double[] dimensions, Double maxVelocity, Double radius) { //Returns Random Particle
        Random random = new Random();

        Double[] pos = new Double[dimensions.length];
        for (int i = 0; i < dimensions.length; i++){
            pos[i] = random.nextDouble() * dimensions[i];
        }

        Double[] velocity = new Double[dimensions.length];
        for (int i = 0; i < dimensions.length; i++){
            velocity[i] = (random.nextDouble()-0.5) * maxVelocity;
//            velocity[i] = 0.0;
        }
        this.wall = new boolean[dimensions.length];
        for (int i = 0; i <dimensions.length; i++){
            this.wall[i] = false;
        }


        this.pos = pos;
        this.velocity = velocity;
        this.radius = radius;
    }



}
