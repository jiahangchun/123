# how to use
按照idea硬件安装的方式当前插件就可以了,可以参考[Doc](https://www.jetbrains.com/help/idea/managing-plugins.html)

#约定大于配置
在这个的开发过程中，我设置了许多的限制条件。
比如
1. 现在只支持post & get 操作;
2. 入参和出参 我都最多支持两层结构；
3. 复杂对象 不展示，或者展示出错


# bug
第一次 先用 convenient的dev-200120-jhc-swaggerVersion分支测试吧。
我就还没来得及测试过，只是简单尝试了下接口：/crm/shipId/get/default/shipId
bug嘛，有很多，有没有人帮忙？

### developer test
run DocTest.main 测试主要方法
or run gradle    测试plugins文档上有的

### how to build plugins
gradle build 生成可执行jar，在/build/libs下找找就有了


# warn
### about lombok
https://plugins.gradle.org/plugin/io.freefair.lombok


### idea plugin doc
[doc](https://www.jetbrains.org/intellij/sdk/docs/tutorials/build_system/gradle_guide.html)
[gradle deployment](https://www.jetbrains.org/intellij/sdk/docs/tutorials/build_system/deployment.html)

