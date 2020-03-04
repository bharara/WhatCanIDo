package Main;

import java.io.IOException;
import org.apache.poi.ss.usermodel.*;

public class Movie extends Content {
    
    int yearP;
    
    Movie () {}
    Movie (int r) throws IOException { // Reconstruct movie from excel sheet by row number
        super(r,"Movie");
        
        Workbook wb = GeneralFunctions.getWorkbook();
        Sheet movieSheet = wb.getSheet("Movie");
        
        yearP = (int) movieSheet.getRow(r).getCell(9).getNumericCellValue();
        time = (int) movieSheet.getRow(r).getCell(8).getNumericCellValue();
        
    }
    Movie(String n, int a, int b, int c, int d, int e, int f, double p, int t, int yp) {
        super(n, a, b, c, d, e, f, p);
        super.time = t;
        yearP = yp;
    }

    
    public String[] webPrint () {
        String[] out = new String[4];
        out[0] = name + " - Movie";
        out[1] = String.format("%d;     Runtime: %d:%d;      Price: %.2f", yearP, time/60, time%60, price);
        out[2] = String.format("Funny: %d       Drama: %d       Action: %d", Funny, Drama, Action);
        out[3] = String.format("Tradegy: %d     Story: %d       Dialouge %d", Tragedy, Story, Dialouge);
        return out;
    }
}

class Game extends Content {
    
    int yearP;
    String genre;
    double ignRating;
    
    Game (int r) throws IOException { // Reconstruct movie from excel sheet by row number
        Workbook wb = GeneralFunctions.getWorkbook();
        Sheet movieSheet = wb.getSheet("Game");
        
        name = movieSheet.getRow(r).getCell(0).getStringCellValue();
        Funny = (int) movieSheet.getRow(r).getCell(1).getNumericCellValue();
        Action = (int) movieSheet.getRow(r).getCell(2).getNumericCellValue();
        Story = (int) movieSheet.getRow(r).getCell(3).getNumericCellValue();
        yearP = (int) movieSheet.getRow(r).getCell(6).getNumericCellValue();
        genre = movieSheet.getRow(r).getCell(7).getStringCellValue();
        ignRating = movieSheet.getRow(r).getCell(8).getNumericCellValue();
        time = (int) movieSheet.getRow(r).getCell(5).getNumericCellValue();
        price = movieSheet.getRow(r).getCell(4).getNumericCellValue();
        row = r;
        
    }
    Game(String n, int a, int c, int e, double p, int t, int yp, String gen, double ign) {
        super(n, a, 50, c, 50, e, 50, p);
        super.time = t;
        yearP = yp;
        genre = gen;
        ignRating = ign;
    }
    
    public String[] webPrint () {
        String[] out = new String[4];
        out[0] = name + " - Game";
        out[1] = String.format("%d;     Playtime: %d hours;     Price: %.2f", yearP, time/60, price);
        out[2] = String.format("Genre: %s;      IGN-Rating %.1f\n", genre, ignRating);
        out[3] = String.format("Fun: %d     Action: %d      Story: %d\n", Funny, Action, Story);
        return out;
    }
}

class Book extends Content {
    
    int pages, yearP;
    String author;
    
    Book (int r) throws IOException {
        super(r,"Book");
        
        Workbook wb = GeneralFunctions.getWorkbook();
        Sheet sheet = wb.getSheet("Book");
        pages = (int) sheet.getRow(r).getCell(8).getNumericCellValue();
        author = sheet.getRow(r).getCell(9).getStringCellValue();
        yearP = (int) sheet.getRow(r).getCell(10).getNumericCellValue();
    }
    Book(String n, int a, int b, int c, int d, int e, int f, double p, int pg, int Y, String auth) {
        super(n, a, b, c, d, e, f, p);
        pages = pg;
        yearP = Y;
        author = auth;
        super.time = pages *2;
    }
    
    void setTime (Person P) {
        super.time = (int) (pages * P.speed);
    }
    
    public String[] webPrint () {
        String[] out = new String[5];
        out[0] = name + " - Book";
        out[1] = String.format("%s;     Pages: %d;      Price: %.2f", yearP, pages, price);
        out[2] = String.format("Author %s", author);
        out[3] = String.format("Funny: %d Drama: %d Action: %d", Funny, Drama, Action);
        out[4] = String.format("Tradegy: %d Story: %d Dialouge %d", Tragedy, Story, Dialouge);
        return out;
    }
}