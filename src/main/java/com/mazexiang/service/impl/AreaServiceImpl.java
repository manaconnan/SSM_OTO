package com.mazexiang.service.impl;

import com.mazexiang.dao.AreaDao;
import com.mazexiang.entity.Area;
import com.mazexiang.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    public List<Area> getAreaList() {
        return areaDao.queryArea();
    }
}
