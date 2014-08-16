package polyhedra;

/**
 * The User Interface for a truncation program
 */

import javax.swing.JComponent;
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
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.MouseInputAdapter;

public class UI
{
    public static void main (String[] args)
    {
    	JFrame mainFrame; //the main window for the program
        mainFrame = new JFrame("Truncation"); //the frame for the program's GUI
        //ends the program when the main window is closed
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(new Dimension(575, 400));
        mainFrame.setMinimumSize(new Dimension(575, 400));
        mainFrame.setLocationRelativeTo(null); //centers the window
        //maximizes the program's main window
        mainFrame.setExtendedState( mainFrame.getExtendedState()|JFrame.MAXIMIZED_BOTH );

        final Rotator[] rotator = new Rotator[1];

        JPanel mainPanel = new JPanel(); //the main panel
        //sets mainPanel to a vertically stacking layout
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));

        JPanel controlPanel = new JPanel(); //the panel for the user's controls
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
        controlPanel.setMaximumSize(new Dimension(175, 400));
        controlPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final Display display = new Display("Tetrahedron");
        display.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.PAGE_AXIS));
        displayPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        displayPanel.add(display);
        
        final JFrame moreFrame = new JFrame();
        moreFrame.setSize(new Dimension(400, 400));
        moreFrame.setResizable(false);
        moreFrame.setLocationRelativeTo(null);
        
        JPanel morePanel = new JPanel();
        morePanel.setLayout(new BoxLayout(morePanel, BoxLayout.PAGE_AXIS));
        
        JPanel moreRBPanel = new JPanel();
        moreRBPanel.setLayout(new BoxLayout(moreRBPanel, BoxLayout.LINE_AXIS));
        moreRBPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        moreRBPanel.setMaximumSize(new Dimension(400, 20));
        
        final JTextArea moreTextArea = new JTextArea(Utility.INSTRUCTIONS);
        moreTextArea.setEditable(false);
        //uncomment if hyperlinks get added to the urls in Utility.CREDITS and Utility.HELP_TEXT
        //moreTextArea.setHighlighter(null);
        moreTextArea.setBackground(morePanel.getBackground());
        moreTextArea.setLineWrap(true);
        moreTextArea.setWrapStyleWord(true);
        moreTextArea.setMargin(new Insets(10, 10, 10, 10));
        moreTextArea.setMaximumSize(new Dimension(400, 370));
        
        JRadioButton instructionsRB = new JRadioButton("Instructions", true);
        instructionsRB.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent actionEvent)
                {
                	moreTextArea.setText(Utility.INSTRUCTIONS);
                	moreTextArea.requestFocusInWindow();
                }
            }
        );
        JRadioButton creditsRB = new JRadioButton("Credits");
        creditsRB.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent actionEvent)
                {
                	moreTextArea.setText(Utility.CREDITS);
                	moreTextArea.requestFocusInWindow();
                }
            }
        );
        JRadioButton helpRB = new JRadioButton("I Want to Help!");
        helpRB.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent actionEvent)
                {
                	moreTextArea.setText(Utility.HELP_TEXT);
                	moreTextArea.requestFocusInWindow();
                }
            }
        );
        
        ButtonGroup moreRBGroup = new ButtonGroup(); //allows only one to be selected at a time
        moreRBGroup.add(instructionsRB);
        moreRBGroup.add(creditsRB);
        moreRBGroup.add(helpRB);
        
        moreRBPanel.add(Box.createVerticalStrut(0));
        moreRBPanel.add(instructionsRB);
        moreRBPanel.add(Box.createVerticalStrut(0));
        moreRBPanel.add(creditsRB);
        moreRBPanel.add(Box.createVerticalStrut(0));
        moreRBPanel.add(helpRB);
        moreRBPanel.add(Box.createVerticalStrut(0));
        
        morePanel.add(Box.createVerticalStrut(10));
        morePanel.add(moreRBPanel);
        morePanel.add(moreTextArea);
        
        moreFrame.add(morePanel);

        JButton moreButton = new JButton("More");
        moreButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //tells moreButton what to do when pressed
        moreButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent actionEvent)
                {
                	moreFrame.setLocationRelativeTo(null);
                	moreFrame.setVisible(true);
                	moreTextArea.requestFocusInWindow();
                    display.requestFocusInWindow();
                }
            }
        );
        
        final JLabel truncLabel = new JLabel();
        truncLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        final JSlider truncSlider = new JSlider(0, 100);
        truncSlider.setEnabled(false);
        truncSlider.setMinorTickSpacing(1);
        truncSlider.setMajorTickSpacing(10);
        truncSlider.setPaintTicks(true);
        truncSlider.setMaximumSize(new Dimension(160, 40));
        truncSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        truncSlider.addChangeListener(new ChangeListener()
            {
                public void stateChanged(ChangeEvent changeEvent)
                {
                    truncLabel.setText(truncSlider.getValue() + "%");
                    display.requestFocusInWindow();
                }
            }
        );
        
        final JButton truncButton = new JButton("Truncate");
        truncButton.setEnabled(false);
        truncButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //tells truncButton what to do when pressed
        truncButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent actionEvent)
                {
                    truncSlider.setEnabled(false);
                    truncButton.setEnabled(false);
                    truncLabel.setText("");
                    display.truncSelectedPercent(truncSlider.getValue());
                    display.clearSelected();
                    display.repaint();
                    display.requestFocusInWindow();
                }
            }
        );

        String [] polyhedra = {"Tetrahedron", "Cube", "Octahedron", "Dodecahedron", "Icosahedron"};
        final JComboBox<String> shapeComboBox = new JComboBox<String>(polyhedra);
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
                    truncSlider.setEnabled(false);
                    truncButton.setEnabled(false);
                    truncLabel.setText("");
                    display.setShape(shapeComboBox.getSelectedItem().toString());
                    display.repaint();
                    display.requestFocusInWindow();
                }
            }
        );

        final JCheckBox facesCheckBox = new JCheckBox("Display Faces", true);
        facesCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
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
                    display.repaint();
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
                	//uncomment to prevent truncation while rotating
                    /*display.clearSelected();
                	truncSlider.setEnabled(false);
                	truncButton.setEnabled(false);
                	truncLabel.setText("");
                	display.repaint();*/
                    display.requestFocusInWindow();
                }
            }
        );

        ButtonGroup mainRBGroup = new ButtonGroup(); //allows only one to be selected at a time
        mainRBGroup.add(selectRB);
        mainRBGroup.add(rotateRB);
        
        JLabel zoomLabel = new JLabel("- Zoom: +");
        zoomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JSlider zoomSlider = new JSlider(10, 50);
        zoomSlider.setValue(20);
        zoomSlider.setMaximumSize(new Dimension(160, 20));
        zoomSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        zoomSlider.addChangeListener(new ChangeListener()
            {
                public void stateChanged(ChangeEvent changeEvent)
                {
                    display.setZoom(zoomSlider.getValue() / 20.0); //sets between 0.5 and 2.5, inclusive
                    display.repaint();
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

                                    truncSlider.setEnabled(true);
                                    truncButton.setEnabled(true);
                                    truncLabel.setText(truncSlider.getValue() + "%");
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

                                    truncSlider.setEnabled(true);
                                    truncButton.setEnabled(true);
                                    truncLabel.setText(truncSlider.getValue() + "%");
                                }
                            }
                        }
                        display.repaint();
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
                        rotator[0] = new Rotator(display);
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
        
        display.addMouseWheelListener(new MouseInputAdapter()
            {
                public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent)
                {
                    zoomSlider.setValue(zoomSlider.getValue() - mouseWheelEvent.getWheelRotation());
                }
            }
        );

        //the actions for keyboard inputs
        Action rotateLeft = new AbstractAction()
            {
                public void actionPerformed(ActionEvent actionEvent)
                {
                    display.rotate(0.0, -Math.PI / 8, 0.0);
                    display.repaint();
                }
            }
        ;
        Action rotateRight = new AbstractAction()
            {
                public void actionPerformed(ActionEvent actionEvent)
                {
                    display.rotate(0.0, Math.PI / 8, 0.0);
                    display.repaint();
                }
            }
        ;
        Action rotateUp = new AbstractAction()
            {
                public void actionPerformed(ActionEvent actionEvent)
                {
                    display.rotate(Math.PI / 8, 0.0, 0.0);
                    display.repaint();
                }
            }
        ;
        Action rotateDown = new AbstractAction()
            {
                public void actionPerformed(ActionEvent actionEvent)
                {
                    display.rotate(-Math.PI / 8, 0.0, 0.0);
                    display.repaint();
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
                    truncSlider.setEnabled(false);
                    truncButton.setEnabled(false);
                    truncLabel.setText("");
                    display.setShape(shapeComboBox.getSelectedItem().toString());
                    display.repaint();
                    display.requestFocusInWindow();
                }
            }
        ;
        Action switchTool = new AbstractAction()
            {
                public void actionPerformed(ActionEvent actionEvent)
                {
                    if (selectRB.isSelected())
                    {
                    	//uncomment to prevent truncation while rotating
                        /*display.clearSelected();
                        truncSlider.setEnabled(false);
                        truncButton.setEnabled(false);
                        truncLabel.setText("");
                        display.repaint();*/
                        rotateRB.setSelected(true);
                        display.requestFocusInWindow();
                    }
                    else if (rotateRB.isSelected())
                    {
                        if (rotator[0] != null)
                        {
                            rotator[0].stop();
                        }
                        selectRB.setSelected(true);
                        display.requestFocusInWindow();
                    }
                }
            }
        ;

        //lays out the keyboard inputs
        display.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "pressed LEFT");
        display.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "pressed RIGHT");
        display.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "pressed UP");
        display.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "pressed DOWN");
        display.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "pressed R");
        display.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "pressed SPACE");

        //lays out the response actions for keyboard inputs
        display.getActionMap().put("pressed LEFT", rotateLeft);
        display.getActionMap().put("pressed RIGHT", rotateRight);
        display.getActionMap().put("pressed UP", rotateUp);
        display.getActionMap().put("pressed DOWN", rotateDown);
        display.getActionMap().put("pressed R", reset);
        display.getActionMap().put("pressed SPACE", switchTool);

        //populates the user's control panel
        controlPanel.add(Box.createVerticalStrut(20));
        controlPanel.add(moreButton);
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
        controlPanel.add(Box.createVerticalStrut(20));
        controlPanel.add(truncSlider);
        controlPanel.add(truncLabel);
        controlPanel.add(truncButton);

        //populates the main panel with the user's controls and the display
        mainPanel.add(controlPanel);
        mainPanel.add(displayPanel);

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);

        display.requestFocusInWindow();
        
        JOptionPane.showMessageDialog(null,
        		"Truncation that results in overly close vertices"
        		+ "\ncan currently cause extremely abnormal behavior."
        		+ "\nIf this occurs, restore normal functionality by"
        		+ "\nresetting the polyhedron using the 'R' key.",
        		"WARNING", JOptionPane.ERROR_MESSAGE);
    }
}
