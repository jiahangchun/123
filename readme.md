# how to use
按照idea硬件安装的方式当前插件就可以了,可以参考[Doc](https://www.jetbrains.com/help/idea/managing-plugins.html)

# 核心思想：约定大于配置
在这个的开发过程中，我设置了许多的限制条件。
比如
1. 现在只支持post & get 操作;
2. 入参和出参 我都最多支持两层结构；
3. 复杂对象 不展示，或者展示出错


# bug
这个插件完全没有经过验证测试，
我的本意是希望更多的人来帮我修复bug，我只是提供一个半成品工具。
所以，但凡遇到任何疑问，自己看代码，自己修复。

### developer test
run DocTest.main

# warn
### about lombok
https://plugins.gradle.org/plugin/io.freefair.lombok


### idea plugin doc
[doc](https://www.jetbrains.org/intellij/sdk/docs/tutorials/build_system/gradle_guide.html)
[gradle deployment](https://www.jetbrains.org/intellij/sdk/docs/tutorials/build_system/deployment.html)

