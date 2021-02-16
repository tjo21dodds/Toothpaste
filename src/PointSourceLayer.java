public class PointSourceLayer extends  Layer{

    public PointSourceLayer(Double[] dimensions, Double strength) {
        super(dimensions, strength);
    }


    @Override
    public void generateField(Double strength) {
        super.generateField(strength);
        int[] sP = new int[]{480,600};
        int[] eP = new int[]{520,980};
        this.map(sP,eP,((pos) ->{
            this.vectorField[this.getVectorIndex(pos)][1] = -1.0*strength;
        }));
    }
}
