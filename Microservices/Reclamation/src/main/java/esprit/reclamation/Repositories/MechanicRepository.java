package esprit.reclamation.Repositories;

import esprit.reclamation.Entities.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MechanicRepository extends JpaRepository<Mechanic, Long> {
}
