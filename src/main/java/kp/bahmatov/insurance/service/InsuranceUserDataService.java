package kp.bahmatov.insurance.service;

import kp.bahmatov.insurance.domain.dto.insurance.userdata.InsuranceUserDataDto;
import kp.bahmatov.insurance.domain.structure.User;
import kp.bahmatov.insurance.domain.structure.insurance.userdata.InsuranceUserData;
import kp.bahmatov.insurance.domain.structure.insurance.userdata.InsuranceUserDataStatus;
import kp.bahmatov.insurance.domain.structure.insurance.content.Content;
import kp.bahmatov.insurance.repo.InsuranceDataRepo;
import kp.bahmatov.insurance.service.interfaces.Auth;
import org.springframework.stereotype.Service;

@Service
public class InsuranceUserDataService {
    private final Auth auth;
    private final InsuranceDataRepo insuranceRepo;

    public InsuranceUserDataService(Auth auth, InsuranceDataRepo insuranceRepo) {
        this.auth = auth;
        this.insuranceRepo = insuranceRepo;
    }

    public void updateInsuranceData(InsuranceUserDataDto requestInsurance) {
        User user = auth.getUser();

        InsuranceUserData insurance = user.getInsurance();
        insurance.setPhone(requestInsurance.getPhone());
        insurance.setPassportId(requestInsurance.getPassportId());
        insurance.setStatus(InsuranceUserDataStatus.CONFIRMED); //FIXME WAIT_CONFIRMATION

        Content content = new Content();
        content.setContent(requestInsurance.getPhoto().getContent());
        content.setType(requestInsurance.getPhoto().getType());

        insurance.setPhoto(content);

        insuranceRepo.saveAndFlush(insurance);
    }
}
