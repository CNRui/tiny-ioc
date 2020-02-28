package com.sai.ioc.beans.io;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class UrlResource implements Resource {
    private URL url;


    public UrlResource(URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    @Override
    public InputStream getInputStream() throws Exception {
        URLConnection urlConnection =url.openConnection();
        urlConnection.connect();
        return urlConnection.getInputStream();
    }
}
