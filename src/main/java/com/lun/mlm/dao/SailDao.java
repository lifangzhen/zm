package com.lun.mlm.dao;

import java.util.List;

import com.lun.mlm.model.Category;
import com.lun.mlm.model.Dish;

public interface SailDao {
	/**类别列表 */
	public List<Category> listCategory(String name, Integer start, Integer end);
	/** 获取类别*/
	public Category getCategory(String id);
	/**类别数量 */
	public Integer countCategory(String name);
	/**新增类别 */
	public void addCategory(Category category);
	/**更新类别 */
	public void updateCategory(Category category);
	/**删除类别 */
	public void deleteCategory(String id);
	/**菜品列表 */
	public List<Dish> listDish(String name, String categoryId,  Integer start, Integer end);
	/**类别的菜品列表*/
	public List<Dish> listDishByCategoryId(String categoryId, Integer status);
	/**菜品 */
	public Dish getDish(String id);
	/**菜品数量 */
	public Integer countDish(String name, String categoryId);
	/**类别的菜品数量*/
	public Integer countDishByCategoryId(String categoryId);
	/**新增菜品 */
	public void addDish(Dish dish);
	/**更新菜品 */
	public void updateDish(Dish dish);
	/**删除菜品 */
	public void deleteDish(String id);
}
