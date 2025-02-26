package esprit.reclamation.Services;

import esprit.reclamation.Entities.Expert;
import esprit.reclamation.Repositories.ExpertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpertService {

    @Autowired
    private ExpertRepository expertRepository;

    public Expert createExpert(Expert expert) {
        return expertRepository.save(expert);
    }

    public List<Expert> getAllExperts() {
        return expertRepository.findAll();
    }

    public Optional<Expert> getExpertById(Long id) {
        return expertRepository.findById(id);
    }
}
