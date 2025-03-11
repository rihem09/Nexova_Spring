package esprit.reclamation.Repositories;


import esprit.reclamation.Entities.Claim;
import esprit.reclamation.Entities.ClaimStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> findByStatus(ClaimStatus status);

    @Query("SELECT c FROM Claim c WHERE (:description IS NULL OR c.description LIKE %:description%) " +
            "AND (:status IS NULL OR c.status = :status) " +
            "AND (:claimDate IS NULL OR c.claimDate = :claimDate)")
    List<Claim> filterClaims(String description, ClaimStatus status, LocalDate claimDate);

}
