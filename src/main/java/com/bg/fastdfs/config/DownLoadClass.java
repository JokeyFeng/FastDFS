package com.bg.fastdfs.config;

import com.github.tobato.fastdfs.proto.storage.DownloadCallback;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ketao on 2017/9/5.
 */
public class DownLoadClass implements DownloadCallback<byte[]> {


    @Override
    public byte[]  recv(InputStream ins) throws IOException {
        return IOUtils.toByteArray(ins);
    }
}
