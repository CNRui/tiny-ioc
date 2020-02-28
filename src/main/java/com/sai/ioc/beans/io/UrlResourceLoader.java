package com.sai.ioc.beans.io;

import java.net.URL;

public class UrlResourceLoader implements ResourceLoader {

    private Resource resource;

    private String location;

    public UrlResourceLoader(String location) {
        this.location = location;
        URL url = this.getClass().getClassLoader().getResource(location);
        this.resource = new UrlResource(url);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public Resource getResource() throws Exception {
        return resource;
    }
}
