package GUIElements;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.JPanel;
import DataElements.Sample;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

public class Graph extends JPanel
{
    private String sampleType;
    private ArrayList<Double> sampleMeans = new ArrayList<Double>();
    private int sampleSize;
    private ArrayList<Graph> graphs;

    private double minSample;
    private double maxSample;
    private double mean;

    public Graph(String sampleType, int sampleSize, ArrayList<Graph> graphs)
    {
        this.sampleType = sampleType;
        this.sampleSize = sampleSize;
        this.graphs = graphs;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        double min = graphs.get(0).getMinSample();
        for (int i = 1; i < this.graphs.size(); i ++) if (this.graphs.get(i).getMinSample() < min) min = this.graphs.get(i).getMinSample();
        double max = graphs.get(0).getMaxSample();
        for (int i = 1; i < this.graphs.size(); i ++) if (this.graphs.get(i).getMaxSample() > max) max = this.graphs.get(i).getMaxSample();
        int binWidth = 1 + (int)(max - min) / 5;
        int displayMin = (int)min - ((int)min % binWidth);
        int displayMax = (int)max - ((int)max % binWidth) + binWidth;
        Graphics2D g2d = (Graphics2D)g;
        Color originalColor = g2d.getColor();
        AffineTransform originalTransform = g2d.getTransform();
        try {this.drawGraph(displayMin, displayMax, binWidth, g2d);}
        catch (Exception ignore) {}
        g2d.setColor(originalColor);
        g2d.setTransform(originalTransform);
    }

    private void drawGraph(int min, int max, int binWidth, Graphics2D g2d)
    {
        g2d.drawLine(20, this.getHeight() - 20, this.getWidth() - 20, this.getHeight() - 20);
        int numLineWidth = this.getWidth() - 40;
        int bins = (max - min) / binWidth;
        float binPixelWidth = (float)numLineWidth / bins;
        int height = this.getHeight();
        g2d.setFont(new Font("Serif", Font.PLAIN, 14));
        g2d.setColor(new Color(0, 0, 0, 200));
        int pointWidth = (int)((double)this.getWidth() / (2.0 * Math.sqrt(this.sampleMeans.size())));
        if (pointWidth > 20) pointWidth = 20;
        if (pointWidth < 1) pointWidth = 1;
        double multiplier = binPixelWidth / binWidth;
        int[] pixelDisplaceCounts = new int[(int)(((max - min) * multiplier) / pointWidth) + 1];
        int maxDisplaceCount = 0;
        g2d.translate(20 - pointWidth / 2, -20 - pointWidth); // beginning at the start of the number line
        for (double sampleMean : sampleMeans)
        {
            int pixelPosition = (int)((sampleMean - min) * multiplier);
            int index = pixelPosition / pointWidth;
            try {if ((pixelDisplaceCounts[index] += 1) > maxDisplaceCount) {maxDisplaceCount = pixelDisplaceCounts[index];}}
            catch (ArrayIndexOutOfBoundsException e) {}
        }
        double[] pixelDisplace = new double[(int)(((max - min) * multiplier) / pointWidth) + 1];
        double verticalOffset = (double)(height - 40 - pointWidth) / maxDisplaceCount;
        if (verticalOffset > 0)
        {
            if (verticalOffset > pointWidth && pointWidth > 1) verticalOffset = pointWidth;
            if (pointWidth == 1) 
                for (int i = 0; i < pixelDisplaceCounts.length; i ++) //uses only lines for efficiency
                {    
                    if (pixelDisplaceCounts[i] > 0)
                        g2d.drawLine(i, height, i, height - (int)(pixelDisplaceCounts[i] * verticalOffset));
                }
            else for (double sampleMean : sampleMeans)
            {
                int pixelPosition = (int)((sampleMean - min) * multiplier);
                int index = pixelPosition / pointWidth;
                g2d.fillRect(pixelPosition, height - (int)pixelDisplace[index], pointWidth, pointWidth);
                pixelDisplace[index] += verticalOffset;
            }
        }
        g2d.setColor(new Color(255, 0, 0));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine((int)((this.mean - min) * multiplier + pointWidth / 2), height + pointWidth + 10,
                     (int)((this.mean - min) * multiplier + pointWidth / 2), height + pointWidth - 10);
        String labelText = this.sampleType;
        int stringWidth = g2d.getFontMetrics().stringWidth(labelText);
        int offsetX = (-stringWidth) / 2;
        int offsetY = 14;
        g2d.setColor(new Color(0, 0, 0));
        g2d.translate(-20 + pointWidth / 2, 20 + pointWidth);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawString(labelText, this.getWidth() / 2 + offsetX, offsetY);
        for (int i = 0; i <= bins; i ++)
        {
            g2d.drawLine(20 + (int)(i * binPixelWidth), height - 20, 20 + (int)(i * binPixelWidth), height - 15);
            String binText = ((Integer)(min + i * binWidth)).toString();
            int binStringWidth = g2d.getFontMetrics().stringWidth(binText);
            int binOffsetX = (-binStringWidth) / 2;
            int binOffsetY = 14;
            g2d.drawString(binText, 20 + binOffsetX + i * binPixelWidth, height - 15 + binOffsetY);
        }
    }

    protected double getMinSample() {return this.minSample;}
    protected double getMaxSample() {return this.maxSample;}
    public String getSampleType() {return this.sampleType;}

    public void addSamples(Sample[] samples)
    {
        for (Sample s : samples) this.sampleMeans.add(s.getSampleMean());
        double min = this.sampleMeans.get(0);
        double max = this.sampleMeans.get(0);
        double sum = 0;
        for (double d : this.sampleMeans)
        {
            if (d < min) min = d;
            if (d > max) max = d;
            sum += d;
        }
        this.minSample = min;
        this.maxSample = max;
        this.mean = sum / this.sampleMeans.size();
    }

    public double getSamplingDistributionSTDV()
    {
        double sum = 0;
        double mean = this.getSamplingDistributionMean();
        for (double d : sampleMeans)
        {
            sum += (d - mean) * (d - mean);
        }
        return Math.sqrt(sum / this.sampleMeans.size());
    }

    public int getSampleCount() {return this.sampleMeans.size();}
    public int getSampleSize() {return this.sampleSize;}
    public double getSamplingDistributionMean() {return this.mean;}
}
