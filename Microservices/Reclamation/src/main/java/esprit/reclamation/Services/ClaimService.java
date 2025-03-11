package esprit.reclamation.Services;


import esprit.reclamation.Entities.Claim;
import esprit.reclamation.Entities.ClaimStatus;
import esprit.reclamation.Repositories.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    public Claim createClaim(Claim claim) {
        return claimRepository.save(claim);
    }

    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    public List<Claim> getClaimsByStatus(ClaimStatus status) {
        return claimRepository.findByStatus(status);
    }

    public void deleteClaim(Long id) {
        claimRepository.deleteById(id);
    }

    public ClaimService(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    public List<Claim> getAllClaims(String description, ClaimStatus status, LocalDate claimDate) {
        return claimRepository.filterClaims(description, status, claimDate);
    }
}
