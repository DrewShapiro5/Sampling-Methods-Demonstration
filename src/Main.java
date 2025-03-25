import javax.swing.JFrame;

public class Main extends JFrame
{
    public static void main(String[] args)
    {
        new Main();
    }

    private SamplingMethodsDemo mainPanel;

    public Main()
    {
        super();
        mainPanel = new SamplingMethodsDemo();

        this.setSize(1000,800);
        this.setName("Sampling Methods Demonstration");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setVisible(true);
    }
}
