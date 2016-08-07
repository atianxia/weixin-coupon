package com.groundnine.coupon.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.common.collect.Sets;
import com.groundnine.coupon.dao.CouponDao;
import com.groundnine.coupon.dao.CouponItemDao;
import com.groundnine.coupon.service.FileUploadService;
import com.groundnine.coupon.util.MD5Util;
import com.groundnine.coupon.vo.CouponInfoVo;
import com.groundnine.coupon.vo.CouponItemPersistVo;
import com.groundnine.coupon.vo.CouponItemVo;

@Service
public class FileUploadServiceImpl implements FileUploadService {
	
	@Resource
	private CouponDao couponDao;
	
	@Resource
	private CouponItemDao couponItemDao;
	
	@Value("${images.directory}")
	private String imagesDirectory;
	
	@Value("${server.host}/${images.path}")
	private String imagesPath;

	@Override
	public String picFileUpload(CommonsMultipartFile file) {
		if(file == null || file.isEmpty()){
			return null;
		}
//		 String pic_path =  request.getSession().getServletContext().getRealPath("/images/"); 
//		 String pic_path = "D://images//";
		 String pic_time = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());   
	     String pic_type = file.getContentType();
	     String file_ture_name = MD5Util.stringMD5(pic_time);
	     
	     if(pic_type.equals("image/jpeg")){
	         file_ture_name =   file_ture_name.concat(".jpg");
	     } else if (pic_type.equals("image/png")){
	         file_ture_name = file_ture_name.concat(".png");
	     } else if(pic_type.equals("image/bmp")){
	         file_ture_name =  file_ture_name.concat(".bmp");
	     } else if(pic_type.equals("image/gif")){
	         file_ture_name = file_ture_name.concat(".gif");
	     } else file_ture_name = file_ture_name.concat(".jpg");
	     
	        // 判断文件是否存在  
	        if (!file.isEmpty()) {  
	            String path = imagesDirectory +file_ture_name;
	            File localFile = new File(path);  
	            try {  
	                file.transferTo(localFile);  
	            } catch (IllegalStateException e) {  
	                e.printStackTrace();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	     return imagesPath + file_ture_name;
	}

	@Override
	public int importExcel(InputStream inputStream) {
		List<CouponItemVo> couponItemVos = CouponExcelHelper.parseExcelToList(inputStream);
		this.batchInsert(convert(couponItemVos));
		return 1;

	}
	
	private void batchInsert(Collection<CouponInfoVo> couponInfoVos){
		if(CollectionUtils.isNotEmpty(couponInfoVos)){
			for(CouponInfoVo couponInfoVo : couponInfoVos){
				this.couponDao.batchInsert(couponInfoVo);
				this.couponItemDao.batchInsert(couponInfoVo);
			}
		}
	}
	
	private Collection<CouponInfoVo> convert(List<CouponItemVo> couponItemVos){
		Map<String, CouponInfoVo> couponInfoMap = new HashMap<String, CouponInfoVo>();
		if(CollectionUtils.isNotEmpty(couponItemVos)){
			for(CouponItemVo couponItemVo : couponItemVos){
				if(couponInfoMap.containsKey(couponItemVo.getCouponName())){
					CouponItemPersistVo itemPersistVo = new CouponItemPersistVo();
					itemPersistVo.setCouponCode(couponItemVo.getCouponCode());
					CouponInfoVo couponInfoVo = couponInfoMap.get(couponItemVo.getCouponName());
					couponInfoVo.getCouponItemPersistVos().add(itemPersistVo);
					couponInfoVo.setAmount(couponInfoVo.getAmount() + 1);
				}else{
					CouponInfoVo couponInfoVo = new CouponInfoVo();
					BeanUtils.copyProperties(couponItemVo, couponInfoVo);
					couponInfoVo.setAmount(1);
					List<CouponItemPersistVo> itemPersistVos = new ArrayList<CouponItemPersistVo>();
					CouponItemPersistVo itemPersistVo = new CouponItemPersistVo();
					itemPersistVo.setCouponCode(couponItemVo.getCouponCode());
					itemPersistVos.add(itemPersistVo);
					couponInfoVo.setCouponItemPersistVos(itemPersistVos);
					couponInfoMap.put(couponItemVo.getCouponName(), couponInfoVo);
				}
					
			}
		}
		return couponInfoMap.values();
	}
	
	
	@Component
	static class CouponExcelHelper {
		
		/**日期格式*/
	    public static String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	    
	    public static DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
	    
		private static final Logger logger = LoggerFactory.getLogger(CouponExcelHelper.class);
		
		public static List<CouponItemVo> parseExcelToList(InputStream inputStream){
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
		private static String convertCellStr(Cell cell) {
			
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


	@Override
	public Set<String> parseCouponCodeExcel(CommonsMultipartFile couponCodeFile) {
		if(couponCodeFile == null || couponCodeFile.isEmpty()){
			return Sets.newHashSet();
		}
		Set<String> coupons = new HashSet<String>();
		InputStream inputStream = null;
		Workbook wb;
		try {
			inputStream = couponCodeFile.getInputStream();
			wb = WorkbookFactory.create(inputStream);
			Sheet sheet = wb.getSheetAt(0);// 取得第一个sheets

			//从第四行开始读取数据
			for (int i = 0; i <= sheet.getLastRowNum(); i++) {

				Row row = sheet.getRow(i); // 获取行(row)对象
				if (row == null) {
					// row为空的话,不处理
					continue;
				}
				String couponCode = CouponExcelHelper.convertCellStr(row.getCell(0));
				coupons.add(couponCode);
			}
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					couponCodeFile.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
			}
		}

		return coupons;
	}
}
