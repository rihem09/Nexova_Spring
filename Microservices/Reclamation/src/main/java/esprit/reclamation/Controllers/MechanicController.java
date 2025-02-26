package esprit.reclamation.Controllers;


import esprit.reclamation.Entities.Mechanic;
import esprit.reclamation.Services.MechanicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mechanics")
public class MechanicController {

    @Autowired
    private MechanicService mechanicService;

    @PostMapping
    public ResponseEntity<Mechanic> createMechanic(@RequestBody Mechanic mechanic) {
        return ResponseEntity.ok(mechanicService.createMechanic(mechanic));
    }

    @GetMapping
    public ResponseEntity<List<Mechanic>> getAllMechanics() {
        return ResponseEntity.ok(mechanicService.getAllMechanics());
    }
}
