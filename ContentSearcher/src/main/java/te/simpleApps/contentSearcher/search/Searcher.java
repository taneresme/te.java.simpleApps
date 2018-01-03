package te.simpleApps.contentSearcher.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import te.simpleApps.contentSearcher.console.Console;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Service
public class Searcher {
    @Autowired
    private Console console;
    @Autowired
    private Regex regex;

    public void search(String folder, String[] searchees) throws Exception {
        regex.init(searchees);
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
        try (FileReader fr = new FileReader(file)) {
            try (BufferedReader br = new BufferedReader(fr)) {
                String line;
                Integer lineNumber = 1;
                while ((line = br.readLine()) != null) {
                    if (regex.match(line)) {
                        if (!fileWritten) {
                            console.blueLn(String.format("File: %s", file.getPath()));
                            fileWritten = true;
                        }
                        console.cyanLn(String.format("\t%d %s", lineNumber, line));
                    }
                    lineNumber++;
                }
            }
        } catch (Exception ex) {
            if (!fileWritten) {
                console.err(String.format("File: %s", file.getPath()));
            }
            console.err(String.format("\t%s", ex.toString()));
        }
    }
}
