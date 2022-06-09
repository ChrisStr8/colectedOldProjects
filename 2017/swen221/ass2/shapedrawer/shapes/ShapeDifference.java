package shapes;

/**
 * This is class represents the difference between two shapes.
 */
public class ShapeDifference extends ShapeOperator {

    public ShapeDifference(Shape shape1, Shape shape2){
        this.shape1 = shape1;
        this.shape2 = shape2;
    }

    @Override
    public boolean contains(int x, int y) {
        return shape1.contains(x, y) && !shape2.contains(x, y);
    }
}
