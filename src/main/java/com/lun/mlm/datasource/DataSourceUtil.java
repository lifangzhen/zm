package com.lun.mlm.datasource;

import java.lang.ThreadLocal;
import javax.sql.DataSource;

/**
* 类说明：线程绑定的数据源名称
*/
public class DataSourceUtil {

	private static final ThreadLocal<String> dataSourceNameHolder = new ThreadLocal<String>();
	/**
	 * 设置当前数据源名称
	 * @param dataSourceName
	 */
	public static void setCurrentDataSourceName(String dataSourceName) {
		dataSourceNameHolder.set(dataSourceName);
	}
	/**
	 * 获得当前数据源名称
	 */
	public static String getCurrentDataSourceName() {
		return (String) dataSourceNameHolder.get();
	}
	/**
	 * 清空当前数据源
	 */
	public static void clearCurrentDataSourceName() {
		dataSourceNameHolder.remove();
	}
	/**
	 * 根据数据源名称获取数据源
	 * @param dataSourceName 数据源名称
	 * @return
	 */
	public static DataSource getDataSource(String dataSourceName){
		return DynamicDataSource.getDataSource(dataSourceName);
	}
	/**
	 * 获取当前数据源
	 * @return
	 */
	public static DataSource getDataSource(){
		return DynamicDataSource.getDataSource(getCurrentDataSourceName());
	}
}
