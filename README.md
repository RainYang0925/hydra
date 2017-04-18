# Hydra

代号九头蛇，致力于打造全平台通用的自动化测试框架

# Pre-Conditions:
1. JDK8 installed (http://www.oracle.com/technetwork/java/javase/downloads/index.html)
2. Maven installed (http://maven.apache.org/download.cgi)


# Quick Start:
1. clone 工程到本地目录
2. try runTest.sh feature/test.control
3. wait & see ... 

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
