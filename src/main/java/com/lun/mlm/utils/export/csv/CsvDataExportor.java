package com.lun.mlm.utils.export.csv;

import java.io.OutputStream;

import com.lun.mlm.utils.export.DataField;
import com.lun.mlm.utils.export.ExportDataSource;
import com.lun.mlm.utils.export.txt.TxtDataExportor;



/**
 * 描述: csv格式数据导出工具
 * @author Hill
 *
 */
public class CsvDataExportor<T> extends TxtDataExportor<T> {
	public CsvDataExportor(DataField[] fields, ExportDataSource<T> dataSource,OutputStream os) {
		super(fields, dataSource, os,",");
	}
}
