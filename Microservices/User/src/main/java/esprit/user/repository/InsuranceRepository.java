package esprit.user.repository;

import esprit.user.models.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
    // Custom query methods
    List<Insurance> findByInsuranceType(Insurance.InsuranceType type);
    List<Insurance> findByOccupancyType(Insurance.OccupancyType type);

    // Advanced search with multiple criteria
    @Query("SELECT i FROM Insurance i WHERE " +
            "(:homeType IS NULL OR i.homeType = :homeType) AND " +
            "(:yearOfConstruction IS NULL OR i.yearOfConstruction = :yearOfConstruction) AND " +
            "(:alarmSystem IS NULL OR i.alarmSystem = :alarmSystem)")
    List<Insurance> advancedSearch(
            Insurance.HomeType homeType,
            Integer yearOfConstruction,
            Boolean alarmSystem
    );
}