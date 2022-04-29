package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.domain.dto.FaqDto;
import kp.bahmatov.insurance.service.FaqService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/faq")
public class FaqController {
    private final FaqService faqService;

    public FaqController(FaqService faqService) {
        this.faqService = faqService;
    }

    @GetMapping
    public List<FaqDto> getAll() {
        return faqService.getAll().stream().map(FaqDto::new).toList();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public int updateFaq(@Valid @RequestBody FaqDto faqDto) {
        return faqService.update(faqDto);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteFaq(@RequestParam("id") int id) {
        faqService.delete(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public int addFaq(@Valid @RequestBody FaqDto faqDto) {
        return faqService.add(faqDto);
    }
}
