package kp.bahmatov.insurance.service;

import kp.bahmatov.insurance.domain.dto.insurance.userdata.InsuranceUserDataDto;
import kp.bahmatov.insurance.domain.structure.User;
import kp.bahmatov.insurance.domain.structure.insurance.content.Content;
import kp.bahmatov.insurance.domain.structure.insurance.content.ContentType;
import kp.bahmatov.insurance.domain.structure.insurance.userdata.InsuranceUserData;
import kp.bahmatov.insurance.domain.structure.insurance.userdata.InsuranceUserDataStatus;
import kp.bahmatov.insurance.exceptions.BadRequestException;
import kp.bahmatov.insurance.repo.InsuranceUserDataRepo;
import kp.bahmatov.insurance.service.interfaces.Auth;
import org.springframework.stereotype.Service;

@Service
public class InsuranceUserDataService {
    private final InsuranceUserDataRepo insuranceRepo;
    private final Auth auth;

    public InsuranceUserDataService(Auth auth,
                                    InsuranceUserDataRepo insuranceRepo) {
        this.auth = auth;
        this.insuranceRepo = insuranceRepo;
    }

    public void updateInsuranceData(InsuranceUserDataDto requestInsurance) {
        if (insuranceRepo.findByPassportId(requestInsurance.getPassportId()).isPresent())
            throw new BadRequestException("Паспорт уже зарегистрирован");

        User user = auth.getUser();

        InsuranceUserData insurance = user.getInsuranceData();
        insurance.setPhone(requestInsurance.getPhone());
        insurance.setPassportId(requestInsurance.getPassportId());
        insurance.setStatus(InsuranceUserDataStatus.WAIT_CONFIRMATION);

        Content content = new Content();
        content.setContent(requestInsurance.getPhoto().getContent());
        ContentType contentType = ContentType.findByString(requestInsurance.getPhoto().getType());
        if (contentType == null)
            throw new BadRequestException("Неправильный формат типа контента");
        content.setType(contentType);

        insurance.setPhoto(content);

        insuranceRepo.saveAndFlush(insurance);
    }
}
