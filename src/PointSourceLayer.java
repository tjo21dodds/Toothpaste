public class PointSourceLayer extends  Layer{

    Double strength;
    public PointSourceLayer(Double[] dimensions, Double strength) {
        super(dimensions, strength);
    }


    @Override
    public void generateField(Double strength) {
        super.generateField(strength);
        this.strength = strength;
        int[] sP = new int[]{400,600};
        int[] eP = new int[]{480,980};
        this.map(sP,eP,((pos) ->{
            this.vectorField[this.getVectorIndex(pos)][1] = -1.0*strength;
        }));
    }

    @Override
    public void apply(Particle particle) {
        particle.setDensity(particle.getDensity() - particle.getDensity()*(Mat.magnitude(this.forceAt(particle))));
    }
}
