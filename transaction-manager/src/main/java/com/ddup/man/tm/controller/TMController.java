package com.ddup.man.tm.controller;

import com.ddup.man.tm.ResourceManagerClient;
import com.ddup.man.tm.TransactionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SUN
 * @date 2023/4/1
 */
@RestController
@RequiredArgsConstructor
public class TMController {

    /**
     * 注册 RM
     */
    @PostMapping("/register")
    public boolean register(@RequestBody ResourceManagerClient resourceManagerClient) {
        transactionManager.register(resourceManagerClient);
        return true;
    }


    /**
     * 阶段一：准备
     */
    @PostMapping("/prepare")
    public boolean prepare() {
        transactionManager.prepare();
        return true;
    }


    /**
     * 阶段二：提交事务
     */
    @PostMapping("/commit")
    public boolean commit() {
        transactionManager.commit();
        return true;
    }

    /**
     * 阶段二：提交事务
     */
    @PostMapping("/rollback")
    public boolean rollback() {
        transactionManager.rollback();
        return true;
    }


    private final TransactionManager transactionManager;


}
