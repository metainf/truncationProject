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
        if((d1-d2)<.01 &&d1-d2>-.01){
            return true;
        }
        return false;
    }
}
