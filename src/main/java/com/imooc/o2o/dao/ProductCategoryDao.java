package com.imooc.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
	// 通过shop id 查询店铺商品类别
	List<ProductCategory> queryProductCategoryList(long shopId);
	
	// 批量新增商品类标
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);
	
	// 删除指定商品类别, 如果有两个参数，mybatis认不出来，必须使用@param注解来标识
	// 加多参数 shopId 是为了防止不是本店铺的对商品列表的操作 
	int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);
}
