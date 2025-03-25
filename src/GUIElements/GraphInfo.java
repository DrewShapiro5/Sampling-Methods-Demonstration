package GUIElements;

import java.awt.Font;

import javax.swing.JTextArea;

public class GraphInfo extends JTextArea
{
    private Graph myGraph;

    public GraphInfo(Graph graph)
    {
        this.myGraph = graph;
        super.setEditable(false);
        super.setFont(new Font("Serif", Font.PLAIN, 14));
        super.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
    }

    public void updateInfo()
    {
        super.setText("Mean: " + ((float)Math.round(1000 * this.myGraph.getSamplingDistributionMean()) / 1000) +
        "\nStd. dev: " + ((float)Math.round(1000 * this.myGraph.getSamplingDistributionSTDV()) / 1000) + 
        "\nSample count: " + this.myGraph.getSampleCount() + 
        "\nSample size:" + this.myGraph.getSampleSize());
    }
}
