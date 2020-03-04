package Main;

import java.io.IOException;
import org.apache.poi.ss.usermodel.*;


// Main parent Abstract class
public abstract class Content {
    public String name;
    int Funny, Drama, Action, Tragedy, Story, Dialouge; // 6 categories in which each contect is rated out of 100
    double price; int time; // 2 variables that budget which content is appropriate for the user
    int row; // Row of that particular content in the respective sheet
    
    Content () {}
    Content (String n, int a, int b, int c, int d, int e, int f, double p) {
        name = n;
        Funny = a;
        Drama = b;
        Action = c;
        Tragedy = d;
        Story = e;
        Dialouge = f;
        setPrice(p);
    }
    Content (int r, String Usheet) throws IOException {
        Workbook wb = GeneralFunctions.getWorkbook();
        Sheet sheet = wb.getSheet(Usheet);
        
        name = sheet.getRow(r).getCell(0).getStringCellValue();
        Funny = (int) sheet.getRow(r).getCell(1).getNumericCellValue();
        Drama = (int) sheet.getRow(r).getCell(2).getNumericCellValue();
        Action = (int) sheet.getRow(r).getCell(3).getNumericCellValue();
        Tragedy = (int) sheet.getRow(r).getCell(4).getNumericCellValue();
        Story = (int) sheet.getRow(r).getCell(5).getNumericCellValue();
        Dialouge = (int) sheet.getRow(r).getCell(6).getNumericCellValue();
        price = sheet.getRow(r).getCell(7).getNumericCellValue();
        row = r;
    }
    
    void setPrice(double p) {
        price = (p>0) ? p: 0; // Price will always be >0;
    }

    public String typeToString() {
        if (this instanceof Movie)
            return "Movie";
        else if (this instanceof Book)
            return "Book";
        else
            return "Game";
    }
    
    public static Content getContent (int row, String typeName) throws IOException {
        switch (typeName) {
            case "Movie":
                return new Movie(row);
            case "Book":
                return new Book(row);
            default:
                return new Game(row);
        }
    }
    
    public abstract String[] webPrint();
    
    public static String[] getContentNameList () throws IOException {
        Workbook wb = GeneralFunctions.getWorkbook();
        Sheet movie = wb.getSheet("Movie"), game = wb.getSheet("Game"), book = wb.getSheet("Book");
        int n = 0;
        String[] list = new String[200];
        
        for (Row r : movie) {
            list[n] = r.getCell(0).getStringCellValue();
            n++;
        }
        for (Row r : book) {
            list[n] = r.getCell(0).getStringCellValue();
            n++;
        }
        for (Row r : game) {
            list[n] = r.getCell(0).getStringCellValue();
            n++;
        }
        return list;
    }
    
    public static Content stringToContent (String name) throws IOException {
        Workbook wb = GeneralFunctions.getWorkbook();
        Sheet movie = wb.getSheet("Movie"), game = wb.getSheet("Game"), book = wb.getSheet("Book");
        
        for (Row r: movie) {
            if (r.getCell(0).getStringCellValue().equals(name))
                return new Movie(r.getRowNum());
        }
        for (Row r: book) {
            if (r.getCell(0).getStringCellValue().equals(name))
                return new Book(r.getRowNum());
        }
        for (Row r: game) {
            if (r.getCell(0).getStringCellValue().equals(name))
                return new Game(r.getRowNum());
        }
        return new Movie();
    }
}