package kp.bahmatov.insurance.service;

import kp.bahmatov.insurance.domain.dto.FaqDto;
import kp.bahmatov.insurance.domain.structure.Faq;
import kp.bahmatov.insurance.exceptions.NotFoundException;
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

    public int add(FaqDto dto) {
        return faqRepo.save(parseFaq(dto)).getId();
    }

    public int update(FaqDto faqDto) {
        return faqRepo.save(parseFaq(faqDto)).getId();
    }

    private Faq parseFaq(FaqDto dto) {
        Faq faq = new Faq();

        if (dto.getId() != null)
            faq.setId(dto.getId());

        faq.setQuestion(dto.getQuestion());
        faq.setAnswer(dto.getAnswer());
        faq.setAuthor(auth.getUser());
        return faq;
    }

    public void delete(int id) {
        Faq faq = faqRepo.findById(id).orElseThrow(() -> new NotFoundException("Вопрос с таким id не найден"));
        faqRepo.delete(faq);
    }
}
