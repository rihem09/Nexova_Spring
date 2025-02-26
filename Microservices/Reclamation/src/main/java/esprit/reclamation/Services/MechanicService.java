package esprit.reclamation.Services;
import esprit.reclamation.Entities.Mechanic;
import esprit.reclamation.Repositories.MechanicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MechanicService {

    @Autowired
    private MechanicRepository mechanicRepository;

    public Mechanic createMechanic(Mechanic mechanic) {
        return mechanicRepository.save(mechanic);
    }

    public List<Mechanic> getAllMechanics() {
        return mechanicRepository.findAll();
    }
    // MechanicService.java
    public Optional<Mechanic> getMechanicById(Long id) {
        return mechanicRepository.findById(id);
    }
}