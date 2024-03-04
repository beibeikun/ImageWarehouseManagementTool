package Module.DataOperations;

public class DeleteTypeCheck
{
    public static boolean deleteTypeCheck(String deleteNum)
    {
        if (deleteNum.length() <= 6)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
