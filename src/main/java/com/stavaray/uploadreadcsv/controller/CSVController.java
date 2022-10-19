package com.stavaray.uploadreadcsv.controller;


import com.stavaray.uploadreadcsv.helper.CSVHelper;
import com.stavaray.uploadreadcsv.message.ResponseMessage;
import com.stavaray.uploadreadcsv.model.User;
import com.stavaray.uploadreadcsv.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/csv")
public class CSVController {

    private final CSVService fileService;

    public CSVController(CSVService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestPart(value = "file") MultipartFile file) {
        String message = "";

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                fileService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

   @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = fileService.getAllUsers();

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/users/pageable")
    public Page findAllPageable(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {

        Pageable pageUser = PageRequest.of(page,size);
        return fileService.findAllPageable(pageUser);

    }


}
