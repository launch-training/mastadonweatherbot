package com.acn.jive.mastadonweatherbot;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connection {

    public HttpURLConnection createApiConnection(String urlString) throws IOException{
            // attempt to create connection
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // set request method to get
            conn.setRequestMethod("GET");

            return conn;
    }
}
