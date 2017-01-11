package com.book.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by vaibhav.rana on 1/10/17.
 */
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "/Users/vaibhav.rana/Downloads/upload-dir/";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
