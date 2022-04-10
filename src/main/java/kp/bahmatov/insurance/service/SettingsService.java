package kp.bahmatov.insurance.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kp.bahmatov.insurance.domain.util.setting.Setting;
import kp.bahmatov.insurance.domain.util.setting.SettingStructure;
import kp.bahmatov.insurance.repo.SettingsRepo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class SettingsService {
    private static SettingsRepo settingsRepo;

    private static String defaultSettingsFilePath;

    @Getter
    private static SettingWrapper confirmerGenerateMinPeriodMinutes;
    @Getter
    private static SettingWrapper confirmerLifetimeMinutes;
    @Getter
    private static SettingWrapper confirmerLifetimeHours;
    @Getter
    private static SettingWrapper mailSendTimeoutSeconds;
    @Getter
    private static SettingWrapper baseSum;
    @Getter
    private static SettingWrapper sendQuestionTimeout;


    private SettingsService(SettingsRepo settingsRepo,
                            @Value("${default.settings.json.file.path}") String defaultSettingsFilePath) {
        SettingsService.settingsRepo = settingsRepo;
        SettingsService.defaultSettingsFilePath = defaultSettingsFilePath;
        fillingDbIfEmpty();
        fillingSettingsMap();
    }

    @Deprecated
    //FIXME read and parse to util class
    private static void fillingDbIfEmpty() {
        if (settingsRepo.count() == 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                if (defaultSettingsFilePath != null) {
                    SettingStructure[] settingStructure = objectMapper.readValue(new File(defaultSettingsFilePath), SettingStructure[].class);
                    List<SettingStructure> settings = Arrays.asList(settingStructure);
                    settingsRepo.saveAll(settings);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private static void fillingSettingsMap() {
        confirmerGenerateMinPeriodMinutes = new SettingWrapper(settingsRepo.findById("confirmerGenerateMinPeriodMinutes").get());
        confirmerLifetimeMinutes = new SettingWrapper(settingsRepo.findById("confirmerLifetimeMinutes").get());
        confirmerLifetimeHours = new SettingWrapper(settingsRepo.findById("confirmerLifetimeHours").get());
        mailSendTimeoutSeconds = new SettingWrapper(settingsRepo.findById("mailSendTimeoutSeconds").get());
        baseSum = new SettingWrapper(settingsRepo.findById("baseSum").get());
        sendQuestionTimeout = new SettingWrapper(settingsRepo.findById("sendQuestionTimeout").get());
    }

    public record SettingWrapper(SettingStructure setting) implements Setting {
        @Override
        public void setValue(String value) {
            SettingsService.settingsRepo.save(this.setting);
            setting.setValue(value);
        }

        @Override
        public String getValue() {
            return setting.getValue();
        }

        public int getIntValue() {
            return Integer.parseInt(setting.getValue());
        }

        public double getDoubleValue() {
            return Double.parseDouble(setting.getValue());
        }

        public float getFloatValue() {
            return Float.parseFloat(setting.getValue());
        }

        public boolean getBooleanValue() {
            return Boolean.parseBoolean(setting.getValue());
        }
    }
}
