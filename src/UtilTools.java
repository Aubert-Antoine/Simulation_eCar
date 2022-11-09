import org.jetbrains.annotations.NotNull;

import static java.lang.Integer.parseInt;

public class UtilTools {

    /*
    SOME USEFUL METHODE
     */

    /**
     * <a href="https://stackoverflow.com/a/14039210">...</a>
     * convert the date dd/mm/yyyy to yyyy-mm-dd
     * @author Antoine Aubert & stackoverflow
     * @param
     * @return
     */
    public static String dateConverter(@NotNull String pDate){
        try {
            return "'"+pDate.split("/")[2]+"/"+pDate.split("/")[1]+"/"+pDate.split("/")[0]+"'";
        }catch (Exception e){
            return null;
        }
    }

    public static  String timeStampConverter(String pDateTime) {
        pDateTime=pDateTime.replace('.',' ');
        try {
            String temp = pDateTime.split("/")[2]+"/"+pDateTime.split("/")[1]+"/"+pDateTime.split("/")[0];
            System.out.println();
            String out = "'"+temp.split(" ")[0] + " " + pDateTime.split(" ")[1]+"'";
            System.out.println(out);
            return out;
        }catch (Exception e){
            return null;
        }
    }

    public static String timeStampConverter2(String pTimeStamp){
        try {
            String[] tmp = pTimeStamp.replace('.',' ').split(" "); // split on '.' not work ??
            String[] date = tmp[0].split("/");
            String time[] = tmp[1].split(":");


            String timeStampFormat = date[2]+date[1]+date[0]+time[0]+time[1]+time[2];
            return "'"+UtilTools.stringToInt(timeStampFormat,0)+"'";
        } catch (Exception e){
            return null;
        }
    }//timeStampConverter2

    /**
     * currentDirectory return the absolut path of the "Simulation eCar" project.
     * @return
     */
    public static String currentDirectory(){
        return System.getProperty("user.dir");
    }//currentDirectory


    /**
     * This methode take a string, make a subtring if is needed and then make it as a int
     * @param pString should contain a numeric sequence
     * @param regex index a the start a the subString
     * @return int
     */
    public static int stringToInt(String pString, int regex){
        int number;
        if(regex != 0 ){
            String subString = pString.substring(regex);
            number = parseInt(subString);
        }else {
            number = parseInt(pString);
        }
        return number;
    }//stringToInt




}//UtilTools


