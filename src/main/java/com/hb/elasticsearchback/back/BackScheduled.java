package com.hb.elasticsearchback.back;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.hb.elasticsearchback.restore.RestoreScheduled;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author wbw
 * @date 2019/6/24 9:18
 */
@Component
@Configuration
@EnableScheduling
public class BackScheduled {

    @Value("${es.ip.host}")
    private String esIpHost;

    @Value("${es.ip.port}")
    private int esIpPort;

    @Value("${es.lock}")
    private Boolean esLock;

    @Value("${es.backup.index}")
    private String esBackupIndex;


    private Log log = LogFactory.get(BackScheduled.class);

    /**
     * 十分钟执行一次定时任务备份数据
     *
     * @author wbw
     * @date 9:21 2019/6/24
     */
    @Scheduled(fixedRate = 600000)
    private void configureTasks() {
        if (StringUtils.isEmpty(esIpHost) || esIpPort == 0) {
            log.error("配置文件错误,请检查.... ip 及 端口");
            System.exit(0);
        }
        if (esLock != null && esLock) {
            RestoreScheduled restoreScheduled = new RestoreScheduled();
            restoreScheduled.restoreData(esIpHost, esIpPort, esBackupIndex);
            return;
        }

        log.info("es 数据备份,开始执行...");
        String indexName = DateUtil.format(new Date(), "yyyyMMddHH");
        HttpRequest request = HttpUtil.createRequest(Method.DELETE, esIpHost + ":" + esIpPort + "/_snapshot/backup/" + indexName + "?pretty");
        HttpResponse execute = request.execute();
        log.info("删除当前索引:\t{},返回信息:\n{}", indexName, execute.body());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("线程延时错误{}", e.getMessage());
        }
        request = HttpUtil.createRequest(Method.PUT, esIpHost + ":" + esIpPort + "/_snapshot/backup/" + indexName + "?wait_for_completion=true");
        execute = request.execute();
        log.info("增量任务备份完成,索引为:\t{},返回信息:\n{}", indexName, execute.body());
    }
}
