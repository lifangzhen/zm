package com.lun.mlm.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lun.mlm.MlmException;
import com.lun.mlm.dao.SailDao;
import com.lun.mlm.model.Category;
import com.lun.mlm.model.Dish;
import com.lun.mlm.model.PageParam;
import com.lun.mlm.utils.ConstantsRefresh;
import com.lun.mlm.utils.StringUtil;

@Controller
public class SailController extends BaseController {
	@Autowired SailDao sailDao;

	@RequestMapping(value="/mlm/storeUi")
	public ModelAndView categoryUi() throws IOException {
		String name = this.getParaMeter("name");
		PageParam pp = this.getPageParam();
		List<Category> list = sailDao.listCategory(name, pp.getStart(), pp.getEnd());
		Integer count = sailDao.countCategory(name);
		ModelAndView mav = new ModelAndView("/mlm/sail/category/list");
		mav.addObject("name", name);
		mav.addObject("list", list);
		mav.addObject("numPerPage",pp.getNumPerPage()); 
		mav.addObject("totalCount",count);
		mav.addObject("currentPage",pp.getPageNum());
		mav.addObject("pageCount",PageParam.pageCount(count, pp.getNumPerPage()));
		return mav;
	}
	
	@RequestMapping(value="/mlm/addCategoryUi")
	public ModelAndView addCategoryUi() {
		String id = this.getParaMeter("id");
		Category cate = new Category();
		if(StringUtil.isNotBlank(id)) cate = sailDao.getCategory(id);
		ModelAndView mav = new ModelAndView("/mlm/sail/category/add");
		mav.addObject("id", id);
		mav.addObject("cate", cate);
		return mav;
	}
	
	@RequestMapping(value="/mlm/addCategory/json")
	@ResponseBody
	public Map<String, Object>  addCategory() {
		String name = this.getParaMeter("name");
		String numberStr = this.getParaMeter("numberStr");
		String id = this.getParaMeter("id");
		Integer number = 0;
		if(StringUtil.isNotBlank(numberStr)) number = Integer.parseInt(numberStr);
		Category category = new Category();
		category.setName(name);
		category.setNumber(number);
		if(StringUtil.isBlank(id)){
			category.setId(StringUtil.getUUID());
			sailDao.addCategory(category);
		}else{
			Category cate = sailDao.getCategory(id);
			if(cate==null) throw new MlmException("300", "更新失败");
			category.setId(id);
			sailDao.updateCategory(category);
		}
		return success(true, ConstantsRefresh.CATEGORY_LIST);
	}
	
	@RequestMapping(value="/mlm/deleteCategory/json")
	@ResponseBody
	public Map<String, Object>  deleteCategory() {
		String id = this.getParaMeter("id");
		Integer count = sailDao.countDishByCategoryId(id);
		if(count>0) throw new MlmException("300", "有"+count+"个菜品正在使用该类别");
		sailDao.deleteCategory(id);
		return success();
	}
	
	@RequestMapping(value="/mlm/tableUi")
	public ModelAndView dishUi() throws IOException {
		String name = this.getParaMeter("name");
		String categoryId = this.getParaMeter("categoryId");
		PageParam pp = this.getPageParam();
		List<Dish> list = sailDao.listDish(name, categoryId, pp.getStart(), pp.getEnd());
		List<Category> cateList = sailDao.listCategory(null, null, null);
		Integer count  = sailDao.countDish(name, categoryId);
		ModelAndView mav = new ModelAndView("/mlm/sail/dish/list");
		mav.addObject("name", name);
		mav.addObject("categoryId", categoryId);
		mav.addObject("list", list);
		mav.addObject("cateList", cateList);
		mav.addObject("numPerPage",pp.getNumPerPage()); 
		mav.addObject("totalCount",count);
		mav.addObject("currentPage",pp.getPageNum());
		mav.addObject("pageCount",PageParam.pageCount(count, pp.getNumPerPage()));
		return mav;
	}
	
	@RequestMapping(value="/mlm/addDishUi")
	public ModelAndView addDishUi() {
		String id = this.getParaMeter("id");
		Dish dish = new Dish();
		if(StringUtil.isNotBlank(id)) dish = sailDao.getDish(id);
		List<Category> cateList = sailDao.listCategory(null, null, null);
		ModelAndView mav = new ModelAndView("/mlm/sail/dish/add");
		mav.addObject("id", id);
		mav.addObject("dish", dish);
		mav.addObject("cateList", cateList);
		return mav;
	}
	
	@RequestMapping(value="/mlm/modifyStockUi")
	public ModelAndView modifyStockUi() {
		String categoryId = this.getParaMeter("categoryId");
		List<Dish> list = sailDao.listDishByCategoryId(categoryId, null);
		ModelAndView mav = new ModelAndView("/mlm/sail/dish/modify");
		mav.addObject("list", list);
		return mav;
	}
	
	@RequestMapping(value="/mlm/modifyStock/json")
	@ResponseBody
	public Map<String, Object>  modifyStock() {
		Enumeration<String> enu = this.request.getParameterNames();
		while(enu.hasMoreElements()){
			String id = enu.nextElement();
			String stockStr = request.getParameter(id);
			Integer stock = 0;
			if(StringUtil.isNotBlank(stockStr)){
				stock = Integer.parseInt(stockStr);
			}
			Dish dish = new Dish();
			dish.setId(id);
			dish.setStock(stock);
			sailDao.updateDish(dish);
		}
		return success(true, ConstantsRefresh.DISH_LIST);
	}
	
	@RequestMapping(value="/mlm/lockdish/json")
	@ResponseBody
	public Map<String, Object> lockdish(){
		String id = this.getParaMeter("id");
		Dish dish = sailDao.getDish(id);
		if(dish==null) throw new MlmException("300", "菜品不存在");
		dish.setStatus(2/dish.getStatus());
		sailDao.updateDish(dish);
		return success();
	}
	
	@RequestMapping(value="/mlm/deleteDish/json")
	@ResponseBody
	public Map<String, Object>  deleteDish() {
		String id = this.getParaMeter("id");
		sailDao.deleteDish(id);
		return success();
	}
}
