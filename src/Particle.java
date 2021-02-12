import java.util.Random;

public class Particle {
    private final Double entropy = 0.99;

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
            if (this.pos[i] < 0.0 || this.pos[i] > dimensions[i]){
                Random random = new Random();
                Double[] pos = new Double[dimensions.length];
                for (int a = 0; a < dimensions.length; a++){
                    pos[a] = random.nextDouble() * dimensions[a];
                }
                this.pos = pos;
            }
            else if (this.pos[i] > dimensions[i]-radius || this.pos[i] < radius){
                this.velocity[i] = -1*this.velocity[i]*entropy;
            }
            this.pos[i] = this.pos[i] + (this.velocity[i]*time);
        }
        for (Layer layer: layers){
            this.velocity = Mat.sum(this.velocity, layer.forceAt(this.pos));
        }
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
        }
        this.pos = pos;
        this.velocity = velocity;
        this.radius = radius;
    }



}
