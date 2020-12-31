import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Main extends JFrame {
    WindmillPanel wp = new WindmillPanel();
    int start = 0;

    public Main() {
        this.add(wp);
    }

    public static void main(String[] args) {
        JFrame frame = new Main();
        frame.setTitle("My JFrame");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    class WindmillPanel extends JPanel {
        int r = 100;

        public WindmillPanel() {
            Timer timer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    start++;
                    repaint();
                }

            });
            timer.start();
        }

        protected void paintComponent(Graphics g) {
            int x = getWidth() / 2 - r;
            int y = getHeight() / 2 - r;
            super.paintComponent(g);
            g.drawOval(x, y, 2 * r, 2 * r);
            g.setColor(Color.RED);
            g.fillArc(x + 10, y + 10, 2 * (r - 10), 2 * (r - 10), start, 60);
            g.fillArc(x + 10, y + 10, 2 * (r - 10), 2 * (r - 10), start + 90, 60);
            g.fillArc(x + 10, y + 10, 2 * (r - 10), 2 * (r - 10), start + 180, 60);
            g.fillArc(x + 10, y + 10, 2 * (r - 10), 2 * (r - 10), start + 270, 60);
        }
    }
}