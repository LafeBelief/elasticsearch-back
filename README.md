# elasticsearch数据备份与恢复

## 描述
每当elasticsearch 断电或者数据锁坏时，此时 重启es就特别麻烦，es 状态为 red，尤其是还原数据，
有鉴于此，所以需要对es 数据进行备份还原。
---
## 首先修改elasticsearch 配置文件 elasticsearch.yml
添加一行 path.repo: ["/usr/local/elasticsearch/snapshot"]

此路径为 es 快照路径， /usr/local/elasticsearch 在这一部分为你的 es 路径所在。
重启es。

## 修改下 ip 和 端口
# 注意：application.yml 的 lock 属性，如果为 备份 一定要设置成 false，如果 为 恢复 设置成 true 
---
## 备份
修改 application.yml 配置文件,运行即可
#### 备份数据日志信息
* 2019-06-24 13:46:24.408  INFO 6392 --- [   scheduling-1] c.h.e.back.BackScheduled                 : es 数据备份,开始执行...
  2019-06-24 13:46:24.477  INFO 6392 --- [   scheduling-1] c.h.e.back.BackScheduled                 : 删除当前索引:	2019062413,返回信息:
  {
    "acknowledged" : true
  }
  
  2019-06-24 13:46:25.620  INFO 6392 --- [   scheduling-1] c.h.e.back.BackScheduled                 : 增量任务备份完成,索引为:	2019062413,返回信息:
  {"snapshot":{"snapshot":"2019062413","uuid":"x45o140WRpS-M80aoqFXBg","version_id":6020499,"version":"6.2.4","indices":["ik_v2","asdf","ikxv2","ikx1v2"],"include_global_state":true,"state":"SUCCESS","start_time":"2019-06-24T05:46:31.650Z","start_time_in_millis":1561355191650,"end_time":"2019-06-24T05:46:31.784Z","end_time_in_millis":1561355191784,"duration_in_millis":134,"failures":[],"shards":{"total":20,"failed":0,"successful":20}}}

---
## 恢复
修改 application.yml 配置文件.
#### 恢复日志信息
出现: '数据恢复完成' , 则恢复成功,es 状态应显示为 green,如果不是,请稍等一会.

## 运行命令
java -jar elasticsearch-back.jar

