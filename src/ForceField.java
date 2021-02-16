public class ForceField extends Layer{
    public ForceField(Double[] dimensions, Double strength) {
        super(dimensions, strength);
    }
    @Override
    public void generateField(Double strength) {
        for(Double[] v: this.vectorField){
            for (int i=0; i<v.length; i++){
                if (i==1){
                    v[i] = strength;
                }
                else{
                    v[i] = 0.0;
                }
            }
        }
    }

    @Override
    public Double[] forceAt(Particle particle) {
        Double[] base = super.forceAt(particle);
//        base = Mat.scale(base,(1/(Mat.magnitude(particle.getVelocity())+1)));
        return base;
    }
}
