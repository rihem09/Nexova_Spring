package esprit.reclamation.Controllers;

import esprit.reclamation.Entities.Claim;
import esprit.reclamation.Entities.Expert;
import esprit.reclamation.Entities.Mechanic;
import esprit.reclamation.Services.ClaimService;
import esprit.reclamation.Services.ExpertService;
import esprit.reclamation.Services.FileStorageService;
import esprit.reclamation.Services.MechanicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    private final ClaimService claimService;
    private final FileStorageService fileStorageService;
    private final ExpertService expertService;
    private final MechanicService mechanicService;

    @Autowired
    public ClaimController(ClaimService claimService,
                           FileStorageService fileStorageService,
                           ExpertService expertService,
                           MechanicService mechanicService) {
        this.claimService = claimService;
        this.fileStorageService = fileStorageService;
        this.expertService = expertService;
        this.mechanicService = mechanicService;
    }
    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createClaim(
            @RequestParam("description") String description,
            @RequestPart("image") MultipartFile image,
            @RequestParam(value = "expertId", required = false) Long expertId,
            @RequestParam(value = "mechanicId", required = false) Long mechanicId) {

        try {
            // Create new claim with default values
            Claim claim = new Claim();
            claim.setDescription(description);

            // Handle image upload
            String imagePath = fileStorageService.storeFile(image);
            claim.setImagePath(imagePath);

            // Set relationships if IDs are provided
            if (expertId != null) {
                Expert expert = expertService.getExpertById(expertId)
                        .orElseThrow(() -> new RuntimeException("Expert not found with id: " + expertId));
                claim.setExpert(expert);
            }

            if (mechanicId != null) {
                Mechanic mechanic = mechanicService.getMechanicById(mechanicId)
                        .orElseThrow(() -> new RuntimeException("Mechanic not found with id: " + mechanicId));
                claim.setMechanic(mechanic);
            }

            Claim savedClaim = claimService.createClaim(claim);
            return new ResponseEntity<>(savedClaim, HttpStatus.CREATED);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to store file: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // Add other endpoints as needed
}