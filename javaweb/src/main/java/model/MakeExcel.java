package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.poi.hssf.eventusermodel.HSSFUserException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class MakeExcel {
	
	/**
	 * 파일 생성 
	 * @param fileName
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 */
	public FileOutputStream makeFile(String path){
		String filePath= path.equals("") ? "./" : path+"/";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String fileName = "export_" + date.format(cal.getTime()) + ".xls";
		File file = new File(filePath);
		
		if(!file.exists())
			file.mkdir();
		 
		try {
			System.out.println(file.getAbsolutePath());
			System.out.println(filePath + fileName);
			FileOutputStream fout = new FileOutputStream(filePath + fileName);
			return fout;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 냠리스트의 어레이리스를 인자로 받아서, 스트링 어레이에 넣고, 
	 * 해당 어레이를  row에 cell로 넣는다. 코드 개선 가능할듯ㅠㅠ하지만.. 연규느님!  
	 * @param list
	 * @return
	 */
	public HSSFWorkbook fillExcel(ArrayList<NyamList> list){

		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet =  excel.createSheet();
		//record line
		HSSFRow recordRow =  sheet.createRow(0);
		String[] record = {"id", "name", "num"};
		for(int i = 0; i <3; i++){
			HSSFCell cell =  recordRow.createCell(i);
			cell.setCellValue(record[i]);
		}
		
		for(int i = 0; i < list.size(); i++){
			NyamList nList = list.get(i);
			ArrayList<String> nyamArray = new ArrayList<String>();			
			nyamArray.add(0, nList.getId());
			nyamArray.add(1, nList.getName());
			nyamArray.add(2, Integer.toString(nList.getNyamNum()));
			
			HSSFRow row = sheet.createRow(i+1);
			
			for(int j = 0; j < 3; j++){
				HSSFCell cell = row.createCell(j);
				cell.setCellValue(nyamArray.get(j));
			}

		}
		
		return excel;
	}
	
	public static void main(String[] args) {
		DAO dao = DAO.getInstance();
		System.out.println("exportExcel");
		MakeExcel me = new MakeExcel();
		HSSFWorkbook book = me.fillExcel(dao.adminNyamHistory()); 
		FileOutputStream fout= me.makeFile("");
		try {
			book.write(fout);
			fout.close();
			System.out.println("file exist");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
