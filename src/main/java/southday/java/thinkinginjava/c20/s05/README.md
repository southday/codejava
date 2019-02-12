### 20.5.3 实现@Unit

实现一个简单的单元测试工具类@Unit

注解类型组成：
- @Test
- @TestObjectCreate
- @TestObjectCleanup
- @TestProperty

需要导入mindview相关类，这里使用maven管理，添加如下依赖：

```xml
<dependency>
  <groupId>com.github.southday</groupId>
  <artifactId>tij4-mindview</artifactId>
  <version>1.0.0</version>
</dependency>
```

目前`tij4-mindview`只是放在我的本地仓库中，因为网址（[https://issues.sonatype.org](https://issues.sonatype.org)）访问太慢，所以暂时没有传到公有仓库中