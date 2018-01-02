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
            searchee = String.format("%s|%s", searchee, this.searchees[i]);
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
            for (int i = 0; i < lines.size(); i++) {
                Matcher matcher = pattern.matcher(lines.get(i));
                if (matcher.find()) {
                    if (!fileWritten) {
                        console.blueLn(String.format("File: %s", file.getPath()));
                        fileWritten = true;
                    }
                    console.cyanLn(String.format("\t%d %s", i, lines.get(i)));
                }
            }
        } catch (Exception ex) {
            if (!fileWritten) {
                console.err(String.format("File: %s", file.getPath()));
                fileWritten = true;
            }
            console.err(String.format("\t%s", ex.toString()));
        }
    }
}
