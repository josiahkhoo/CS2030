import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

class Main {

    public static void main(String[] args) {
        BufferedReader scroll = null;
        try {
            scroll = new BufferedReader(new FileReader(args[0]));
        } catch (FileNotFoundException e) {
            System.err.println(e);
        }
        Farm f = new Farm();
        String line = new String();
        while (true) {
            try {
                line = scroll.readLine();
                if (line == null) {
                    break;
                }
            } catch (IOException e) {
                System.err.println(e);
            }
            try {
                f.runInstruction(line);
            } catch (IllegalInstructionException e) {
                System.err.println(e.getMessage());
            }
        }
    }

}
