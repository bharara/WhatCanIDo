package Main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;


public class GeneralFunctions {
    public static final String FILE_NAME = "D:\\2- OOP\\WhatCanIDo\\Excel.xlsx";
    
    public static Workbook getWorkbook () throws FileNotFoundException, IOException {
        InputStream file = new FileInputStream(FILE_NAME);

        return new XSSFWorkbook(file);
    }
    
    public static void Save(Workbook wb) throws FileNotFoundException, IOException {
        try (OutputStream fileOut = new FileOutputStream(FILE_NAME)) {
            wb.write(fileOut);
        }
    }
    
    public static String getCss () throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader ("D:/2- OOP/WhatCanIDo/style.txt"));
        String line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        }
        
        finally {
            reader.close();
        }
    }
}

class RandomCollection<E> {
    private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
    private final Random random;
    private double total = 0;

    public RandomCollection() {
        this(new Random());
    }

    public RandomCollection(Random random) {
        this.random = random;
    }

    public RandomCollection<E> add(double weight, E result) {
        if (weight <= 0) return this;
        total += weight;
        map.put(total, result);
        return this;
    }

    public E next() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }
}