/**
 * <html>
 * <body>
 * <P> Copyright©2015-2016 Yiheni. All rights reserved. </p>
 * <p> 粤ICP备16046232号-1 </p>
 * <p> Created on 2018年08月29日</p>
 * <p> Created by JokeyZheng</p>
 * </body>
 * </html>
 */
package com.yiheni.fastdfs.rest;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * 测试了
 *
 * @Project fastdfs-client
 * @Package com.yiheni.fastdfs.rest
 * @ClassName DemoController
 * @Author JokeyZheng
 * @Date 2018年08月29日
 * @Version 1.0
 */
@RestController
public class DemoController {

    @GetMapping(value = "/sleep/one", produces = "application/json")
    public JSONObject sleepOne() {
        System.out.println("模拟业务处理1分钟");
        Long serverTime = System.currentTimeMillis();
        try {
            Thread.sleep(60 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (System.currentTimeMillis() < serverTime + (30 * 1000)) {
            System.out.println("正在处理业务，当前时间：" + LocalDate.now() + "，开始时间：" + serverTime);
        }

        System.out.println("模拟业务处理1分钟");
        return new JSONObject();
    }
}
