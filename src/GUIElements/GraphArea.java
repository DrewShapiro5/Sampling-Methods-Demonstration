package GUIElements;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import DataElements.Sample;

public class GraphArea extends JPanel implements ActionListener
{
    private ArrayList<Graph> graphs = new ArrayList<Graph>();
    private ArrayList<GraphInfo> infos = new ArrayList<GraphInfo>();
    private ArrayList<JButton> deleteButtons = new ArrayList<JButton>();

    public GraphArea()
    {
        this.setBackground(Color.darkGray);
    }

    public void addSamples(Sample[] samples)
    {
        if (samples.length == 0) return;
        if (samples[0] == null) return;
        String name = samples[0].getSampleType();
        int sampleSize = samples[0].getPlots().length;
        if (this.graphs.size() == 0)
        {
            this.graphs.add(new Graph(name, sampleSize, graphs));
            this.graphs.get(0).addSamples(samples);
            this.infos.add(new GraphInfo(this.graphs.get(0)));
            this.infos.get(0).updateInfo();
            this.deleteButtons.add(new JButton("Delete\ngraph"));
            this.deleteButtons.get(0).addActionListener(this);
            this.updateGraphOrganization();
            return;
        }
        for (int i = 0; i < graphs.size(); i ++)
        {
            if (this.graphs.get(i).getSampleType().equals(name))
            {
                this.graphs.get(i).addSamples(samples);
                this.graphs.get(i).repaint();
                this.infos.get(i).updateInfo();
                return;
            }
        }
        for (int i = 0; i < graphs.size(); i ++)
        {
            if (graphs.get(i).getSampleType().compareTo(name) > 0)
            {
                this.graphs.add(i, new Graph(name, sampleSize, graphs));
                this.graphs.get(i).addSamples(samples);
                this.infos.add(i, new GraphInfo(this.graphs.get(i)));
                this.infos.get(i).updateInfo();
                this.deleteButtons.add(i, new JButton("Delete\ngraph"));
                this.deleteButtons.get(i).addActionListener(this);
                this.updateGraphOrganization();
                return;
            }
        }
        this.graphs.add(new Graph(name, sampleSize, graphs));
        this.graphs.get(this.graphs.size() - 1).addSamples(samples);
        this.infos.add(new GraphInfo(this.graphs.get(this.graphs.size() - 1)));
        this.infos.get(this.infos.size() - 1).updateInfo();
        this.deleteButtons.add(new JButton("Delete\ngraph"));
        this.deleteButtons.get(this.deleteButtons.size() - 1).addActionListener(this);
        this.updateGraphOrganization();
        return;
    }

    private void updateGraphOrganization()
    {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        this.removeAll();
        if (graphs.size() == 0) return;
        gbc.fill = GridBagConstraints.BOTH;
        for (int i = 0; i < graphs.size() - 1; i ++)
        {
            gbc.gridx = i;
            gbc.gridy = 0;
            gbc.insets = new Insets(10, 10, 0, 0);
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            this.add(this.graphs.get(i), gbc);
            gbc.gridy = 1;
            gbc.insets = new Insets(10, 10, 0, 0);
            gbc.weightx = 0.0;
            gbc.weighty = 0.0;
            this.add(this.infos.get(i), gbc);
            gbc.gridy = 2;
            gbc.insets = new Insets(0, 10, 10, 0);
            this.add(this.deleteButtons.get(i), gbc);
        }
        gbc.gridx = graphs.size() - 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 0, 10);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        this.add(this.graphs.get(graphs.size() - 1), gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 0, 10);
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        this.add(this.infos.get(graphs.size() - 1), gbc);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 10, 10);
        this.add(this.deleteButtons.get(graphs.size() - 1), gbc);
        this.revalidate();
        this.repaint();
    }

    public void resetGraphs()
    {
        this.graphs = new ArrayList<Graph>();
        this.infos = new ArrayList<GraphInfo>();
        this.deleteButtons = new ArrayList<JButton>();
        this.removeAll();
        this.updateGraphOrganization();
        this.revalidate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        for (int i = 0; i < this.graphs.size(); i ++)
        {
            if (e.getSource() == this.deleteButtons.get(i))
            {
                this.graphs.remove(i);
                this.infos.remove(i);
                this.deleteButtons.remove(i);
                this.updateGraphOrganization();
                this.revalidate();
                this.repaint();
                return;
            }
        }
    }
}
