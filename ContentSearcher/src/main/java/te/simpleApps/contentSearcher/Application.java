package te.simpleApps.contentSearcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import te.simpleApps.contentSearcher.config.Config;
import te.simpleApps.contentSearcher.console.Console;
import te.simpleApps.contentSearcher.search.Searcher;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private Console console;
    @Autowired
    private Searcher searcher;
    @Autowired
    private Config config;


    public static void main(String[] args) {
        /*Disable logging*/
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }

    @Override
    public void run(String... strings) {
        try {
            if (config.getShowColors()) {
                System.setProperty("jansi.passthrough", "true");
                console.showColors();
            }

            /*Read folder name to search*/
            String folder = console.in("Folder path to search: ");

            /*Read texts to be searched*/
            String textsInput = console.in("Texts to search (use (,) for multiple input, use (:) for decimal range): ");
            String[] texts = textsInput.split(",");

            searcher.search(folder, texts);
        } catch (Exception ex) {
            console.err(ex.toString());
        }
        try {
            console.normal("Press any key to exit...");
            console.in();
        } catch (Exception ex) {
        }
    }
}