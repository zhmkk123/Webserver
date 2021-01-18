上一个版本完成了注册页面提交表单数据以及HttpRequest对表单的解析。
本版本完成处理注册业务。

注册页面中form表单中action="./regUser"使得表单提交后的请求路径requestURI的值为/myweb/regUser
因此，ClientHandler在处理请求的环节要对这个请求路径做分支判定，如果请求路径是这个值就要处理注册，而
不是去webapps目下找到对应的文件。
ClientHandler也要做一个改动，以前是根据uri属性作为请求路径，现在则应当根据requestURI这个属性作为
请求路径，因为uri可能包含参数，而不是纯粹的请求部分了。但是通过HttpRequest的解析，我们将抽象路径uri中
的请求部分存入了属性requestURI中，因此可以使用它。

实现:
1:修改ClientHandler处理请求环节获取请求路径的操作，将原来的获取HttpRequest中uri改为获取requestURI
2:添加一个分支判断，如果请求路径是"/myweb/regUser"则执行处理注册的操作
  否则才执行原来的处理环节操作(去webapps下找文件...)
3:处理请求的环节我们也做功能拆分，不在ClientHandler中直接处理
  3.1:新建一个包com.webserver.servlet
      这个包保存所有处理业务的类
  3.2:在servlet包中新建一个类RegServlet并定义service方法用于处理注册业务
  3.3:在ClientHandler判断请求为注册时实例化RegServlet并调用service方法处理注册即可