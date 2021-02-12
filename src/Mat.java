public class Mat {
    public static Double magnitude(Double[] vector){
        Double result = 0.0;
        for (Double i: vector){
            result = result + Math.pow(i,2);
        }
        return Math.pow(result,0.5);
    }

    public static Double[] dotProduct(Double[] a, Double[] b){
        Double[] r = new Double[a.length];
        for (int i = 0; i< a.length; i++){
            r[i] = a[i]*b[i];
        }
        return r;
    }

    public static Double[] sum(Double[] a, Double[] b){
        Double[] r = new Double[a.length];
        for (int i = 0; i < a.length; i++){
            r[i] = a[i] + b[i];
        }
        return r;
    }

    public static Double[] pow(Double[] a, Double b){
        Double[] r = new Double[a.length];
        for (int i = 0; i < a.length; i++){
            if (a[i]<0.0){
                r[i] = -1.0 * Math.pow(Math.abs(a[i]),b);

            }
            else {
                r[i] = Math.pow(a[i], b);
            }
        }
        return r;
    }

    public static Double[] negate(Double[] vector){
        Double[] r = new Double[vector.length];
        for (int i = 0; i<vector.length; i++){
            r[i] = -1.0 * vector[i];
        }
        return r;
    }

    public static Double[] abs(Double[] vector){
        Double[] r = new Double[vector.length];
        for (int i = 0; i<vector.length; i++){
            r[i] = Math.abs(vector[i]);
        }
        return r;
    }

    public static Double[] scale(Double[] vector, Double scaler){
        Double[] r = new Double[vector.length];
        for (int i = 0; i<vector.length; i++){
            r[i] = vector[i] * scaler;
        }
        return r;
    }

    public static int[] convert(Double[] vector){
        int[] r = new int[vector.length];
        for (int i = 0; i<vector.length; i++){
            r[i] = (int)(vector[i].doubleValue());
        }
        return r;
    }

    public static Double mul(Double[] vector) {
        Double r = 1.0;
        for (int i = 0; i<vector.length; i++){
            r = r * vector[i];
        }
        return r;

    }
}
