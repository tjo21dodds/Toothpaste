public class DragLayer extends Layer{

    private int radius = 5;
    public DragLayer(Double[] dimensions, Double strength) {
        super(dimensions, strength);
    }

    private void modifyField(Particle particle) {
        int[] pos = Mat.convert(particle.getPos());
        int[] sP = new int[pos.length];
        int[] eP = new int[pos.length];
        sP = this.safeSelect(sP);
        eP = this.safeSelect(eP);
        for (int d = 0; d < pos.length; d++){
            sP[d] = pos[d] - radius;
            eP[d] = pos[d] + radius;
        }
        Double[] velocity = particle.getVelocity();
        this.map(sP,eP,(iPos)->{
            int i = this.getVectorIndex(iPos);
            for (int d =0; d<velocity.length;d++) {
                this.vectorField[i][d] = (0.9 * this.vectorField[i][d]) + (0.1 * velocity[d]);
            }
        });
    }

    @Override
    public Double[] forceAt(Particle particle) {
        modifyField(particle);
        return super.forceAt(particle);
    }
}
