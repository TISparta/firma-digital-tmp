import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

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

    private View view;

    private Box horizontalBoxControls;
    private Box horizontalBoxView;
    private ControlPanel controlPanel;

    private Buttons buttons;

    private ImagePanel imagePanel;

    public PdfViewer(File document) throws Exception {
        initialize(document);
    }

    private void initialize(File file) throws Exception {
        PDDocument doc = PDDocument.load(file);
        filePath = file.getPath();
        initScreen(doc);
        initView(file);
        initPanelControls();
        initButtons();
        initPageNumber();
        initSelectedPage();
        addMouseListener();
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

    private void initView (File file) {
        frame = new JFrame();
        view = new View(frame, file);
    }

    private void initPanelControls () {
        horizontalBoxControls = Box.createHorizontalBox();
        horizontalBoxView = Box.createHorizontalBox();
        controlPanel = new ControlPanel(frame, horizontalBoxControls, horizontalBoxView);
    }

    private void initButtons () {
        buttons = new Buttons(horizontalBoxControls);
        buttons.getBtnFirstPage().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                selectPage(0);
            }
        });
        buttons.getBtnPreviousPage().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentPageIndex > 0) {
                    selectPage(currentPageIndex - 1);
                }
            }
        });
        buttons.getBtnNextPage().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentPageIndex < (numberOfPages - 1)) {
                    selectPage(currentPageIndex + 1);
                }
            }
        });
        buttons.getBtnLastPage().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectPage(numberOfPages - 1);
            }
        });
    }

    private void initPageNumber () {
        txtPageNumber = new JTextField();
        horizontalBoxControls.add(txtPageNumber);
        txtPageNumber.setHorizontalAlignment(SwingConstants.CENTER);
        txtPageNumber.setEditable(false);
        txtPageNumber.setPreferredSize(new Dimension(50, txtPageNumber.getPreferredSize().width));
        txtPageNumber.setColumns(10);
    }

    private void initSelectedPage () {
        panelSelectedPage = new JPanel();
        panelSelectedPage.setBackground(Color.LIGHT_GRAY);
        horizontalBoxView.add(panelSelectedPage);
        panelSelectedPage.setPreferredSize(new Dimension(width, height));
        panelSelectedPage.setBorder(new EmptyBorder(0, 0, 0, 0));
        panelSelectedPage.setLayout(new BorderLayout(0, 0));
        selectPage(0);
        view.finalSettings(frame);
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

    private void selectPage(int pageIndex) {
        imagePanel = view.selectPage(pageIndex, renderer, panelSelectedPage, imagePanel, width, height);
        pdfWidth = imagePanel.getPreferredSize().width;
        pdfHeight = imagePanel.getPreferredSize().height;
        currentPageIndex = pageIndex;
        String pageText = String.format("Page: %d / %d", pageIndex + 1, numberOfPages);
        txtPageNumber.setText(pageText);
        buttons.selectPageButtonsEffect(pageIndex, numberOfPages);
    }

}
