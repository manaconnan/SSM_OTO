package com.mazexiang.dao;

import com.mazexiang.entity.Area;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AreaDao {
    /**
     *  列出区域列表
     * @return
     */
    List<Area> queryArea();
}
