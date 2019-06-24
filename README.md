# elasticsearch数据备份与恢复

## 首先修改elasticsearch 配置文件application.yml
添加一行 path.repo: ["/usr/local/elasticsearch/snapshot"]

此路径为 es 快照路径， /usr/local/elasticsearch 在这一部分为你的 es 路径所在。


## 备份
修改 application.yml 配置文件,运行即可

## 恢复
修改 application.yml 配置文件,backup.index 的值修改为你的 snapshot  值. 
