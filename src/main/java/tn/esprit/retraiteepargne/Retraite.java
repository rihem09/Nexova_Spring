package tn.esprit.retraiteepargne;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Retraite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateInscri;
    private Double montantCoti;
    private Long ageDebutVersement;
    private Long ageFinVersement;
    private String beneficiaire;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateInscri() {
        return dateInscri;
    }

    public void setDateInscri(Date dateInscri) {
        this.dateInscri = dateInscri;
    }

    public Double getMontantCoti() {
        return montantCoti;
    }

    public void setMontantCoti(Double montantCoti) {
        this.montantCoti = montantCoti;
    }

    public Long getAgeDebutVersement() {
        return ageDebutVersement;
    }

    public void setAgeDebutVersement(Long ageDebutVersement) {
        this.ageDebutVersement = ageDebutVersement;
    }

    public Long getAgeFinVersement() {
        return ageFinVersement;
    }

    public void setAgeFinVersement(Long ageFinVersement) {
        this.ageFinVersement = ageFinVersement;
    }

    public String getBeneficiaire() {
        return beneficiaire;
    }

    public void setBeneficiaire(String beneficiaire) {
        this.beneficiaire = beneficiaire;
    }
}
