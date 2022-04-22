import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class DataDriven {

    @BeforeMethod
    public static ArrayList<String> getData(String testData) throws IOException {
        ArrayList<String> a=new ArrayList<String>();


        //Getting Access to the excel file
        FileInputStream fis=new FileInputStream("src/main/resources/demo.xlsx");
        XSSFWorkbook workbook=new XSSFWorkbook(fis);

        int sheets=workbook.getNumberOfSheets();//to get total number of sheets
        for(int i=0;i<sheets;i++)
        {
            if(workbook.getSheetName(i).equalsIgnoreCase("Sheet1")) //Getting the Sheet name
            {

                //get access to sheet
                XSSFSheet sheet=workbook.getSheetAt(i); //Identify Testcases coloum by scanning the entire 1st row


                //access to rows
                Iterator<Row> rows= sheet.iterator();// to get accss to all rows
                Row firstrow= rows.next();
                Iterator<Cell> ce=firstrow.cellIterator();//row is collection of cells
                int k=0;
                int coloumn = 0;

                //To get the row which has our desired test case data
                while(ce.hasNext())
                {
                    Cell value=ce.next();

                    if(value.getStringCellValue().equalsIgnoreCase("TestCases")) //Scanning row value which equals Test cases
                    {
                        coloumn=k;

                    }

                    k++;
                }

                //Printing out the index of column
                System.out.println(coloumn);

                //rows to cells

                ////once coloumn is identified then scan entire testcase coloum to identify purcjhase testcase row
                while(rows.hasNext())
                {

                    Row r=rows.next();

                    // once we got the column index, it scans to match the testcase data
                    if(r.getCell(coloumn).getStringCellValue().equalsIgnoreCase(testData))
                    {

                        ////after you grab purchase testcase row = pull all the data of that row and feed into test
                        Iterator<Cell>  cv=r.cellIterator();
                        while(cv.hasNext())
                        {
                            Cell c=	cv.next();
                            if(c.getCellTypeEnum()== CellType.STRING)
                            {
                             a.add(c.getStringCellValue());
                            }
                            else{
                            a.add(NumberToTextConverter.toText(c.getNumericCellValue()));
                                }
                        }
                    }
                }


            }
        }
        return a;

        //Identify testcases column by scanning 1st row
        //one colum is identified and then scan entire test case column to identify purchase
        //after you grab purchase testcase row = pull data of that row


    }
}
