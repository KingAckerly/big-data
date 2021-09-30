package com.lsm.big.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("big_data")
public class BigDataEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String uuid;
    private String mobile;

    public Integer getId() {
        return id;
    }

    public BigDataEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public BigDataEntity setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public BigDataEntity setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    @Override
    public String toString() {
        return "BigDataEntity{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
