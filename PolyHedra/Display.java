package polyhedra;

/**
 * A display and controller for a polyhedron
 */

import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.awt.BasicStroke;

public class Display extends JPanel
{
    private int width = 400;
    private int height = 400;
    private Color backgroundColor = Color.white;
    private Color edgeColor = Color.black;
    private Color faceColor = Color.red;
    private Color highlightColor = Color.blue;
    private BufferedImage buffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    private Graphics2D g2D = (Graphics2D)buffImage.getGraphics();
    private boolean facesVisible = true;
    private double zoom = 1; //should stay between .5 and 2.5, inclusive
    private Polyhedron polyhedron;
    private Vertex selectedVertex;
    private Edge selectedEdge;
    private int panelWidth;
    private int panelHeight;
    private double scale;
    private int drawWidth;
    private int drawHeight;
    private int edgeWidth;
    private int edgeHeight;

    @Override
    protected void paintComponent(Graphics g)
    {
        g2D.setColor(backgroundColor);
        g2D.fillRect(0, 0, width, height);

        ArrayList<Face> faces = new ArrayList<Face>(polyhedron.getFaces());
        ArrayList<Face> orderedFaces = new ArrayList<Face>();

        //drains faces in order to populate orderedFaces with the faces from low to high z
        while (faces.size() > 0)
        {
            double minZ = Double.MAX_VALUE;
            int minIndex = 0;
            for (int i = 0; i < faces.size(); i ++)
            {
            	//using the centroid occasionally leads to incorrect ordering
                double currentZ = faces.get(i).getCentroid().getZ();
                if (currentZ < minZ)
                {
                    minZ = currentZ;
                    minIndex = i;
                }
            }
            orderedFaces.add(faces.get(minIndex));
            faces.remove(minIndex);
        }

        GeneralPath[] faceShapes = new GeneralPath[orderedFaces.size()];

        //creates drawable shapes for all of the faces
        for (int i = 0; i < orderedFaces.size(); i ++)
        {
            Face currentFace = orderedFaces.get(i);

            ArrayList<Vertex> orderedVertices = new ArrayList<Vertex>(currentFace.getOrderedVertices());

            faceShapes[i] = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
                currentFace.getNumVertices());

            Vertex currentVertex = orderedVertices.get(0);
            faceShapes[i].moveTo(this.translateX(currentVertex.getX()),
                this.translateY(currentVertex.getY()));
            for (int j = 1; j < orderedVertices.size(); j ++)
            {
                currentVertex = orderedVertices.get(j);
                faceShapes[i].lineTo(this.translateX(currentVertex.getX()),
                    this.translateY(currentVertex.getY()));
            }
            faceShapes[i].closePath();
        }

        //draws the edges and faces
        for (int i = 0; i < faceShapes.length; i ++)
        {
            g2D.setColor(edgeColor);
            g2D.draw(faceShapes[i]);
            if (facesVisible)
            {
                g2D.setColor(faceColor);
                g2D.fill(faceShapes[i]);
            }
        }

        //draws the selected object in a different color (if applicable)
        if (selectedVertex != null)
        {
            g2D.setColor(highlightColor);
            g2D.setStroke(new BasicStroke(5));
            g2D.drawLine(this.translateX(selectedVertex.getX()),
                this.translateY(selectedVertex.getY()), this.translateX(selectedVertex.getX()),
                this.translateY(selectedVertex.getY()));
            g2D.setStroke(new BasicStroke(2));
        }
        else if (selectedEdge != null)
        {
            Vertex[] endVertices = selectedEdge.getVertices();

            g2D.setColor(highlightColor);
            g2D.setStroke(new BasicStroke(3));
            g2D.drawLine(this.translateX(endVertices[0].getX()),
                this.translateY(endVertices[0].getY()), this.translateX(endVertices[1].getX()),
                this.translateY(endVertices[1].getY()));
            g2D.setStroke(new BasicStroke(2));
        }

