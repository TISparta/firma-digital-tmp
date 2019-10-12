import javax.swing.*;
import java.awt.*;


public class Buttons {

    private JButton btnLastPage;
    private JButton btnNextPage;
    private JButton btnPreviousPage;
    private JButton btnFirstPage;

    public Buttons (Box horizontalBoxControls) {
        initButtonFirstPage(horizontalBoxControls);
        initButtonPreviousPage(horizontalBoxControls);
        initButtonNextPage(horizontalBoxControls);
        initButtonLastPage(horizontalBoxControls);
    }

    public JButton getBtnLastPage() {
        return btnLastPage;
    }

    public JButton getBtnNextPage() {
        return btnNextPage;
    }

    public JButton getBtnPreviousPage() {
        return btnPreviousPage;
    }

    public JButton getBtnFirstPage() {
        return btnFirstPage;
    }

    private void initButtonFirstPage (Box horizontalBoxControls) {
        btnFirstPage = new JButton("First Page");
        horizontalBoxControls.add(btnFirstPage);
        Component horizontalStrutLeft_1 = Box.createHorizontalStrut(10);
        horizontalBoxControls.add(horizontalStrutLeft_1);
    }

    private void initButtonPreviousPage (Box horizontalBoxControls) {
        btnPreviousPage = new JButton("Previous Page");
        horizontalBoxControls.add(btnPreviousPage);
        Component horizontalStrutLeft_2 = Box.createHorizontalStrut(10);
        horizontalBoxControls.add(horizontalStrutLeft_2);
    }

    private void initButtonNextPage (Box horizontalBoxControls) {
        btnNextPage = new JButton("Next Page");
        horizontalBoxControls.add(btnNextPage);
    }

    private void initButtonLastPage (Box horizontalBoxControls) {
        btnLastPage = new JButton("Last Page");
        horizontalBoxControls.add(btnLastPage);
    }

    public void selectPageButtonsEffect (int pageIndex, int numberOfPages) {
        if (pageIndex == 0) {
            enableDisableButtons(0);
        } else if (pageIndex == (numberOfPages - 1)) {
            enableDisableButtons(1);
        } else {
            enableDisableButtons(-1);
        }
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
}
