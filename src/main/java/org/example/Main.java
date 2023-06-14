package org.example;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Main {
    static Database handler;

    //add values into the table
    public static void add(String name, String path, String extension, String size){
        boolean flag = name.isEmpty() || path.isEmpty() || extension.isEmpty() || size.isEmpty();
        if (flag) {
            System.out.println("Empty.");
            return;
        }
        String st = "INSERT INTO FILE VALUES (" +
                "'" + name + "'," +
                "'" + path + "'," +
                "'" + extension + "'," +
                "'" + size + "')";
        if (handler.execAction(st)) {
            System.out.println("info entered");
        } else {
            System.out.println("info not entered");
        }
    }

    //get file values from the directory
    public static void main(String[] args){
        handler = new Database();
        File directory = new File("/Users/bettybao/Documents/GitHub/Module4-databases/");
        LinkedList<File> list = searchFolder(directory);
        for (File file : list) {
            if (!file.isDirectory()) {
                String fileName = file.getName();
                String path = file.getAbsolutePath();
                String[] fileParts = file.getName().split("\\.");
                String extension = fileParts[fileParts.length - 1];
                long size = file.length();
                System.out.println(fileName);
                add(fileName, path, extension, String.valueOf(size));
            }
            System.out.println("File values inserted into the database successfully.");
        }
        try {
            printFiles();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static LinkedList<File> searchFolder(File directory) {
        File[] files = directory.listFiles();
        LinkedList<File> fileLinkedList = new LinkedList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                fileLinkedList.addAll(searchFolder(file));
            } else {
                fileLinkedList.add(file);
            }
        }
        return fileLinkedList;
    }

    //display all four fields from the file table
    public static void printFiles() throws SQLException{
        String qu = "SELECT * FROM FILE";
        ResultSet rs = handler.execQuery(qu);
            while(rs.next()) {
                String name = rs.getString("name");
                String path = rs.getString("path");
                String extension = rs.getString("extension");
                String size = rs.getString("size");
                System.out.println("Entry: Name" + name + "\tPath" + path + "\tExtension" + extension + "\tSize" + size);
            }
    }
}