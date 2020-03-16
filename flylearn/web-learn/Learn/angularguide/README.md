## angular2官方文档浏览|实现|心得

> 在完成了从0到1的教程之后，为了进一步巩固和加深angular2的理解，决定在通读一遍angular2官方文档，并且实现其中的代码，并整理出文档

### 一、快速上手

能够快速进行angular2开发的小教程，从设置开发环境，到最后打开完成Hello-angular2的过程。

``` 
ng new my-app //创建新项目
ng server --oepn //启动开发服务器
ng g c test; //创建新组件
ng g s service; //创建新service
```

之后就是了解整个项目的目录文件，这个之前已经知道了，组件、模板、样式、图片以及第三方模块、配置文件、单元测试、gitp配置文件、npm配置文件等等一系列文件都可以在这里知道其作用。

### 二、 “英雄”指南教程项目

（1）简介

大致描述了英雄指南教程的作用以及在这个教程当中能够学习到的内容。以下为将要完成工作：

- 使用`内置指令`来显示 / 隐藏元素，并且显示英雄数据的列表。
- 创建 Angular 组件以显示英雄的详情，并显示一个英雄数组。
- 为只读数据使用`单项数据绑定`。
- 添加可编辑字段，使用`双向数据`绑定来更新模型。
- 把`组件中的方法绑定到用户事件`上，比如按键和点击。
- 让用户可以在主列表中选择一个英雄，然后在详情视图中`编辑`他。
- 使用`管道`来格式化数据。
- 创建`共享`的服务来管理这些英雄。
- 使用`路由`在不同的视图及其组件之间导航。

这些基本内容都在之前的项目当中遇到并使用过了，接下来就完成这个指南项目。

（2）完成过程

1. 首先创建heroes的组件和类，并组合起来使用，并且使用管道uppercasepipe来格式化名称，同时也使用ngModel来实现双向数据绑定，通过app.module来引入必要的指令包（FormsModule）。

2. 创建hero.json创建英雄的json文件，并通过json-server模拟服务；创建各自的路由module文件，引入路由；创建service来获取数据，使用Observable来完成数据的获取及传递：

   获取数据：   `return this.http.get(url) .map(res => res.json() as Hero[]).catch(this.handleError);`

   接收数据：   `this.heroesService.getAllHero().subscribe((heroes:Hero[]) => {this.heroes = [...heroes];});`

   之后就是\*ngFor循环，\*ngIf=‘selectHero’判断是否为undefined,[class.selelected]="hero === selectHero"来进行样式的改变。

3. 接着将英雄详情拆分出去，作为单独的组件，这就涉及到了父子组件之间的通信问题，可以@Input装饰器从父组件获取东西，同时也可以使用@Output() onRemoveTriggered = new EventEmitter<boolean>();向父组件发射事件。

4. 最开始是创建service并通过Observable的方式获取数据，这个在最开始的时候已经完成了；接下来就是创建一个MessageService，以此来进行类之间的松耦合通讯。

5. > Angular 的最佳实践之一就是在一个独立的顶级模块中加载和配置路由器，它专注于路由功能，然后由根模块 `AppModule` 导入它。

   根据这句话来完成这个流程的工作，使用路由来控制各个模块的展示。主要是使用routerLink来完成各个路由的装载，使其可以放在一个页面当中展示出来

6. ​

   ​

