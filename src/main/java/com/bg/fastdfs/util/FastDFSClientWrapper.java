package com.bg.fastdfs.util;

import com.bg.fastdfs.config.AppConfig;
import com.bg.fastdfs.config.DownLoadClass;
import com.bg.fastdfs.constant.AppConstants;
import com.bg.fastdfs.domain.FastException;
import com.bg.fastdfs.domain.ResponseError;
import com.bg.fastdfs.domain.ResultJson;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by ketao on 2017/9/5.
 */
@Component
public class FastDFSClientWrapper {
    private final Logger logger = LoggerFactory.getLogger(FastDFSClientWrapper.class);

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private AppConfig appConfig;   // 项目参数配置

    /**
     * 上传文件
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public String uploadFile(MultipartFile file) throws FastException {
        try {
            StorePath storePath = storageClient.uploadFile(file.getInputStream(),file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
            return  new ResultJson(Boolean.TRUE,storePath.getFullPath()).successResult();
        }catch (Exception e){
            e.printStackTrace();
            throw new FastException(ResponseError.FIlE_UPLOAD_FAUILE,e.getMessage());
        }
    }

    /**
     * 将一段字符串生成一个文件上传
     * @param content 文件内容
     * @param fileExtension
     * @return
     */
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = storageClient.uploadFile(stream,buff.length, fileExtension,null);
        return storePath.getFullPath();
    }

    // 封装图片完整URL地址
    private String getResAccessUrl(StorePath storePath) {
        String fileUrl = AppConstants.HTTP_PRODOCOL + appConfig.getResHost() + "/" + storePath.getFullPath();
        return fileUrl;
    }

    /**
     * 删除文件
     * @param fileUrl 文件访问地址
     * @return
     */
    public String deleteFile(String fileUrl) throws FastException {
        if (StringUtils.isEmpty(fileUrl)) {
            throw new FastException(ResponseError.FIlE_URL_ISNULL,null);
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
            return  new ResultJson(Boolean.TRUE,"删除成功").successResult();
        } catch (Exception e) {
            logger.warn(e.getMessage());
            throw new FastException(ResponseError.FIlE_DELETE_FAUILE,e.getMessage());
        }
    }

    /**
     * 下载文件
     * @param fileUrl 文件访问地址
     * @return
     */
    public  ResponseEntity<byte[]> download(String fileUrl){


        byte[] bytes=null;
        HttpHeaders headers = new HttpHeaders();
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            bytes = storageClient.downloadFile(storePath.getGroup(), storePath.getPath()
                    , new DownLoadClass());
            headers.setContentDispositionFormData("attachment",  new String(fileName.getBytes("UTF-8"),"iso-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(bytes, headers, HttpStatus.CREATED);

    }




}

