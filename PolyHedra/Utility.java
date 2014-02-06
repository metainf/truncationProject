package PolyHedra;


/**
 * The utility class for UI
 * 
 * @author (Michael Vrablik) 
 * @version (1/18/2014)
 */
public class Utility
{
    public static String HELP_TEXT = "Help text to be written";
    public static boolean inRange(double d1, double d2){
        if((d1-d2)<.02 &&d1-d2>-.02){
            return true;
        }
        return false;
    }
    
    /**
     * Returns (in radians, over the interval [0, 2pi)) the standard angle of the line from (0, 0) to (x, y)
     *
     * @param  x   the x-coordinate
     * @param  y   the y-coordinate
     * @return     the standard angle of the line from (0, 0) to (x, y)
     */
    public static double angleOf(double x, double y)
    {
        if (x > 0.0 && y >= 0.0)
        {
            return Math.atan(y / x);
        }
        else if (x == 0.0 && y > 0.0)
        {
            return Math.PI / 2;
        }
        else if (x < 0.0)
        {
            return Math.PI + Math.atan(y / x);
        }
        else if (x == 0.0 && y < 0.0)
        {
            return 3 * Math.PI / 2;
        }
        else if (x > 0.0 && y < 0.0)
        {
            return (2 * Math.PI) + Math.atan(y / x);
        }
        else //if (x == 0.0 && y == 0.0)
        {
            System.out.println("ERROR: Utility.angleOf(0.0, 0.0) CANNOT BE EVALUATED");
            return 0.0;
        }
    }
}
