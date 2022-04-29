package kp.bahmatov.insurance.repo;

import kp.bahmatov.insurance.domain.util.setting.SettingStructure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepo extends JpaRepository<SettingStructure, String> {
}
