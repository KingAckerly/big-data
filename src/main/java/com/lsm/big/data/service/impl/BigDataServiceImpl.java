package com.lsm.big.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsm.big.data.entity.BigDataEntity;
import com.lsm.big.data.mapper.BigDataMapper;
import com.lsm.big.data.service.IBigDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class BigDataServiceImpl extends ServiceImpl<BigDataMapper, BigDataEntity> implements IBigDataService {

    private static Logger logger = LoggerFactory.getLogger(BigDataServiceImpl.class);

    @Resource
    private BigDataMapper bigDataMapper;

    /**
     * 线程数是核数*2+1,此mac是8核,8*2+1=17,这里就设置为15
     * 15个线程,每1w条数据提交一个任务到线程池
     * 550w条数据,耗时31983ms
     *
     * @throws Exception
     */
    @Transactional
    @Override
    public void read() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/xiaosi/develop/big-data.txt"));
        String lineTxt;
        String[] str;
        List<BigDataEntity> bigDataEntities = new ArrayList<>();
        BigDataEntity bigDataEntity;
        ExecutorService threadPool = new ThreadPoolExecutor(15, 15, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new ThreadPoolExecutor.AbortPolicy());
        int num = 0;
        Instant start = Instant.now();
        while ((lineTxt = bufferedReader.readLine()) != null) {
            str = lineTxt.split(",");
            bigDataEntity = new BigDataEntity();
            bigDataEntity.setUuid(str[0]);
            bigDataEntity.setMobile(str[1]);
            bigDataEntities.add(bigDataEntity);
            num++;
            if (num == 10000) {
                threadPool.submit(new Inner(bigDataEntities));
                num = 0;
                bigDataEntities = new ArrayList<>();
            }
        }
        if (num < 10000 && num > 0) {
            threadPool.submit(new Inner(bigDataEntities));
        }
        bufferedReader.close();
        threadPool.shutdown();
        while (true) {
            if (threadPool.isTerminated()) {
                long finish = Duration.between(start, Instant.now()).toMillis();
                logger.info("Time-Consuming : {} ms", finish);
                break;
            }
        }
    }

    @Override
    public void delete() {
        bigDataMapper.delete(null);
    }

    class Inner implements Runnable {
        private List<BigDataEntity> bigDataEntities;

        public Inner(List<BigDataEntity> bigDataEntities) {
            this.bigDataEntities = bigDataEntities;
        }

        public void run() {
            bigDataMapper.saveBatch(bigDataEntities);
        }
    }
}
