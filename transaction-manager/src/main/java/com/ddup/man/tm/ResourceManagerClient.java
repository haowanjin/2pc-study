package com.ddup.man.tm;

import com.ddup.man.common.constant.UrlConstant;
import com.ddup.man.common.utils.HttpUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author SUN
 * @date 2023/4/1
 */
@Data
@Slf4j
public class ResourceManagerClient {

    private String server;
    private int port;

    /**
     * 回复内容
     * <p>
     * 回复 TRUE(OK) 可以继续 / 回复 FASLE(), 不可以继续
     */
    private Boolean ack;

    public ResourceManagerClient(String server, int port) {
        this.server = server;
        this.port = port;
    }

    public void prepare() {
        try {
            this.ack = HttpUtil.post(String.format("http://%s:%s/%s", server, port, UrlConstant.PREPARE), null, Boolean.class);
            log.info("{} {} prepare 回复了 {}", server, port, ack);
        } catch (Exception e) {
            // 请求发送失败，等一秒后重新请求
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ignored) {
            }
            log.info("RM {}:{} 发送 prepare 失败，重启发送请求", server, port);
            prepare();
        }
    }

    public void commit() {
        this.ack = HttpUtil.post(String.format("http://%s:%s/%s", server, port, UrlConstant.COMMIT), null, Boolean.class);
        log.info("{} {} commit 回复了 {}", server, port, ack);
    }

    public void rollback() {
        HttpUtil.post(String.format("http://%s:%s/%s", server, port, UrlConstant.ROLLBACK), null, Boolean.class);
    }
}
