package com.github.beibeikun.imagewarehousemanagementtool.util.common;

import com.github.beibeikun.imagewarehousemanagementtool.constant.printOutMessage;

import static com.github.beibeikun.imagewarehousemanagementtool.util.common.GetCorrectTime.getCorrectTime;

public class SystemPrintOut
{
    public static void systemPrintOut(String printoutmessage, int type, int type2)
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
                System.out.println(info + success + printoutmessage);
                break;
            case 2:
                System.out.println(info + error + printoutmessage);
                break;
            case 3:
                System.out.println(printOutMessage.DIVIDER);
                System.out.println("                 " + printoutmessage);
                System.out.println(printOutMessage.DIVIDER);
                break;
            default:
                break;
        }
    }

}
