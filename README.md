# Hydra

代号九头蛇，致力于打造全平台通用的自动化测试框架

# Pre-Conditions:
1. JDK8 installed (http://www.oracle.com/technetwork/java/javase/downloads/index.html)
2. Maven installed (http://maven.apache.org/download.cgi)


# Quick Start:
1. clone 工程到本地目录
2. try runTest.sh feature/test.control
3. wait & see ... 

# 框架基本思路
1. BDD，测试同学专注测试逻辑。
2. 尽量Download & Play。 
3. 基于JVM和Maven，能复用大部分互联网公司的轮子（UI的WebDriver、Appium、Restful、BDD的Cucumber-jvm、Jasmine、Pytest(需要jython支持）
4. 起于JVM + Jasmine, 即通过JVM上的动态脚本语言能力，Inject Jasmine的BDD框架，同时能无缝访问java世界中的无数的成熟工具。
5. 对于测试用例开发者来说，一个简单的文本编辑器即可。
6. 同时也可以通过maven直接打出all-in-one的可执行jar包。方便接入C.I
7. 提供统一的Report和Verdict。 设定了统一的CaseResult和SuiteResult，对于不同的Runner（Jasmine，TestNG，PyTest或者Cucumber-jvm）来说能获得统一的case和suite粒度的报告。

# 工程配置方法

1、用idea打开，选择pom.xml，工程的入口为com.xiaohongshu.automation.Application.java    
2、将conf路径加入到classpath中，方式如下：     
    
    IntelliJ中右键点击conf目录选择Mark folder as “Resource Root”。

# 工程规范

1. 所有的包请都放在com.xiaohongshu.automation下
2. 包/类／方法等命名需要有明确的含义，且符合命名规则
3. 必须写unittest，对应class的unittest class，命名为Test_**,且放在相同包路径下    
4. unittest的行覆盖率要求在75%以上，且全部运行通过，才能push    

# 目录说明

1. conf: 配置文件路径
2. src.main.java: 源代码
3. src.main.resources: 资源路径
4. src.test.java: 测试代码

# JAVA源代码包说明

java包路径为：com.xiaohongshu.automation，其下：

1. config: 配置解析相关功能
2. runner：执行器
3. service：支持的各种测试服务包
4. result: 测试结果包
5. report：报告生成包
6. utils: 工具包
