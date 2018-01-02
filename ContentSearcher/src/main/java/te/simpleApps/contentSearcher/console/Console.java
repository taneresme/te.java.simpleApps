package te.simpleApps.contentSearcher.console;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class Console {
    private void out(String out, String color) {
        System.out.print(color + out);
    }

    private void outLn(String out, String color) {
        System.out.println(color + out);
    }

    public void out(String out) {
        System.out.print(Colors.ANSI_RESET + out);
    }

    public void normal(String out) {
        System.out.println(Colors.ANSI_RESET + out);
    }

    public void err(String out) {
        outLn(out, Colors.ANSI_RED);
    }

    public void blueLn(String out) {
        outLn(out, Colors.ANSI_BLUE);
    }

    public void cyanLn(String out) {
        outLn(out, Colors.ANSI_CYAN);
    }

    public String in() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    public String in(String out) throws IOException {
        out(out, Colors.ANSI_GREEN);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }
}
