
# Vue-SpringBoot-固定资产管理系统
这是一个前后端分离项目，前端框架主要为VUE搭建，后端框架主要为SpringBoot搭建，集成的一个后台管理系统项目
=======
# Vue-Springboot
Vue+Springboot 管理系统

###### written by leslie lu！！！

###### 配置参考

后端端口号为:http://localhost:9090
前端端口号为:http://localhost:8080

###### 此项目为前后端分离项目

如需要修改后端端口号需在application.yml中的server: port: 自行修改
如若修改了后端端口号，请在对应前端页面中将9090改为你所修改的端口号(Person、User、Menu、File、Purchase、Accept、Allocation、Management、Account)

同时若前端端口不为8080，则需在config包的CorsConfig中的跨域方案进行调整

数据库密码修改在 application.yml 中，数据库名称为leslielu，数据都在leslielu.sql中
同时还需在  application.yml 修改files: upload: paths: 为你项目所在目录\files\；这是你上传文件或者上传头像所要保存的文件夹
切勿删除files文件夹，否则则需去file数据库删除所有数据的md5，方可上传头像，不然后台去查数据比较md5时，数据的md5存在，而文件夹中并无对于md5的图片，则会导致图片无法正常显示

记得从Maven中导入所需的项目依赖

代码生成器模版在resource/templates，若需定制可自行修改相关参数，生成时需在CodeGenerator需要修改输出路径、表名等参数

前端运行需通过终端进入到vue的文件中使用命令:npm run serve （需要有node.js情况下才能启动）
后端运行通过启动 SbootApplication 即可

登录页面为http://localhost:8080/login
帐号:admin    密码:admin

###### 功能说明

#### 共有四个模块
### 1.登录与注册模块
主要功能是实现用户的登录与注册，并携带token，防止用户直接绕过登录而进入到系统的内部

### 2.主页模块
主要功能是通过Echarts来展示数据库中的一些数据，起到一定的视觉效果

### 3.系统管理模块
主要功能是负责完成用户管理与权限菜单的设置，区分好管理员与普通用户的权限，同时若需设置多个角色权限可以在角色管理添加，然后在菜单管理中进行权限分配

## 共分为5个小模块
# 用户管理
对于用户表实现增删改查以及导入、导出功能，
# 角色管理
对于角色表实现增删改查以及分配菜单，即确定权限范围的功能
# 菜单管理
对于菜单表实现增删改查的功能
若需添加新的菜单，请先在vue创建对应文件，并按照相关格式填写表单，不然会触发404错误;
（ps，有时即使成功添加后，仍然会出现404错误，请打开浏览器f12查询控制台，若显示错误是无法从/xx页面跳转至/xx页面，则请尝试按照/vue/router/index.js中的绿色文字操作）
# 文件管理
实现文件表增删改查以及上传、下载功能，同时也是负责头像上传的功能
# 台帐管理
实现固定资产表中已经结束的任务流程的查询功能，并且列出每个环节的审批负责人；同时还有导出与删除功能

### 4.业务管理模块

## 共分为4个小模块
# 购置管理
实现固定资产表中购置管理的增删改查功能，以及管理员审批功能与导出功能
# 验收管理
实现固定资产表中验收管理的增删改查功能，以及管理员审批功能与导出功能
# 调拨管理
实现固定资产表中调拨管理的增删改查功能，以及管理员审批功能与导出功能
# 处置管理
实现固定资产表中处置管理的增删改查功能，以及管理员审批功能与导出功能


### PS
**初次登录会直接进入到后台管理系统，但此时由于TOKEN的时长限制，并无法查询任何内容，需退出重新登录**

**若前端启动时遇到 《'vue-cli-service' 不是内部或外部命令，也不是可运行的程序》时，请从终端进入到vue的目录输入以下命令安装所需资源 《npm i -D @vue/cli-service》**
### ps：记得是以管理员身份进入cmd模式，目录跳转命令 cd /d 你所在的路径**

###### 以上均由Leslie Lu完成，若有什么bug，可以在leslielu21@163.com与我交流一下