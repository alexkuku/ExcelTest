package jxl;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;

public class JxlReadExcelTest {


    public static void main(String[] args) throws IOException, BiffException {
        Workbook workbook = Workbook.getWorkbook(new File("/Users/ku/Desktop/aa.xls"));
        Sheet sheet = workbook.getSheet(0);
        for (int i = 0; i < sheet.getRows(); i++) {
            for (int j = 0; j < sheet.getColumns(); j++) {
                Cell cell = sheet.getCell(j, i);
                System.out.print(cell.getContents() + "\t");
            }
            System.out.println();
        }
        workbook.close();
    }
}
