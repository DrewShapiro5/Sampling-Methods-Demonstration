package GUIElements;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import DataElements.SettingsController;

public class MenuBar extends JPanel implements ActionListener, DocumentListener
{
    private SettingsController settings;

    //GUI elements for resizing the grid
    private DimensionField gridWidthField;
    private DimensionField gridHeightField;
    private JButton resizeGridButton = new JButton("Reset sample space");

    //GUI elements for taking samples
    private JCheckBox visualDemonstrationBox = new JCheckBox("Show sample visual", true);
    private DimensionField sampleCountField = new DimensionField("1", 6);
    private JButton takeSampleButton = new JButton("take samples");

    private JRadioButton srsButton = new JRadioButton("Simple random sample");
    private JRadioButton stratifiedButton = new JRadioButton("Stratified random sample");
    private JRadioButton clusterButton = new JRadioButton("Cluster sample");
    private JRadioButton columnButton = new JRadioButton("Column");
    private JRadioButton rowButton = new JRadioButton("Row");
    private JRadioButton customButton = new JRadioButton("Custom");
    private DimensionField strataWidthField;
    private DimensionField strataHeightField;

    //GUI elements for options panel
    private JCheckBox showDataBox = new JCheckBox("Show data", false);
    private JCheckBox showGridLabelsBox = new JCheckBox("Show grid labels", true);

    public MenuBar(int gridWidth, int gridHeight, SettingsController settings)
    {
        this.settings = settings;

        this.gridWidthField = new DimensionField(String.valueOf(gridWidth), 2);
        this.gridHeightField = new DimensionField(String.valueOf(gridHeight), 2);
        this.gridWidthField.setHorizontalAlignment(DimensionField.CENTER);
        this.gridHeightField.setHorizontalAlignment(DimensionField.CENTER);

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 0, 10);
        this.add(createResizePanel(), gbc);
        gbc.gridy = 1;
        this.add(createSampleTypePanel(), gbc);
        gbc.gridy = 2;
        this.add(createStrataSelectionPanel(), gbc);
        gbc.gridy = 3;
        this.add(createTakeSamplePanel(), gbc);
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.add(createOptionsPanel(), gbc);

        this.resizeGridButton.addActionListener(this);
        this.visualDemonstrationBox.addActionListener(this);
        this.showDataBox.addActionListener(this);
        this.takeSampleButton.addActionListener(this);
        this.srsButton.addActionListener(this);
        this.stratifiedButton.addActionListener(this);
        this.clusterButton.addActionListener(this);
        this.columnButton.addActionListener(this);
        this.rowButton.addActionListener(this);
        this.customButton.addActionListener(this);
        this.strataWidthField.getDocument().addDocumentListener(this);
        this.strataHeightField.getDocument().addDocumentListener(this);
        this.showGridLabelsBox.addActionListener(this);
        resetButtons();

