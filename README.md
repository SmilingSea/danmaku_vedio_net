# 四轮考核

## 简介

本项目是一个基于弹幕视频网站需求实现的接口项目。

## 技术栈

maven springboot springmvc  Junit Tomcat mybatis-plus docker docker-compose sf4j redis  阿里云COS JWT Spring Security Fastjson Lombook Druid MySQL Git

## 项目结构（目录树）注释

```shell
├── src
│   └── main
│       ├── java
│       │   └── com
│       │       └── jiang
│       │           ├── common		//通用类
│       │           ├── config		//配置类
│       │           ├── controller	//控制器
│       │           ├── domain		//实体类
│       │           ├── dto			//DTO数据传输对象
│       │           ├── filter		//过滤器类
│       │           ├── handler		//SpringSecurity自定义handler控制器
│       │           ├── mapper		//Mapper接口类
│       │           ├── service		//服务层
│       │           │   ├── impl	//接口
│       │           └── util		//工具包
│       └── resources
│   └── test-classes
│       └── com
│           └── jiang
├── pom.xml							//配置文件
```

## 项目功能介绍

本项目实现了弹幕视频网站的基本功能，五个模块：用户模块、社交模块、评论模块、视频模块、搜索模块

### 用户模块

实现了用户的注册、登录、查询用户信息与保存用户头像的功能

### 社交模块

实现了用户之间的关注与取关功能，和查找用户的关注、粉丝以及朋友的功能。

### 评论模块

实现了在视频下方评论、在评论下方评论的功能。

### 视频模块

实现了发布视频、点击视频以及查询点击量排行榜的功能

### 搜索模块

实现了搜索视频、查询用户以及查看搜索记录的功能。

## 项目亮点

使用了阿里云对象存储功能来储存视频

### 项目待改进点

1. 没有通过springsecurity实现鉴权
2. 视频模块没有办法删除视频

## 如何启动

使用docker-compose，我使用的环境是mysql8.0.23和redis的最新版

1. 下载项目地址中的jhh文件夹，上传到服务器中

2. 进入到文件夹内部

3. 启动

   ```
   docker-compose up -d
   ```

4. 使用数据库连接软件导入sql文件

5. 重启mysql即可正常使用

