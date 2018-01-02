package te.simpleApps.contentSearcher.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import te.simpleApps.contentSearcher.console.Console;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Searcher {
    private String[] searchees;
    private String searchee = "";

    @Autowired
    private Console console;

    public void search(String folder, String[] searchees) throws Exception {
        if (searchees.length == 0) {
            throw new Exception("Please provide text to be searched!");
        }

        this.searchees = searchees;
        searchee = this.searchees[0];
        for (int i = 1; i < this.searchees.length; i++) {
            searchee = searchee + "|" + this.searchees[i];
        }

        search(folder);
    }

    private void search(String folder) {
        File[] files = new File(folder).listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                search(file.getPath());
            } else {
                search(file);
            }
        }
    }

    private void search(File file) {
        Boolean fileWritten = false;
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            List<String> lines = Files.readAllLines(file.toPath());

            Pattern pattern = Pattern.compile(String.format("(?i)((?:%s))", searchee));
            for (String line : lines) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    if (!fileWritten) {
                        console.blueLn("File: " + file.getPath());
                        fileWritten = true;
                    }
                    console.cyanLn("\t" + line);
                }
            }
        } catch (Exception ex) {
            if (!fileWritten) {
                console.err("File: " + file.getPath());
                fileWritten = true;
            }
            console.err("\t" + ex.toString());
        }
    }
}
