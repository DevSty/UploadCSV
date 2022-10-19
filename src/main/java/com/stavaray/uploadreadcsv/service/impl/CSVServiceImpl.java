package com.stavaray.uploadreadcsv.service.impl;

import com.stavaray.uploadreadcsv.dao.UserRepository;
import com.stavaray.uploadreadcsv.helper.CSVHelper;
import com.stavaray.uploadreadcsv.model.User;
import com.stavaray.uploadreadcsv.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CSVServiceImpl implements CSVService {

    private final UserRepository userRepository;

    public CSVServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(MultipartFile file) {
        try {
            List<User> users = CSVHelper.csvUsers(file.getInputStream());
            userRepository.saveAll(users);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAllPageable(Pageable pageable) {
        Page<User> pageUsers = userRepository.findAllPageable(pageable);
        return pageUsers;
    }


}
