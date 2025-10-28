package vn.project.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.project.jobhunter.domain.Company;
import vn.project.jobhunter.domain.User;
import vn.project.jobhunter.domain.response.ResultPaginationDTO;
import vn.project.jobhunter.service.CompanyService;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/companies")
    public ResponseEntity<ResultPaginationDTO> getAllCompany(
            @Filter Specification<Company> spec, Pageable pageable) {

        // String sCurrent = currentOptional.isPresent() ? currentOptional.get() : "";
        // String sPageSize = pageSizeOptional.isPresent() ? pageSizeOptional.get() :
        // "";

        // int current = Integer.parseInt(sCurrent);
        // int pageSize = Integer.parseInt(sPageSize);

        // Pageable pageable = PageRequest.of(current - 1, pageSize);
        // List<Company> getAllCompany =
        // this.companyService.handleGetAllCompany(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(this.companyService.handleGetAllCompany(spec, pageable));
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") Long id) {
        Company getCompany = this.companyService.handleGetCompany(id);
        return ResponseEntity.status(HttpStatus.OK).body(getCompany);
    }

    @PostMapping("/companies")
    public ResponseEntity<Company> createCompany(@Valid @RequestBody Company companyResponse) {
        Company newCompany = this.companyService.handleCreateCompany(companyResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCompany);
    }

    @PutMapping("companies/{id}")
    public ResponseEntity<Company> putCompany(@Valid @RequestBody Company newInfo) {
        Company updateCompany = this.companyService.handleUpdateCompany(newInfo);
        return ResponseEntity.ok(updateCompany);
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> updateCompany(@PathVariable("id") Long id) {
        this.companyService.handleDeleteCompany(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
