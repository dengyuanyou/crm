package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.query.ChartQueryObject;

import cn.wolfcode.crm.service.IChartService;
import cn.wolfcode.crm.service.IEmployeeService;
import cn.wolfcode.crm.util.JsonUtil;
import cn.wolfcode.crm.util.MessageUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.*;

//潜在客户/流失/客户池...
@Controller
@RequestMapping("/chart")
public class ChartController {

    @Autowired
    private IChartService chartService;
    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping("/list")
    @RequiresPermissions(value = {"潜在客户列表","chart:list"}, logical = Logical.OR)
    public String list(Model model,@ModelAttribute("qo") ChartQueryObject qo){
        model.addAttribute("charts",chartService.queryCustomerChart(qo));
        return "chart/list";
    }

    /**
     * 查看柱状图
     * @param model
     * @param qo
     * @return
     */
    @RequestMapping("/chartByBar")
    @RequiresPermissions(value = {"查看柱状图","chart:chartByBar"}, logical = Logical.OR)
    public String chartByBar(Model model,@ModelAttribute("qo") ChartQueryObject qo){
        //准备报表中需要的数据
        String groupTypeName = MessageUtil.changMsg(qo);
        model.addAttribute("groupTypeName",groupTypeName);

        List<Map<String, Object>> maps = chartService.queryCustomerChart(qo);

        //用来存放所有的分组类型
        List<Object> groupByTypes = new ArrayList<>();
        //用来存放所有的每组的人数
        List<Object> numbers = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            groupByTypes.add(map.get("groupByType"));
            numbers.add(map.get("number"));
        }
        //将集合数据转换成json字符串
        model.addAttribute("groupByTypes", JsonUtil.toJsonString(groupByTypes));
        model.addAttribute("numbers", JsonUtil.toJsonString(numbers));
        return "chart/chartByBar";
    }

    /**
     * 查看饼状图
     * @param model
     * @param qo
     * @return
     */
    @RequestMapping("/chartByPie")
    @RequiresPermissions(value = {"查看饼状图","chart:chartByPie"}, logical = Logical.OR)
    public String chartByPie(Model model,@ModelAttribute("qo") ChartQueryObject qo) {
        //数据往饼状图中填充数据
        String groupTypeName = MessageUtil.changMsg(qo);
        model.addAttribute("groupTypeName",groupTypeName);

        List list = employeeService.selectGroupTypeNames(qo);

        //用来存放分组的类型
        List legendDate = new ArrayList<>();
        //用来存放每一个类型的数量
        List seriesDate = new ArrayList<>();

        Map data;

        //遍历集合中的每一个Map集合,则seriesDate集合存储的是一个个Map集合,且每一个Map集合的key为销售员工名字,value为销售的客户数量
        for (Iterator iterator = list.iterator(); iterator.hasNext(); seriesDate.add(data))
        {
            Map map = (Map)iterator.next();
            legendDate.add(map.get("name"));
            data = new HashMap();
            data.put("name", map.get("name"));
            data.put("value", map.get("number"));
        }

        model.addAttribute("legendDate",JsonUtil.toJsonString(legendDate));
        model.addAttribute("seriesDate",JsonUtil.toJsonString(seriesDate));
        return "chart/chartByPie";
    }
}
