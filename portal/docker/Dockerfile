#使用 JDK 8 环境为基础镜像，如果镜像不是本地的将会从 DockerHub 进行下载
FROM openjdk:8-jdk-alpine

#这个命令的效果是：在宿主机的/var/lib/docker目录下创建一个临时文件并把它链接到tomcat容器的工作目录/tmp目录
#VOLUME /tmp

#复制文件并且重命名。
ADD bqhealth-cloud-physical/bqhealth-cloud/bqhealth-cloud-portal.jar app.jar

#为了缩短 Tomcat 的启动时间，添加 java.security.egd 的系统属性指向 /dev/urandom 作为 ENTRYPOINT。
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar", "--spring.profiles.active=dev"]

