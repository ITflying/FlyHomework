### angular学习心得

> 虽然之前有过使用angluar做项目的经验，但是之前都是根据模式模仿的做，看起来更像是jsp文件，这次跟着教程系统的学习了一遍。

#### （1）初步认识

先了解一下Anuglar2吧，它是谷歌推出的一个`跨平台全终端框架`，拥有完整的解决方案；支持TyepScript；同时能够使用Rxjs和redure来进行响应式开发；支持原声安卓和IOS的开发；支持服务端渲染。说完了Anuglar2的优点，然后说说它的缺点吧，首当其冲就是SEO问题，它所有内容都是动态获取并渲染生产的；其次是性能问题，为了实现数据的双向绑定，复杂对象及大数组会存在相关的性能问题。
介绍完Anuglar2之后，我们开始第一个程序hello-angular，通过这个小项目了解Anuglar2的项目结构。进行angular2构建项目的流程为

1. 安装node.js，node xxx.msi
   .	安装TS ，npm install -g typescript typings
   .	安装cli，npm install -g @angular/cli
   .	安装Visual Studio Code
   .	ng new hello-angluar,通过VS打开项目。

之后就可以看到整个项目的文件目录了。其中e2e是端到端测试代码目录，node_modules是存放资源包的地方，out应该是输出到浏览器用的文件夹，src就是用来编程的文件夹，里面会有app（应用目录），assets（站点资源文件夹），environments（配置环境文件夹），dist是项目打包之后的资源文件，当然还有很多文件来支持整个项目运行。

其中了解Anuglar2的结构必须要知道`模块`和`组件`的存在，模块按我理解就像是MVC当中的业务模块，然后组件就是其中更细分的业务，比如mvc中的controller之类的。里面会有routing、service、component、module、ts实体类等要素。

#### （2）login的基本实现

实现一个登陆功能，这个就用到了service层，同时在component当中使用DI来完成对service的注入；也用到了双向绑定，表单数据的获取及自定义验证。这些功能的实现如果使用之前的jquery方式来完成的话，需要进行大量的click和操作元素的操作，但是Anuglar2数据绑定的方式大大减少了这种元素操作。

#### （3）TODO页面的简单实现

首先创建路由，按照谷歌的标准，每个模块都应该有个rout的module来专门管理路由，在app.routing.module当中引入这些路由。通过为了使用这些路由，需要一个插头（outlet）来装载路由，也就是 <router-outlet></router-outlet>。同时使用angular-in-memory-web-api来进行内存数据库的使用，这样我们就可以像访问服务器进行数据的交互。同时更新service，采用promise模式进行数据的交互。

#### （4）模块化应用

将TODO模块为todo-header,todo-bottom,todo-list,todo-ite，这就得完成子组件和父组件之间的交互，子组件通过EventEmitter来发射变化给父组件感知，第一次使用到Rx来完成项目事件的绑定。改写todo.module，将子组件包含进来，引入CommonModule。同时创建路由文件，app.rout中创建无组件路由。同时使用json-server来完成json文件的服务器化。最后将剩余TODO的功能补全。

#### （5）多用户版本的待办事项应用

这个部分主要是加上用户的概念，进行用户账户验证，为此需要使用一个用户管理service，验证service，路由拦截器来完成整个用户验证流程。同时我们还了解到路由守卫的概念，路由器支持以下多种守卫：

- 用CanActivate来处理导航到某路由的情况。


- 用CanActivateChild处理导航到子路由的情况。
- 用CanDeactivate来处理从当前路由离开的情况。
- 用Resolve在路由激活之前获取路由数据。
- 用CanLoad来处理异步导航到某特性模块的情况。

这样之后就可以完善各文件的逻辑，完成整个用户验证的流程，在这里使用了localstarge来进行缓存用户信息，之后就不会使用了。同时我们学习到用VSCode来进行调试，依赖于debugger for chrome这个插件可以代码的调试的，很棒的功能。

#### （6）使用第三方样式库及模块优化

使用ng build --prod --aot来建立生产环境，将创建的dist文件夹里的内容部署到服务器就可以看到具体的使用了。而使用第三方样式库则使用了angular2-mdl来做例子，使用scss文件dion定义颜色，使用它提供的标签来完成样式的修改美化。
之后对模块进行优化，将一些通用的module放在一起，方便重复调用。多个组件之间的通信使用Rx来完成能够简化其流程，这个东西我感觉在这里使用就像一个全局的观察者和被观察者的使用。

#### （7）响应式css框架

使用mdl第三方样式库改造login页面，使其变成响应式css布局，同时利用bing提供的api接口自动更换login页面图片。利用angular2的动画功能实现注册框的渐隐渐现功能。

#### （8）Rx--隐藏在Angular 2.x中利剑

Rx响应式扩展能够大大的减少对元素的操作，并且能够响应式的处理数据、操作事件。利用类操作符、管道等来完成。

#### （9）Redux

Redux就是用来解决应用状态而提出的解决方案，通过类似数据库的表存储状态，然后通过Action和Reducer来完成各类状态的管理。这个东西我没有升入研究，只是大概了解了一下。

#### （10）查缺补漏大合集

这里依次介绍了引入第三方JS库的错误解决方案，惰性路由（ loadChildren: 'app/playground/playground.module#PlaygroundModule'）和子路由(children:[])，Angular的一些动画效果，各类管道应用（感觉就是对数据的格式化处理），Directive指令：结构性指令、属性型（Attribute）指令，Component。

------

最后将整个项目的跟着做了一遍，基础的方面大概都知道了，大部分的概念和有用的东西也接触过了，之后就是开始做项目，学习更深层的东西了。

衷心感谢：@接灰的电子产品
教程简书地址：https://www.jianshu.com/p/9af9f203e0b1
github地址：https://github.com/wpcfan/awesome-tutorials