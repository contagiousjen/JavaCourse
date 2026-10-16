package mandelbrot;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MandelbrotPanel extends JPanel {

    private static final int MAX_ITER = 256;

    private double xMin = -2.5;
    private double xMax = 1.0;
    private double yMin = -1.25;
    private double yMax = 1.25;

    private BufferedImage image;

    public MandelbrotPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        render();
    }

    private void render() {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int px = 0; px < width; px++) {
            for (int py = 0; py < height; py++) {
                double cx = xMin + (xMax - xMin) * px / width;
                double cy = yMin + (yMax - yMin) * py / height;

                int iter = iterate(cx, cy);
                image.setRGB(px, py, toColor(iter));
            }
        }
    }

    private int iterate(double cx, double cy) {
        double zx = 0, zy = 0;
        for (int i = 0; i < MAX_ITER; i++) {
            double zx2 = zx * zx - zy * zy + cx;
            double zy2 = 2 * zx * zy + cy;
            zx = zx2;
            zy = zy2;
            if (zx * zx + zy * zy > 4) return i;
        }
        return MAX_ITER;
    }

    private int toColor(int iter) {
        if (iter == MAX_ITER) return Color.BLACK.getRGB();
        float hue = (float) iter / MAX_ITER;
        return Color.HSBtoRGB(hue, 0.8f, 1.0f);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}
