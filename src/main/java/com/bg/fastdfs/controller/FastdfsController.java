package com.bg.fastdfs.controller;

import com.bg.fastdfs.fastdfs.FastDFSClient;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ketao on 2017/9/5.
 */
@RestController
@RequestMapping("fast")
public class FastdfsController {
    private static Logger logger = LoggerFactory.getLogger(FastdfsController.class);

    @ApiOperation(value = "FastDFS-Client文件上传接口")
    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return "文件为空";
        }
        return FastDFSClient.saveFile(file);
    }

/*    @ApiOperation(value = "FastDFS-Client文件删除接口")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public  String delete(String fileUrl) throws FastException {
        String result = dfsClient.deleteFile(fileUrl);
        return result;
    }*/

    @ApiOperation(value = "FastDFS-Client文件下载接口")
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(String fileUrl) throws Exception {
        return FastDFSClient.download(fileUrl);
    }

}
