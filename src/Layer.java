public class Layer {
    private int[] dimensions;
    private Double[][] vectorField;

    public Layer(Double[] dimensions, Double strength){
        this.dimensions = Mat.convert(dimensions);
        int length = (Mat.mul(dimensions).intValue());
        this.vectorField = new Double[length][dimensions.length];
        this.generateField(strength);

    }

    public void generateField(Double strength){
        for(Double[] v: this.vectorField){
            for (int i=0; i<v.length; i++){
                if (i==1){
                    v[i] = 1*strength;
                }
                else{
                    v[i] = 0.0;
                }
            }
        }
    }

    public Double[] getVector(int[] pos){
        int r = 0;
        int rollingDepth = 1;
        for (int depth = 0; depth < this.dimensions.length; depth++){
            r = r + ((pos[depth])*rollingDepth);
            rollingDepth = rollingDepth * this.dimensions[depth];
        }
        try {
            return this.vectorField[r];
        }
        catch (Exception e){
            return this.vectorField[0];
        }
    }

    public Double[] forceAt(Double[] pos){
        return this.getVector(Mat.convert(pos));
    }
}
