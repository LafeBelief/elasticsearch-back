package com.hb.elasticsearchback.restore;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.util.StringUtils;

/**
 * 数据恢复
 *
 * @author wbw
 * @date 2019/6/24 10:26
 */
public class RestoreScheduled {


    private Log log = LogFactory.get(RestoreScheduled.class);

    public void restoreData(String esIpPort, int esIpHost, String esBackupIndex) {
        if (StringUtils.isEmpty(esBackupIndex) || !NumberUtil.isNumber(esBackupIndex)) {
            log.info("索引错误,请检查配置文件...." + esBackupIndex);
            System.exit(0);
        }
        HttpRequest request = HttpUtil.createRequest(Method.DELETE, "http://" + esIpPort + ":" + esIpHost + "/_all");
        HttpResponse execute = request.execute();
        log.info("数据清空完成,信息:\n{}", execute.body());

        request = HttpUtil.createRequest(Method.POST, "http://" + esIpPort + ":" + esIpHost + "/_snapshot/backup/" + esBackupIndex + "/_restore");
        execute = request.execute();
        log.info("数据恢复完成,信息:\n{}", execute.body());
        System.exit(0);
    }
}
