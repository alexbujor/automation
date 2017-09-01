package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser {

    public boolean stringExtractor(String stringToMatch, String regex) {
        String email = "";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(stringToMatch);
        if (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                System.out.println(m.group(i));
                email = m.group(i);
            }
            return true;
        }
        return false;
    }
}
