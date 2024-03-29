import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class UI {

    private JFrame frmDocumentSigner;
    private JTextField txtDocPath;
    private File selectedFile;
    private JPanel panelContainer;
    private GroupLayout gl_panelContainer;
    private JButton btnOpenDocument;
    private JButton btnPreviewDocument;
    private JLabel lblFullPath;

    public static void main (String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run () {
                try {
                    UI window = new UI();
                    window.frmDocumentSigner.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public UI () {
        createWindow();
        addButtonOpenDocument();
        addButtonPreviewDocument();
        configureTxtDocPath();
        addHorizontalGroup();
        addVerticalGroup();
    }

    private void createWindow () {
        frmDocumentSigner = new JFrame();
        frmDocumentSigner.setResizable(false);
        frmDocumentSigner.setTitle("Document Signer");
        frmDocumentSigner.setBounds(100, 100, 600, 600);
        frmDocumentSigner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmDocumentSigner.setLocationRelativeTo(null);
        panelContainer = new JPanel();
        frmDocumentSigner.getContentPane().add(panelContainer, BorderLayout.CENTER);
    }

    private void addButtonOpenDocument () {
        btnOpenDocument = new JButton("Open Document");
        btnOpenDocument.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("PDF document", "pdf"));
                if (fileChooser.showOpenDialog(frmDocumentSigner) == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    txtDocPath.setText(selectedFile.getAbsolutePath());
                }
            }
        });
    }

    private void addButtonPreviewDocument () {
        btnPreviewDocument = new JButton("Preview Document");
        btnPreviewDocument.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                if (selectedFile == null) {
                    JOptionPane.showMessageDialog(frmDocumentSigner, "Preview file not selected!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            new PdfViewer(selectedFile);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void configureTxtDocPath () {
        txtDocPath = new JTextField();
        txtDocPath.setEditable(false);
        txtDocPath.setColumns(10);
    }

    private void addHorizontalGroup () {
        lblFullPath = new JLabel("Full Path:");
        gl_panelContainer = new GroupLayout(panelContainer);
        gl_panelContainer
                .setHorizontalGroup(gl_panelContainer.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(GroupLayout.Alignment.LEADING, gl_panelContainer.createSequentialGroup().addContainerGap()
                    .addGroup(gl_panelContainer.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(btnPreviewDocument, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE,263, Short.MAX_VALUE)
                    .addGroup(gl_panelContainer.createSequentialGroup().addComponent(lblFullPath)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtDocPath, GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnOpenDocument, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
                );

    }

    private void addVerticalGroup () {
        gl_panelContainer
                .setVerticalGroup(gl_panelContainer.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(gl_panelContainer.createSequentialGroup().addContainerGap()
                    .addGroup(gl_panelContainer.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDocPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFullPath).addComponent(btnOpenDocument))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnPreviewDocument, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                    .addContainerGap())
                );
        panelContainer.setLayout(gl_panelContainer);
    }

}
