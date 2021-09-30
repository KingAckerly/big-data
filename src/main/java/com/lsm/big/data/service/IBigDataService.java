package com.lsm.big.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsm.big.data.entity.BigDataEntity;

public interface IBigDataService extends IService<BigDataEntity> {
    void read() throws Exception;

    void delete();
}
