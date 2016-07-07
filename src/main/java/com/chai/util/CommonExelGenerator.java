package com.chai.util;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.chai.model.LabelValueBean;

public class CommonExelGenerator extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			JSONArray data = (JSONArray) model.get("export_data");
			ArrayList<LabelValueBean> headerOfTable=(ArrayList<LabelValueBean>) model.get("headerOfTable");
			System.out.println("exel data   "+data);
			HSSFSheet worksheet = workbook.createSheet("export");
			HSSFRow headerRow = worksheet.createRow(0);
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			int cellCount=0;
			for (LabelValueBean bean : headerOfTable) {
				 HSSFCell cell=headerRow.createCell(cellCount);
				 cell.setCellValue(bean.getLabel());
				 cell.setCellStyle(cellStyle);
				 cellCount++;
			}
			int i=0;
			for (i = 0; i < data.length(); i++) {
				System.out.println("row" + i);
				HSSFRow row = worksheet.createRow(i+1);
				JSONObject rowObject = (JSONObject) data.get(i);
				cellCount=0;
				for (LabelValueBean bean : headerOfTable) {
					 HSSFCell cell=row.createCell(cellCount);
					if(rowObject.isNull(bean.getValue())){
						cell.setCellValue("");
					}else{
						
						cell.setCellValue(String.valueOf(rowObject.get(bean.getValue())) );
					}
					 cellCount++;
				}
			}	
			System.out.println("leaving... excel builder");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
