package com.xmu.footprint.feign;

import com.xmu.footprint.domain.GoodsPo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value="Goods")
@Service
@RequestMapping("/goodsService")
/**
 * @author
 */
public interface GoodsApi {
    /**
     * 根据商品id查找商品
     * @param id
     * @return
     */
    @GetMapping("/goodsPo/{id}")
    GoodsPo userGetGoodsById(@PathVariable(value = "id") Integer id);
}
