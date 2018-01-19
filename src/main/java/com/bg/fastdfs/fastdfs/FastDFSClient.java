package com.bg.fastdfs.fastdfs;

import com.bg.fastdfs.domain.ResultJson;
import com.bg.fastdfs.util.SplitUtil;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by Chenjing on 2018/1/18.
 *
 * @author Chenjing
 */
public class FastDFSClient {
    private static Logger logger = LoggerFactory.getLogger(FastDFSClient.class);
    private static TrackerClient trackerClient;
    private static TrackerServer trackerServer;
    private static StorageServer storageServer;

    static {
        try {
            Properties properties = new Properties();
            properties.load(FastDFSClient.class.getClassLoader().getResourceAsStream("fast-dfs.properties"));
            ClientGlobal.initByProperties(properties);
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            storageServer = trackerClient.getStoreStorage(trackerServer);
        } catch (Exception e) {
            logger.error("FastDFS 客户端初始化失败，信息{}", e.toString());
        }
    }

    public static String saveFile(MultipartFile multipartFile) throws Exception {
        String[] fileAbsolutePath;
        String fileName = multipartFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        byte[] file_buff = null;
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
            if (inputStream != null) {
                int len1 = inputStream.available();
                file_buff = new byte[len1];
                inputStream.read(file_buff);
            }
        } catch (Exception e) {
            logger.error("保存文件出现了异常，信息{}", e.toString());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        FastDFSFile file = new FastDFSFile(fileName, file_buff, ext);
        fileAbsolutePath = FastDFSClient.upload(file);
        return new ResultJson(Boolean.TRUE, fileAbsolutePath[0] + "/" + fileAbsolutePath[1]).successResult();
    }

    private static String[] upload(FastDFSFile file) throws IOException, MyException {
        logger.info("文件名: " + file.getName() + "，文件长度:" + file.getContent().length);
        NameValuePair[] metaList = new NameValuePair[1];
        metaList[0] = new NameValuePair("author", "Chenjing");
        String[] uploadResults;
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), metaList);
        String groupName = uploadResults[0];
        String remoteFileName = uploadResults[1];

        logger.info("上传文件成功!" + "group_name:" + groupName + ", remoteFileName:" + " " + remoteFileName);
        return uploadResults;
    }

    public static FileInfo getFile(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            return storageClient.get_file_info(groupName, remoteFileName);
        } catch (IOException e) {
            logger.error("IO Exception: Get File from Fast DFS failed", e);
        } catch (Exception e) {
            logger.error("Non IO Exception: Get File from Fast DFS failed", e);
        }
        return null;
    }

    private static byte[] downFile(String path) throws Exception {
        logger.info("文件路径:{}", path);
        List<String> list = SplitUtil.split("/", path);
        if (list.isEmpty()) {
            return null;
        }
        String groupName = list.get(0);
        StringBuilder remoteFileName = new StringBuilder("");
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                continue;
            }
            remoteFileName.append(list.get(i)).append("/");
        }
        remoteFileName.deleteCharAt(remoteFileName.length() - 1);

        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        return storageClient.download_file(groupName, remoteFileName.toString());
    }

    public static ResponseEntity<byte[]> download(String fileUrl) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        byte[] bytes = downFile(fileUrl);
        headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("UTF-8"), "iso-8859-1"));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(bytes, headers, HttpStatus.CREATED);
    }

    public static void deleteFile(String groupName, String remoteFileName)
            throws Exception {
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        int i = storageClient.delete_file(groupName, remoteFileName);
        logger.info("delete file successfully!!!" + i);
    }

    public static StorageServer[] getStoreStorages(String groupName)
            throws IOException {
        return trackerClient.getStoreStorages(trackerServer, groupName);
    }

    public static ServerInfo[] getFetchStorages(String groupName,
                                                String remoteFileName) throws IOException {
        return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
    }

    public static String getTrackerUrl() {
        return "http://" + trackerServer.getInetSocketAddress().getHostString() + ":" + ClientGlobal.getG_tracker_http_port() + "/";
    }
}
