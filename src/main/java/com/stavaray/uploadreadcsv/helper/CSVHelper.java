package com.stavaray.uploadreadcsv.helper;

import com.stavaray.uploadreadcsv.model.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {
    public static String TYPE = "text/csv";
    //static String[] HEADERS = { "Id", "Name", "LastName","Description", "Banned" };

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<User> csvUsers(InputStream is) {
        try (
                BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                CSVParser csvParser = new CSVParser(fileReader,
                        CSVFormat.DEFAULT.builder()
                                .setHeader()
                                .setSkipHeaderRecord(false)
                                .setIgnoreHeaderCase(true)
                                .setTrim(true)
                                .build())
             ) {

            List<User> users = new ArrayList<>();
            csvParser.getRecords()
                    .stream()
                    .forEach(csv ->
                        users.add(new User(
                                //Long.parseLong(csv.get("Id")),
                                csv.get("Name"),
                                csv.get("LastName"),
                                csv.get("Description"),
                                Boolean.parseBoolean(csv.get("Banned"))))
                    );

            System.out.println("*************** USERS *************");
            users.stream().forEach(System.out::println);

            return users;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
