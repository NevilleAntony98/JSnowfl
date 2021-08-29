package com.najose.jsnowfl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Helpers {
    public static String fetchURL(String urlString) {
        StringBuffer stringBuffer = new StringBuffer();

        try {
            URL url = new URL(urlString);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            bufferedReader.close();
        } catch (IOException exception) {
            System.out.print(exception);
        }

        return stringBuffer.toString();
    }
}
