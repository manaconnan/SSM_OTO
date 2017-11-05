package com.mazexiang.web.superadmin;

import com.mazexiang.entity.Area;
import com.mazexiang.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/superadmin")
public class AreaController {

    Logger logger = LoggerFactory.getLogger(AreaController.class);
    @Autowired
    private AreaService areaService;
    @RequestMapping(value = "/listarea" ,method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> listArea(){
        logger.info("===== start ===");
        long startTime = System.currentTimeMillis();
        Map<String,Object> modelMap = new HashMap<>();
        List<Area> list = areaService.getAreaList();
        try {
            modelMap.put("rows",list);
            modelMap.put("total",list.size());

        }catch (Exception e){
            e.printStackTrace();
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
        }
        long endTime = System.currentTimeMillis();
        logger.debug("===cost time : [{}ms]",(endTime-startTime));
        logger.info("=====end====");

        return modelMap;
    }

}
