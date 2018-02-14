## demo-captcha
init

### 一、介绍
>找时间完善文档

spring-boot + dubbo + redis 的服务框架，提供dubbo服务和rest服务。

### 二、详情

#### 2.1.关于`dubbo`版本

随着dubbo社区的维护重启，和即将到来的3.0版本，都是值得期待的改变。
由于历史原因，本demo工程最初用的是dubbo的衍生分支，和第三方的springboot版本，不能很好的兼容原生的dubbo，所以考虑新拉分支，支持阿里原生dubbo和springboot版本。


分支介绍

|id|分支|dubbo版本|备注|
|:---|:---|:---|:---|
|1|master|2.6.0|原生dubbo，阿里维护的|
|2|dev_internal|2.8.4|衍生，闭源的|


### 二、开发计划相关

#### todo-list

2018-2-13
- 1.pom的优化，javassist包引用问题；
- 2.用阿里原生的dubbo包，升级到2.6.0版本；
- 3.用阿里原生的springboot包；
- 4.captcha的bo梳理，统一名称；
- 5.LoggerFactory的重复问题优化，<log4j:WARN No appenders could be found for logger (com.alibaba.dubbo.common.logger.LoggerFactory).>；
- 6.完善md文档；

### 完成情况

### 其他
