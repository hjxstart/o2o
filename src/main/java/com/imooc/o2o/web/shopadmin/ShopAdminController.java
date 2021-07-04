package com.imooc.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/shopadmin", method= {RequestMethod.GET})
public class ShopAdminController {

	@RequestMapping(value = "/shopoperation")
	public String shopOperation() {
		return "shopadmin/shopoperation";
	}
	@RequestMapping(value = "/shoplist")
	public String shopList() {
		return "shopadmin/shoplist";
	}
	@RequestMapping(value = "/shopmanagement")
	public String shopManage() {
		return "shopadmin/shopmanagement";
	}
	
	@RequestMapping(value = "/productcategorymanagement", method=RequestMethod.GET)
	private String productCategoryManage() {
		return "shopadmin/productcategorymanagement";
	}
	
	@RequestMapping(value = "/productoperation")
	public String productOperation() {
		return "shopadmin/productoperation";
	}
}
