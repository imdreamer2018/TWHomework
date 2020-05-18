
<p align="center">
<img align="center" src="http://imdreamer.oss-cn-hangzhou.aliyuncs.com/picGo/springboot.png"/>


<p align="center"><b>SimpleBlog API</b></p>

------

<p align="center" style="color:grey;font-size:15px;">Simple blog web application by springBoot</p>

<img align="center" src="http://imdreamer.oss-cn-hangzhou.aliyuncs.com/picGo/QQ20200518-163041.png"/>

## Table of Contents

- [Background](https://github.com/imdreamer2018/TWHomework#background)
- [Install](https://github.com/imdreamer2018/TWHomework#install)
- [Contrubuting](https://github.com/imdreamer2018/TWHomework#Contrubuting)
- [License](https://github.com/imdreamer2018/TWHomework#license)

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

<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-2-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->

### Contributors ✨

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->

<table>
  <tr>
    <td align="center"><a href="https://www.dreamer.im"><img src="https://avatars0.githubusercontent.com/u/35443799?v=4" width="100px;" alt=""/><br /><sub><b>imdreamer</b></sub></a><br /><a href="#design-imdreamer2018" title="Design">🎨</a></td>
    <td align="center"><a href="https://github.com/764775185"><img src="https://avatars0.githubusercontent.com/u/44635799?v=4" width="100px;" alt=""/><br /><sub><b>764775185</b></sub></a><br /><a href="https://github.com/imdreamer2018/TWHomework/commits?author=764775185" title="Code">💻</a></td>
  </tr>
</table>

<!-- markdownlint-enable -->
<!-- prettier-ignore-end -->
<!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification. Contributions of any kind welcome!

>>>>>>> cfdd54a6691b21b9a708f9795d83748d8e2ab6de

## License

[MIT ](https://github.com/imdreamer2018/TWHomework/blob/master/LICENSE) © Imdreamer