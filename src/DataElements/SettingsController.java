package DataElements;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import GUIElements.GraphArea;
import GUIElements.SamplingSpace;

public class SettingsController extends TimerTask
{
    private SamplingSpace sampleSpace;
    private GridSampler sampler;
    private GraphArea graphs;
    private boolean doVisualDemonstration = true;

    private Timer timer = new Timer();
    private Queue<SampleDataContainer> sampleQueue = new LinkedList();

    public static final int STRATIFIED = 183;
    public static final int CLUSTER = 184;

    public SettingsController(SamplingSpace sampleSpace, GraphArea graphs)
    {
        this.sampleSpace = sampleSpace;
        this.sampler = new GridSampler();
        this.graphs = graphs;
        timer.schedule(this, 0, 50);
    }

    public void run()
    {
        SampleDataContainer current = this.sampleQueue.poll();
        if (current == null) return;
        int count = current.sampleCount;
        String sampleType = current.sampleType;
        Dimension strataDimensions = current.strataDimensions;
        Grid grid = current.grid;
        int sampleTypeConst = current.sampleTypeConst;
        Sample[] samples = new Sample[current.sampleCount];
        if (current.sampleType != null)
        for (int i = 0; i < count; i ++) samples[i] = this.sampler.takeSample(grid, (int)strataDimensions.getWidth(), (int)strataDimensions.getHeight(), sampleType, sampleTypeConst);
        else for (int i = 0; i < count; i ++) samples[i] = this.sampler.takeSample(grid);
        this.sampleSpace.getGrid().setHighlightedPlots(samples[samples.length - 1].getPlots());
        this.sampleSpace.repaint();
        this.graphs.addSamples(samples);
    }

    public void flushQueue()
    {
        if (this.sampleQueue.isEmpty()) return;
        SampleDataContainer[] takeSampleArray = new SampleDataContainer[sampleQueue.size()];
        takeSampleArray = sampleQueue.toArray(takeSampleArray);
        sampleQueue.clear();
        ArrayList<Sample> samples = new ArrayList<Sample>();
        for (SampleDataContainer current : takeSampleArray)
        {
            if (current == null) continue;
            int count = current.sampleCount;
            String sampleType = current.sampleType;
            Dimension strataDimensions = current.strataDimensions;
            Grid grid = current.grid;
            int sampleTypeConst = current.sampleTypeConst;
            if (current.sampleType != null)
            for (int i = 0; i < count; i ++) samples.add(this.sampler.takeSample(grid, (int)strataDimensions.getWidth(), (int)strataDimensions.getHeight(), sampleType, sampleTypeConst));
            else for (int i = 0; i < count; i ++) samples.add(this.sampler.takeSample(grid));
        }
        Sample[] sampleArray = new Sample[samples.size()];
        this.graphs.addSamples(samples.toArray(sampleArray));
        this.sampleSpace.getGrid().setHighlightedPlots(sampleArray[sampleArray.length - 1].getPlots());
        this.sampleSpace.repaint();
    }

    private class SampleDataContainer
    {
        public int sampleCount;
        public Grid grid;
        public Dimension strataDimensions;
        public String sampleType;
        public int sampleTypeConst;
        public SampleDataContainer(int sampleCount, Grid grid, Dimension strataDimensions, String sampleType, int sampleTypeConst)
        {
            this.sampleCount = sampleCount;
            this.grid = grid;
            this.strataDimensions = strataDimensions;
            this.sampleType = sampleType;
            this.sampleTypeConst = sampleTypeConst;
        }
    }

    public void resizeGrid(int gridWidth, int gridHeight) throws IllegalArgumentException
    {
        if (gridWidth < 4 || gridHeight < 4) 
            throw new IllegalArgumentException("Dimensions must be greater than 3");
        if (gridWidth > 50 || gridHeight > 50) 
            throw new IllegalArgumentException("Dimensions must be less than or equal to 50");
        if ((0.0 + gridWidth) / gridHeight > 4 || (0.0 + gridHeight) / gridWidth > 4) 
            throw new IllegalArgumentException("One dimension cannot be more than 4 times the other");
        if (gridWidth < 0 || gridHeight < 0) 
            throw new IllegalArgumentException("Dimensions cannot be negative");
        this.sampleSpace.getGrid().resetGrid(gridWidth, gridHeight);
        this.sampleSpace.getGrid().setStrataDimensions(-1, -1);
        this.sampleSpace.repaint();
        this.graphs.resetGraphs();
    }

    public void disableStrata()
    {
        Grid g = this.sampleSpace.getGrid();
        g.setStrataDimensions(-1, -1);
        g.resetHighlightedPlots();
        this.sampleSpace.repaint();
    }

    public void setColumnStrata()
    {
        Grid g = this.sampleSpace.getGrid();
        g.setStrataDimensions(1, g.getGridHeight());
        g.resetHighlightedPlots();
        this.sampleSpace.repaint();
    }

