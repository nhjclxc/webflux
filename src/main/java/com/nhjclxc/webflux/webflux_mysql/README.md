# webflux
springboot项目实现流式输出 - webflux-mysql

参考：https://www.jianshu.com/p/60a4e4480eaf
https://cloud.tencent.com/developer/article/2224413


1. 创建项目添加相关依赖【[pom.xml](..%2F..%2F..%2F..%2F..%2F..%2F..%2Fpom.xml)】
2. 配置文件【[application.yml](..%2F..%2F..%2F..%2F..%2Fresources%2Fapplication.yml)】
3. 创建相关表结构【[tab_user.sql](tab_user.sql)】
4. 编写代码【[]()】
   - 编写实体类【[TabUser.java](TabUser.java)】
   - 编写对应的仓库接口【[TabUserRepository.java](TabUserRepository.java)】
   - 编写处理器TabUserHandler【[TabUserHandler.java](TabUserHandler.java)】
   - 配置请求地址路由【[RouterConfiguration.java](RouterConfiguration.java)】
