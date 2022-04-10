package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.domain.dto.FaqDto;
import kp.bahmatov.insurance.service.FaqService;
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

    //FIXME only for admins
    @PostMapping
    public void addFaq(@Valid @RequestBody FaqDto faqDto) {
        faqService.addFaq(faqDto);
    }
}
