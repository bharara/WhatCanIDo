package Main;

import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

// Class defing user and algromatheic methods
public class Person {
    public String name;
    double speed; //Time in minutes in which a person completes a book page.
    int numberOfContentConsumed; //Count of Content Consumed
    int funny, drama, action, tragedy, story, dialouge;   
    int row; //Row named after the person in excel sheet
    
    
    public Person (int r) throws IOException {
        Workbook wb = GeneralFunctions.getWorkbook();
        Sheet personSheet = wb.getSheet("Person");
        
        name = personSheet.getRow(r).getCell(0).getStringCellValue();
        speed = personSheet.getRow(r).getCell(1).getNumericCellValue();
        funny = (int) personSheet.getRow(r).getCell(2).getNumericCellValue();
        drama = (int) personSheet.getRow(r).getCell(3).getNumericCellValue();
        action = (int) personSheet.getRow(r).getCell(4).getNumericCellValue();
        tragedy = (int) personSheet.getRow(r).getCell(5).getNumericCellValue();
        story = (int) personSheet.getRow(r).getCell(6).getNumericCellValue();
        dialouge = (int) personSheet.getRow(r).getCell(7).getNumericCellValue();
        numberOfContentConsumed = (int) personSheet.getRow(r).getCell(8).getNumericCellValue();
        row = r;
    } // When we tell which rowed person to pick. It retrives object from excel file
    public Person (String n) throws IOException { this(n,2); } // Person constructor with name only
    public Person (String n, int s) throws IOException { // Person's constructor with name and speed. Writes to Excel file as well as contents elsewhere
        
        // Assign given and default values to field variables
        name = n;
        speed = s;
        funny = drama = action = tragedy = story = dialouge = 50;
        
        // Wriing the Values in Excel File
        Workbook wb = GeneralFunctions.getWorkbook();
        
        // In Person sheet
        Sheet personSheet = wb.getSheet("Person");
        row = personSheet.getLastRowNum() + 1; //The row number in Person sheet
        personSheet.createRow(row).createCell(0).setCellValue(name);
        personSheet.getRow(row).createCell(1).setCellValue(speed);
        for (int i=2; i<8; i++) { personSheet.getRow(row).createCell(i).setCellValue(50); } // Setting values in progressive rows for the 6 categories
        personSheet.getRow(row).createCell(8).setCellValue(numberOfContentConsumed);
        
        // In content sheets
        wb.getSheet("Movie").getRow(0).createCell(row+11).setCellValue(name);
        wb.getSheet("Book").getRow(0).createCell(row+11).setCellValue(name);
        wb.getSheet("Game").getRow(0).createCell(row+11).setCellValue(name);
        
        // Saving ...
        GeneralFunctions.Save(wb);
    } // Person's constructor with name and speed. Writes to Excel file as well as contents elsewhere
    
    public void addReview (int review, Content reviewableContent) throws IOException {
        
        numberOfContentConsumed++;
        
        // This is the Algorithm for Moderating the category score of the user based on the review of that film
        funny = (int) (funny*numberOfContentConsumed + reviewableContent.Funny*review/5)/(1+numberOfContentConsumed);
        drama = (int) (drama*numberOfContentConsumed + reviewableContent.Drama*review/5)/(1+numberOfContentConsumed);
        action = (int) (action*numberOfContentConsumed + reviewableContent.Action*review/5)/(1+numberOfContentConsumed);
        tragedy = (int) (tragedy*numberOfContentConsumed + reviewableContent.Tragedy*review/5)/(1+numberOfContentConsumed);
        story = (int) (story*numberOfContentConsumed + reviewableContent.Story*review/5)/(1+numberOfContentConsumed);
        dialouge = (int) (dialouge*numberOfContentConsumed + reviewableContent.Dialouge*review/5)/(1+numberOfContentConsumed);
        
        
        // Wriing the Review in Excel File
        Workbook wb = GeneralFunctions.getWorkbook();
        Sheet contentSheet = wb.getSheet(reviewableContent.typeToString());
        contentSheet.getRow(reviewableContent.row).createCell(row+11).setCellValue(review);
        
        
        // Writing Updates Ratings in Excel File
        Sheet personSheet = wb.getSheet("Person");
        personSheet.getRow(row).getCell(2).setCellValue(funny);
        personSheet.getRow(row).getCell(3).setCellValue(drama);
        personSheet.getRow(row).getCell(4).setCellValue(action);
        personSheet.getRow(row).getCell(5).setCellValue(tragedy);
        personSheet.getRow(row).getCell(6).setCellValue(story);
        personSheet.getRow(row).getCell(7).setCellValue(dialouge);
        personSheet.getRow(row).getCell(8).setCellValue(dialouge);
        GeneralFunctions.Save(wb);
    } // Write review to the sheet and edit User score
    
