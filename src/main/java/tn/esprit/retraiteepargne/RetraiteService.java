package tn.esprit.retraiteepargne;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RetraiteService {

    @Autowired
    private RetraiteRepo retraiteRepo;

    public List<Retraite> getAllRetraites() {
        return retraiteRepo.findAll();
    }

    public Optional<Retraite> getRetraiteById(Long id) {
        return retraiteRepo.findById(id);
    }

    public Retraite saveRetraite(Retraite retraite) {
        return retraiteRepo.save(retraite);
    }

    public Retraite updateRetraite(Long id, Retraite retraiteDetails) {
        return retraiteRepo.findById(id).map(retraite -> {
            retraite.setDateInscri(retraiteDetails.getDateInscri());
            retraite.setMontantCoti(retraiteDetails.getMontantCoti());
            retraite.setAgeDebutVersement(retraiteDetails.getAgeDebutVersement());
            retraite.setAgeFinVersement(retraiteDetails.getAgeFinVersement());
            retraite.setBeneficiaire(retraiteDetails.getBeneficiaire());
            return retraiteRepo.save(retraite);
        }).orElseThrow(() -> new RuntimeException("Retraite not found with id " + id));
    }

    public void deleteRetraite(Long id) {
        retraiteRepo.deleteById(id);
    }
}
