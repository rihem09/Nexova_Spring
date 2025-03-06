package esprit.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import esprit.user.models.Insurance;
import java.util.List;

@FeignClient(name = "Insurance", path = "/api/insurance")
public interface InsuranceClient {

    @PostMapping
    Insurance createInsurance(@RequestBody Insurance insurance);

    @GetMapping
    List<Insurance> getAllInsurances();

    @GetMapping("/{id}")
    Insurance getInsuranceById(@PathVariable Long id);

    @PutMapping("/{id}")
    Insurance updateInsurance(@PathVariable Long id, @RequestBody Insurance insurance);

    @DeleteMapping("/{id}")
    void deleteInsurance(@PathVariable Long id);
}