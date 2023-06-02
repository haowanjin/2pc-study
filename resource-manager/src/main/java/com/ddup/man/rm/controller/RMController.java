package com.ddup.man.rm.controller;

import com.ddup.man.rm.ResourceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SUN
 * @date 2023/4/1
 */
@RestController
@RequiredArgsConstructor
public class RMController {
    
    /**
     * 阶段一：准备
     */
    @PostMapping("/prepare")
    public boolean prepare() {
        return resourceManager.prepare();
    }
    
    /**
     * 阶段二：提交事务
     */
    @PostMapping("/commit")
    public boolean commit() {
        resourceManager.commit();
        return true;
    }
    
    @PostMapping("/rollback")
    public boolean rollback() {
        resourceManager.rollback();
        return true;
    }
    
    private final ResourceManager resourceManager;
    
}