    public void setRowStrata()
    {
        Grid g = this.sampleSpace.getGrid();
        g.setStrataDimensions(g.getGridWidth(), 1);
        g.resetHighlightedPlots();
        this.sampleSpace.repaint();
    }

    public void setCustomStrata(int width, int height)
    {
        Grid g = this.sampleSpace.getGrid();
        g.setStrataDimensions(width, height);
        g.resetHighlightedPlots();
        this.sampleSpace.repaint();
    }

    public void takeSRS(int count)
    {

        if (!this.doVisualDemonstration) {this.sampleQueue.add(new SampleDataContainer(count, this.sampleSpace.getGrid(), null, null, 0));}
        else
        {
            int countPerContainer = count / 100;
            if (countPerContainer < 1) countPerContainer = 1;
            for (int i = 0; i < count / countPerContainer; i ++) this.sampleQueue.add(new SampleDataContainer(countPerContainer, this.sampleSpace.getGrid(), null, null, 0));
            if (count % countPerContainer != 0) this.sampleQueue.add(new SampleDataContainer(count % countPerContainer, this.sampleSpace.getGrid(), null, null, 0));
        }
    }

    public void takeGroupS(int count, String sampleType, int sampleTypeConst) throws IllegalStateException
    {
        Grid grid = this.sampleSpace.getGrid();
        Dimension strataDimensions = this.sampleSpace.getGrid().getStrataDimension();
        int sampleTypeConstant;
        String exceptionSampleTypeString = "";
        String sampleTypePrefix = "";
        if (sampleTypeConst == STRATIFIED)
        {
            sampleTypeConstant = GridSampler.STRATIFIED;
            exceptionSampleTypeString = "Strata";
            sampleTypePrefix = "Stratify ";
        }
        else if (sampleTypeConst == CLUSTER)
        {
            sampleTypeConstant = GridSampler.CLUSTER;
            exceptionSampleTypeString = "Cluster";
            sampleTypePrefix = "Cluster ";
        }
        else throw (new IllegalArgumentException("Invalid sampleTypeConst"));
        String sampleTypeCombinedString = sampleTypePrefix + sampleType;
        if (!sampleType.equals("by row") && !sampleType.equals("by column"))
        {
            if (strataDimensions.getWidth() == 1 && strataDimensions.getHeight() == grid.getGridHeight())
                throw (new IllegalStateException("Dimensions equal to column"));
            if (strataDimensions.getWidth() == grid.getGridWidth() && strataDimensions.getHeight() == 1) 
                throw (new IllegalStateException("Dimensions equal to row"));
            if (strataDimensions.getWidth() <= 0 || strataDimensions.getHeight() <= 0) 
                throw (new IllegalStateException("Input valid " + exceptionSampleTypeString.toLowerCase() + " dimensions"));
            if (grid.getGridWidth() % strataDimensions.getWidth() != 0 || grid.getGridHeight() % strataDimensions.getHeight() != 0) 
                throw (new IllegalStateException(exceptionSampleTypeString + " dimensions do not fit in the grid"));
            if ((grid.getGridWidth() * grid.getGridHeight()) / (strataDimensions.getWidth() * strataDimensions.getHeight()) < 
                Math.sqrt(grid.getGridWidth() * grid.getGridHeight()) / 3)
                throw (new IllegalStateException(exceptionSampleTypeString + " dimensions are too large"));
            if ((grid.getGridWidth() * grid.getGridHeight()) / (strataDimensions.getWidth() * strataDimensions.getHeight()) >
                Math.sqrt(grid.getGridWidth() * grid.getGridHeight()) * 2)
                throw (new IllegalStateException(exceptionSampleTypeString + " dimensions are too small"));
        }
        if (!this.doVisualDemonstration) {this.sampleQueue.add(new SampleDataContainer(count, grid, strataDimensions, sampleTypeCombinedString, sampleTypeConstant));}
        else
        {
            int countPerContainer = count / 100;
            if (countPerContainer < 1) countPerContainer = 1;
            for (int i = 0; i < count / countPerContainer; i ++) this.sampleQueue.add(new SampleDataContainer(countPerContainer, grid, strataDimensions, sampleTypeCombinedString, sampleTypeConstant));
            if (count % countPerContainer != 0) this.sampleQueue.add(new SampleDataContainer(count % countPerContainer, grid, strataDimensions, sampleTypeCombinedString, sampleTypeConstant));
        }
    }

    public void setVisualDemonstration(boolean display)
    {
        this.sampleSpace.getGrid().setHighlightPlots(display);
        this.sampleSpace.repaint();
        this.doVisualDemonstration = display;
    }

    public void setDisplayData(boolean display)
    {
        this.sampleSpace.getGrid().setDisplayData(display);
        this.sampleSpace.repaint();
    }

    public void setShowGridLabels(boolean display)
    {
        this.sampleSpace.getGrid().setShowGridLabels(display);
        this.sampleSpace.repaint();
    }

    public int getGridWidth() {return this.sampleSpace.getGrid().getGridWidth();}
    public int getGridHeight() {return this.sampleSpace.getGrid().getGridHeight();}
}
