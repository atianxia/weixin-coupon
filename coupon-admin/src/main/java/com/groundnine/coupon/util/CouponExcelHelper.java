package com.groundnine.coupon.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.groundnine.coupon.vo.CouponItemVo;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CouponExcelHelper {
	
	/**日期格式*/
    public static String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
    public static DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
    
	private static final Logger logger = LoggerFactory.getLogger(CouponExcelHelper.class);
	
	public List<CouponItemVo> parseExcelToList(InputStream inputStream){
		List<CouponItemVo> couponList = new ArrayList<CouponItemVo>();

		try {
			Workbook wb = WorkbookFactory.create(inputStream);

			Sheet sheet = wb.getSheetAt(0);// 取得第一个sheets

			//从第四行开始读取数据
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {

				Row row = sheet.getRow(i); // 获取行(row)对象
				if (row == null) {
					// row为空的话,不处理
					continue;
				}
				
				CouponItemVo couponItemVo = new CouponItemVo();
				for (int j = 0; j < row.getLastCellNum(); j++) {
					
					try {
						String cellStr = convertCellStr(row.getCell(j));
						switch (j) {
						case 0:
							couponItemVo.setCouponName(cellStr);
							break;
						case 1:
							couponItemVo.setBrandLogo(cellStr);
							break;
						case 2:
							couponItemVo.setUsingRule(cellStr);
							break;
						case 3:
							couponItemVo.setBuyLink(cellStr);
							break;
						case 4:
							couponItemVo.setCouponCode(cellStr);
							break;
						case 5:
							couponItemVo.setExpireDate(DateUtils.parseDate(cellStr, DATE_PATTERN));
							break;
						}
					} catch (Exception e) {
						break;
					}
					
				}
				// 将添加数据后的对象填充至list中
				couponList.add(couponItemVo);
			}

		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				logger.info("没有数据流!");
			}
		}
		
		return couponList;
	}
	
	/**
	 * 把单元格内的类型转换至String类型
	 */
	private String convertCellStr(Cell cell) {
		
		String cellStr  = null;

		switch (cell.getCellType()) {

		case Cell.CELL_TYPE_STRING:
			// 读取String
			cellStr = cell.getStringCellValue().toString();
			break;

		case Cell.CELL_TYPE_BOOLEAN:
			// 得到Boolean对象的方法
			cellStr = String.valueOf(cell.getBooleanCellValue());
			break;

		case Cell.CELL_TYPE_NUMERIC:

			// 先看是否是日期格式
			if (DateUtil.isCellDateFormatted(cell)) {

				// 读取日期格式
				cellStr = DateFormatUtils.format(cell.getDateCellValue(), DATE_PATTERN);

			} else {

				// 读取数字
				cellStr = String.valueOf(decimalFormat.format(cell.getNumericCellValue()));
			}
			break;

		case Cell.CELL_TYPE_FORMULA:
			// 读取公式
			cellStr = cell.getCellFormula().toString();
			break;
		}
		return cellStr;
	}
	
}
