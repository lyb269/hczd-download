package com.hczd.download.card.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;



/**加油卡消费数据使用的Excel辅助类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-17 上午10:44:11
 */
public class HZ_Gas_Card_Excel_Util {
	private Workbook wwb;

	private Sheet ws;
	
	public Sheet getSheet(){
		return ws;
	}
	
	public Workbook getWorkBook(){
		return wwb;
	}

	public void close() {
		if (wwb != null)
			wwb.close();
	}

	public String getValue(int col, int row) {
		return ws.getCell(col, row).getContents().trim();
	}
	
	public Cell getCell(int col, int row){
		return ws.getCell(col, row) ;
	}

	public int getRows() {
		return ws.getRows();
	}

	public void getSheet(int sheetIndex) {
		try {
			ws = wwb.getSheet(sheetIndex);
		} catch (IndexOutOfBoundsException e) {

			e.printStackTrace();
		}
	}

	public void getWorkBook(String path) {

		try {
			InputStream is = new FileInputStream(path);
			wwb = Workbook.getWorkbook(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
