## SpringBoot基于maven构建镜像

### maven 构建镜像配置

#### pom.xml 文件添加docker插件

```
<plugin>
    <groupId>com.spotify</groupId>
    <artifactId>docker-maven-plugin</artifactId>
    <version>0.4.11</version>
    <configuration>
        <imageName>${docker.image.prefix}/${project.artifactId}</imageName>
        <imageTags>
            <imageTag>${project.version}</imageTag>
            <imageTag>latest</imageTag>
        </imageTags>
        <dockerDirectory>src/main/docker</dockerDirectory>
        <buildArgs>
            <JAR_FILE>${project.build.finalName}.jar</JAR_FILE>
        </buildArgs>
        <resources>
            <resource>
                <targetPath>/</targetPath>
                <directory>${project.build.directory}</directory>
                <include>${project.build.finalName}.jar</include>
            </resource>
        </resources>
    </configuration>
</plugin>
```

```
1、${docker.image.prefix} 是镜像的前缀
2、${project.artifactId} 是镜像名字
3、${project.version} 版本号，此处也用来当做镜像的版本号
```

#### src/main/docker 目录下新增文件 Dockerfile，内容如下：

```
FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS="-Xmx4G -Xms4G -Xmn2G"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
```

#### 构建Docker镜像

```
docker clean package docker:build
```

#### 查看/删除镜像

```
docker images
docker rmi IMAGE ID
```

#### 启动镜像服务

```
 docker run -t -i -p 8080:8080 sinochem/test-docker-demo:latest
```

#### 查看/删除容器

```
docker ps -a
docker rm CONTAINER ID  
```

#### 查看日志

##### 查看指定时间后的日志，只显示最后100行:

```
docker logs -f -t --since="2018-02-08" --tail=100 CONTAINER_ID
```

##### 查看最近30分钟的日志:

```
docker logs --since 30m CONTAINER_ID
```

##### 查看某时间之后的日志：

```
docker logs -t --since="2018-02-08T13:23:37" CONTAINER_ID
```

##### 查看某时间段日志：

```
docker logs -t --since="2018-02-08T13:23:37" --until "2018-02-09T12:23:37" CONTAINER_ID
```

