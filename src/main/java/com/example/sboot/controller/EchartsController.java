package com.example.sboot.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.example.sboot.common.Result;
import com.example.sboot.entity.Assets;
import com.example.sboot.entity.Files;
import com.example.sboot.entity.User;
import com.example.sboot.service.IAssetsService;
import com.example.sboot.service.IFileService;
import com.example.sboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页所需的相关数据的后端编写
 *
 * @author LeslieLu
 */
@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IFileService fileService;

    @Resource
    private IAssetsService assetsService;

    //柱状图
    @GetMapping("/example")
    public Result get(){
        Map<String,Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y",CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));

        return Result.success(map);
    }

    //柱状图 各季度注册用户人数
    @GetMapping("/members")
    public Result members(){

        List<User> list = userService.list();
        int q1 = 0; //第一季度
        int q2 = 0; //第二季度
        int q3 = 0; //第三季度
        int q4 = 0; //第四季度
        for(User user : list){
            Date createTime = user.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter){
                case Q1: q1 += 1;break;
                case Q2: q2 += 1;break;
                case Q3: q3 += 1;break;
                case Q4: q4 += 1;break;
                default:break;
            }
        }

        return Result.success(CollUtil.newArrayList(q1,q2,q3,q4));
    }

//    柱状图 各季度事件总数
    @GetMapping("/event")
    public Result event(){
        List<Assets> list1 = assetsService.list();
        int q1 = 0; //第一季度
        int q2 = 0; //第二季度
        int q3 = 0; //第三季度
        int q4 = 0; //第四季度
        for (Assets assets : list1) {
            Date createTime = assets.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter){
                case Q1: q1 += 1;break;
                case Q2: q2 += 1;break;
                case Q3: q3 += 1;break;
                case Q4: q4 += 1;break;
                default:break;
            }
        }

        return Result.success(CollUtil.newArrayList(q1,q2,q3,q4));
    }


    //饼状图 地区人数
    @GetMapping("/address")
    public Result address(){

        List<User> list = userService.list();
        int q1 = 0; //广东的人数
        int q2 = 0; //上海的人数
        int q3 = 0; //北京的人数
        int q4 = 0; //香港的人数
        int q5 = 0; //澳门的人数
        int q6 = 0; //广西的人数


        for(User user : list){
            String userAddress = user.getAddress();
//            System.out.println("***********************" + userAddress);
            if(userAddress.matches("广东(.*)"))   q1 += 1;    //正则表达式匹配广东xx
            else if (userAddress.matches("上海(.*)")) q2 += 1;    //正则表达式匹配上海xx
            else if(userAddress.matches("北京(.*)"))    q3 += 1;  //正则表达式匹配北京xx
            else if(userAddress.matches("香港(.*)"))    q4 += 1;  //正则表达式匹配香港xx
            else if(userAddress.matches("澳门(.*)"))    q5 += 1;  //正则表达式匹配澳门xx
            else if(userAddress.matches("广西(.*)"))    q6 += 1;  //正则表达式匹配广西xx

        }

        return Result.success(CollUtil.newArrayList(q1,q2,q3,q4,q5,q6));
    }

    //查询并显示用户总数
    @GetMapping("/userOnline")
    public Result userOnline(){

        List<User> list = userService.list();
        int q1 = 0;
        for(User user : list){
//            Integer id = user.getId();
//            if(id != null) q1 += 1;
            Integer deleted = user.getDeleted();
            if(deleted == 0)  q1 += 1;
        }

        return Result.success(q1);
    }

    //查询并显示文件总数
    @GetMapping("/fileOnline")
    public Result fileOnline(){

        List<Files> list = fileService.list();
        int q1 = 0;
        for(Files files : list){
//            Integer id = files.getId();
            Boolean isDelete = files.getIsDelete();
            if(!isDelete) q1 += 1;  //查询文件中isDelete为0的数量（即未删除）
        }

        return Result.success(q1);
    }

    //查询并显示购置管理待处理事件总数
    @GetMapping("/purchaseOnline")
    public Result purchaseOnline(){

        List<Assets> list = assetsService.list();
        int q1 = 0;
        for (Assets assets : list) {
            Integer purchase = assets.getPurchase();
            if(purchase.equals(1)) q1 += 1;     //查询状态为待处理的总数
        }

        return Result.success(q1);
    }

    //查询并显示验收管理待处理事件总数
    @GetMapping("/acceptOnline")
    public Result acceptOnline(){

        List<Assets> list = assetsService.list();
        int q1 = 0;
        for (Assets assets : list) {
            Integer accept = assets.getAccept();
            if(accept.equals(1)) q1 += 1;     //查询状态为待处理的总数
        }

        return Result.success(q1);
    }

    //查询并显示调拨管理待处理事件总数
    @GetMapping("/allocationOnline")
    public Result allocationOnline(){

        List<Assets> list = assetsService.list();
        int q1 = 0;
        for (Assets assets : list) {
            Integer allocation = assets.getAllocation();
            if(allocation.equals(1)) q1 += 1;     //查询状态为待处理的总数
        }

        return Result.success(q1);
    }

    //查询并显示处置管理待处理事件总数
    @GetMapping("/managementOnline")
    public Result managementOnline(){

        List<Assets> list = assetsService.list();
        int q1 = 0;
        for (Assets assets : list) {
            Integer management = assets.getManagement();
            if(management.equals(1)) q1 += 1;     //查询状态为待处理的总数
        }

        return Result.success(q1);
    }

    //查询并显示已完成事件总数
    @GetMapping("/finalOnline")
    public Result finalOnline(){

        List<Assets> list = assetsService.list();
        int q1 = 0;
        for (Assets assets : list) {
            Integer management = assets.getManagement();
            if(management.equals(2)) q1 += 1;     //查询状态为待处理的总数
        }

        return Result.success(q1);
    }


}
