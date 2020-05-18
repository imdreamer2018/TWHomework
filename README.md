<p align="center">
<img align="center" src="http://imdreamer.oss-cn-hangzhou.aliyuncs.com/picGo/springboot.png"/>

<p align="center"><b>SimpleBlog API</b></p>

------

<p align="center" style="color:grey;font-size:15px;">Simple blog web application by springBoot</p>

<img align="center" src="http://imdreamer.oss-cn-hangzhou.aliyuncs.com/picGo/QQ20200518-163041.png"/>

## Table of Contents

- [Background](https://github.com/imdreamer2018/SimpleBlog#background)
- [Install](https://github.com/imdreamer2018/SimpleBlog#install)
- [License](https://github.com/imdreamer2018/SimpleBlog#license)

## Background

<font face="roman">This blog web application build by springBoot when I work on ThoughtWorks as an intern.Cao Linjing and I wrote this homework together by code pair.</font>

------

### Application characteristics including:

- Post blog and comment API
- Migrate mysql support
- Redis authentication
- Email support
- Swagger support
- Authentication
- Role permissions
- REST API
- **Perfect application structure**

------

## Install

```shell
#environment Java8,gradle,springBoot
git clone https://github.com/imdreamer2018/TWHomework.git
cd TWHomework
./gradlew build
./gradlew bootRun
```

#### Necessary environment

If you want run this project in your local, you should install **mysql** and **redis**.And you can read in application.yml about mysql and redis configuration.

```yaml
datasource:
    url: ${SPRING_MYSQL_URL:jdbc:mysql://127.0.0.1:3306/spring_test?characterEncoding=utf8&characterSetResults=utf8}
    username: ${SPRING_MYSQL_ROOT_USERNAME:root}
    password: ${SPRING_MYSQL_ROOT_PASSWORD:yourpassword}
redis:
    database: 0
    host: ${SPRING_REDIS_HOST:127.0.0.1}
    port: 6379
    password: ${SPRING_REDIS_PASSWORD:yourpassword}    
```

so you should set mysql(host,database,username,password) and redis(host,password) configuration. Make sure your mysql and redis configuration are correct,then this web application will be successful runing.And click http://127.0.0.1:8080/doc.html

**I recommend you using docker to build your project. I have set up the relevant environment in docker.So you can read Docker depoly.**

------

### Docker depoly

You can build your docker images or pull my docker images.

#### Build your docker images

```shell
#using bootJar task create project Jar file
./gradlew bootJar
#Notice: move the jar file to this project root directory
#Then build your docker images
docker-compose up
```

#### Pull my docker images

```shell
#pull my docker.io images
docker pull imdreamer/tw-homework:v1
docker-compose up
```

## Contributing

Feel free to dive in! [Open an issue](https://github.com/RichardLitt/standard-readme/issues/new) or submit PRs.

Standard Readme follows the [Contributor Covenant](http://contributor-covenant.org/version/1/3/0/) Code of Conduct.

### Contributors

This project exists thanks to all the people who contribute.

## License

[MIT ](https://github.com/imdreamer2018/SimpleBlog/blob/master/LICENSE) © Imdreamer