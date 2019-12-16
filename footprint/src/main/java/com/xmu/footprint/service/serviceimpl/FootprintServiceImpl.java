package com.xmu.footprint.service.serviceimpl;

import com.xmu.footprint.domain.FootprintItem;
import com.xmu.footprint.domain.FootprintItemPo;
import com.xmu.footprint.mapper.FootprintMapper;
import com.xmu.footprint.service.FootprintService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
/**
 * @author
 */
public class FootprintServiceImpl  implements FootprintService {
    @Resource
    private FootprintMapper footprintMapper;

    @Override
    public List<FootprintItem> listFootprintByCondition(String userName, String goodsName) {return footprintMapper.listFootprintByCondition(userName,goodsName);}

    @Override
    public List<FootprintItem> listFootprintByUserId(Integer userId) {return footprintMapper.listFootprintByUserId(userId);}

    @Override
    public int deleteFootprintById(Integer id) {return footprintMapper.deleteFootprintById(id);}

    @Override
    public List<FootprintItemPo> findFootprintItem(Integer userId,Integer goodsId) {return footprintMapper.findFootprintItem(userId,goodsId);}

    @Override
    public FootprintItemPo addFootprint(FootprintItemPo footprintItemPo) {
        footprintMapper.addFootprint(footprintItemPo);
        return footprintMapper.getFootprintItemPoById(footprintItemPo.getId());
    }

    @Override
    public FootprintItemPo updateFootprint(FootprintItemPo footprintItemPo) {
        footprintMapper.updateFootprint(footprintItemPo);
        return footprintMapper.getFootprintItemPoById(footprintItemPo.getId());
    }
}
