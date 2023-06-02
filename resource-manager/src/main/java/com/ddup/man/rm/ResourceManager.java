package com.ddup.man.rm;

import com.ddup.man.common.XA;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author SUN
 * @date 2023/4/1
 */
@Slf4j
@Data
@Component
public class ResourceManager implements XA {
    @Value("${server.port}")
    private int port;

    /**
     * 回复
     * <p>
     * 回复 TRUE(OK) 可以继续 / 回复 FALSE(), 不可以继续
     */

    @Override
    public boolean prepare() {
        boolean ack = Math.random() * 100 - 50 > 0;
        log.info("{} 收到 PREPARE 请求，回复 {}", port, ack);
        return ack;
    }

    @Override
    public boolean commit() {
        boolean ack = Math.random() * 1000 - 500 > 0;
        log.info("执行事务提交 COMMIT 操作,操作{}", ack ? "成功" : "失败");
        return ack;
    }

    @Override
    public void rollback() {
        log.info("执行事务回滚 ROLLBACK 操作");
    }
}
