package te.simpleApps.contentSearcher.search;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Regex {
    private String searchee = "";
    private Pattern pattern;

    private void check(String[] range) throws Exception {
        if (!isInt(range[0])) {
            throw new Exception("Invalid range min-value!");
        }
        if (!isInt(range[1])) {
            throw new Exception("Invalid range max-value!");
        }

        Integer min = Integer.valueOf(range[0]);
        Integer max = Integer.valueOf(range[1]);
        if (max < min || max == min) {
            throw new Exception("Invalid range!");
        }
    }

    public void init(String[] searchees) throws Exception {
        if (searchees.length == 0) {
            throw new Exception("Please provide text to be searched!");
        }

        if (searchees.length == 1
                && searchees[0].indexOf(':') > 0
                && searchees[0].split(":").length == 2) {
            /*The input is a range*/
            String[] range = searchees[0].split(":");
            check(range);

            searchee = "";
            for (int i = 0; i < range[1].length(); i++) {
                if (i > range[0].length()) {
                    searchee = String.format("%s[0-%c]", searchee, range[1].charAt(i));
                } else {
                    searchee = String.format("%s[%c-%c]", searchee, range[0].charAt(i), range[1].charAt(i));
                }
            }
        } else {
            /*The input are items*/
            searchee = searchees[0];
            for (int i = 1; i < searchees.length; i++) {
                searchee = String.format("%s|%s", searchee, searchees[i]);
            }
        }

        pattern = Pattern.compile(String.format("(?i)((?:%s))", searchee));
    }

    public Boolean match(String line) {
        Matcher matcher = pattern.matcher(line);
        return matcher.find();
    }

    public Boolean isInt(String str) {
        try {
            Integer.valueOf(str);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
