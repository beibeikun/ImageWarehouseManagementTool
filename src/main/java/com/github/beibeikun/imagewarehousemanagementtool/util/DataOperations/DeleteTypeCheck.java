package com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations;

public class DeleteTypeCheck
{
    public static boolean deleteTypeCheck(String deleteNum)
    {
        return deleteNum.length() <= 6;
    }
}
