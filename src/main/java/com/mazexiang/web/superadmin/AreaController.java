package com.mazexiang.web.superadmin;

import com.mazexiang.entity.Area;
import com.mazexiang.service.AreaService;
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
    @Autowired
    private AreaService areaService;
    @RequestMapping(value = "/listarea" ,method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> listArea(){
        Map<String,Object> map = new HashMap<String, Object>();
        List<Area> list = areaService.getAreaList();
        try {
            map.put("rows",list);
            map.put("total",list.size());

        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
            map.put("errMsg",e.toString());
        }
        return map;
    }

}
