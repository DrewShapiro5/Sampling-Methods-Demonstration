package GUIElements;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import DataElements.Grid;

public class SamplingSpace extends JPanel
{
    private Grid grid;
    public SamplingSpace(int gridWidth, int gridHeight)
    {
        super();
        this.grid = new Grid(gridWidth, gridHeight);
        this.setBackground(new Color(20, 150, 50));
    }

    public Grid getGrid()
    {
        return this.grid;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        grid.drawGrid(g, this.getWidth(), this.getHeight());
    }
}