package com.imooc.o2o.dao;

import java.util.List;

import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductImg;

public interface ProductImgDao {
	// 批量添加商品详情图片
	int batchInsertProductImg(List<ProductImg> productImgList);
	// 根据 product id 删除商品的图片信息
	int deleteProductImgByProductId(long productId);
	//  根据productId 获取原来的图片
	List<ProductImg> queryProductImgList(Long productId);
	
}
