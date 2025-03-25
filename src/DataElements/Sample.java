package DataElements;

public class Sample
{
    private Plot[] plots;
    private String sampleType;

    public Sample(Plot[] plots, String sampleType)
    {
        this.plots = plots;
        this.sampleType = sampleType;
    }

    public Plot[] getPlots() {return this.plots;}
    public String getSampleType() {return this.sampleType;}
    public double getSampleMean()
    {
        double sum = 0;
        for (Plot p : this.plots)
        {
            sum += p.getYield();
        }
        return sum / this.plots.length;
    }
}
