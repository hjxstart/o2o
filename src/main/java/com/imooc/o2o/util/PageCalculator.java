package com.imooc.o2o.util;

/**
 * 解决后端的行数和前端的页数的转换
 * @author jxh
 *
 */
public class PageCalculator {
	public static int calculateRowIndex(int pageIndex, int pageSize) {
		// 如果页面没页5条，第一页1就从0条数据开始选，如果页面为2就从5条开始选择。
		return (pageIndex > 0 ? (pageIndex -1) * pageSize : 0);
	}
}
