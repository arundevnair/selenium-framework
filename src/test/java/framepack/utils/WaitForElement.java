package framepack.utils;


public class WaitForElement {
    public static  int shortTime =  10;
/*    public static final int mediumTime =  30;
    public static final int longTime =  60;
    public static final int veryLongTime =  180;*/

    public static void setShortTime(int time){
        System.out.println("Setting the framepack.utils.WaitForElement.shortTime as " + time);
        shortTime = time;
    }
}
