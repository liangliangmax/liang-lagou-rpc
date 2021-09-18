### 项目说明

项目目录为liang-lagou-rpc 下的 lg-rpc，其他为demo



lg-rpc-api为接口层，提供了公共的api接口

lg-rpc-provider为服务端，使用netty提供接口服务；

lg-rpc-consumer为消费端，注入api接口，在需要的地方注入api即可



在浏览器中输入http://localhost:8080/user/getUserById?id=2 ， id可以是1或者2。

在com.lagou.rpc.consumer.controller.UserController中会接受到请求，然后调用IUserService生成的代理对象去调用服务端，在创建代理对象时候里面维护了一个简单的服务器列表，正常这个列表应该是从zookeeper或者别的注册中心获取，这里简单就直接写死了。



发送请求的时候采用轮询机制，轮询服务器列表中的地址，向指定的地址发送请求。控制台会打印出

```
从RpcClientProxy.NetAddress(ip=127.0.0.1, port=9900)处请求数据
从RpcClientProxy.NetAddress(ip=127.0.0.1, port=9901)处请求数据
从RpcClientProxy.NetAddress(ip=127.0.0.1, port=9900)处请求数据
从RpcClientProxy.NetAddress(ip=127.0.0.1, port=9901)处请求数据
从RpcClientProxy.NetAddress(ip=127.0.0.1, port=9900)处请求数据
```

页面会显示出结果

```
{"id":2,"name":"李四"}
```

