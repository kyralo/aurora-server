# 前言
本项目初期架构比较简陋, 后续会一直精修完善.

# 项目简介
aurora是一个视频网站, 这是aurora项目的后端源码, 
网站外观是仿YouTube进行开发的(选油管为原型的原因, 主要是因为油管的风格比较简约, 用起来体验好一点),
然后, 本项目目前只具备了一个视频网站的基本雏形, 后续开发会持续加入新模块, 
并且项目目前使用springboot框架搭建, 后续会换成spring cloud alibaba.


# Api 文档
![](https://cdn.jsdelivr.net/gh/kyralo/aurora-server/aurora-server-api.png)

# 演示DEMO
[AURORA](https://aurora.kyralo.online)

# 前端项目地址
[aurora-web](https://github.com/kyralo/aurora-web)

# 项目用到的后端技术
- 基本骨架: Springboot + Mybatis
- 数据库: MySQL (使用阿里druid数据库连接池技术)
- 鉴权: Spring security + jwt
- 缓存: Redis (视频热门模块, 用户点赞模块使用到)
- 定时任务: Spring Task
- 邮件: Spring mail
- 日志记录: Logback
- 文件上传: 现在暂时是本地上传(之后会引入阿里oss)
- api文档: Swagger (样式引用了GitHub上作者caspar-chen写的样式)
- 项目构建工具: Maven

# 用到的库
- tk.mybatis 用于基础SQL的生成
- hutool 和 guava 通用工具类集成

# 项目目录结构
    AuroraApplication.java
    ├── common
    │   ├── config
    │   │   ├── DaoConfig.java
    │   │   ├── RedisConfig.java
    │   │   ├── SwaggerConfig.java
    │   │   ├── TaskExecutorConfig.java
    │   │   ├── WebMvcConfig.java
    │   │   └── WebMvcDevConfig.java
    │   ├── constants
    │   │   ├── CommonConstants.java
    │   │   ├── SecurityConstants.java
    │   │   └── VideoConstants.java
    │   ├── security
    │   │   ├── MethodSecurityConfig.java
    │   │   ├── RestAuthenticationEntryPoint.java
    │   │   ├── WebSecurityConfig.java
    │   │   └── filter
    │   ├── task
    │   │   ├── CommentTask.java
    │   │   ├── GivesTask.java
    │   │   └── VideoTask.java
    │   └── util
    │       ├── CommonUtil.java
    │       ├── ErrorMessage.java
    │       ├── FileUpUtil.java
    │       └── JwtUtil.java
    ├── fileup
    │   ├── service
    │   │   ├── FileUpService.java
    │   │   └── impl
    │   └── web
    │       └── FileUpController.java
    ├── search
    │   ├── domain
    │   │   └── Search.java
    │   ├── service
    │   │   ├── SearchService.java
    │   │   └── impl
    │   └── web
    │       └── SearchController.java
    ├── user
    │   ├── dao
    │   │   ├── UserCollectionMapper.java
    │   │   ├── UserCommentMapper.java
    │   │   ├── UserGivesMapper.java
    │   │   └── UserMapper.java
    │   ├── domain
    │   │   ├── Comment.java
    │   │   ├── GivesForComments.java
    │   │   ├── GivesForVideo.java
    │   │   ├── RegisterVo.java
    │   │   ├── User.java
    │   │   ├── UserCollection.java
    │   │   ├── UserComment.java
    │   │   └── UserGives.java
    │   ├── service
    │   │   ├── LoginService.java
    │   │   ├── MailService.java
    │   │   ├── UserCollectionService.java
    │   │   ├── UserCommentService.java
    │   │   ├── UserGivesService.java
    │   │   ├── UserService.java
    │   │   └── impl
    │   └── web
    │       ├── LoginController.java
    │       ├── UserCollectionController.java
    │       ├── UserCommentController.java
    │       ├── UserController.java
    │       └── UserGivesController.java
    └── video
        ├── dao
        │   ├── VideoKindMapper.java
        │   └── VideoMapper.java
        ├── domain
        │   ├── Video.java
        │   ├── VideoKind.java
        │   └── VideoList.java
        ├── service
        │   ├── VideoKindService.java
        │   ├── VideoService.java
        │   └── impl
        └── web
            ├── VideoController.java
            └── VideoKindController.java


