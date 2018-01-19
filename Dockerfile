FROM java:8-jre
MAINTAINER Chenjing <1134991001@qq.com>
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
ADD fastdfs-client-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xms2048m", "-Xmx2048m","-jar", "/app/fastdfs-client-0.0.1-SNAPSHOT.jar"]
EXPOSE 7071