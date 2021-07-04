package com.imooc.o2o.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value="/getshopmanagementinfo", method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 尝试从请求中获取 shopId
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		// 尝试从 session 中获取 shopId
		if(shopId <= 0) {
			Object currentShopObj = request.getSession().getAttribute("currentShop");
			// 如果都没有 shopId 就重定向回之前的页面
			if(currentShopObj == null) {
				modelMap.put("redirect", true);
				modelMap.put("url", "shoplist");
			} else {
				Shop currentShop = (Shop) currentShopObj;
				modelMap.put("redirect", false);
				modelMap.put("shopId", currentShop.getShopId());
			}
		} else {
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			modelMap.put("redirect", false);
		}
		return modelMap;
	}
	
	@RequestMapping(value="/getshoplist", method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopList(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		PersonInfo user = new PersonInfo();
		user.setUserId(1L);
		request.getSession().setAttribute("user", user);
		user = (PersonInfo) request.getSession().getAttribute("user");
		user.setUserId(1L);
		user.setName("test");
		long employeeId = user.getUserId();
		try {
			Shop shopCondition = new Shop();
			shopCondition.setOwner(user);
			ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
			modelMap.put("shopList", se.getShopList());
			modelMap.put("user", user);
			modelMap.put("success", true);
		} catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
		
	}
	
	@RequestMapping(value="/modifyshop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyShop(HttpServletRequest request) throws ShopOperationException, IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 1.接受并转化相应的参数，思路：获取前端传过来的店铺信息，将数据转换为实体类，同时获取前端传过来的文件流，将图片存放在shopImg里面去。
		// 使用工具类，将相应的参数转化为字符串
		
		// 验证码
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		// 使用 jackson-databind库 将字符串转化为实体类，JSON to POJO and back
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		// 获取前端文件流，保存上传图片的文件流
		CommonsMultipartFile shopImg = null; // 保存上传图片的文件流
		// 从 request session 的上下文获取相关文件上传的内容
		CommonsMultipartResolver commmonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if(commmonsMultipartResolver.isMultipart(request)) { // 判断是否有上传的文件流
			// 如果有上传的文件流，就进行转换 MultipartHttpServletRequest 类型的对象，该对象可以被spring处理文件流对象
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			// 从该上传对象中提取出图片的文件流，（shopImg 是与前端协商的名称）
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		}
		
		// 2.修改店铺
		if(shop != null && shop.getShopId() != null) {
			PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
			shop.setOwner(owner);
			ShopExecution se;
			try {
				if(shopImg == null) {
					se = shopService.modifyShop(shop,null);
				}else {
					ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
					se = shopService.modifyShop(shop, imageHolder);
				}
				
				if(se.getState() == ShopStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			}catch(ShopOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}catch(IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;
			 
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}
		// 3.返回结果
	}
	
	
	@RequestMapping(value="/getshopbyid",method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopById(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String,Object>();
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId > -1) {
			try {
				Shop shop = shopService.getByShopId(shopId);
				List<Area> areaList = areaService.getAreaList();
				modelMap.put("shop", shop);
				modelMap.put("areaList", areaList);
				modelMap.put("success", true);
			} catch(Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
			
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}
	
	@RequestMapping(value="/getshopinitinfo", method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getshopinitinfo() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory()); // 选出不为空的类别
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		} catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	
	@RequestMapping(value="/registershop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) throws ShopOperationException, IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 1.接受并转化相应的参数，思路：获取前端传过来的店铺信息，将数据转换为实体类，同时获取前端传过来的文件流，将图片存放在shopImg里面去。
		// 使用工具类，将相应的参数转化为字符串
		
		// 验证码
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		// 使用 jackson-databind库 将字符串转化为实体类，JSON to POJO and back
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		// 获取前端文件流，保存上传图片的文件流
		CommonsMultipartFile shopImg = null; // 保存上传图片的文件流
		// 从 request session 的上下文获取相关文件上传的内容
		CommonsMultipartResolver commmonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if(commmonsMultipartResolver.isMultipart(request)) { // 判断是否有上传的文件流
			// 如果有上传的文件流，就进行转换 MultipartHttpServletRequest 类型的对象，该对象可以被spring处理文件流对象
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			// 从该上传对象中提取出图片的文件流，（shopImg 是与前端协商的名称）
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		} else { // 如果没有图片就报错，因为图片是必须的
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		
		// 2.注册店铺
		if(shop != null && shopImg != null) {
			PersonInfo owner = new PersonInfo();
			owner.setUserId(1L);
			shop.setOwner(owner);
			ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
			ShopExecution se = shopService.addShop(shop, imageHolder);
			if(se.getState() == ShopStateEnum.CHECK.getState()) {
				modelMap.put("success", true);
				List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
				if(shopList == null || shopList.size() == 0) {
					shopList = new ArrayList<Shop>();
					shopList.add(se.getShop());
					request.getSession().setAttribute("shopList", shopList);
				}else {
					shopList.add(se.getShop());
					request.getSession().setAttribute("shopList", shopList);
				}
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", se.getStateInfo());
			}
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}
		// 3.返回结果
	}
	
}
