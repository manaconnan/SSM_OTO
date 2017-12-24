package com.mazexiang.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mazexiang.cache.JedisUtil;
import com.mazexiang.dao.AreaDao;
import com.mazexiang.entity.Area;
import com.mazexiang.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class AreaServiceImpl implements AreaService {
    Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private JedisUtil.Keys jedisKey;
    @Autowired
    private JedisUtil.Strings jedisStrings;

    private final static String AREALISTKEY="arealist";

    @Transactional
    public List<Area> getAreaList() {
        String key =AREALISTKEY;
        List<Area> areaList = null;
        ObjectMapper mapper = new ObjectMapper();
        if(!jedisKey.exists(key)){
            areaList = areaDao.queryArea();
            String jsonString;
            try {
                jsonString = mapper.writeValueAsString(areaList);
            }catch (JsonProcessingException e){
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new RuntimeException(e.getMessage());// 由于是事务，所有需要抛出RuntimeException ，让事务回滚
            }
            jedisStrings.set(key,jsonString);
        }else {
            String jsonString = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,Area.class);
            try {
                areaList = mapper.readValue(jsonString,javaType);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new RuntimeException(e.getMessage());// 由于是事务，所有需要跑出RuntimeException ，让事务回滚

            }
        }

        return areaList;
    }
}
