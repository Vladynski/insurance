package kp.bahmatov.insurance.service;

import kp.bahmatov.insurance.domain.structure.User;
import kp.bahmatov.insurance.domain.util.Confirmer;
import kp.bahmatov.insurance.exceptions.ForbiddenException;
import kp.bahmatov.insurance.repo.ConfirmerRepo;
import kp.bahmatov.insurance.service.interfaces.Auth;
import kp.bahmatov.insurance.util.CodeGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConfirmerService {
    private final int generateConfirmerMinPeriod;
    private final Auth auth;
    private final ConfirmerRepo confirmerRepo;

    public ConfirmerService(Auth auth,
                            ConfirmerRepo confirmerRepo) {
        this.auth = auth;
        this.confirmerRepo = confirmerRepo;
        generateConfirmerMinPeriod = SettingsService.getConfirmerGenerateMinPeriodMinutes().getIntValue();
    }

    public void throwExceptionIfItIsNotAuthUserCode(String code) {
        if (!isItAuthUserCode(code))
            throw new ForbiddenException("Неверный код подтверждения");
    }

    public boolean isItAuthUserCode(String code) {
        Confirmer byOwner = getMyConfirmer();

        if (byOwner == null)
            return false;

        if (LocalDateTime.now().isAfter(byOwner.getDeadline())) {
            confirmerRepo.delete(byOwner);
            return false;
        }

        if (byOwner.getCode().equals(code)) {
            confirmerRepo.delete(byOwner);
            return true;
        }

        return false;
    }

    public boolean canIGenerateNewCode() {
        Confirmer confirmer = getMyConfirmer();
        if (confirmer == null)
            return true;
        return ifLifetimeLongerThanMinutesGetCode(confirmer, generateConfirmerMinPeriod) == null;
    }

    public Confirmer getMyConfirmer() {
        return confirmerRepo.findByOwner(auth.getUser());
    }

    private String ifLifetimeLongerThanMinutesGetCode(Confirmer confirmer, int minutes) {
        return confirmer != null && LocalDateTime.now().plusMinutes(minutes).isBefore(confirmer.getDeadline()) ? confirmer.getCode() : null;
    }

    public String generateNewCodeForMe() {
        return generateNewCodeForMe(
                SettingsService.getConfirmerLifetimeMinutes().getIntValue(),
                SettingsService.getConfirmerLifetimeHours().getIntValue());
    }

    public String generateNewCodeForMe(int lifetimeMinutes, int lifetimeHours) {
        User owner = auth.getUser();

        Confirmer byOwner = getMyConfirmer();

        String oldCode = ifLifetimeLongerThanMinutesGetCode(byOwner, generateConfirmerMinPeriod);
        if (oldCode != null)
            return oldCode;

        if (byOwner != null)
            confirmerRepo.delete(byOwner);

        Confirmer confirmer = new Confirmer();
        confirmer.setCode(CodeGenerator.generateCode());
        confirmer.setOwner(owner);
        LocalDateTime deadline = LocalDateTime.now().plusMinutes(lifetimeMinutes).plusHours(lifetimeHours);
        confirmer.setDeadline(deadline);

        confirmerRepo.save(confirmer);

        return confirmer.getCode();
    }
}
