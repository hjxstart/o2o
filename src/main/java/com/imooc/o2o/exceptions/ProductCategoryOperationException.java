package com.imooc.o2o.exceptions;

public class ProductCategoryOperationException extends RuntimeException {
	static final long serialVersionUID = 1182563719599527969L;

	public ProductCategoryOperationException(String msg) {
		super(msg);
	}
}
