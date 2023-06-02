package com.ddup.man.twopc;

import com.ddup.man.common.constant.UrlConstant;
import com.ddup.man.common.utils.HttpUtil;

/**
 * 事务管理者
 *
 */
public class TransactionManager {

    private final String server;

    private final int port;

    public TransactionManager(String server, int port) {
        this.server = server;
        this.port = port;
    }

    /**
     * 开启事务
     */
    public void start() {
        prepare();
    }

    public void register(ResourceManager resourceManager) {
        HttpUtil.post(String.format("http://%s:%s/%s", this.server, this.port, UrlConstant.REGISTER), resourceManager,
                Boolean.class);

    }

    private void prepare() {
        HttpUtil.post(String.format("http://%s:%s/%s", this.server, this.port, UrlConstant.PREPARE), null,
                Boolean.class);
    }


}
