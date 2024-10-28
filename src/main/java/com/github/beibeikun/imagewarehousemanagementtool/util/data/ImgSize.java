package com.github.beibeikun.imagewarehousemanagementtool.util.data;

import com.github.beibeikun.imagewarehousemanagementtool.constant.others;

/**
 * ImgSize 类用于根据给定的字符串获取图像的尺寸。
 */
public class ImgSize
{
    /**
     * 根据给定的字符串获取图像尺寸。
     *
     * @param size 图像尺寸的字符串
     * @return 图像的尺寸
     */
    public static int getImgSize(String size)
    {
        if (size.equals(others.FULL_SIZE))
        {
            return 0;  // 全尺寸
        }
        else if (size.equals(others.AUTO))
        {
            return 1;  // 自动尺寸
        }
        else
        {
            return Integer.parseInt(size.substring(0, 4));  // 根据字符串获取尺寸值
        }
    }
}
