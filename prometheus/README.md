# prometheus

## 监听配置

在prometheus.yml文件配置监听的服务

```
- job_name: 'application'  # job名称,用于查询
    scrape_interval: 5s
    metrics_path: '/actuator/prometheus' 
    file_sd_configs:
      - files: ['D:\dev_tools\prometheus-2.45.0.windows-amd64\appliction.json']
```



- job_name

  job名称用来查询

- scrape_interval

  刷新时间间隔

- metrics_path

  ```
  spring-boot-starter-actuator 监控的地址，固定写法
  ```

- file_sd_configs

  核心属性，是监听服务配置的路径，配置的文件主要用来配置监听的服务ip地址和配置监听服务的名称，

### file_sd_configs

文件格式为json

```
[
    {
        "targets": [
            "localhost:8080" 
        ],
        "labels": {
            "instance": "ting-prometheus",
            "service": "ting-prometheus-service"
        }
    }
]
```

#### targets

监听服务的地址

#### labels

添加标签用来区分服务的，可以自由配置方便后期执行 PromSQL 查询语句区分

#### 

# java使用prometheus注意事项

如果name一样，tags也必须保持一致，否则会提示异常信息

```
id为[MeterId{name='get_total', tags=[tag(application=ting-prometheus),tag(post_param_key_tag=post-json3)]}]执行失败,错误信息:Prometheus requires that all meters with the same name have the same set of tag keys. There is already an existing meter named 'get_total' containing tag keys [application, get_key_tag, tag_test]. The meter you are attempting to register has keys [application, post_param_key_tag].
```



# 查询语句

```PromSQL
ting_total{application="ting-prometheus", instance="ting-prometheus",post_param_key_tag="post-json"}
```



ting_total： 监控配置的name

application和instance 就是 配置服务的监听文件的lables属性

post_param_key_tag：就是自定义的tags key