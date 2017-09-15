package com.bg.fastdfs.controller;

import com.bg.fastdfs.domain.ResponseError;
import com.bg.fastdfs.domain.ResultJson;
import com.bg.fastdfs.util.FastDFSClientWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ketao on 2017/9/5.
 */
@RestController
@RequestMapping("fast")
public class FastdfsController {
    @Autowired
    private FastDFSClientWrapper dfsClient;

    // 上传图片
    @ApiOperation(value = "FastDFS-Client文件上传接口")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 省略业务逻辑代码。。。
        try {
            String imgUrl = dfsClient.uploadFile(file);
            return new ResultJson(Boolean.TRUE,imgUrl).successResult();
        }catch (Exception e){
            e.printStackTrace();
            return new ResultJson(Boolean.FALSE,e.getMessage(), ResponseError.FIlE_UPLOAD_FAUILE).errorResult();
        }

    }
    @ApiOperation(value = "FastDFS-Client文件删除接口")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public  String delete(String fileUrl){
        try {
            dfsClient.deleteFile(fileUrl);
            return  new ResultJson(Boolean.TRUE,"删除成功").successResult();
        }catch (Exception e){
            e.printStackTrace();
            return new ResultJson(Boolean.FALSE,e.getMessage(), ResponseError.FIlE_DELETE_FAUILE).errorResult();
        }
    }
    @ApiOperation(value = "FastDFS-Client文件下载接口")
    @RequestMapping(value = "/download",method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(String fileUrl){
        ResponseEntity<byte[]> download = dfsClient.download(fileUrl);
        return download;
    }

}
