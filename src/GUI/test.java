package GUI;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class test extends JTextArea {

    private OutputStream out = this.new PaneOutputStream();

    private Color candy = new Color(240, 240, 255);

    @Override
    public boolean isOpaque() {

        return false;
    }

    public OutputStream getOutputStream() {

        return out;
    }

    @Override
    protected void paintComponent(Graphics g) {

        int width = getWidth();
        int height = getHeight();

        Color old = g.getColor();
        g.setColor(getBackground());
        g.fillRect(0, 0, width, height);

        Rectangle r = new Rectangle();
        r.x = 0;
        r.y = 0;
        r.width = width;
        r.height = getRowHeight();

        g.setColor(candy);
        for (int heightIncrement = 2 * getRowHeight();
             r.y < height;
             r.y += heightIncrement) {

            g.fillRect(r.x, r.y, r.width, r.height);
        }
        g.setColor(old);

        super.paintComponent(g);
    }

    private class PaneOutputStream extends OutputStream {

        private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        private final Timer timer;

        private PaneOutputStream() {

            timer = new Timer(50, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    commit();
                }
            });
        }

        @Override
        public void write(int b) throws IOException {

            buffer.write(b);
            timer.restart();
        }

        private void commit() {

            assert SwingUtilities.isEventDispatchThread();

            timer.stop();
            if( buffer.size() > 0 ) {

                append(buffer.toString());
                buffer.reset();
            }
        }
    }
}