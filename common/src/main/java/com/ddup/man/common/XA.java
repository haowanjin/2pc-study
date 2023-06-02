package com.ddup.man.common;

/**
 * XA 协议 / 也叫做 2PC / 两阶段提交
 */
public interface XA {
    
    boolean prepare();
    
    boolean commit();
    
    void rollback();
}
