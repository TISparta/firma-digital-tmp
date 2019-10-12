import javax.swing.*;
import java.awt.*;

public class ControlPanel {

    public ControlPanel (JFrame frame, Box horizontalBoxControls, Box horizontalBoxView) {
        JPanel controlPanel = new JPanel();
        frame.getContentPane().add(controlPanel, BorderLayout.SOUTH);
        controlPanel.setLayout(new BorderLayout(0, 0));
        initHorizontalConfiguration(horizontalBoxControls, horizontalBoxView, controlPanel);
        initVerticalConfiguration(frame, horizontalBoxView, controlPanel);
    }

    private void initHorizontalConfiguration (Box horizontalBoxControls, Box horizontalBoxView, JPanel controlPanel) {
        controlPanel.add(horizontalBoxControls);
        initHorizontalLeftConfiguration(horizontalBoxControls, horizontalBoxView);
        initHorizontalRightConfiguration(horizontalBoxControls, horizontalBoxView);
    }

    private void initHorizontalLeftConfiguration (Box horizontalBoxControls, Box horizontalBoxView) {
        Component horizontalStrutLeft = Box.createHorizontalStrut(10);
        horizontalBoxControls.add(horizontalStrutLeft);
        Component horizontalStrutViewLeft = Box.createHorizontalStrut(10);
        horizontalBoxView.add(horizontalStrutViewLeft);
    }

    private void initHorizontalRightConfiguration (Box horizontalBoxControls, Box horizontalBoxView) {
        Component horizontalStrutRight = Box.createHorizontalStrut(10);
        horizontalBoxControls.add(horizontalStrutRight);
        Component horizontalStrutRight_2 = Box.createHorizontalStrut(10);
        horizontalBoxControls.add(horizontalStrutRight_2);
        Component horizontalStrutRight_1 = Box.createHorizontalStrut(10);
        horizontalBoxControls.add(horizontalStrutRight_1);
        Component horizontalStrutViewRight = Box.createHorizontalStrut(10);
        horizontalBoxView.add(horizontalStrutViewRight);
    }

    private void initVerticalConfiguration (JFrame frame, Box horizontalBoxView, JPanel controlPanel) {
        Component verticalStrutTop = Box.createVerticalStrut(10);
        controlPanel.add(verticalStrutTop, BorderLayout.NORTH);
        Component verticalStrutBottom = Box.createVerticalStrut(10);
        controlPanel.add(verticalStrutBottom, BorderLayout.SOUTH);
        Box verticalBoxView = Box.createVerticalBox();
        frame.getContentPane().add(verticalBoxView, BorderLayout.WEST);
        Component verticalStrutView = Box.createVerticalStrut(10);
        verticalBoxView.add(verticalStrutView);
        verticalBoxView.add(horizontalBoxView);
    }

}
