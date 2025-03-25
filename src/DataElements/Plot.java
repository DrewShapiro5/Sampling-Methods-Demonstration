package DataElements;
public class Plot
{
    private int xPos;
    private int yPos;
    private double cropYield;

    public Plot(int x, int y, double cropYield)
    {
        this.xPos = x;
        this.yPos = y;
        this.cropYield = cropYield;
    }

    public int getX() {return this.xPos;}
    public int getY() {return this.yPos;}
    public double getYield() {return this.cropYield;}
}
