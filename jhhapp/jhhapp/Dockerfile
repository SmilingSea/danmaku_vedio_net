# Docker image for springboot file run
# VERSION 0.0.1
# Author: 
# 基础镜像使用java
FROM openjdk:8
# 作者
MAINTAINER jhh
# VOLUME 指定了临时文件目录为/tmp。
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
VOLUME /tmp 
# 将jar包添加到容器中并更名为xx.jar
ADD danmaku_vedio_net-1.0-SNAPSHOT.jar service.jar 
# 运行jar包
RUN bash -c 'touch /service.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/service.jar"]

