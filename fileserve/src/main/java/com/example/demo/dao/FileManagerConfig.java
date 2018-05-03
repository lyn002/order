package com.example.demo.dao;

import com.sun.xml.internal.ws.developer.Serialization;

public interface FileManagerConfig extends Serialization{
    public static final String FILE_DEFAULT_AUTHOR = "lyn";

    public static final String PROTOCOL = "http://";

    public static final String SEPARATOR = "/";

    public static final String TRACKER_NGNIX_ADDR = "193.112.72.91";

    public static final String TRACKER_NGNIX_PORT = "";

    public static final String CLIENT_CONFIG_FILE = "fdfs_client.conf";
}
