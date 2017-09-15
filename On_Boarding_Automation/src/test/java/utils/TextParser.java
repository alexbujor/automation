package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser {

    public boolean stringExtractor(String stringToMatch, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(stringToMatch);
        if (m.find())
            return true;
        return false;
    }
}
