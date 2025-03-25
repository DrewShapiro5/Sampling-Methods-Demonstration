package DataElements;

public class GridSampler
{
    public static final int STRATIFIED = 183;
    public static final int CLUSTER = 184;

    public Sample takeSample(Grid grid)
    {
        Plot[][] plots = grid.getPlots();
        int sampleSize = (int)(Math.sqrt(grid.getGridWidth() * grid.getGridHeight()));
        Plot[] samplePlots = new Plot[sampleSize];

        int sampleIndex = 0;
        while (sampleIndex < sampleSize)
        {
            int randomX = (int)(Math.random() * grid.getGridWidth());
            int randomY = (int)(Math.random() * grid.getGridHeight());
            boolean duplicatePlot = false;
            for (int i = 0; i < sampleIndex; i ++)
            {
                if (plots[randomX][randomY] == samplePlots[i])
                {
                    duplicatePlot = true;
                    break;
                }
            }
            if (!duplicatePlot)
            {
                samplePlots[sampleIndex] = plots[randomX][randomY];
                sampleIndex += 1;
            }
        }
        return new Sample(samplePlots, "Simple Random Sample");
    }

    public Sample takeSample(Grid grid, int strataWidth, int strataHeight, String sampleType, int sampleTypeConst)
    {
        Plot[][] plots = grid.getPlots();
        int strataCountX = grid.getGridWidth() / strataWidth;
        int strataCountY = grid.getGridHeight() / strataHeight;
        Plot[] samplePlots;
        if (sampleTypeConst == STRATIFIED)
        {
            int sampleSize = strataCountX * strataCountY;
            samplePlots = new Plot[sampleSize];
            int sampleIndex = 0;
            for (int x = 0; x < strataCountX; x ++)
            {
                for (int y = 0; y < strataCountY; y ++)
                {
                    int randomX = (int)(Math.random() * strataWidth);
                    int randomY = (int)(Math.random() * strataHeight);
                    int plotX = x * strataWidth + randomX;
                    int plotY = y * strataHeight + randomY;
                    samplePlots[sampleIndex] = plots[plotX][plotY];
                    sampleIndex += 1;
                }
            }
        }
        else if (sampleTypeConst == CLUSTER)
        {
            int sampleSize = strataWidth * strataHeight;
            samplePlots = new Plot[sampleSize];
            int strataX = (int)(Math.random() * strataCountX);
            int strataY = (int)(Math.random() * strataCountY);
            for (int sampleIndex = 0; sampleIndex < sampleSize; sampleIndex ++)
            {
                int plotX = strataX * strataWidth + sampleIndex % strataWidth;
                int plotY = strataY * strataHeight + sampleIndex / strataWidth;
                samplePlots[sampleIndex] = plots[plotX][plotY];
            }
        }
        else
        {
            throw (new IllegalArgumentException("Invalid sampleTypeConst: " + sampleTypeConst));
        }
        return new Sample(samplePlots, sampleType);
    }
}
