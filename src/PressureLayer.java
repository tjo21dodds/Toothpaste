public class PressureLayer extends Layer{

    private int[] cells;
    private int[] nCells;
    private int cellScale = 5;
    private int cellNum = 10;
    private Double strength;
    public PressureLayer(Double[] dimensions, Double strength) {
        super(dimensions, strength);
        this.strength = strength;
        Double length = (Mat.mul(dimensions)/cellScale);
        cells = new int[length.intValue()];
        nCells = new int[length.intValue()];
    }

    public void wipeCells(){
        for (int i = 0; i<cells.length; i++){
            cells[i] = nCells[i];
            nCells[i] = 0;
        }
    }

    private int getCellIndex(int[] pos){
        pos = safeSelect(pos);
        int r = 0;
        int rollingDepth = 1;
        for (int depth = 0; depth < this.dimensions.length; depth++){
            r = r + ((pos[depth]/cellScale)*rollingDepth);
            rollingDepth = rollingDepth * this.dimensions[depth];
        }

        return r;
    }

    private void incrementCell(int[] pos){
        int i = getCellIndex(pos);
        nCells[i] = nCells[i] + 1;
    }

    @Override
    public Double[] forceAt(Particle particle) {
        int[] pos = this.safeSelect(Mat.convert(particle.getPos()));
        Double[] vector = new Double[pos.length];
        for (int i = 0; i < this.dimensions.length; i++){
            int fVal = 0;
            for (int a = 0; a < cellNum; a++) {
                int[] fPos = pos.clone();
                fPos[i] = fPos[i] + 1;
                try {
                    fVal = fVal + this.cells[this.getCellIndex(fPos)];
                }
                catch (Exception e){
                    fVal = fVal + (fVal/i); //Maintain average
                }
            }
            int nVal = 0;
            for (int a = 0; a < cellNum; a++) {
                int[] nPos = pos.clone();
                nPos[i] = nPos[i] - 1;
                try {
                    nVal = nVal + this.cells[this.getCellIndex(nPos)];
                }
                catch (Exception e){
                    nVal = nVal + (nVal/i); //Maintain average
                }
            }
            vector[i] = (nVal - fVal) * this.strength;
        }
        incrementCell(pos);
        return vector;
    }
}
