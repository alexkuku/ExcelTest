package jxl;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;

public class JxlWriteExcelTest {

    public static void main(String[] args) throws IOException, WriteException {
        String[] title = {"编号", "姓名", "性别"};

        File file = new File("/Users/ku/Desktop/aa.xls");

        file.createNewFile();

        WritableWorkbook workbook = Workbook.createWorkbook(file);
        WritableSheet sheet = workbook.createSheet("Sheet1", 0);

        Label label = null;

        for (int i = 0; i < title.length; i++) {
            //列，行，值
            label = new Label(i, 0, title[i]);
            sheet.addCell(label);
        }

        for (int i = 1; i < 10; i++) {
            label = new Label(0, i ,String.valueOf(i));
            sheet.addCell(label);
            label = new Label(1, i ,"kkk" + i);
            sheet.addCell(label);
            label = new Label(2, i ,"M");
            sheet.addCell(label);
        }

        workbook.write();
        workbook.close();
    }
}
