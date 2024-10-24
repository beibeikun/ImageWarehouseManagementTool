package com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations;

import com.github.beibeikun.imagewarehousemanagementtool.constant.others;

public class ImgSize
{
    public static int getImgSize(String size)
    {
        if (size.equals(others.FULL_SIZE))
        {
            return 0;
        }
        else if (size.equals(others.AUTO))
        {
            return 1;
        }
        else
        {
            return Integer.parseInt(size.substring(0, 4));
        }
    }
}
