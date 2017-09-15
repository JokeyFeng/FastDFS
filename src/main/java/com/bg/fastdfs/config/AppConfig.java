package com.bg.fastdfs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by ketao on 2017/9/5.
 */
@Component
public class AppConfig {

    @Value("${fastfds.reshost}")
    private String resHost;

    @Value("${fastfds.fdfsstorageport}")
    private String fdfsStoragePort;

    public String getResHost() {
        return resHost;
    }

    public void setResHost(String resHost) {
        this.resHost = resHost;
    }

    public String getFdfsStoragePort() {
        return fdfsStoragePort;
    }

    public void setFdfsStoragePort(String fdfsStoragePort) {
        this.fdfsStoragePort = fdfsStoragePort;
    }
}
