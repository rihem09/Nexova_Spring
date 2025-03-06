package esprit.user.service;

import esprit.user.models.InsuranceCompanyInfo;
import esprit.user.repository.InsuranceCompanyInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InsuranceCompanyInfoService {
    private final InsuranceCompanyInfoRepository repository;

    @Transactional
    public InsuranceCompanyInfo createCompanyInfo(InsuranceCompanyInfo companyInfo) {
        // Validate tax registration number uniqueness
        if (repository.existsByTaxRegistrationNumber(companyInfo.getTaxRegistrationNumber())) {
            throw new IllegalArgumentException("Company with this tax registration number already exists");
        }
        return repository.save(companyInfo);
    }

    @Transactional(readOnly = true)
    public List<InsuranceCompanyInfo> getAllCompanyInfos() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<InsuranceCompanyInfo> getCompanyInfoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public InsuranceCompanyInfo updateCompanyInfo(Long id, InsuranceCompanyInfo updatedInfo) {
        return repository.findById(id)
                .map(existingInfo -> {
                    existingInfo.setCompanyName(updatedInfo.getCompanyName());
                    existingInfo.setIndustrySector(updatedInfo.getIndustrySector());
                    existingInfo.setHeadOfficeAddress(updatedInfo.getHeadOfficeAddress());
                    existingInfo.setNumberOfEmployees(updatedInfo.getNumberOfEmployees());
                    existingInfo.setValueOfProfessionalEquipment(updatedInfo.getValueOfProfessionalEquipment());
                    return repository.save(existingInfo);
                })
                .orElseThrow(() -> new RuntimeException("Company Info not found"));
    }

    @Transactional
    public void deleteCompanyInfo(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<InsuranceCompanyInfo> findCompaniesByIndustrySector(String sector) {
        return repository.findByIndustrySector(sector);
    }

    @Transactional(readOnly = true)
    public List<InsuranceCompanyInfo> findCompaniesWithSecuritySystems() {
        return repository.findCompaniesWithSecuritySystems();
    }

    @Transactional(readOnly = true)
    public List<InsuranceCompanyInfo> findCompaniesWithLargeWorkforce(Integer minEmployees) {
        return repository.findByNumberOfEmployeesGreaterThan(minEmployees);
    }
}