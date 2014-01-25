package PolyHedra;

/**
 * The User Interface for a truncation program
 * 
 * @author (Michael Vrablik) 
 * @version (1/18/2014)
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.MouseInputAdapter;

public class UI
{
    static JFrame mainFrame;    //the main window for the program
    final static Display display = new Display("Tetrahedron");

    public static void main (String[] args)

    {
        mainFrame = new JFrame("Shrinking Elephants"); //the frame for the program's GUI
        //ends the program when the main window is closed
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(new Dimension(575, 400));
        mainFrame.setMinimumSize(new Dimension(575, 400));
        mainFrame.setLocationRelativeTo(null);  //centers the window
        //maximizes the program's main window
        mainFrame.setExtendedState( mainFrame.getExtendedState()|JFrame.MAXIMIZED_BOTH );

        final Rotator[] rotator = new Rotator[1];

        JPanel mainPanel = new JPanel();  //the main panel
        //sets mainPanel to a vertically stacking layout
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));

        JPanel controlPanel = new JPanel(); //the panel for the user'c controls
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
        controlPanel.setMaximumSize(new Dimension(175, 400));
        controlPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        display.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.PAGE_AXIS));
        displayPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        displayPanel.add(display);

        JButton helpButton = new JButton("Help!");
        helpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //tells helpButton what to do when pressed
        helpButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent actionEvent)
                {
                    JOptionPane.showMessageDialog(null, Utility.HELP_TEXT, "Instructions",
                        JOptionPane.PLAIN_MESSAGE);
                    display.requestFocusInWindow();
                }
            }
        );

        String [] polyhedra = {"Tetrahedron", "Cube", "Octahedron", "Dodecahedron", "Icosahedron"};
        final JComboBox shapeComboBox = new JComboBox(polyhedra);
        shapeComboBox.setMaximumSize(new Dimension(160, 20));
        shapeComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        shapeComboBox.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent actionEvent)
                {
                    if (rotator[0] != null)
                    {
                        rotator[0].stop();
                    }
                    display.clearSelected();
                    display.setShape(shapeComboBox.getSelectedItem().toString());
                    display.requestFocusInWindow();
                }
            }
        );

        final JCheckBox facesCheckBox = new JCheckBox("Display Faces", true);
        facesCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        //facesCheckBox.setSelected(true);
        facesCheckBox.addItemListener(new ItemListener()
            {
                public void itemStateChanged(ItemEvent itemEvent)
                {
                    if (itemEvent.getStateChange() == ItemEvent.DESELECTED)
                    {
                        display.setFacesVisible(false);
                    }
                    else if (itemEvent.getStateChange() == ItemEvent.SELECTED)
                    {
                        display.setFacesVisible(true);
                    }
                    display.requestFocusInWindow();
                }
            }
        );

        final JRadioButton selectRB = new JRadioButton("Select and truncate", true);
        selectRB.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectRB.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent actionEvent)
                {
                    if (rotator[0] != null)
                    {
                        rotator[0].stop();
                    }
                    display.requestFocusInWindow();
                }
            }
        );

        final JRadioButton rotateRB = new JRadioButton("Rotate");
        rotateRB.setAlignmentX(Component.CENTER_ALIGNMENT);
        rotateRB.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent actionEvent)
                {
                    display.clearSelected();
                    display.requestFocusInWindow();
                }
            }
        );

        ButtonGroup rBGroup = new ButtonGroup();    //allows only one to be selected at a time
        rBGroup.add(selectRB);
        rBGroup.add(rotateRB);
        
        JLabel zoomLabel = new JLabel("-     Zoom:     +");
        zoomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JSlider zoomSlider = new JSlider(10, 30);
        zoomSlider.setMinorTickSpacing(1);
        zoomSlider.setPaintTicks(true);
        zoomSlider.setMaximumSize(new Dimension(160, 40));
        zoomSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        zoomSlider.addChangeListener(new ChangeListener()
            {
                public void stateChanged(ChangeEvent changeEvent)
                {
                    display.setZoom(zoomSlider.getValue() / 20.0); //sets between 0.5 and 1.5, inclusive
                    display.requestFocusInWindow();
                }
            }
        );

        display.addMouseListener(new MouseInputAdapter()
            {
                public void mouseClicked(MouseEvent mouseEvent)
                {
                    if (selectRB.isSelected())
                    {
                        display.clearSelected();

                        //checks for and selects a vertex within 2 pixels
                        for (int dx = -2; dx <= 2; dx ++)
                        {
                            for (int dy = -2; dy <= 2; dy ++)
                            {
                                if (! display.hasSelected()
                                && display.hasVertexAtXY(mouseEvent.getX() + dx, mouseEvent.getY() + dy))
                                {
                                    display.selectVertexAtXY(mouseEvent.getX() + dx,
                                        mouseEvent.getY() + dy);

                                    //show a truncation-controlling component
                                    //slider? (would need ability to reverse truncate)
                                    //button? (while depressed, truncate) (can't fully trucate?)
                                    //popup with a percentage chooser?
                                    //change would not be visible until committed
                                }
                            }
                        }

                        //checks for and selects an edge within 2 pixels (if vertex not selected)
                        for (int dx = -2; dx <= 2; dx ++)
                        {
                            for (int dy = -2; dy <= 2; dy ++)
                            {
                                if (! display.hasSelected()
                                && display.hasEdgeAtXY(mouseEvent.getX() + dx, mouseEvent.getY() + dy))
                                {
                                    display.selectEdgeAtXY(mouseEvent.getX() + dx,
                                        mouseEvent.getY() + dy);

                                    //show a truncation-controlling component
                                    //slider? (would need ability to reverse truncate)
                                    //button? (while depressed, truncate) (can't fully trucate?)
                                    //popup with a percentage chooser?
                                    //change would not be visible until committed
                                }
                            }
                        }
                    }
                }
            }
        );

        display.addMouseListener(new MouseInputAdapter()
            {
                public void mousePressed(MouseEvent mouseEvent)
                {
                    if (rotateRB.isSelected())
                    {
                        if (rotator[0] != null)
                        {
                            rotator[0].stop();
                        }
                        rotator[0] = new Rotator();
                        rotator[0].execute();
                    }
                }
            }
        );
        display.addMouseListener(new MouseInputAdapter()
            {
                public void mouseReleased(MouseEvent mouseEvent)
                {
                    if (rotator[0] != null)
                    {
                        rotator[0].stopInput();
                    }
                }
            }
        );
        display.addMouseListener(new MouseInputAdapter()
            {
                public void mouseExited(MouseEvent mouseEvent)
                {
                    if (rotator[0] != null)
                    {
                        rotator[0].stopInput();
                    }
                }
            }
        );

        //the actions for keyboard inputs
        Action rotateLeft = new AbstractAction()
            {
                public void actionPerformed(ActionEvent actionEvent)
                {
                    if (rotateRB.isSelected())
                    {
                        //if the polyhedron isn't currently spinning
                        if (rotator[0] == null)
                        {
                            display.rotate(new Point((int)(display.getSize().getWidth() / 8), 0), new Point());
                        }
                        else if (! rotator[0].isRunning())
                        {
                            display.rotate(new Point((int)(display.getSize().getWidth() / 8), 0), new Point());
                        }
                    }
                }
            }
        ;
        Action rotateRight = new AbstractAction()
            {
                public void actionPerformed(ActionEvent actionEvent)
                {
                    if (rotateRB.isSelected())
                    {
                        //if the polyhedron isn't currently spinning
                        if (rotator[0] == null)
                        {
                            display.rotate(new Point(), new Point((int)(display.getSize().getWidth() / 8), 0));
                        }
                        else if (! rotator[0].isRunning())
                        {
                            display.rotate(new Point(), new Point((int)(display.getSize().getWidth() / 8), 0));
                        }
                    }
                }
            }
        ;
        Action rotateUp = new AbstractAction()
            {
                public void actionPerformed(ActionEvent actionEvent)
                {
                    if (rotateRB.isSelected())
                    {
                        //if the polyhedron isn't currently spinning
                        if (rotator[0] == null)
                        {
                            display.rotate(new Point(0, (int)(display.getSize().getHeight() / 8)), new Point());
                        }
                        else if (! rotator[0].isRunning())
                        {
                            display.rotate(new Point(0, (int)(display.getSize().getHeight() / 8)), new Point());
                        }
                    }
                }
            }
        ;
        Action rotateDown = new AbstractAction()
            {
                public void actionPerformed(ActionEvent actionEvent)
                {
                    if (rotateRB.isSelected())
                    {
                        //if the polyhedron isn't currently spinning
                        if (rotator[0] == null)
                        {
                            display.rotate(new Point(), new Point(0, (int)(display.getSize().getHeight() / 8)));
                        }
                        else if (! rotator[0].isRunning())
                        {
                            display.rotate(new Point(), new Point(0, (int)(display.getSize().getHeight() / 8)));
                        }
                    }
                }
            }
        ;
        Action reset = new AbstractAction()
            {
                public void actionPerformed(ActionEvent actionEvent)
                {
                    if (rotator[0] != null)
                    {
                        rotator[0].stop();
                    }
                    display.clearSelected();
                    display.setShape(shapeComboBox.getSelectedItem().toString());
                    display.requestFocusInWindow();
                }
            }
        ;

        //lays out the keyboard inputs
        display.getInputMap(display.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "pressed LEFT");
        display.getInputMap(display.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "pressed RIGHT");
        display.getInputMap(display.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "pressed UP");
        display.getInputMap(display.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "pressed DOWN");
        display.getInputMap(display.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "pressed R");

        //lays out the response actions for keyboard inputs
        display.getActionMap().put("pressed LEFT", rotateLeft);
        display.getActionMap().put("pressed RIGHT", rotateRight);
        display.getActionMap().put("pressed UP", rotateUp);
        display.getActionMap().put("pressed DOWN", rotateDown);
        display.getActionMap().put("pressed R", reset);

        //populates the user's control panel
        controlPanel.add(Box.createVerticalStrut(20));
        controlPanel.add(helpButton);
        controlPanel.add(Box.createVerticalStrut(20));
        controlPanel.add(selectRB);
        controlPanel.add(rotateRB);
        controlPanel.add(Box.createVerticalStrut(20));
        controlPanel.add(shapeComboBox);
        controlPanel.add(Box.createVerticalStrut(20));
        controlPanel.add(facesCheckBox);
        controlPanel.add(Box.createVerticalStrut(20));
        controlPanel.add(zoomLabel);
        controlPanel.add(zoomSlider);

        //populates the main panel with the user's controls and the diaplay
        mainPanel.add(controlPanel);
        mainPanel.add(displayPanel);

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);

        display.requestFocusInWindow();
    }

    /**
     * Returns the display
     *
     * @return     the display
     */
    public static Display getDisplay()
    {
        return display;
    }

}
