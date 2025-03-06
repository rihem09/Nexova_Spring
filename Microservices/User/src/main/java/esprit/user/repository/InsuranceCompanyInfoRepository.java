package esprit.user.repository;

import esprit.user.models.InsuranceCompanyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InsuranceCompanyInfoRepository extends JpaRepository<InsuranceCompanyInfo, Long> {
    // Find by company name
    Optional<InsuranceCompanyInfo> findByCompanyName(String companyName);

    // Find by industry sector
    List<InsuranceCompanyInfo> findByIndustrySector(String industrySector);

    // Find companies with more than a certain number of employees
    List<InsuranceCompanyInfo> findByNumberOfEmployeesGreaterThan(Integer employeeCount);

    // Custom query to find companies with security systems
    @Query("SELECT i FROM InsuranceCompanyInfo i WHERE i.presenceOfSecuritySystem = true")
    List<InsuranceCompanyInfo> findCompaniesWithSecuritySystems();

    // Check if a company exists by tax registration number
    boolean existsByTaxRegistrationNumber(String taxRegistrationNumber);
}