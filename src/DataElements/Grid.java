package DataElements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Grid
{
    private Plot[][] plots;
    private boolean displayData = false;
    private boolean highlightPlots = true;
    private Plot[] highlightedPlots = new Plot[0];
    private int showGridLabels = 1;
    private Dimension strataDimensions = new Dimension(-1, -1);

    public Grid(int gridWidth, int gridHeight)
    {
        this.resetGrid(gridWidth, gridHeight);
    }

    public int getGridWidth() {return plots.length;}
    public int getGridHeight() {return plots[0].length;}
    public Plot[][] getPlots() {return this.plots;}

    public void setDisplayData(boolean displayData) {this.displayData = displayData;}
    public void setShowGridLabels(boolean showGridLabels) {if (showGridLabels) this.showGridLabels = 1; else this.showGridLabels = 0;}
    public void setHighlightPlots(boolean highlightPlots) {this.highlightPlots = highlightPlots;}
    public void setHighlightedPlots(Plot[] highlightedPlots) {this.highlightedPlots = highlightedPlots;}
    public void resetHighlightedPlots() {this.highlightedPlots = new Plot[0];}

    public void setStrataDimensions(int strataWidth, int strataHeight)
    {
        strataDimensions = new Dimension(strataWidth, strataHeight);
    }
    
    public void resetGrid(int gridWidth, int gridHeight)
    {
        this.plots = new Plot[gridWidth][gridHeight];
        double midLineIndex = (double)(gridHeight - 1) / 2;
        for (int x = 0; x < plots.length; x ++)
        {
            for (int y = 0; y < plots[x].length; y ++)
            {
                double distanceFromMidline = Math.abs(midLineIndex - y);
                double relativeDistance = distanceFromMidline / midLineIndex; //produces values from 0-1
                double cropYield = 80 + (20 * relativeDistance) + (Math.random() * 5 + Math.random() * 5);
                plots[x][y] = new Plot(x, y, cropYield);
            }
        }
        this.resetHighlightedPlots();
    }

    public void drawGrid(Graphics g, double containerWidth, double containerHeight)
    {
        if (plots == null) return;
        Graphics2D g2d = (Graphics2D)g;
        double cellSize;
        int startX;
        int startY;
        if (containerWidth / (plots.length + this.showGridLabels) < containerHeight / (plots[0].length + 1 + this.showGridLabels))
        {
            cellSize = containerWidth / (plots.length + this.showGridLabels);
            startX = 0;
            startY = (int)((containerHeight - cellSize * (plots[0].length + 1 + this.showGridLabels)) / 2);
        }
        else
        {
            cellSize = containerHeight / (plots[0].length + 1 + this.showGridLabels);
            startX = (int)((containerWidth - cellSize * (plots.length + this.showGridLabels)) / 2);
            startY = 0;
        }
        int cellSizeInt = (int)cellSize;
        Color originalColor = g2d.getColor();
        AffineTransform originalTransform = g2d.getTransform();
        int gridPixelWidth = (int)(cellSize * plots.length);
        int gridPixelHeight = (int)(cellSize * plots[0].length);
        Color blue = new Color(10, 50, 240);
        Color black = new Color(0, 0, 0);
        g2d.translate(startX, startY);
        g2d.setFont(new Font("Serif", Font.PLAIN, cellSizeInt / 2));
        //draw graph labels
        if (this.showGridLabels == 1)
        {
            g2d.setColor(Color.lightGray);
            g2d.fillRect(cellSizeInt, 0, gridPixelWidth, cellSizeInt);
            g2d.fillRect(0, (int)(1.5 * cellSize), cellSizeInt, gridPixelHeight);
            g2d.setColor(Color.darkGray);
            //g2d.fillRect(0, 0, cellSizeInt, (int)(1.5 * cellSize));
            g2d.setColor(black);
            //g2d.drawRect(0, 0, cellSizeInt, (int)(1.5 * cellSize));
            g2d.drawRect(cellSizeInt, 0, gridPixelWidth, cellSizeInt);
            g2d.drawRect(0, (int)(1.5 * cellSize), cellSizeInt, gridPixelHeight);
            g2d.setColor(Color.darkGray);
            g2d.translate(cellSizeInt, 0);
            for (int x = 0; x < plots.length; x ++)
            {
                char startVal = 'A';
                if (x > 25) startVal += 6;
                String labelText = "" + (startVal += x);
                int stringWidth = g2d.getFontMetrics().stringWidth(labelText);
                int offsetX = (cellSizeInt - stringWidth) / 2;
                int offsetY = (3 * cellSizeInt) / 4;
                g2d.drawString(labelText, (int)(x * cellSize) + offsetX, offsetY);
                g2d.drawLine((int)(x * cellSize), 0, (int)(x * cellSize), cellSizeInt);
            }
            g2d.translate(-cellSizeInt, 0);
            g2d.translate(0, (int)(cellSize * 1.5));
            for (int y = 0; y < plots[0].length; y ++)
            {
                String labelText = ((Integer)(y + 1)).toString();
                int stringWidth = g2d.getFontMetrics().stringWidth(labelText);
                int offsetX = (cellSizeInt - stringWidth) / 2;
                int offsetY = (3 * cellSizeInt) / 4;
                g2d.drawString(labelText, offsetX, (int)(y * cellSize) + offsetY);
                g2d.drawLine(0, (int)(y * cellSize), cellSizeInt, (int)(y * cellSize));
            }
            g2d.translate(cellSizeInt, -cellSizeInt / 2);
        }
        //draw the irrigation ditches
        g2d.setColor(blue);
        g2d.fillRect(0, 0, gridPixelWidth, (int)(cellSize / 2));
        g2d.setColor(black);
        g2d.drawRect(0, 0, gridPixelWidth, (int)(cellSize / 2));
        g2d.translate(0, (int)(cellSize / 2));
        g2d.setColor(blue);
        g2d.fillRect(0, gridPixelHeight, gridPixelWidth, (int)(cellSize / 2));
        g2d.setColor(black);
        g2d.drawRect(0, gridPixelHeight, gridPixelWidth, (int)(cellSize / 2));    
        //draw the grid
        g2d.setColor(new Color(180, 200, 40));
        g2d.fillRect(0, 0, gridPixelWidth, gridPixelHeight);
        if (this.highlightPlots)
            for (Plot p : this.highlightedPlots)
            {
                g2d.setColor(Color.green);
                g2d.fillRect((int)(p.getX() * cellSize), (int)(p.getY() * cellSize), cellSizeInt, cellSizeInt);
                g2d.setColor(black);
                String yieldText = ((Integer)((int)p.getYield())).toString(); //offset the display of yield based on the length of the string
                int stringWidth = g2d.getFontMetrics().stringWidth(yieldText);
                int offsetX = (cellSizeInt - stringWidth) / 2;
                int offsetY = (3 * cellSizeInt) / 4;
                g2d.drawString(yieldText, (int)(p.getX() * cellSize) + offsetX, (int)(p.getY() * cellSize) + offsetY);
            }
        g2d.setColor(black);
        for (int i = 0; i < plots.length + 1; i ++) g2d.drawLine((int)(i * cellSize), 0, (int)(i * cellSize), gridPixelHeight);
        for (int i = 0; i < plots[0].length; i ++) g2d.drawLine(0, (int)(i * cellSize), gridPixelWidth, (int)(i * cellSize));
        if (this.displayData)
        {
            for (int x = 0; x < plots.length; x ++)
            {
                for (int y = 0; y < plots[0].length; y ++)
                {
                    String yieldText = ((Integer)((int)plots[x][y].getYield())).toString();
                    int stringWidth = g2d.getFontMetrics().stringWidth(yieldText);
                    int offsetX = (cellSizeInt - stringWidth) / 2;
                    int offsetY = (3 * cellSizeInt) / 4;
                    g2d.drawString(yieldText, (int)(x * cellSize) + offsetX, (int)(y * cellSize) + offsetY);
                }
            }
        }
        if (this.strataDimensions.getWidth() > 0) drawStrata(g2d, cellSize);
        g2d.setColor(originalColor);
        g2d.setTransform(originalTransform);
    }

    private void drawStrata(Graphics2D g2d, double cellSize)
    {
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(new Color(200, 0, 0, 150));
        if (strataDimensions.getWidth() == 0 || strataDimensions.getHeight() == 0) return;
        for (int i = 0; i < getGridWidth() / (int)(strataDimensions.getWidth()); i ++)
        {
            for (int j = 0; j < getGridHeight() / (int)(strataDimensions.getHeight()); j ++)
            {
                g2d.drawRect((int)(i * cellSize * strataDimensions.getWidth()), (int)(j * cellSize * strataDimensions.getHeight()), (int)(cellSize * strataDimensions.getWidth()), (int)(cellSize * strataDimensions.getHeight()));
            }
        }
    }

    public Dimension getStrataDimension() {return this.strataDimensions;}
}
