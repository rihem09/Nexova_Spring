package tn.esprit.retraiteepargne;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetraiteRepo extends JpaRepository<Retraite, Long> {
}
