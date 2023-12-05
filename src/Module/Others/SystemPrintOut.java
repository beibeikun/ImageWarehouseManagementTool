package Module.Others;

import static Module.Others.GetCorrectTime.getCorrectTime;

public class SystemPrintOut {
    public static void systemPrintOut(String printoutmessage, int type, int type2) {
        String info = "[" + getCorrectTime() + "]";
        String success = "   Succeeded: ";
        String error = "   [E R R O R]: ";
        if (type == 0) {
            System.out.println("---------------------------------------------------------------------------------");
        } else if (type == 1) {
            System.out.println(info + success + printoutmessage);
        } else if (type == 2) {
            System.out.println(info + error + printoutmessage);
        } else if (type == 3) {
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println("                 "+printoutmessage);
            System.out.println("---------------------------------------------------------------------------------");
        }
    }

}
