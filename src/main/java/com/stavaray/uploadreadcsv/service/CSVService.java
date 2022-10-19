package com.stavaray.uploadreadcsv.service;

import com.stavaray.uploadreadcsv.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CSVService {

    public void save(MultipartFile file);
    public List<User> getAllUsers();
    public Page<User> findAllPageable(Pageable pageable);

}
