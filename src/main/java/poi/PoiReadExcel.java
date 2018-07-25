package poi;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PoiReadExcel {

    public static void main(String[] args) throws IOException {
        //获取excel文件
        File file = new File("F://aa.xls");

        HSSFWorkbook workbook = new HSSFWorkbook(FileUtils.openInputStream(file));
        //HSSFSheet sheet = workbook.getSheet("Sheet0");
        HSSFSheet sheet = workbook.getSheetAt(0);

        int lastRowNum = sheet.getLastRowNum();

        for (int i = sheet.getFirstRowNum(); i <= lastRowNum; i++) {
            HSSFRow row = sheet.getRow(i);

            int lastCellNum = row.getLastCellNum();

            for (int j = row.getFirstCellNum(); j < lastCellNum; j++)  {
                HSSFCell cell = row.getCell(j);
                String value = getCellValue(cell);
                System.out.print(value + "    ");
            }
            System.out.println();
        }
    }

    public static String getCellValue(HSSFCell cell) {
        String strCell = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_STRING://字符串类型
                    strCell = cell.getStringCellValue();
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC://数字类型
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                        SimpleDateFormat sdf = null;
// short dataFormat = cell.getCellStyle().getDataFormat();
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = cell.getDateCellValue();
                        strCell = sdf.format(date);
                    } else {
                        strCell = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN://boolean类型
                    strCell = String.valueOf(cell.getBooleanCellValue());
                    break;
                case HSSFCell.CELL_TYPE_BLANK:
                    strCell = "";
                    break;
                default:
                    strCell = "";
                    break;
            }
        }
        if (strCell.equals("") || strCell == null) {
            strCell = "";
        }
        return strCell.trim();
    }


}
