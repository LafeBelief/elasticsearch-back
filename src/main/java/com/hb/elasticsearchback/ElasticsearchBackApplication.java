package com.hb.elasticsearchback;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.hb.elasticsearchback.back.BackScheduled;
import com.hb.elasticsearchback.restore.RestoreScheduled;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * elasticsearch 数据备份与恢复
 *
 * @author wbw
 * @date 9:11 2019/6/24
 */
@SpringBootApplication
public class ElasticsearchBackApplication {


    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchBackApplication.class, args);

    }

}