    int getFavContentScore (int contentRowNumber, String typeOfContent) throws IOException { // Return's how much each movie is related to your perevios review. This doenn't take budgeing into account
        Workbook wb = GeneralFunctions.getWorkbook();
        Sheet contentSheet = wb.getSheet(typeOfContent);
        
        if (typeOfContent.equals("Game"))
            return (int) ( Math.abs(funny-contentSheet.getRow(contentRowNumber).getCell(1).getNumericCellValue())
                + Math.abs(action-contentSheet.getRow(contentRowNumber).getCell(2).getNumericCellValue())
                + Math.abs(dialouge-contentSheet.getRow(contentRowNumber).getCell(3).getNumericCellValue()));
        else
            return (int) ( Math.abs(funny-contentSheet.getRow(contentRowNumber).getCell(1).getNumericCellValue())
                + Math.abs(drama-contentSheet.getRow(contentRowNumber).getCell(2).getNumericCellValue())
                + Math.abs(action-contentSheet.getRow(contentRowNumber).getCell(3).getNumericCellValue())
                + Math.abs(tragedy-contentSheet.getRow(contentRowNumber).getCell(4).getNumericCellValue())
                + Math.abs(story-contentSheet.getRow(contentRowNumber).getCell(5).getNumericCellValue())
                + Math.abs(dialouge-contentSheet.getRow(contentRowNumber).getCell(6).getNumericCellValue()));
    } // Return's how much each movie is related to your perevios review. This doenn't take budgeing into account
    
    public Content[] getFavContent (boolean getMovie, boolean getBook, boolean getGame) throws IOException { //Get content most related to the user without budgeting
        Content[] favContent = new Content[100];
        Workbook wb = GeneralFunctions.getWorkbook();
        Sheet movie = wb.getSheet("Movie");
        Sheet book = wb.getSheet("Book");
        Sheet game = wb.getSheet("Game");
        
        
        int n = 0;
        int j = 0;
        for (int k=25; k<250; k+=10) {
            if (n<25) {
                if (getMovie) {
                    for (int i=1; i<movie.getLastRowNum(); i++) {
                        int score = getFavContentScore(i,"Movie");
                        if (j<score & score<k) { // j<score<k
                            favContent[n] = new Movie(i);
                            n++;
                        }
                    }
                }
                if (getBook) {
                    for (int i=1; i<book.getLastRowNum(); i++) {
                        int score = getFavContentScore(i,"Book");
                        if (j<score & score<k) {
                            favContent[n] = new Book(i);
                            n++;
                        }
                    }
                }
                if (getGame) {
                    for (int i=1; i<game.getLastRowNum(); i++) {
                        int score = getFavContentScore(i,"Game");
                        if (j<score & score<k) {
                            favContent[n] = new Game(i);
                            n++;
                        }
                    }
                }
            }
            else
                return favContent;
            j = k;
        }
        return favContent;
    }

    public Content[][] seprateBestToBudget (Content[] favContent, int time, double money) {
        Content[] nonBudgetedContent = new Content[100];
        Content[] budgetedContent = new Content[100];
        
        int n =0;
        for (Content content : favContent) {
            if (content!=null) {
                if (money>content.price & time>content.time)
                    budgetedContent[n] =  content;
                else
                    nonBudgetedContent[n] = content;
                n++;
            }
        }
        Content[][] returnContent = {nonBudgetedContent, budgetedContent};
        return returnContent;
    }
        
    static String[] getUserList () throws IOException { //Return list of all persons
        Workbook wb = GeneralFunctions.getWorkbook();
        Sheet personSheet = wb.getSheet("Person");
        int size = personSheet.getLastRowNum();
        String[] userList = new String[size];
        
        for (int i=1; i<=size; i++)
            userList[i-1] = personSheet.getRow(i).getCell(0).getStringCellValue();
        return userList;
    } // Return list of all persons
    
    public static int getRowNumber (String name) throws IOException {
        Workbook wb = GeneralFunctions.getWorkbook();
        
        for (Row r : wb.getSheet("Person"))
            if (r.getCell(0).getStringCellValue().equals(name))
                return r.getRowNum();
        return 1;
    } //Takes name and return row number. Elsewise return 1
}