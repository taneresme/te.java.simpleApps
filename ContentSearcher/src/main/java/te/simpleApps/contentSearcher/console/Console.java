package te.simpleApps.contentSearcher.console;

import org.fusesource.jansi.AnsiConsole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import te.simpleApps.contentSearcher.config.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.fusesource.jansi.Ansi.ansi;

@Service
public class Console {
    private String colorFormat = "@|%s %s|@";
    private Boolean showColors = false;

    public void showColors() {
        showColors = true;
        AnsiConsole.systemInstall();
    }

    private void out(String out, String color) {
        if (showColors) {
            System.out.print(ansi().eraseScreen().render(String.format(colorFormat, color, out)));
        } else {
            System.out.print(out);
        }
    }

    private void outLn(String out, String color) {
        if (showColors) {
            System.out.println(ansi().eraseScreen().render(String.format(colorFormat, color, out)));
        } else {
            System.out.println(out);
        }
    }

    public void out(String out) {
        System.out.print(ansi().eraseScreen().reset().render(out));
    }

    public void normal(String out) {
        System.out.println(ansi().eraseScreen().reset().render(out));
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
