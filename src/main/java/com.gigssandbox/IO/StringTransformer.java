package com.gigssandbox.IO;

public class StringTransformer {
    private static final String DEFAULT_DELIMITER = ";";
    private static final String SPACE = " ";

    public String createSolidString(String... strings) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String string : strings) {
            stringBuffer.append(string).append(SPACE);
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        return String.valueOf(stringBuffer);
    }

    public String[] split(String string) {
        return string.trim().split(DEFAULT_DELIMITER);
    }
}
