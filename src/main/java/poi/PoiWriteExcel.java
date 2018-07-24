package poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PoiWriteExcel {

    public static void main(String[] args) throws IOException {
        //create an excel
        HSSFWorkbook workbook = new HSSFWorkbook();
        //create a sheet
        HSSFSheet sheet = workbook.createSheet();
        //create a row,param is index
        HSSFRow row = sheet.createRow(0);
        //the min unit of excel
        HSSFCell cell = null;

        //表头
        String[] title = {"id", "name", "sex"};
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
        }

        //表体
        for (int i = 1; i <= 10; i++) {
            row = sheet.createRow(i);
            cell = row.createCell(0);
            cell.setCellValue(i);
            cell = row.createCell(1);
            cell.setCellValue("kkk" + i);
            cell = row.createCell(2);
            cell.setCellValue("男");
        }

        //生成excel文件
        File file = new File("F:/aa.xls");
        file.createNewFile();
        FileOutputStream stream = new FileOutputStream(file);
        workbook.write(stream);
        stream.close();
    }
}
