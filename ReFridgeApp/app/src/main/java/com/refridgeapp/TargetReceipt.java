import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;

public class TargetReceipt {
    public static ArrayList<String> getItemList(String OCRData){
        Pattern sku = Pattern.compile("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]");
        ArrayList<String> itemList = new ArrayList<String>(Arrays.asList(OCRData.split("\n")));
        //Removes all lines without a sku number or $
        for(int i = itemList.size() - 1; i >= 0; i--){
            Matcher skuMatcher = sku.matcher(itemList.get(i));
            if(!skuMatcher.find() || !itemList.get(i).contains("$")){
                itemList.remove(i);
            }
        }

        for(int i = itemList.size() - 1; i >= 0; i--){
            int priceIndex = 0;
            String[] splitLine = itemList.get(i).split(" ");
            for(int j = splitLine.length - 1; j >= 0; j--){
                if(splitLine[j].contains("$")){
                    priceIndex = j;
                }
            }
            String item = "";
            for(int j = 1; j < priceIndex; j++){
                item += splitLine[j] + " ";
            }
            itemList.set(i, item.substring(0, item.length() - 1));
        }
        return itemList;
    }
}
