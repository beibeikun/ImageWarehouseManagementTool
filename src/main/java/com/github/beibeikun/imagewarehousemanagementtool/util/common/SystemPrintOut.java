package com.github.beibeikun.imagewarehousemanagementtool.util.common;

import com.github.beibeikun.imagewarehousemanagementtool.constant.printOutMessage;

import static com.github.beibeikun.imagewarehousemanagementtool.util.common.GetCorrectTime.getCorrectTime;

public class SystemPrintOut
{
    public static void systemPrintOut(String thisPrintOutMessage, int type, int type2)
    {
        String info = "[" + getCorrectTime() + "]";
        String success = "   Succeeded: ";
        String error = "   [E R R O R]: ";
        switch (type)
        {
            case 0:
                System.out.println(printOutMessage.DIVIDER);
                break;
            case 1:
                System.out.println(info + success + thisPrintOutMessage);
                break;
            case 2:
                System.out.println(info + error + thisPrintOutMessage);
                break;
            case 3:
                System.out.println(printOutMessage.DIVIDER);
                System.out.println("                 " + thisPrintOutMessage);
                System.out.println(printOutMessage.DIVIDER);
                break;
            default:
                break;
        }
    }

}
