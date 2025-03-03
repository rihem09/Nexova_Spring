package com.example.event.Controllers;

import com.example.event.Entities.Pass;
import com.example.event.Services.PassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/pass")
@CrossOrigin(origins = "http://localhost:4200")
public class PassRestAPI {

    @Autowired
    PassService passService;

    @PostMapping("/create_pass")
    public Pass createPass(@RequestBody Pass pass) {
        Pass p=passService.createPass(pass);
        return p;
    }

    @GetMapping("/show_pass")
    public List<Pass> showPass() {
        List<Pass> passList = passService.getAllPass();
        return passList;
    }

    @PutMapping("/mod_pass")
    public Pass modPass(@RequestBody Pass pass) {
        Pass p=passService.updatePass(pass);
        return p;
    }

    @DeleteMapping("/del_pass/{idPass}")
    public void deletePass(@PathVariable("idPass")long idp) {
        passService.deletePass(idp);
    }
}
