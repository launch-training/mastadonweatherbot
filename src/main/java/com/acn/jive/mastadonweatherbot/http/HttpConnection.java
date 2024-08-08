package com.acn.jive.mastadonweatherbot.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection {

    public HttpURLConnection createApiConnection(String urlString) throws HttpException {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            return conn;
        } catch(IOException ex) {
            throw new HttpException("An exception occurred while creating the Http-URL-Connection ", ex);
        }
    }
}
