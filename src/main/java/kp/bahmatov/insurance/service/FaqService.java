package kp.bahmatov.insurance.service;

import kp.bahmatov.insurance.domain.dto.FaqDto;
import kp.bahmatov.insurance.domain.structure.Faq;
import kp.bahmatov.insurance.repo.FaqRepo;
import kp.bahmatov.insurance.service.interfaces.Auth;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaqService {
    private final FaqRepo faqRepo;
    private final Auth auth;

    public FaqService(FaqRepo faqRepo, Auth auth) {
        this.faqRepo = faqRepo;
        this.auth = auth;
    }

    public List<Faq> getAll() {
        return faqRepo.findAll();
    }

    public void addFaq(FaqDto faqDto) {
        Faq faq = new Faq();
        faq.setQuestion(faqDto.getQuestion());
        faq.setAnswer(faqDto.getAnswer());
        faq.setAuthor(auth.getUser());
        faqRepo.save(faq);
    }
}
