package com.ddup.man.twopc;


public class TwoPc {
    
    
    public static void main(String[] args) {
        // 1. 准备好 TM
        TransactionManager tm = new TransactionManager("127.0.0.1", 9900);
        
        // 2. 准备好 RM
        ResourceManager rm1 = new ResourceManager("127.0.0.1", 9901);
        ResourceManager rm2 = new ResourceManager("127.0.0.1", 9902);
        
        // 向 TM 注册 RM
        tm.register(rm1);
        tm.register(rm2);
        
        // 3. 启动事务
       tm.start();
        
    }
}
