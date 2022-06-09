package shapes;


/**
 * This class captures the abstract notion of a ShapeOperator.
 */
public abstract class ShapeOperator implements Shape {
    protected Shape shape1;
    protected Shape shape2;

    @Override
    public Rectangle boundingBox() {
        int x;
        int y;
        int x2;
        int y2;
        if(shape1.boundingBox().x<shape2.boundingBox().x){
            x = shape1.boundingBox().x;
            x2 = shape2.boundingBox().x+shape2.boundingBox().width;
        }else{
            x = shape2.boundingBox().x;
            x2 = shape1.boundingBox().x+shape1.boundingBox().width;
        }
        if(shape1.boundingBox().y<shape2.boundingBox().y){
            y = shape1.boundingBox().y;
            y2 = shape2.boundingBox().y+shape2.boundingBox().height;
        }else{
            y = shape2.boundingBox().y;
            y2 = shape1.boundingBox().y+shape1.boundingBox().height;
        }
        return new Rectangle(x, y, y2-y, x2-x);
    }
}
