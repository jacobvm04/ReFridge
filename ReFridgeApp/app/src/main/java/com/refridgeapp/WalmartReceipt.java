import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;

public class WalmartReceipt {
    public static ArrayList<String> getItemList(String OCRData){
        Pattern upc = Pattern.compile("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]");
        ArrayList<String> itemList = new ArrayList<String>(Arrays.asList(OCRData.split("\n")));
        //Removes all lines without a upc number
        for(int i = itemList.size() - 1; i >= 0; i--){
            Matcher upcMatcher = upc.matcher(itemList.get(i));
            if(!upcMatcher.find()){
                itemList.remove(i);
            }
        }

        for(int i = itemList.size() - 1; i >= 0; i--){
            int upcIndex = 0;
            String[] splitLine = itemList.get(i).split(" ");
            for(int j = splitLine.length - 1; j >= 0; j--){
                Matcher upcMatcher = upc.matcher(splitLine[j]);
                if(upcMatcher.find()){
                    upcIndex = j;
                }
            }
            String item = "";
            for(int j = 0; j < upcIndex; j++){
                item += splitLine[j] + " ";
            }
            itemList.set(i, item.substring(0, item.length() - 1));
        }
        return itemList;
    }
}
