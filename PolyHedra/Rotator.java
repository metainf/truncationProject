package polyhedra;

/**
* Rotates the polyhedron based upon mouse positions
*
* @author (Michael Vrablik)
*/

import javax.swing.SwingWorker;
import java.awt.MouseInfo;
import java.awt.Point;

public class Rotator extends SwingWorker<String, String>
{
    private boolean running;
    private boolean takingInput;
    private Display display;

    public Rotator(Display newDisplay)
    {
        display = newDisplay;
    }

    @Override
    public String doInBackground()
    {
        running = true;
        takingInput = true;

        Point mouseP = MouseInfo.getPointerInfo().getLocation();
        Point oldP = new Point((int)mouseP.getX() - (int)display.getLocationOnScreen().getX(),
                (int)mouseP.getY() - (int)display.getLocationOnScreen().getY());
        Point newP = new Point((int)mouseP.getX() - (int)display.getLocationOnScreen().getX(),
                (int)mouseP.getY() - (int)display.getLocationOnScreen().getY());

        while (running)
        {
            long oldTime = System.currentTimeMillis();
            
            if (takingInput)
            {
                mouseP = MouseInfo.getPointerInfo().getLocation();
                oldP.setLocation(newP);
                newP.setLocation(mouseP.getX() - display.getLocationOnScreen().getX(),
                    mouseP.getY() - display.getLocationOnScreen().getY());
            }
            
            display.rotate(oldP, newP);
            display.repaint();

            try {
                //waits a tenth of a second, minus the computation and rotation time
                Thread.sleep((100000 - (System.currentTimeMillis() - oldTime))/ 1000);
            } catch (InterruptedException e) {
            }
        }

        return "";
    }

    public void stopInput()
    {
        takingInput = false;
    }

    public void stop()
    {
        running = false;
        takingInput = false;
    }

    public boolean isRunning()
    {
        return running;
    }

}
