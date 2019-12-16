package com.xmu.footprint.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xmu.footprint.domain.FootprintItem;
import com.xmu.footprint.domain.FootprintItemPo;
import com.xmu.footprint.feign.GoodsApi;
import com.xmu.footprint.mapper.FootprintMapper;
import com.xmu.footprint.service.FootprintService;
import com.xmu.footprint.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/footprintService")
/**
 * @author
 */
public class FootprintController {
    @Autowired
    private FootprintService footprintService;

    @Autowired
    GoodsApi goodsApi;

    @Resource
    FootprintMapper footprintMapper;

    List<FootprintItem> footprintItemLists;
    FootprintItem footprintItem;

    /**
     * 管理员获取足迹列表/list
     *
     * @param page 分页页数
     * @param limit 分页大小
     * @return List<FootprintItem>，即获取的足迹列表
     */
    @GetMapping("/admin/footprints")
    public Object listFootprintByCondition(String userName, String goodsName,
                                                        @RequestParam(defaultValue = "1") Integer page,
                                                        @RequestParam(defaultValue = "10") Integer limit) {
        PageHelper.startPage(page,limit);
        List<FootprintItem> footprintItemList=footprintService.listFootprintByCondition(userName,goodsName);
        PageInfo<FootprintItem> footprintItemPageInfo=new PageInfo<>(footprintItemList);
        List<FootprintItem> pageList=footprintItemPageInfo.getList();

        footprintItemLists=new ArrayList<>();
        for(int i=0;i<pageList.size();i++) {
            footprintItem=pageList.get(i);
            footprintItem.setGoodsPo(goodsApi.userGetGoodsById(footprintItem.getGoodsId()));
            footprintItemLists.add(footprintItem);
        }
        Object retObj=ResponseUtil.ok(footprintItemLists);
        return retObj;
    }

    /**
     * 用户获取足迹列表/list
     *
     * @param page 分页页数
     * @param limit 分页大小
     * @return List<FootprintItem>，即用户的足迹列表
     */
    @GetMapping("/footprints")
    public Object listFootprintByUserId(Integer userId,
                                                     @RequestParam(defaultValue = "1") Integer page,
                                                     @RequestParam(defaultValue = "0") Integer limit) {
        PageHelper.startPage(page,limit);
        List<FootprintItem> footprintItemList=footprintService.listFootprintByUserId(userId);
        PageInfo<FootprintItem> footprintItemPageInfo=new PageInfo<>(footprintItemList);
        List<FootprintItem> pageList=footprintItemPageInfo.getList();

        footprintItemLists=new ArrayList<>();
        for(int i=0;i<pageList.size();i++) {
            footprintItem=pageList.get(i);
            footprintItem.setGoodsPo(goodsApi.userGetGoodsById(footprintItem.getGoodsId()));
            footprintItemLists.add(footprintItem);
        }
        Object retObj=ResponseUtil.ok(footprintItemLists);
        return retObj;
    }

    /**
     * 删除用户足迹/delete
     *
     * @param id 足迹ID
     * @return retObi，即删除成功或失败
     */
    @DeleteMapping("/footprints/{id}")
    public Object deleteFootprintsById(@PathVariable(value ="id")Integer id) {
        Object  retObj;
        if(footprintService.deleteFootprintById(id)==1) {
            retObj=ResponseUtil.ok();
        }
        else {
            retObj=ResponseUtil.notexist();
        }
        return retObj;
    }

    /**
     * 内部接口：添加足迹信息/add
     *
     * @param footprintItemPo
     * @return FootprintItemPo，即添加的足迹信息
     */
    @PostMapping("/footprints")
    public Object addFootprint(@RequestBody FootprintItemPo footprintItemPo) {
        Object retObj;
        int size=footprintService.findFootprintItem(footprintItemPo.getUserId(),footprintItemPo.getGoodsId()).size();
        if(size>0) {
            retObj=ResponseUtil.ok(footprintService.updateFootprint(footprintItemPo));
            return retObj;
        }//用户浏览过商品，更改上次浏览时间
        //用户首次浏览商品，添加足迹信息
        if((footprintMapper.addFootprint(footprintItemPo))==1){
            retObj=ResponseUtil.ok(footprintService.addFootprint(footprintItemPo));
        }
        else {
            retObj=ResponseUtil.badArgumentValue();
        }
        return retObj;
    }
}
