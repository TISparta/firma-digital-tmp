import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PdfViewer {

    private float pdfWidth;
    private float pdfHeight;

    private String filePath;
    private JFrame frame;
    private MyPDFRenderer renderer;
    private JPanel panelSelectedPage;

    private int numberOfPages;
    private int currentPageIndex = 0;

    private int width;
    private int height;

    private JTextField txtPageNumber;
    private JButton btnLastPage;
    private JButton btnNextPage;
    private JButton btnPreviousPage;
    private JButton btnFirstPage;

    private JPanel panelControls;
    private Box horizontalBoxControls;
    private Box horizontalBoxView;
    private ImagePanel imagePanel;

    public PdfViewer(File document) throws Exception {
        initialize(document);
    }

    private void initialize(File file) throws Exception {
        PDDocument doc = PDDocument.load(file);
        filePath = file.getPath();
        initScreen(doc);
        initFrame(file);
        initPanelControls();
        initButtonFirstPage();
        initButtonPreviousPage();
        initButtonNextPage();
        initPageNumber();
        initButtonLastPage();
        initSelectedPage();
        initFinalView();
        addMouseListener();
    }

    private void selectPage(int pageIndex) {
        BufferedImage renderImage = null;

        try {
            renderImage = renderer.renderImage(pageIndex, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        selectPageConfiguration(renderImage, pageIndex);
        selectPageButtonsEffect(pageIndex);
        panelSelectedPage.revalidate();
        panelSelectedPage.repaint();
        pdfWidth = imagePanel.getPreferredSize().width;
        pdfHeight = imagePanel.getPreferredSize().height;
    }

    private void enableDisableButtons(int actionIndex) {
        switch (actionIndex) {
            case 0:
                btnFirstPage.setEnabled(false);
                btnPreviousPage.setEnabled(false);
                btnNextPage.setEnabled(true);
                btnLastPage.setEnabled(true);
                break;
            case 1:
                btnFirstPage.setEnabled(true);
                btnPreviousPage.setEnabled(true);
                btnNextPage.setEnabled(false);
                btnLastPage.setEnabled(false);
                break;
            default:
                btnFirstPage.setEnabled(true);
                btnPreviousPage.setEnabled(true);
                btnNextPage.setEnabled(true);
                btnLastPage.setEnabled(true);
        }
    }

    private void selectPageConfiguration (BufferedImage renderImage, int pageIndex) {
        panelSelectedPage.removeAll(); // Remove children
        imagePanel = new ImagePanel(renderImage, width, height);
        imagePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        imagePanel.setLayout(new CardLayout(0, 0));
        imagePanel.setPreferredSize(new Dimension(width, height));
        panelSelectedPage.add(imagePanel, BorderLayout.CENTER);
        currentPageIndex = pageIndex;
        String pageText = String.format("Page: %d / %d", pageIndex + 1, numberOfPages);
        txtPageNumber.setText(pageText);
    }

    private void selectPageButtonsEffect (int pageIndex) {
        if (pageIndex == 0) {
            enableDisableButtons(0);
        } else if (pageIndex == (numberOfPages - 1)) {
            enableDisableButtons(1);
        } else {
            enableDisableButtons(-1);
        }
    }

    private void initScreen (PDDocument doc) {
        numberOfPages = doc.getNumberOfPages();
        renderer = new MyPDFRenderer(doc);
        // Getting/calculating screen dimensions...
        float realWidth = new Float(doc.getPage(0).getMediaBox().getWidth());
        float realHeight = new Float(doc.getPage(0).getMediaBox().getHeight());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Double ratio = 0.8;
        height = (int) (screenSize.getHeight() * ratio);
        width = (int) ((height * realWidth) / realHeight);
    }

    private void initButtonFirstPage () {
        btnFirstPage = new JButton("First Page");
        btnFirstPage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                selectPage(0);
            }
        });
        horizontalBoxControls.add(btnFirstPage);
        Component horizontalStrutLeft_1 = Box.createHorizontalStrut(10);
        horizontalBoxControls.add(horizontalStrutLeft_1);
    }

    private void initButtonPreviousPage () {
        btnPreviousPage = new JButton("Previous Page");
        btnPreviousPage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentPageIndex > 0) {
                    selectPage(currentPageIndex - 1);
                }
            }
        });
        horizontalBoxControls.add(btnPreviousPage);
        Component horizontalStrutLeft_2 = Box.createHorizontalStrut(10);
        horizontalBoxControls.add(horizontalStrutLeft_2);
    }

    private void initButtonNextPage () {
        btnNextPage = new JButton("Next Page");
        btnNextPage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentPageIndex < (numberOfPages - 1)) {
                    selectPage(currentPageIndex + 1);
                }
            }
        });
        horizontalBoxControls.add(btnNextPage);
    }

    private void initButtonLastPage () {
        btnLastPage = new JButton("Last Page");
        btnLastPage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectPage(numberOfPages - 1);
            }
        });
        horizontalBoxControls.add(btnLastPage);
    }

    private void initPageNumber () {
        txtPageNumber = new JTextField();
        horizontalBoxControls.add(txtPageNumber);
        txtPageNumber.setHorizontalAlignment(SwingConstants.CENTER);
        txtPageNumber.setEditable(false);
        txtPageNumber.setPreferredSize(new Dimension(50, txtPageNumber.getPreferredSize().width));
        txtPageNumber.setColumns(10);
    }

    private void initPanelControls () {
        panelControls = new JPanel();
        frame.getContentPane().add(panelControls, BorderLayout.SOUTH);
        panelControls.setLayout(new BorderLayout(0, 0));
        initHorizontalConfiguration();
        initVerticalConfiguration();
    }

    private void initHorizontalConfiguration () {
        horizontalBoxControls = Box.createHorizontalBox();
        panelControls.add(horizontalBoxControls);
        horizontalBoxView = Box.createHorizontalBox();
        initHorizontalLeftConfiguration();
        initHorizontalRightConfiguration();
    }

    private void initHorizontalLeftConfiguration () {
        Component horizontalStrutLeft = Box.createHorizontalStrut(10);
        horizontalBoxControls.add(horizontalStrutLeft);
        Component horizontalStrutViewLeft = Box.createHorizontalStrut(10);
        horizontalBoxView.add(horizontalStrutViewLeft);
    }

    private void initHorizontalRightConfiguration () {
        Component horizontalStrutRight = Box.createHorizontalStrut(10);
        horizontalBoxControls.add(horizontalStrutRight);
        Component horizontalStrutRight_2 = Box.createHorizontalStrut(10);
        horizontalBoxControls.add(horizontalStrutRight_2);
        Component horizontalStrutRight_1 = Box.createHorizontalStrut(10);
        horizontalBoxControls.add(horizontalStrutRight_1);
        Component horizontalStrutViewRight = Box.createHorizontalStrut(10);
        horizontalBoxView.add(horizontalStrutViewRight);
    }

    private void initVerticalConfiguration () {
        Component verticalStrutTop = Box.createVerticalStrut(10);
        panelControls.add(verticalStrutTop, BorderLayout.NORTH);
        Component verticalStrutBottom = Box.createVerticalStrut(10);
        panelControls.add(verticalStrutBottom, BorderLayout.SOUTH);
        Box verticalBoxView = Box.createVerticalBox();
        frame.getContentPane().add(verticalBoxView, BorderLayout.WEST);
        Component verticalStrutView = Box.createVerticalStrut(10);
        verticalBoxView.add(verticalStrutView);
        verticalBoxView.add(horizontalBoxView);
    }

    private void initSelectedPage () {
        panelSelectedPage = new JPanel();
        panelSelectedPage.setBackground(Color.LIGHT_GRAY);
        horizontalBoxView.add(panelSelectedPage);
        panelSelectedPage.setPreferredSize(new Dimension(width, height));
        panelSelectedPage.setBorder(new EmptyBorder(0, 0, 0, 0));
        panelSelectedPage.setLayout(new BorderLayout(0, 0));
    }

    private void initFrame (File file) {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setTitle(file.getName());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initFinalView () {
        selectPage(0);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.repaint();
    }

    private void addMouseListener () {
        panelSelectedPage.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed (MouseEvent e) {
                System.out.println("Signing");
                System.out.println(e.getX() + "," + e.getY());
                float x = e.getX();
                float y = pdfHeight - e.getY();
                try {
                    Firma.sign(filePath, Firma.pathBase + "keyStore.p12", "output.pdf", x, y);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
}
