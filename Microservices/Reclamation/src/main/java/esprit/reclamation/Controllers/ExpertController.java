package esprit.reclamation.Controllers;
import esprit.reclamation.Entities.Expert;
import esprit.reclamation.Services.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experts")
public class ExpertController {

    @Autowired
    private ExpertService expertService;

    @PostMapping
    public ResponseEntity<Expert> createExpert(@RequestBody Expert expert) {
        return ResponseEntity.ok(expertService.createExpert(expert));
    }

    @GetMapping
    public ResponseEntity<List<Expert>> getAllExperts() {
        return ResponseEntity.ok(expertService.getAllExperts());
    }
}
