import java.util.ArrayList;
import java.util.Arrays;

public class HarrisTeeterReceipt {
    public static ArrayList<String> getItemList(String OCRData){
        ArrayList<String> itemList = new ArrayList<String>(Arrays.asList(OCRData.split("\n")));
        for(int i = itemList.size() - 1; i >= 0; i--){
            if(!itemList.get(i).split(" ")[0].contains("SC") && !itemList.get(i).split(" ")[0].contains("sc")){
                itemList.remove(i);
            }
        }
        for(int i = itemList.size() - 1; i >= 0; i--){
            int priceIndex = 0;
            String[] splitLine = itemList.get(i).split(" ");
            for(int j = splitLine.length - 1; j >= 0; j--){
                if(splitLine[j].contains(".")){
                    priceIndex = j;
                }
            }
            String item = "";
            for(int j = 2; j < priceIndex; j++){
                item += splitLine[j] + " ";
            }
            itemList.set(i, item.substring(0, item.length() - 1));
        }
        return itemList;
    }
}
