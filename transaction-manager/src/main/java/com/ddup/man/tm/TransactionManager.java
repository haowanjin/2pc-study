package com.ddup.man.tm;

import com.ddup.man.common.XA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 事务管理者
 *
 * @author SUN
 * @date 2023/4/1
 */
@Slf4j
@Component
public class TransactionManager implements XA {

    private final Set<ResourceManagerClient> resourceManagerClients = new HashSet<>();

    /**
     * 带调度的线程池
     */
    private final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(10);

    /**
     * 注册 RM
     */
    public void register(ResourceManagerClient resourceManagerClient) {
        log.info("注册 RM {}:{}", resourceManagerClient.getServer(), resourceManagerClient.getPort());
        resourceManagerClients.add(resourceManagerClient);
    }

    /**
     * 阶段一：准备
     */
    @Override
    public boolean prepare() {
        log.info("启动阶段一：PREPARE");

        scheduledThreadPoolExecutor.schedule(() -> {
            // 1 分钟后检查结果
            // FIXME: 2PC 缺点 1：必须等所有资源都返回数据后，才能进入到下一阶段，性能差
            boolean ack = resourceManagerClients.stream().allMatch(ResourceManagerClient::getAck);
            if (ack) {
                // 如果所有都返回了 true， 那么进入下一个阶段
                // FIXME: 2PC 缺点 2：到 COMMIT 阶段时，如果 RM 资源节点挂掉就无法收到 COMMIT 请求，将造成数据不一致
                // FIXME: 2PC 缺点 3：发送 COMMIT 请求后，如果某个 RM 资源节点出错了，其他 RM 资源节点的数据又如何处理 没有规定
                if (!commit()) {
                    rollback();
                }
            } else {
                // 回滚
                // FIXME: 2PC 缺点 2：到 COMMIT 阶段时，如果 RM 资源节点挂掉就无法收到 ROLLBACK 请求，将造成数据不一致
                rollback();
            }
        }, 10, TimeUnit.SECONDS);

        for (ResourceManagerClient resourceManagerClient : resourceManagerClients) {
            scheduledThreadPoolExecutor.execute(resourceManagerClient::prepare);
        }
        return true;
    }

    /**
     * 阶段二：提交事务
     */
    @Override
    public boolean commit() {
        log.info("启动阶段二：COMMIT");
        for (ResourceManagerClient resourceManagerClient : resourceManagerClients) {
            resourceManagerClient.commit();
        }
        return resourceManagerClients.stream().anyMatch(ResourceManagerClient::getAck);
    }

    /**
     * 回滚
     */
    @Override
    public void rollback() {
        log.info("TM 开始进行回滚 rollback");
        for (ResourceManagerClient resourceManagerClient : resourceManagerClients) {
            resourceManagerClient.rollback();
        }
    }
}