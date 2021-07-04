package com.imooc.o2o.service;

import java.util.List;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.exceptions.ProductOperationException;

public interface ProductService {
	// 添加商品信息以及图片处理。思路：1.处理缩略图;2.处理商品详情图片;3.添加商品信息
	//ProductExecution addProduct(Product product, InputStream thumbnail, String thumbnailName, List<InputStream> productImgList, List<String> productImgNameList) throws ProductOperationException;
	// 封装 图片和图片名
	ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException;
	// 通过商品 Id 查询唯一的商品信息(商品编辑)
	Product getProductById(long productId);
	// 修改商品信息以及图片处理(商品编辑)
	ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException;
}
