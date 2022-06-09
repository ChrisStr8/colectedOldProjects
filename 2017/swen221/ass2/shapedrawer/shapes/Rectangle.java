package shapes;

/**
 * This is class represents rectangular shapes
 */
public class Rectangle implements Shape {
    public final int x;
    public final int y;
    public final int width;
    public final int height;

    public Rectangle (int x, int y, int height, int width){
        this.x=x;
        this.y=y;
        this.height=height;
        this.width=width;
    }

    @Override
    public boolean contains(int x, int y) {
        return (x >= this.x && x <= this.x + this.width - 1) && (y >= this.y && y <= this.y + this.height - 1);
    }

    @Override
    public Rectangle boundingBox() {
        return this;
    }
}