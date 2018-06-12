package com.lun.mlm.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.lun.mlm.dao.SailDao;
import com.lun.mlm.model.Category;
import com.lun.mlm.model.Dish;

@Repository("sailDao")
public class SailDaoImpl extends SqlMapClientDaoSupport implements SailDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> listCategory(String name, Integer start, Integer end) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("start", start);
		map.put("end", end);
		return this.getSqlMapClientTemplate().queryForList("Dish_SqlMap.listCategory", map);
	}
	
	@Override
	public Category getCategory(String id) {
		return (Category) this.getSqlMapClientTemplate().queryForObject("Dish_SqlMap.getCategory", id);
	}
	
	@Override
	public Integer countCategory(String name) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject("Dish_SqlMap.countCategory", name);
	}

	@Override
	public void addCategory(Category category) {
		this.getSqlMapClientTemplate().insert("Dish_SqlMap.addCategory", category);
	}

	@Override
	public void updateCategory(Category category) {
		this.getSqlMapClientTemplate().update("Dish_SqlMap.updateCategory", category);
	}

	@Override
	public void deleteCategory(String id) {
		this.getSqlMapClientTemplate().delete("Dish_SqlMap.deleteCategory", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dish> listDish(String name, String categoryId, Integer start, Integer end) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("categoryId", categoryId);
		map.put("start", start);
		map.put("end", end);
		return this.getSqlMapClientTemplate().queryForList("Dish_SqlMap.listDish", map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Dish> listDishByCategoryId(String categoryId, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryId", categoryId);
		map.put("status", status);
		return this.getSqlMapClientTemplate().queryForList("Dish_SqlMap.listDishByCategoryId", map);
	}
	
	@Override
	public Dish getDish(String id) {
		return (Dish) this.getSqlMapClientTemplate().queryForObject("Dish_SqlMap.getDish", id);
	}
	
	@Override
	public Integer countDish(String name, String categoryId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryId", categoryId);
		map.put("name", name);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("Dish_SqlMap.countDish", map);
	}
	
	@Override
	public Integer countDishByCategoryId(String categoryId) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject("Dish_SqlMap.countDishByCategoryId", categoryId);
	}

	@Override
	public void addDish(Dish dish) {
		this.getSqlMapClientTemplate().insert("Dish_SqlMap.addDish", dish);
	}

	@Override
	public void updateDish(Dish dish) {
		this.getSqlMapClientTemplate().update("Dish_SqlMap.updateDish", dish);
	}

	@Override
	public void deleteDish(String id) {
		this.getSqlMapClientTemplate().update("Dish_SqlMap.deleteDish", id);
	}

}
