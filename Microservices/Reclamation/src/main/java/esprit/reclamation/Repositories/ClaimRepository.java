package esprit.reclamation.Repositories;


import esprit.reclamation.Entities.Claim;
import esprit.reclamation.Entities.ClaimStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> findByStatus(ClaimStatus status);
}
