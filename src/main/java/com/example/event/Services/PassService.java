package com.example.event.Services;

import com.example.event.Entities.Pass;
import com.example.event.Repositories.PassRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PassService {

    @Autowired
    PassRepo passRepo;

    public Pass createPass(Pass pass) {return passRepo.save(pass);}
    public List<Pass> getAllPass() {return passRepo.findAll();}
    public Pass updatePass(Pass pass) {return passRepo.save(pass);}
    public void deletePass(long id) {passRepo.deleteById(id);}
}
