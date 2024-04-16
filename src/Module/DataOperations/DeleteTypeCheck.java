package Module.DataOperations;

public class DeleteTypeCheck
{
    public static boolean deleteTypeCheck(String deleteNum)
    {
        return deleteNum.length() <= 6;
    }
}
