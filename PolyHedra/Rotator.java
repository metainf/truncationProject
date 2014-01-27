package PolyHedra;

/**
 * Rotates the polyhedron based upon mouse positions
 * 
 * @author (Michael Vrablik) 
 * @version (1/18/2014)
 */

import javax.swing.SwingWorker;
import java.awt.MouseInfo;
import java.awt.Point;

public class Rotator extends SwingWorker<String, String>
{
    private boolean running;
    private boolean takingInput;

    /**
     * Constructor for objects of class Rotator
     */
    public Rotator()
    {
        //
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    @Override
    public String doInBackground()
    {
        running = true;
        takingInput = true;

        Point mouseP = MouseInfo.getPointerInfo().getLocation();
        Point oldP = new Point((int)mouseP.getX() - (int)UI.getDisplay().getLocationOnScreen().getX(),
                (int)mouseP.getY() - (int)UI.getDisplay().getLocationOnScreen().getY());
        Point newP = new Point((int)mouseP.getX() - (int)UI.getDisplay().getLocationOnScreen().getX(),
                (int)mouseP.getY() - (int)UI.getDisplay().getLocationOnScreen().getY());

        while (running)
        {
            long oldTime = System.currentTimeMillis();
            
            if (takingInput)
            {
                mouseP = MouseInfo.getPointerInfo().getLocation();
                oldP.setLocation(newP);
                newP.setLocation(mouseP.getX() - UI.getDisplay().getLocationOnScreen().getX(),
                    mouseP.getY() - UI.getDisplay().getLocationOnScreen().getY());
            }
            
            UI.getDisplay().rotate(oldP, newP);
            UI.getDisplay().repaint();

            try {
                //waits a tenth of a second, minus the computation and rotation time
                Thread.sleep((100000 - (System.currentTimeMillis() - oldTime))/ 1000);
            } catch (InterruptedException e) {
            }
        }

        return "";
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y
     */
    public void stopInput()
    {
        takingInput = false;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y
     */
    public void stop()
    {
        running = false;
        takingInput = false;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y
     */
    public boolean isRunning()
    {
        return running;
    }

}
