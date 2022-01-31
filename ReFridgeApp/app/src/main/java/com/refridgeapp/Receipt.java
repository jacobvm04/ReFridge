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

public class Receipt {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<String> getItemList(Text result){
        List<String> itemList = new ArrayList<>();
        String resultText = result.getText();
        if (resultText.contains("Harris Teeter")) {
            itemList = processHarris(result);
        }

        Log.d("items", itemList.toString());
        return itemList;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static List<String> processHarris(Text result) {
        Log.d("Receipt Type:", "Harris!");
        List<String> itemList;
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
        return itemList;
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
}
