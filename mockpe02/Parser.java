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

    public Parser chop(String delim, int... numbers) {
        Object[] chopObjects = this.lines.stream().map(
                x -> {
                    List<Object> list = Arrays.asList(numbers);
                    StringTokenizer st = new StringTokenizer(x, delim);
                    StringBuilder sb = new StringBuilder();
                    int count = 1;
                    while (st.hasMoreTokens()) {
                        String part = st.nextToken();
                        if (list.contains(count)) {
                            sb.append(part);
                        }
                        count += 1;    
                    }
                    return sb.toString();
                    
                }).toArray();
        String[] chopStrings = Arrays.copyOf(chopObjects, chopObjects.length, String[].class);
        return Parser.parse(Arrays.asList(chopStrings));
    }
                


    private String rotate(String input) {
        StringBuilder finalStringBuilder = new StringBuilder();
        boolean firstItem = true;
        boolean firstWord = true;
        List<Integer> rotateIndex = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(input);
        while (stringTokenizer.hasMoreTokens()) {
            String word = stringTokenizer.nextToken();
            StringBuilder stringBuilder = new StringBuilder(word);
            int lengthWord = word.length();
            for (int i = 0; i < lengthWord; i++) {
                char character = word.charAt(i);
                if (i < (lengthWord - 1)) {
                    char nextCharacter = word.charAt(i + 1);
                    if (!(Character.isLetter(nextCharacter)) && (!(Character.toString(nextCharacter).equals("'")))) {
                        List<Integer> newIndex = new ArrayList<>(rotateIndex);
                        if (!(newIndex.isEmpty())) {
                            int integer = newIndex.remove(0);
                            newIndex.add(integer);
                        }
                        for (int j = 0; j < rotateIndex.size(); j++) {
                            int currIndex = rotateIndex.get(j);
                            int replaceIndex = newIndex.get(j);
                            char newCharacter = word.charAt(replaceIndex);
                            stringBuilder.setCharAt(currIndex, newCharacter);
                        }
                        break;
                    }
                }

                if (Character.toString(character).equals("'")) {
                    if ((lengthWord - 1 - i) == 1) {
                        List<Integer> newIndex = new ArrayList<>(rotateIndex);
                        if (!(newIndex.isEmpty())) {
                            int integer = newIndex.remove(0);
                            newIndex.add(integer);
                        }
                        for (int j = 0; j < rotateIndex.size(); j++) {
                            int currIndex = rotateIndex.get(j);
                            int replaceIndex = newIndex.get(j);
                            char newCharacter = word.charAt(replaceIndex);
                            stringBuilder.setCharAt(currIndex, newCharacter);
                        }
                        break;
                    }
                }

                if (i == (lengthWord - 1)) {
                    List<Integer> newIndex = new ArrayList<>(rotateIndex);
                    if (!(newIndex.isEmpty())) {
                        int integer = newIndex.remove(0);
                        newIndex.add(integer);
                    }
                    for (int j = 0; j < rotateIndex.size(); j++) {
                        int currIndex = rotateIndex.get(j);
                        int replaceIndex = newIndex.get(j);
                        char newCharacter = word.charAt(replaceIndex);
                        stringBuilder.setCharAt(currIndex, newCharacter);
                    }
                    break;
                }
                if (firstItem) {
                    firstItem = false;
                } else {
                    rotateIndex.add(i);
                }
            }
            firstItem = true;
            rotateIndex = new ArrayList<>();
            if (firstWord) {
                firstWord = false;
            } else {
                finalStringBuilder.append(" ");
            }
            finalStringBuilder.append(stringBuilder.toString());
        }
        return finalStringBuilder.toString();
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


