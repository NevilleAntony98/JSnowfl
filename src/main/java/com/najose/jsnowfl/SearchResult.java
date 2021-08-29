package com.najose.jsnowfl;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

public class SearchResult {

    @SerializedName("age")
    String age;

    @SerializedName("seeder")
    int seeder;

    @SerializedName("leecher")
    int leecher;

    @SerializedName("magnet")
    String magnet;

    @SerializedName("name")
    String name;

    @SerializedName("url")
    String url;

    @SerializedName("site")
    String site;

    @SerializedName("size")
    String size;

    @SerializedName("trusted")
    String trusted;

    @SerializedName("nsfw")
    boolean nsfw;

    @SerializedName("type")
    boolean type;

    public void fetchMagnet(String token) {
        String encodedURL = Base64.getEncoder()
                .encodeToString(URLEncoder.encode(url, StandardCharsets.UTF_8).getBytes());
        String quotedSite = URLEncoder.encode(site, StandardCharsets.UTF_8);

        String magnetQuery = Snowfl.BASENAME + token + "/" + quotedSite + "/" + encodedURL;
        String magnetJSON = Helpers.fetchURL(magnetQuery);

        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> URLMagnet = new Gson().fromJson(magnetJSON, type);
        magnet = URLMagnet.get("url");
    }
}
