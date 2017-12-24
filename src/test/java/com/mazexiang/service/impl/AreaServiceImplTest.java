package com.mazexiang.service.impl;

import com.mazexiang.BaseTest;
import com.mazexiang.entity.Area;
import com.mazexiang.service.AreaService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class AreaServiceImplTest extends BaseTest {

    @Autowired
    private AreaService areaService;
    @Test
    public void testGetAreaList() throws Exception {
        List<Area> areaList = areaService.getAreaList();
        System.out.println(areaList.size());
    }

}