        panelWidth = this.getParent().getWidth();
        panelHeight = this.getParent().getHeight();
        scale = Math.min((double)panelWidth / width, (double)panelHeight / height);
        drawWidth = (int)Math.round(width * scale);
        drawHeight = (int)Math.round(height * scale);
        edgeWidth = (panelWidth - drawWidth) / 2;
        edgeHeight = (panelHeight - drawHeight) / 2;
        
        this.setSize(panelWidth, panelHeight);
        
        super.paintComponent(g);

        //copies the image to the screen, scaled and centered
        g.drawImage(buffImage, edgeWidth, edgeHeight, drawWidth, drawHeight, null);
    }

    /**
     * Constructor for objects of class Display
     */
    public Display(String newShape)
    {
        if (newShape.equals("Tetrahedron"))
        {
            polyhedron = new Polyhedron(Polyhedron.TETRAHEDRON);
        }
        else if (newShape.equals("Cube"))
        {
            polyhedron = new Polyhedron(Polyhedron.CUBE);
        }
        else if (newShape.equals("Octahedron"))
        {
            polyhedron = new Polyhedron(Polyhedron.OCTAHEDRON);
        }
        else if (newShape.equals("Dodecahedron"))
        {
            polyhedron = new Polyhedron(Polyhedron.DODECAHEDRON);
        }
        else if (newShape.equals("Icosahedron"))
        {
            polyhedron = new Polyhedron(Polyhedron.ICOSAHEDRON);
        }
        
        g2D.setStroke(new BasicStroke(2));
    }

    /**
     * Translates cartesian X values (from the polyhedron) into graphical X values (for display)
     *
     * @param cartesianX the cartesian X value to be translated
     * @return the graphical X value
     */
    private int translateX(double cartesianX)
    {
        return (int)Math.round((width / 4.0) * cartesianX * zoom + (width / 2.0));
    }

    /**
     * Translates cartesian Y values (from the polyhedron) into graphical Y values (for display)
     *
     * @param cartesianY the cartesian Y value to be translated
     * @return the graphical Y value
     */
    private int translateY(double cartesianY)
    {
        return (int)Math.round(-1 * (height / 4.0) * cartesianY * zoom + (height / 2.0));
    }

    /**
     * Translates graphical X values (from the display) into cartesian X values (for the polyhedron)
     *
     * @param graphicalX the graphical X value to be translated
     * @return the cartesian X value
     */
    private double untranslateX(int graphicalX)
    {
        return (graphicalX - (width / 2.0)) / ((width / 4.0) * zoom);
    }

    /**
     * Translates graphical Y values (from the display) into cartesian Y values (for the polyhedron)
     *
     * @param graphicalY the graphical Y value to be translated
     * @return the cartesian Y value
     */
    private double untranslateY(int graphicalY)
    {
        return -1 * (graphicalY - (height / 2.0)) / ((height / 4.0) * zoom);
    }

    /**
     * Sets the polyhedron to the given one
     *
     * @param newShape the polyhedron to be set to
     */
    public void setShape(String newShape)
    {
        if (newShape.equals("Tetrahedron"))
        {
            polyhedron = new Polyhedron(Polyhedron.TETRAHEDRON);
        }
        else if (newShape.equals("Cube"))
        {
            polyhedron = new Polyhedron(Polyhedron.CUBE);
        }
        else if (newShape.equals("Octahedron"))
        {
            polyhedron = new Polyhedron(Polyhedron.OCTAHEDRON);
        }
        else if (newShape.equals("Dodecahedron"))
        {
            polyhedron = new Polyhedron(Polyhedron.DODECAHEDRON);
        }
        else if (newShape.equals("Icosahedron"))
        {
            polyhedron = new Polyhedron(Polyhedron.ICOSAHEDRON);
        }
    }

    /**
     * Sets whether the faces of the polyhedron are displayed
     *
     * @param newFacesVisible whether the faces should be displayed
     */
    public void setFacesVisible(boolean newFacesVisible)
    {
        facesVisible = newFacesVisible;
    }

    /**
     * Sets the zoom level for the polyhedron's display
     *
     * @param newZoom the new zoom level (should be between 0.5 and 2.5, inclusive)
     */
    public void setZoom(double newZoom)
    {
        zoom = newZoom;
    }

    /**
     * Rotates the polyhedron in a direction and by an amount determined by two given (X, Y) points
     *
     * @param point1 the first point to be used in direction and amount determination
     * @param point2 the second point to be used in direction and amount determination
     */
    public void rotate(Point point1, Point point2)
    {
        polyhedron.rotate(this.untranslateX((int)((point1.getX() - edgeWidth) / scale)),
            this.untranslateY((int)((point1.getY() - edgeHeight) / scale)),
            this.untranslateX((int)((point2.getX() - edgeWidth) / scale)),
            this.untranslateY((int)((point2.getY() - edgeHeight) / scale)));
    }
    
    /**
     * Rotates the polyhedron in a direction and by an amount determined by angles a, b, and c
     *
     * @param a the angle around the x-axis
     * @param b the angle around the y-axis
     * @param c the angle around the z-axis
     */
    public void rotate(double a, double b, double c)
    {
        polyhedron.rotate(a, b, c);
    }

    /**
     * Returns whether there is a vertex at the given display coordinates
     *
     * @param x the X coordinate for the test
     * @param y the Y coordinate for the test
     * @return whether there is a vertex at (x, y)
     */
    public boolean hasVertexAtXY(int x, int y)
    {
        if (x < edgeWidth || x > edgeWidth + drawWidth || y < edgeHeight || y > edgeHeight + drawHeight)
        {
            return false;
        }
        x = (int)((x - edgeWidth) / scale);
        y = (int)((y - edgeHeight) / scale);
        if (new Color(buffImage.getRGB(x, y)).equals(edgeColor)
        || new Color(buffImage.getRGB(x, y)).equals(highlightColor))
        {
            return polyhedron.hasVertexAtXY(this.untranslateX(x), this.untranslateY(y));
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns whether there is an edge at the given display coordinates
     *
     * @param x the X coordinate for the test
     * @param y the Y coordinate for the test
     * @return whether there is an edge at (x, y)
     */
    public boolean hasEdgeAtXY(int x, int y)
    {
        if (x < edgeWidth || x > edgeWidth + drawWidth || y < edgeHeight || y > edgeHeight + drawHeight)
        {
            return false;
        }
        x = (int)((x - edgeWidth) / scale);
        y = (int)((y - edgeHeight) / scale);
        if (new Color(buffImage.getRGB(x, y)).equals(edgeColor)
        || new Color(buffImage.getRGB(x, y)).equals(highlightColor))
        {
            return polyhedron.hasEdgeAtXY(this.untranslateX(x), this.untranslateY(y));
        }
        else
        {
            return false;
        }
    }

    /**
     * Deselects everything that is currently selected
     */
    public void clearSelected()
    {
        selectedVertex = null;
        selectedEdge = null;
    }

    /**
     * Returns whether there is currently something selected
     *
     * @return whether there is currently something selected
     */
    public boolean hasSelected()
    {
        if (selectedVertex == null && selectedEdge == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Selects the vertex at the given display coordinates
     *
     * @param x the X coordinate for the vertex
     * @param y the Y coordinate for the vertex
     */
    public void selectVertexAtXY(int x, int y)
    {
        selectedVertex = polyhedron.getVertexAtXY(this.untranslateX((int)((x - edgeWidth) / scale)),
        this.untranslateY((int)((y - edgeHeight) / scale)));
    }

    /**
     * Selects the edge at the given display coordinates
     *
     * @param x one of the X coordinates for the edge
     * @param y one of the Y coordinates for the edge
     */
    public void selectEdgeAtXY(int x, int y)
    {
        selectedEdge = polyhedron.getEdgeAtXY(this.untranslateX((int)((x - edgeWidth) / scale)),
        this.untranslateY((int)((y - edgeHeight) / scale)));
    }
    
    public void truncSelectedPercent(int percent)
    {
        if (selectedVertex != null)
        {
            polyhedron.truncPercent(selectedVertex, percent);
        }
        else if (selectedEdge != null)
        {
            polyhedron.truncPercent(selectedEdge, percent);
        }
    }
}
