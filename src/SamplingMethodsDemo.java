import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.*;

import DataElements.SettingsController;
import GUIElements.*;

public class SamplingMethodsDemo extends JPanel
{
    public final int START_DIMENSION = 10;

    private GraphArea graphArea = new GraphArea();
    private SamplingSpace samplingSpace = new SamplingSpace(START_DIMENSION, START_DIMENSION);
    private SettingsController settings = new SettingsController(samplingSpace, graphArea);
    private MenuBar menuBar = new MenuBar(START_DIMENSION, START_DIMENSION, settings);

    public SamplingMethodsDemo()
    {
        super();
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        this.add(menuBar, gbc);
        gbc.weightx = 1;
        gbc.gridx = 1;
        this.add(samplingSpace, gbc);
        gbc.weightx = 0.5;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        this.add(graphArea, gbc);
    }
}