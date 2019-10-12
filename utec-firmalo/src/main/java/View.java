import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class View {
    public View (JFrame frame, File file) {
        frame.setResizable(false);
        frame.setTitle(file.getName());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void finalSettings (JFrame frame) {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.repaint();
    }

    public ImagePanel selectPage(int pageIndex, MyPDFRenderer renderer, JPanel panelSelectedPage, ImagePanel imagePanel, int width, int height) {
        BufferedImage renderImage = null;

        try {
            renderImage = renderer.renderImage(pageIndex, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imagePanel = new ImagePanel(renderImage, width, height);
        selectPageConfiguration(renderImage, pageIndex, panelSelectedPage, imagePanel, width, height);
        panelSelectedPage.revalidate();
        panelSelectedPage.repaint();
        return imagePanel;
    }

    private void selectPageConfiguration (BufferedImage renderImage, int pageIndex, JPanel panelSelectedPage, ImagePanel imagePanel, int width, int height) {
        panelSelectedPage.removeAll(); // Remove children
        imagePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        imagePanel.setLayout(new CardLayout(0, 0));
        imagePanel.setPreferredSize(new Dimension(width, height));
        panelSelectedPage.add(imagePanel, BorderLayout.CENTER);
    }
}
