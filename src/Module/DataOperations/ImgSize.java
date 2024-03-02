package Module.DataOperations;

public class ImgSize
{
    public static int getImgSize(String size)
    {
        if (size.equals("fullsize"))
        {
            return 0;
        }
        else
        {
            return Integer.parseInt(size.substring(0, 4));
        }
    }
}
