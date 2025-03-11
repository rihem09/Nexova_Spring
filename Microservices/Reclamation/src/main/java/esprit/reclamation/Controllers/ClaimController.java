package esprit.reclamation.Controllers;

import esprit.reclamation.Entities.Claim;
import esprit.reclamation.Entities.ClaimStatus;
import esprit.reclamation.Entities.Expert;
import esprit.reclamation.Entities.Mechanic;
import esprit.reclamation.Repositories.ClaimRepository;
import esprit.reclamation.Services.ClaimService;
import esprit.reclamation.Services.ExpertService;
import esprit.reclamation.Services.FileStorageService;
import esprit.reclamation.Services.MechanicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/claims")
@Validated
public class ClaimController {

    private final ClaimService claimService;
    private final FileStorageService fileStorageService;
    private final ExpertService expertService;
    private final MechanicService mechanicService;
    private final ClaimRepository claimRepository;

    private final Path uploadDir = Paths.get("uploads").toAbsolutePath().normalize();

    @Autowired
    public ClaimController(ClaimService claimService,
                           FileStorageService fileStorageService,
                           ExpertService expertService,
                           MechanicService mechanicService,
                           ClaimRepository claimRepository) {
        this.claimService = claimService;
        this.fileStorageService = fileStorageService;
        this.expertService = expertService;
        this.mechanicService = mechanicService;
        this.claimRepository = claimRepository;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createClaim(
            @Valid @RequestParam("description") String description,
            @RequestPart("image") MultipartFile image,
            @RequestParam(value = "expertId", required = false) Long expertId,
            @RequestParam(value = "mechanicId", required = false) Long mechanicId) {

        if (image.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'image ne peut pas être vide");
        }

        try {
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String imagePath = fileStorageService.storeFile(image);
            String imageUrl = "/api/claims/files/" + imagePath;

            Claim claim = new Claim();
            claim.setDescription(description);
            claim.setImagePath(imageUrl);

            if (expertId != null) {
                Expert expert = expertService.getExpertById(expertId)
                        .orElseThrow(() -> new RuntimeException("Expert non trouvé avec l'ID : " + expertId));
                claim.setExpert(expert);
            }

            if (mechanicId != null) {
                Mechanic mechanic = mechanicService.getMechanicById(mechanicId)
                        .orElseThrow(() -> new RuntimeException("Mécanicien non trouvé avec l'ID : " + mechanicId));
                claim.setMechanic(mechanic);
            }

            Claim savedClaim = claimService.createClaim(claim);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedClaim);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Échec de l'enregistrement du fichier : " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Claim>> getAllClaims(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) ClaimStatus status,
            @RequestParam(required = false) LocalDate claimDate) {
        List<Claim> claims = claimService.getAllClaims(description, status, claimDate);
        return ResponseEntity.ok(claims);
    }

    @PutMapping(value = "/update/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Claim> updateClaim(
            @PathVariable Long id,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("status") String status) {

        return claimRepository.findById(id).map(claim -> {
            claim.setDescription(description);
            claim.setStatus(ClaimStatus.valueOf(status));

            // ✅ Utiliser FileStorageService pour stocker correctement l'image
            if (image != null && !image.isEmpty()) {
                try {
                    String imagePath = fileStorageService.storeFile(image); // Stocke l'image
                    String imageUrl = "/api/claims/files/" + imagePath; // Construit l'URL d'accès
                    claim.setImagePath(imageUrl);
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((Claim) null); // ✅ Correction
                }
            }

            claimRepository.save(claim);
            return ResponseEntity.ok(claim);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body((Claim) null));
    }





    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClaim(@PathVariable Long id) {
        claimService.deleteClaim(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/files/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path filePath = uploadDir.resolve(filename);
            System.out.println("Chemin du fichier recherché : " + filePath);

            if (!Files.exists(filePath)) {
                System.out.println("Fichier non trouvé : " + filename);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                        .contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}