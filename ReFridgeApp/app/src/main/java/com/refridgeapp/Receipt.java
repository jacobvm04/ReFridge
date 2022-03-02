package com.refridgeapp;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import com.google.mlkit.vision.text.Text;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.text.Normalizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Receipt {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<String> getItemList(Text result){
        List<String> itemList = new ArrayList<>();
        List<String> processedItemList = new ArrayList<>();
        int[][] tokens;
        String resultText = result.getText();
        if (resultText.contains("Harris Teeter")) {
            itemList = processHarris(result);
            processedItemList = processList(itemList);
            Log.d("processed:", processedItemList.toString());
            tokens = tokenizeOCR(processedItemList);
            Log.d("tokens:", tokens.toString());
        }

        Log.d("items", itemList.toString());
        return itemList;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static List<String> processHarris(Text result) {
        Log.d("Receipt Type:", "Harris!");
        List<String> itemList;
        List<String> processedItemList;
        List<Text.TextBlock> blockList = result.getTextBlocks();
        int index = 0;
        int from, to;
        from = to = index;
        for (Text.TextBlock block : blockList) {
            String blockText = block.getText().toLowerCase();
            index++;
            if (blockText.contains("cashier")) {
                from = index;
            }
            if (blockText.contains("tax")) {
                to = (index - 1);
                break;
            }
        }
        List<Text.TextBlock> selectedBlocks = blockList.subList(from,to);
        List<Text.Line> selectedLines = blocksToLines(selectedBlocks);
        selectedLines = linesRegexProcess(selectedLines, "\\d{3}");
        selectedLines = linesRegexProcess(selectedLines, "round|donation|cashier|price|customer|vic|your|store|harris|teeter|food|pay|matthews|student|disc");
        selectedLines = linesRegexProcess(selectedLines, "[0-9]+.\\d{2}");
        itemList = processAndExportLines(selectedLines);
        // check if length is 0, then return an error to the main camera activity
        itemList = removeAccents(itemList);
        Log.d("items:", itemList.toString());
        return (itemList);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static List<Text.Line> linesRegexProcess(List<Text.Line> lines, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        List<Integer> indexes = new ArrayList<>();
        int index = 0;
        for(Text.Line line: lines) {
            String lineText = line.getText();
            Matcher matcher = pattern.matcher(lineText);
            boolean matchFound = matcher.find();
            if (matchFound) {
                index++;
                continue;
            }
            indexes.add(index);
            index++;

        }
        return getByIndices(lines, indexes);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static List<String> processAndExportLines(List<Text.Line> lines) {
        int index = 0;
        List<Integer> indexes = new ArrayList<>();
        for (Text.Line line: lines) {
            List<Text.Element> elements = line.getElements();
            if (elements.size() < 2) {
                index++;
                continue;
            }
            indexes.add(index);
            Set<Text.Element> set = new HashSet<Text.Element>(elements);

            if(set.size() < elements.size()){
                indexes.remove(index);
                index++;
                continue;
            }

            index++;
        }

        List<Text.Line> processedLines = getByIndices(lines,indexes);
        return exportLines(processedLines);
    }
    private static List<String> exportLines(List<Text.Line> lines) {
        List<String> itemList = new ArrayList<>();
        for (Text.Line line: lines) {
            itemList.add(line.getText());
        }
        return itemList;
    }
    private static List<Text.Line> blocksToLines(List<Text.TextBlock> blocks) {
        List<Text.Line> listLines = new ArrayList<>();
        for (Text.TextBlock block : blocks) {
            listLines.addAll(block.getLines());
        }
        return listLines;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static <T> List<T> getByIndices(List<T> list, List<Integer> indexes) {
        return indexes.stream().map(list::get).collect(toList());
    }

    public static List<String> processList(List<String> inputList){
        List<String> returnList = new ArrayList<>();
        for(int i = inputList.size() - 1; i >= 0; i--){
            String item = inputList.get(i);
            item = item.toLowerCase();
            item = item.replaceAll("[^a-z]", "");
            item = item.trim();
            returnList.set(i, item);
        }
        // I *really* don't like this implicit modification, but so be it
        for(int i = returnList.size() - 1; i >= 0; i--){
            if(returnList.subList(0, i).contains(returnList.get(i)) || returnList.get(i) == ""){
                inputList.remove(i);
                returnList.remove(i);
            }
        }
        return returnList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static List<String> removeAccents(List<String> inputList) {
        int index = 0;
        for (String string: inputList) {
            StringBuilder sb = new StringBuilder(string.length());
            string = Normalizer.normalize(string, Normalizer.Form.NFD);
            for (char c : string.toCharArray()) {
                if (c <= '\u007F') sb.append(c);
            }
            String noAccents = Stream.of(sb.toString().toLowerCase().trim().split("\\s"))
                    .filter(word -> word.length() > 0)
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                    .collect(Collectors.joining(" "));
            inputList.set(index,noAccents);
            index++;
        }
        return inputList;
    }
    private static int[][] tokenizeOCR(List<String> inputList) {
        int[][] items = new int[inputList.size()][20];
        int index = 0;
        for (String string: inputList) {
            char[] chars = string.toCharArray();
            int i;
            for (i = (chars.length-1); i >= 0; i--) {
                items[index][20-(chars.length-(i))] = ((int) chars[i]);
            }
            index++;
        }
        return items;
    }
}
