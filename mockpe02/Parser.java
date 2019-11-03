import java.util.List;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Arrays;

public class Parser {

    private List<String> lines;

    private Parser(List<String> lines) {
        this.lines = lines;
    }

    public static Parser parse(List<String> lines) {
        Parser parser = new Parser(lines);
        return parser;
    }

    public Parser wordcount() {
        int count = this.lines.stream().reduce(0,
                (finalSum, currString) -> {
                    StringTokenizer currToken = new StringTokenizer(currString);
                    finalSum += currToken.countTokens();
                    return finalSum;
                },
                (a, b) -> a);
        String countString = Integer.toString(count);
        return Parser.parse(Arrays.asList(new String[]{countString}));
    }

    public Parser linecount() {
        int count = this.lines.stream().reduce(0, 
                (finalSum, currString) -> finalSum + 1,
                (a, b) -> a);
        String countString = Integer.toString(count);
        return Parser.parse(Arrays.asList(new String[]{countString}));
    }

    public boolean match(String string, String pattern) {
        StringBuilder stringBuilder = new StringBuilder(string);
        int index = stringBuilder.indexOf(pattern);
        if (index == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Parser grab(String str) {
        Object[] validObjects = this.lines.stream().filter(x -> match(x, str)).toArray();
        String[] validStrings = Arrays.copyOf(validObjects, validObjects.length, String[].class);
        return Parser.parse(Arrays.asList(validStrings));
    }

    public Parser echo() {
        String line = this.lines.stream().reduce(new StringBuilder(),
                (finalStr, currStr) -> {
                    StringTokenizer stringTokenizer = new StringTokenizer(currStr);
                    while (stringTokenizer.hasMoreTokens()) {
                        finalStr.append(stringTokenizer.nextToken());
                        finalStr.append(" ");
                    }
                    return finalStr;
                },
                (a, b) -> a).toString();
        if (line.length() >= 1) {
            line = line.substring(0, line.length() - 1);
        }
        return Parser.parse(Arrays.asList(new String[]{line}));
    }

    public Parser chop(int start, int end) {
        Object[] chopObjects = this.lines.stream().map(
                x -> {
                    if ((x.length() >= start) && start <= end){
                        int startIndex = start;
                        int endIndex = end;
                        if (start <= 0) {
                            startIndex = 1;
                        }
                        if (end > x.length()) {
                            endIndex = x.length();
                        }
                        if (x.length() == 0) {
                            return new String();
                        }
                        return x.substring(startIndex - 1, endIndex);
                    } else {
                        return new String();
                    }
                }).toArray();
        String[] chopStrings = Arrays.copyOf(chopObjects, chopObjects.length, String[].class);
        return Parser.parse(Arrays.asList(chopStrings));
    }

    private String rotate(String input) {
        StringBuilder stringBuilder = new StringBuilder(input);
        boolean firstItem = true;
        List<Integer> rotateIndex = new ArrayList<>();
        List<Integer> whiteSpaceIndex = new ArrayList<>();
        int lengthInput = input.length();
        for (int i = 0; i < lengthInput; i++) {
            char character = input.charAt(i);
            
            if (i == lengthInput - 1) {
                List<Integer> newIndex = new ArrayList<>(rotateIndex);
                if (!(newIndex.isEmpty())) {
                    int integer = newIndex.remove(0);
                    newIndex.add(integer);
                }
                for (int j = 0; j < rotateIndex.size(); j++) {
                    int currIndex = rotateIndex.get(j);
                    int replaceIndex = newIndex.get(j);
                    char newCharacter = input.charAt(replaceIndex);
                    stringBuilder.setCharAt(currIndex, newCharacter);
                }
                rotateIndex = new ArrayList<>();
                break;
            }
            char nextCharacter = input.charAt(i + 1);
            if (Character.isSpaceChar(character) && Character.isSpaceChar(nextCharacter)) {
                whiteSpaceIndex.add(i);
                continue;
            }
            if (Character.isSpaceChar(character)) {
                continue;
            }
            if (Character.isSpaceChar(nextCharacter)) {
                firstItem = true;
                List<Integer> newIndex = new ArrayList<>(rotateIndex);
                if (!(newIndex.isEmpty())) {
                    int integer = newIndex.remove(0);
                    newIndex.add(integer);
                }
                for (int j = 0; j < rotateIndex.size(); j++) {
                    int currIndex = rotateIndex.get(j);
                    int replaceIndex = newIndex.get(j);
                    char newCharacter = input.charAt(replaceIndex);
                    stringBuilder.setCharAt(currIndex, newCharacter);
                }
                rotateIndex = new ArrayList<>();
                continue;
            }
            if (firstItem) {
                firstItem = false;
                continue;
            }
            rotateIndex.add(i);
        }
        for (int i = whiteSpaceIndex.size(); i > 0; i--) {
            int index = whiteSpaceIndex.get(i - 1);
            stringBuilder = removeIndex(stringBuilder, i);
        }
        return stringBuilder.toString();
    }

    private StringBuilder removeIndex(StringBuilder sb, int i) {
        String string1 = sb.substring(0, i);
        String string2 = sb.substring(i + 1, sb.length());
        String combined = String.format(string1 + string2);
        return new StringBuilder(combined);
    }

    public Parser shuffle() {
        Object[] shuffleObjects = this.lines.stream().map(x -> rotate(x)).toArray();
        String[] shuffleStrings = Arrays.copyOf(shuffleObjects, shuffleObjects.length, String[].class);
        return Parser.parse(Arrays.asList(shuffleStrings));
    }

    @Override
    public String toString() {
        String output = this.lines.stream().collect(() -> new StringBuilder(),
                (accString, currString) -> accString.append(currString).append("\n"),
                (accString, currString) -> accString.append(currString)).toString();
        if (output.length() == 0) {
            return output;
        }
        return output.substring(0, output.length() - 1);
    }   
}


