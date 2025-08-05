package com.example.demo.Service;


import com.example.demo.Repository.AidRepository;
import com.example.demo.model.Aid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;;
import java.util.List;

@Service
@AllArgsConstructor
public class AidService {
    private final AidRepository aidRepository;

    public List<Aid> getAllAids() {
        return aidRepository.findAll();
    }
}