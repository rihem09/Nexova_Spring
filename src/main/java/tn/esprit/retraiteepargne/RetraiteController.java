package tn.esprit.retraiteepargne;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
//@CrossOrigin(origins = "http://localhost:4200") // Allow Angular to access this API
@RestController
@RequestMapping("/retraite")
public class RetraiteController {

    @Autowired
    private RetraiteService retraiteService;

    @GetMapping("/retrieve-retraites")
    public List<Retraite> getAllRetraites() {
        return retraiteService.getAllRetraites();
    }

    @GetMapping("/retrieve-retraite/{idRetraite}")
    public Optional<Retraite> getRetraiteById(@PathVariable Long id) {
        return retraiteService.getRetraiteById(id);
    }

    @PostMapping("/add-retraite")
    public Retraite createRetraite(@RequestBody Retraite retraite) {
        return retraiteService.saveRetraite(retraite);
    }

    @PutMapping("/modify-retraite")
    public Retraite updateRetraite(@PathVariable Long id, @RequestBody Retraite retraiteDetails) {
        return retraiteService.updateRetraite(id, retraiteDetails);
    }
    @DeleteMapping("/delete-retraite/{idRetraite}")
    public void deleteRetraite(@PathVariable Long id) {
        retraiteService.deleteRetraite(id);
    }
}