        this.setBackground(Color.lightGray);
    }

    private JPanel createResizePanel()
    {
        JPanel resizePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel gridWidthLabel = new JLabel("Grid width");
        JLabel gridHeightLabel = new JLabel("Grid height");
        gridWidthLabel.setHorizontalAlignment(JLabel.CENTER);
        gridHeightLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        resizePanel.add(this.gridWidthField, gbc);
        gbc.gridx = 1;
        resizePanel.add(this.gridHeightField, gbc);
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 10;
        resizePanel.add(gridWidthLabel, gbc);
        gbc.gridx = 1;
        resizePanel.add(gridHeightLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        resizePanel.add(this.resizeGridButton, gbc);

        return resizePanel;
    }

    private JPanel createSampleTypePanel()
    {
        JPanel sampleTypePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel sampleTypeLabel = new JLabel("Sample type");
        sampleTypeLabel.setHorizontalAlignment(JLabel.CENTER);
        sampleTypeLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        sampleTypePanel.add(sampleTypeLabel, gbc);
        gbc.gridy = 1;
        sampleTypePanel.add(this.srsButton, gbc);
        gbc.gridy = 2;
        sampleTypePanel.add(this.stratifiedButton, gbc);
        gbc.gridy = 3;
        sampleTypePanel.add(this.clusterButton, gbc);
        return sampleTypePanel;
    }

    private JPanel createStrataSelectionPanel()
    {
        JPanel strataSelectionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel stratifyByLabel = new JLabel("Group by");
        stratifyByLabel.setHorizontalAlignment(JLabel.CENTER);
        stratifyByLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        JLabel strataWidthLabel = new JLabel("Custom width");
        JLabel strataHeightLabel = new JLabel("Custom height");
        strataWidthLabel.setHorizontalAlignment(JLabel.CENTER);
        strataHeightLabel.setHorizontalAlignment(JLabel.CENTER);
        this.strataWidthField = new DimensionField("", 2);
        this.strataHeightField = new DimensionField("", 2);
        this.strataWidthField.setHorizontalAlignment(DimensionField.CENTER);
        this.strataHeightField.setHorizontalAlignment(DimensionField.CENTER);
        this.strataWidthField.setEnabled(false);
        this.strataHeightField.setEnabled(false);
        gbc.gridwidth = 2;
        strataSelectionPanel.add(stratifyByLabel, gbc);
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        strataSelectionPanel.add(this.columnButton, gbc);
        gbc.gridx = 1;
        strataSelectionPanel.add(this.rowButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        strataSelectionPanel.add(this.customButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        strataSelectionPanel.add(this.strataWidthField, gbc);
        gbc.gridx = 1;
        strataSelectionPanel.add(this.strataHeightField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        strataSelectionPanel.add(strataWidthLabel, gbc);
        gbc.gridx = 1;
        strataSelectionPanel.add(strataHeightLabel, gbc);
        return strataSelectionPanel;
    }

    private JPanel createTakeSamplePanel()
    {
        JPanel takeSamplePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        takeSamplePanel.add(this.visualDemonstrationBox, gbc);
        gbc.gridy = 1;
        JPanel sampleCountPanel = new JPanel(new GridLayout(2, 1));
        JLabel sampleCountLabel = new JLabel("Sample count");
        sampleCountLabel.setHorizontalAlignment(JLabel.CENTER);
        sampleCountPanel.add(this.sampleCountField);
        sampleCountPanel.add(sampleCountLabel);
        takeSamplePanel.add(sampleCountPanel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridheight = 2;
        takeSamplePanel.add(this.takeSampleButton, gbc);

        return takeSamplePanel;
    }

    private JPanel createOptionsPanel()
    {
        JPanel optionsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel optionsLabel = new JLabel("Options");
        optionsLabel.setHorizontalAlignment(JLabel.CENTER);
        optionsLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        optionsPanel.add(optionsLabel, gbc);
        gbc.gridy = 1;
        optionsPanel.add(this.showDataBox, gbc);
        gbc.gridy = 2;
        optionsPanel.add(this.showGridLabelsBox, gbc);
        
        return optionsPanel;
    }

    private void resetButtons()
    {
        this.srsButton.setSelected(true);
        this.stratifiedButton.setSelected(false);
        this.clusterButton.setSelected(false);
        this.columnButton.setSelected(false);
        this.rowButton.setSelected(false);
        this.customButton.setSelected(false);
        this.sampleCountField.setText("1");
        this.settings.disableStrata();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if ((e.getSource() == this.takeSampleButton || e.getSource() == this.resizeGridButton || e.getSource() == this.visualDemonstrationBox)) this.settings.flushQueue();
        if (e.getSource() == this.resizeGridButton)
        {
            try
            {
                this.settings.resizeGrid(Integer.parseInt(this.gridWidthField.getText()), Integer.parseInt(this.gridHeightField.getText()));
                this.strataWidthField.setText("");
                this.strataHeightField.setText("");
                resetButtons();
            }
            catch (Exception exc)
            {
                gridWidthField.setText(((Integer)this.settings.getGridWidth()).toString());
                gridHeightField.setText(((Integer)this.settings.getGridHeight()).toString());
                JOptionPane.showMessageDialog(null, exc.getMessage(), "Unable to resize grid:", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if (e.getSource() == this.srsButton)
        {
            if (!this.srsButton.isSelected()) this.srsButton.setSelected(true);
            else
            {
                this.stratifiedButton.setSelected(false);
                this.clusterButton.setSelected(false);
                this.columnButton.setSelected(false);
                this.rowButton.setSelected(false);
                this.customButton.setSelected(false);
                this.strataWidthField.setEnabled(false);
                this.strataHeightField.setEnabled(false);
                this.settings.disableStrata();
            }
        }
        else if (e.getSource() == this.stratifiedButton)
        {
            if (!this.stratifiedButton.isSelected()) this.stratifiedButton.setSelected(true);
            else if (this.srsButton.isSelected())
            {
                this.srsButton.setSelected(false);
                this.columnButton.setSelected(true);
                this.settings.setColumnStrata();
            }
            else
            {
                this.clusterButton.setSelected(false);
            }
        }
        else if (e.getSource() == this.clusterButton)
        {
            if (!this.clusterButton.isSelected()) this.clusterButton.setSelected(true);
            else if (this.srsButton.isSelected())
            {
                this.srsButton.setSelected(false);
                this.columnButton.setSelected(true);
                this.settings.setColumnStrata();
            }
            else
            {
                this.stratifiedButton.setSelected(false);
            }
        }
        else if (e.getSource() == this.columnButton)
        {
            if (!this.columnButton.isSelected()) this.columnButton.setSelected(true);
            else if (this.srsButton.isSelected())
            {
                this.srsButton.setSelected(false);
                this.stratifiedButton.setSelected(true);
                this.settings.setColumnStrata();
            }
            else
            {
                this.rowButton.setSelected(false);
                this.customButton.setSelected(false);
                this.settings.setColumnStrata();
                this.strataWidthField.setEnabled(false);
                this.strataHeightField.setEnabled(false);
            }
        }
        else if (e.getSource() == this.rowButton)
        {
            if (!this.rowButton.isSelected()) this.rowButton.setSelected(true);
            else if (this.srsButton.isSelected())
            {
                this.srsButton.setSelected(false);
                this.stratifiedButton.setSelected(true);
                this.settings.setRowStrata();
            }
            else
            {
                this.columnButton.setSelected(false);
                this.customButton.setSelected(false);
                this.settings.setRowStrata();
                this.strataWidthField.setEnabled(false);
                this.strataHeightField.setEnabled(false);
            }
        }
        else if (e.getSource() == this.customButton)
        {
            if (!this.customButton.isSelected()) this.customButton.setSelected(true);
            else if (this.srsButton.isSelected())
            {
                this.srsButton.setSelected(false);
                this.stratifiedButton.setSelected(true);
                this.strataWidthField.setEnabled(true);
                this.strataHeightField.setEnabled(true);
                try {this.settings.setCustomStrata(Integer.parseInt(this.strataWidthField.getText()), Integer.parseInt(this.strataHeightField.getText()));}
                catch (NumberFormatException exc) {this.settings.disableStrata();}
            }
            else
            {
                this.columnButton.setSelected(false);
                this.rowButton.setSelected(false);
                this.strataWidthField.setEnabled(true);
                this.strataHeightField.setEnabled(true);
                try {this.settings.setCustomStrata(Integer.parseInt(this.strataWidthField.getText()), Integer.parseInt(this.strataHeightField.getText()));}
                catch (NumberFormatException exc) {this.settings.disableStrata();}
            }
        }
        else if (e.getSource() == this.showDataBox)
        {
            if (this.showDataBox.isSelected()) this.settings.setDisplayData(true);
            else this.settings.setDisplayData(false);
        }
        else if (e.getSource() == this.showGridLabelsBox)
        {
            if (this.showGridLabelsBox.isSelected()) this.settings.setShowGridLabels(true);
            else this.settings.setShowGridLabels(false);
        }
        else if (e.getSource() == this.visualDemonstrationBox)
        {
            this.settings.setVisualDemonstration(this.visualDemonstrationBox.isSelected());
        }
        else if (e.getSource() == this.takeSampleButton)
        {
            int sampleTypeConst = SettingsController.STRATIFIED;
            try
            {
                if (this.srsButton.isSelected())
                {
                    this.settings.takeSRS(Integer.parseInt(this.sampleCountField.getText()));
                    return;
                }
                else if (this.stratifiedButton.isSelected()) sampleTypeConst = SettingsController.STRATIFIED;
                else sampleTypeConst = SettingsController.CLUSTER;
                if (this.columnButton.isSelected())
                    this.settings.takeGroupS(Integer.parseInt(this.sampleCountField.getText()), "by column", sampleTypeConst);
                else if (this.rowButton.isSelected())
                    this.settings.takeGroupS(Integer.parseInt(this.sampleCountField.getText()), "by row", sampleTypeConst);
                else if (this.customButton.isSelected())
                    this.settings.takeGroupS(Integer.parseInt(
                        this.sampleCountField.getText()),
                        "by custom: " + this.strataWidthField.getText() + "x" + this.strataHeightField.getText(), sampleTypeConst
                        );
                if (!this.visualDemonstrationBox.isSelected()) this.settings.flushQueue();
            }
            catch (NumberFormatException exc)
            {
                JOptionPane.showMessageDialog(null, "Input strata dimensions", "Unable to take sample:", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (IllegalStateException exc)
            {
                switch (exc.getMessage())
                {
                    case "Dimensions are equal column":
                        this.customButton.setSelected(false);
                        this.columnButton.setSelected(true);
                        this.settings.setColumnStrata();
                        this.strataWidthField.setEnabled(false);
                        this.strataHeightField.setEnabled(false);
                        this.settings.takeGroupS(Integer.parseInt(this.sampleCountField.getText()), "by column", sampleTypeConst);
                        break;
                    case "Dimensions are equal to row":
                        this.customButton.setSelected(false);
                        this.rowButton.setSelected(true);
                        this.settings.setRowStrata();
                        this.strataWidthField.setEnabled(false);
                        this.strataHeightField.setEnabled(false);
                        this.settings.takeGroupS(Integer.parseInt(this.sampleCountField.getText()), "by row", sampleTypeConst);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, exc.getMessage(), "Unable to take sample:", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e)
    {
        try {this.settings.setCustomStrata(Integer.parseInt(this.strataWidthField.getText()), Integer.parseInt(this.strataHeightField.getText()));}
        catch (NumberFormatException exc) {this.settings.disableStrata();}
    }

    @Override
    public void insertUpdate(DocumentEvent e)
    {
        try {this.settings.setCustomStrata(Integer.parseInt(this.strataWidthField.getText()), Integer.parseInt(this.strataHeightField.getText()));}
        catch (NumberFormatException exc) {this.settings.disableStrata();}
    }

    @Override
    public void removeUpdate(DocumentEvent e)
    {
        try {this.settings.setCustomStrata(Integer.parseInt(this.strataWidthField.getText()), Integer.parseInt(this.strataHeightField.getText()));}
        catch (NumberFormatException exc) {this.settings.disableStrata();}
    }
}