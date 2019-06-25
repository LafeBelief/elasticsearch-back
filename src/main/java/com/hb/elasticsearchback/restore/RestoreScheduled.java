package com.hb.elasticsearchback.restore;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

/**
 * 数据恢复
 *
 * @author wbw
 * @date 2019/6/24 10:26
 */
public class RestoreScheduled {


    private Log log = LogFactory.get(RestoreScheduled.class);

    public void restoreData(String ipPort) {

        String backupIndex = this.getBackupIndex(ipPort);
        HttpRequest request = HttpUtil.createRequest(Method.DELETE, "http://" + ipPort + "/_all");
        HttpResponse execute = request.execute();
        log.info("数据清空完成,信息:\n{}", execute.body());

        request = HttpUtil.createRequest(Method.POST, "http://" + ipPort + "/_snapshot/backup/" + backupIndex + "/_restore");
        execute = request.execute();
        log.info("数据恢复完成,信息:\n{}", execute.body());
        System.exit(0);
    }

    private String getBackupIndex(String ipPort) {
        try {
            HttpResponse execute = HttpUtil.createGet("http://" + ipPort + "/_snapshot/backup/_all").execute();
            String body = execute.body();
            if (!body.contains("include_global_state") || !body.contains("snapshot") || !body.contains("uuid")) {
                log.info("elasticsearch 备份索引错误 ....");
                System.exit(0);
            }
            JSONObject jsonObject = JSONUtil.parseObj(body);
            JSONArray snapshots = jsonObject.getJSONArray("snapshots");

            JSONObject snapshot = JSONUtil.parseObj(snapshots.get(snapshots.size() - 1));
            return snapshot.getStr("snapshot");
        } catch (Exception e) {
            log.error("elasticsearch 数据恢复失败....",e);
            System.exit(0);
            return null;
        }
    }


}
