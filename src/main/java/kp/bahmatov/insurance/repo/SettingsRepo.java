package kp.bahmatov.insurance.repo;

import kp.bahmatov.insurance.domain.util.setting.SettingStructure;
import org.springframework.data.repository.CrudRepository;

public interface SettingsRepo extends CrudRepository<SettingStructure, String> {
}
