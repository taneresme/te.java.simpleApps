package te.simpleApps.contentSearcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import te.simpleApps.contentSearcher.console.Console;
import te.simpleApps.contentSearcher.search.Searcher;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private Console console;
    @Autowired
    private Searcher searcher;

    public static void main(String[] args) {
        /*Disable logging*/
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }

    @Override
    public void run(String... strings) {
        try {
            /*Read folder name to search*/
            String folder = console.in("Folder path to search: ");

            /*Read texts to be searched*/
            String textsInput = console.in("Texts to search (use comma for multiple input): ");
            String[] texts = textsInput.split(",");

            searcher.search(folder, texts);

            console.normal("Press any key to exit...");
            console.in();
        } catch (Exception ex) {
            console.err(ex.toString());
        }
    }
}