import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.function.Function;


public class Layer {
    protected int[] dimensions;
    protected Double[][] vectorField;

    interface MapFunc {
        abstract void perform(int[] pos);
    }

    protected int[] safeSelect(int[] pos) {
        int[] r = new int[dimensions.length];
        for (int i = 0; i < dimensions.length; i++){
            if (pos[i]<0){
                r[i] = 0;
            }
            else if (pos[i] >= dimensions[i]){
                r[i] = dimensions[i]-1;
            }
            else{
                r[i] = pos[i];
            }
        }
        return r;
    }

    public Layer(Double[] dimensions, Double strength){
        this.dimensions = Mat.convert(dimensions);
        int length = (Mat.mul(dimensions).intValue());
        this.vectorField = new Double[length][dimensions.length];
        this.generateField(strength);

    }
    public void generateField(Double strength){
        for(Double[] v: this.vectorField){
            for (int i=0; i<v.length; i++){
                v[i] = 0.0;
            }
        }
    }

    public int getVectorIndex(int[] pos){
        pos = safeSelect(pos);
        int r = 0;
        int rollingDepth = 1;
        for (int depth = 0; depth < this.dimensions.length; depth++){
            r = r + ((pos[depth])*rollingDepth);
            rollingDepth = rollingDepth * this.dimensions[depth];
        }

        return r;
    }


    public void map(int[] sPos, int[] ePos, MapFunc mapFunc){
        ArrayList<int[]> possiblePoses = new ArrayList<>();
        possiblePoses.add(new int[sPos.length]);
        for (int d = 0; d<sPos.length; d ++){
            ArrayList<int[]> newPoses = new ArrayList<>();
            for (int i = 0; i < possiblePoses.size(); i++){
                ArrayList<int[]> tPoses = new ArrayList<>();
                int[] cPos = possiblePoses.get(i);
                for (int a = sPos[d]; a < ePos[d]; a++){
                    int[] aPos = cPos.clone();
                    aPos[d] = a;
                    tPoses.add(aPos);
                }
                newPoses.addAll(tPoses);
            }
            possiblePoses = newPoses;
        }
        for (int[] pos: possiblePoses){
            mapFunc.perform(pos);
        }
    }

    public Double[] getVector(int[] pos){
        return this.vectorField[this.getVectorIndex(pos)];
    }

    public Double[] forceAt(Particle particle){
        return this.getVector(this.safeSelect(Mat.convert(particle.getPos())));
    }
}
