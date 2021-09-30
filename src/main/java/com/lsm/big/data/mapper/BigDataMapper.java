package com.lsm.big.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsm.big.data.entity.BigDataEntity;

import java.util.List;

public interface BigDataMapper extends BaseMapper<BigDataEntity> {
    int saveBatch(List<BigDataEntity> bigDataEntities);
}
