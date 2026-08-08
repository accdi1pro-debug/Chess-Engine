import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Main extends JPanel implements ActionListener  { 
    public Timer timer ;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mine Sweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create an instance of your custom class
        Main gamePanel = new Main();

        frame.add(gamePanel);
        frame.pack(); // Shrinks the window perfectly around your 600x600 panel
        frame.setLocationRelativeTo(null); // Centers the window on your screen
        frame.setVisible(true);
    }

    public Main (){
        this.setPreferredSize(new Dimension(1000, 1000));

        timer = new Timer(10, this); 
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Timer tick: update game state if needed and repaint
        
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clears the screen
        for (int c = 0; c < 8; c++) {
            for (int r = 0; r < 8; r++) {
                if (r %2 ==0){
                    g.setColor(Color.black);
                    if (c %2 ==0){
                        g.setColor(Color.white);
                    }
                }
                if (r %2 !=0){
                    g.setColor(Color.white);
                    if (c %2 ==0){
                        g.setColor(Color.black);
                    }
                }
                
                g.fillRect(r*100 + 100 , c*100 +100, 100, 100);
            }
        }
    }
}
