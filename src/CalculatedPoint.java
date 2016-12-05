public class CalculatedPoint {
    private Float X;
    private Float Y;
    CalculatedPoint(Float x, Float y){
        X = x;
        Y = y;
    }

    Float getX(){
        return X;
    }

    Float getY(){
        return Y;
    }

    void setX(Float x){
        X = x;
    }

    void setY(Float y){
        Y = y;
    }
}
