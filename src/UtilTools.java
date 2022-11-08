import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilTools {

    /*
    SOME USEFUL METHODE
     */

    /**
     * <a href="https://stackoverflow.com/a/14039210">...</a>
     * convert the date dd/mm/yyyy to yyyy-mm-dd
     * @author Antoine Aubert & stackoverflow
     * @param pDate the date at java.sql.date format -> notice that the result is the same with String format
     * @return
     */
    public static String dateConverter(String pDate){
        if(pDate.contains("null")){
            return null;
        }else {
            java.util.Date date = new Date(pDate);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String format = formatter.format(date);

            return "'"+format+"'"; // fix the bug due to null don't need quote but values need inside sql requests
        }
    }//dateConverter()

    public static String timeStampConverter(String pTimeStamp){
        System.out.println("time stamp in param "+pTimeStamp);

        return pTimeStamp;
    }//timeStampConverter(.)

    /**
     * currentDirectory return the absolut path of the "Simulation eCar" project.
     * @return
     */
    public static String currentDirectory(){
        return System.getProperty("user.dir");
    }//currentDirectory
}
