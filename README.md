# 仓储 - 文件上传下载
## 项目框架汇总
- Spring boot
- Maven来构建管理项目，
- logback日志


# 关于项目工程介绍
- util: 代码工具类
- common: 公共组建
- domain: 对象PO实体
- controller: 控制器
- service: 业务逻辑层
- mapper: 数据库交互层，使用mybatis orm框架
- 

## application.properties 配置
```properties
#端口号
server.port=8081
#日志参数
logging.pattern.console="%d - %msg%n"
logging.file= /var/log/tomcat/sell.log
logging.level.com.bg.fastdfs=debug

#fastdfs连接参数
fastfds.reshost=192.168.1.131
fastfds.fdfsstorageport=22122

spring.jmx.enabled=false

fdfs.soTimeout=1500
fdfs.connectTimeout=600
fdfs.thumbImage.width=150
fdfs.thumbImage.height=150
fdfs.trackerList[0]=192.168.1.131:22122
```
## FastdfsController restful接口
- /fast/upload FastDFS-Client文件上传接口
- /fast/download FastDFS-Client文件下载接口
- /fast/delete FastDFS-Client文件删除接口



