package com.nowcoder.community;

import com.nowcoder.community.service.AlphaService;
import org.aspectj.lang.annotation.Aspect;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class ThreadPoolTests {

    private static final Logger logger= LoggerFactory.getLogger(ThreadPoolTests.class);


    //spring普通线程池
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    //Spring可执行定时任务线程池
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    private AlphaService alphaService;

    private void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //Spring普通线程池
    @Test
    public void testThreadPoolTaskExecutor(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                logger.debug("Hello ThreadPoolTaskExecutor");
            }
        };

        for (int i =0;i<10;i++){
            taskExecutor.submit(task);
        }
        sleep(10000);
    }

    //spring定时任务线程池
    @Test
    public void testThreadPoolTaskScheduler(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                logger.debug("Hello ThreadPoolTaskExecutor");
            }
        };

        Date startTime = new Date(System.currentTimeMillis()+10000);
        taskScheduler.scheduleAtFixedRate(task,startTime,1000);

        sleep(30000);
    }

    //spring普通线程池（简化）
    @Test
    public void testThreadPoolTaskExecutorSimple(){
        for (int i =0;i<10;i++){
            alphaService.execute1();
        }
        sleep(10000);
    }

    //spring定时任务线程池（简化）
    @Test
    public void testThreadPoolTaskSchedulerSimple(){
        sleep(30000);
    }
}
