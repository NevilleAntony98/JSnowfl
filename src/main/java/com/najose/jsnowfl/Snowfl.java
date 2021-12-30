package com.najose.jsnowfl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;

public class Snowfl {
    static final String BASENAME = "https://snowfl.com/";
    private String token;
    private static Pattern scriptNamePattern = Pattern.compile("(b.min.js\\?v=\\w+?)\"");
    private static Pattern tokenVarPattern = Pattern.compile("url: *\"/\" *\\+ *(\\w+?) *\\+ *\"/newsfeed\"");

    public SearchResult[] search(String searchString) {
        fetchToken();
        String query = generateQuery(searchString);

        return fetchResults(query);
    }

    private void fetchToken() {
        String html = Helpers.fetchURL(BASENAME);
        Matcher matcher = scriptNamePattern.matcher(html);
        matcher.find();
        String scriptName = matcher.group(1);

        String script = Helpers.fetchURL(BASENAME + scriptName);
        matcher = tokenVarPattern.matcher(script);
        matcher.find();
        String tokenVar = matcher.group(1);
        String tokenPatternString = String.format("%s *= *\"(\\w+?)\"", tokenVar);

        Pattern tokenPattern = Pattern.compile(tokenPatternString);
        matcher = tokenPattern.matcher(script);
        matcher.find();
        token = matcher.group(1);
    }

    private String generateQuery(String searchString) {
        String encodedSearch = URLEncoder.encode(searchString, StandardCharsets.UTF_8);
        String randomString = UUID.randomUUID().toString().replace("-", "").substring(0, 8);

        String query = BASENAME + token + "/" + encodedSearch + "/" + randomString + "/0/SEED/NONE/1?="
                + System.currentTimeMillis();

        return query;
    }

    private SearchResult[] fetchResults(String query) {
        String results = Helpers.fetchURL(query);
        Gson gson = new Gson();
        SearchResult[] searchResults = gson.fromJson(results, SearchResult[].class);

        return searchResults;
    }

    public String getToken() {
        return token;
    }
}