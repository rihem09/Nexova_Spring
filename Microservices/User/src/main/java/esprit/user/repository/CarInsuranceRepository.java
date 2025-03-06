package esprit.user.repository;

import esprit.user.models.CarInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CarInsuranceRepository extends JpaRepository<CarInsurance, Long> {
    List<CarInsurance> findByMakeAndModel(String make, String model);
    List<CarInsurance> findByYear(Integer year);
    List<CarInsurance> findByFuelType(CarInsurance.FuelType fuelType);
    List<CarInsurance> findByStatus(CarInsurance.InsuranceStatus status);

    @Query("SELECT c FROM CarInsurance c WHERE " +
           "(:make IS NULL OR c.make = :make) AND " +
           "(:model IS NULL OR c.model = :model) AND " +
           "(:year IS NULL OR c.year = :year) AND " +
           "(:fuelType IS NULL OR c.fuelType = :fuelType)")
    List<CarInsurance> advancedSearch(
            @Param("make") String make,
            @Param("model") String model,
            @Param("year") Integer year,
            @Param("fuelType") CarInsurance.FuelType fuelType
    );
}