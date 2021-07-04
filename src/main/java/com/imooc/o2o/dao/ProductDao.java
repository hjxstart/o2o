package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Product;

public interface ProductDao {
	// 添加商品
	int insertProduct(Product product);
	// 根据 product id 查询商品的信息
	Product queryProductById(long productId);
	// 根据 product 修改商品的信息
	int updateProduct(Product product);
}
