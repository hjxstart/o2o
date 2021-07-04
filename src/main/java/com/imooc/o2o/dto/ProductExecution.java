package com.imooc.o2o.dto;

import java.util.List;

import com.imooc.o2o.entity.Product;
import com.imooc.o2o.enums.ProductStateEnum;

public class ProductExecution {
	private int state; // 结果状态
	private String stateInfo; // 状态标识
	private int count; // 商品数量；
	private Product product; // 操作的product(增删改商品的时候使用)
	private List<Product> productList; // 获取product列表(查询商品列表的时候用)
	public ProductExecution() {
		
	}
	// 失败调用的构造器
	public ProductExecution(ProductStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	// 成功时候调用的构造器, 返回单个 product
	public ProductExecution(ProductStateEnum stateEnum, Product product) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.product = product;
	}
	
	// 成功时候调用的构造器, 返回多个 product
	public ProductExecution(ProductStateEnum stateEnum, List<Product> productList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.productList = productList;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	
	
}
