package com.yiheni.fastdfs.rest;

import com.google.common.collect.Maps;
import com.yiheni.fastdfs.entity.Result;
import com.yiheni.fastdfs.fastdfs.FastDfsClient;
import com.yiheni.fastdfs.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


/**
 * @author Administrator
 */
@RestController
@RequestMapping("fast")
@Api(description = "FastDFS文件接口")
public class FastDfsController {

    @ApiOperation(value = "文件上传")
    @PostMapping("/upload")
    public Result singleFileUpload(@RequestParam("file") @ApiParam(value = "文件，小于10M") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return ResultUtil.failed("文件为空");
        }
        return ResultUtil.success(FastDfsClient.saveFile(file));
    }

    @ApiOperation(value = "文件下载")
    @GetMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam @ApiParam(value = "文件路径") String fileUrl) throws Exception {
        return FastDfsClient.download(fileUrl);
    }

    @ApiOperation(value = "删除文件")
    @DeleteMapping("/file")
    public Result delete(@RequestParam("groupName") @ApiParam(value = "文件组名") String groupName,
                         @RequestParam("serverPath") @ApiParam(value = "文件路径") String serverPath)
            throws Exception {
        return ResultUtil.success(FastDfsClient.deleteFile(groupName, serverPath));
    }

    @ApiOperation(value = "查看文件信息")
    @GetMapping("/file/info")
    public Result info(@RequestParam("groupName") @ApiParam(value = "文件组名") String groupName,
                       @RequestParam("serverPath") @ApiParam(value = "文件路径") String serverPath)
            throws Exception {
        return ResultUtil.success(FastDfsClient.getFileInfo(groupName, serverPath));
    }
}
