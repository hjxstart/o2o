package com.imooc.o2o.service;

import java.io.InputStream;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.exceptions.ShopOperationException;

public interface ShopService {
	
	// 需要返回 count 和 shoplist 故选择 ShopExecution类型
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
	
	// 通过店铺ID获取店铺信息
	Shop getByShopId(long shopId);
	
	// 更新店铺信息，包括对图片的处理，ShopExecution 封装了状态信息和店铺信息，对店铺的操作都会返回这个类
	//ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;
	// 优化封装 图片和图片名
	ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;
	
	// 由于InputStream无法获取文件名字，故需要传入文件名字
	//ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;
	ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;
}
