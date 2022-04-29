package kp.bahmatov.insurance.service;

import kp.bahmatov.insurance.domain.dto.SettingsDto;
import kp.bahmatov.insurance.domain.util.setting.Setting;
import kp.bahmatov.insurance.domain.util.setting.SettingStructure;
import kp.bahmatov.insurance.exceptions.BadRequestException;
import kp.bahmatov.insurance.exceptions.NotFoundException;
import kp.bahmatov.insurance.exceptions.validation.CustomValidationException;
import kp.bahmatov.insurance.repo.SettingsRepo;
import kp.bahmatov.insurance.service.settingspredicates.*;
import kp.bahmatov.insurance.service.settingspredicates.SettingPredicate.Type;
import kp.bahmatov.insurance.util.DefaultStructureJsonParser;
import kp.bahmatov.insurance.util.SettingTypeChecker;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class SettingsService {
    private static final Logger logger = LoggerFactory.getLogger(SettingsService.class);

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
    @Getter
    private static SettingWrapper insuranceContractText;


    private SettingsService(SettingsRepo settingsRepo,
                            @Value("${default.settings.json.file.path}") String defaultSettingsFilePath) {
        SettingsService.settingsRepo = settingsRepo;
        SettingsService.defaultSettingsFilePath = defaultSettingsFilePath;
        fillingDbIfEmpty();
        fillingSettingsMap();
    }

    private static void fillingDbIfEmpty() {
        if (settingsRepo.count() == 0) {
            try {
                if (defaultSettingsFilePath != null) {
                    SettingStructure[] settingStructure = DefaultStructureJsonParser.parse(defaultSettingsFilePath, SettingStructure[].class);
                    List<SettingStructure> settings = Arrays.asList(settingStructure);
                    settingsRepo.saveAll(settings);
                }
            } catch (IOException e) {
                logger.error("Не удалось загрузить настройки");
                throw new RuntimeException(e);
            }
        }
    }


    private static void fillingSettingsMap() {
        confirmerGenerateMinPeriodMinutes = SettingWrapper.create(findByKey("confirmerGenerateMinPeriodMinutes"), new NotLessZero());
        confirmerLifetimeMinutes = SettingWrapper.create(findByKey("confirmerLifetimeMinutes"), new NotLessZero());
        confirmerLifetimeHours = SettingWrapper.create(findByKey("confirmerLifetimeHours"), new NotLessZero());
        mailSendTimeoutSeconds = SettingWrapper.create(findByKey("mailSendTimeoutSeconds"), new NotLessZero());
        baseSum = SettingWrapper.create(findByKey("baseSum"), new MoreZero(), new LimitationDecimalPlaces(2));
        sendQuestionTimeout = SettingWrapper.create(findByKey("sendQuestionTimeout"), new NotLessZero());
        insuranceContractText = SettingWrapper.create(findByKey("insuranceContractText"), new NotEmptyText(), new HasVersion());
    }

    public static List<SettingStructure> getAll() {
        return SettingWrapper.getAll();
    }

    public static void updateSetting(SettingsDto settingsDto) {
        SettingWrapper editWrapper = SettingWrapper.settings.get(settingsDto.getKey());
        if (editWrapper == null)
            throw new NotFoundException("Настройка с таким ключом не найдена");

        if (settingsDto.getDescription() != null) {
            if (!settingsDto.getDescription().isBlank()) {
                editWrapper.setDescription(settingsDto.getDescription());
            } else {
                throw new BadRequestException("Описание не может быть пустым");
            }
        }
        if (settingsDto.getValue() != null) {
            editWrapper.setValue(settingsDto.getValue());
        }
    }

    private static SettingStructure findByKey(String key) {
        return settingsRepo.findById(key).orElseThrow(() -> new RuntimeException("Не удалось найти настройку с ключом: " + key));
    }

    public record SettingWrapper(SettingStructure setting, SettingPredicate[] predicates) implements Setting {
        private static final Map<String, SettingWrapper> settings = new HashMap<>();

        public static SettingWrapper create(SettingStructure structure,
                                            SettingPredicate... predicateList) {
            if (settings.containsKey(structure.getKey()))
                return settings.get(structure.getKey());
            SettingWrapper newWrapper = new SettingWrapper(structure, predicateList);
            settings.put(structure.getKey(), newWrapper);
            return newWrapper;
        }

        public static SettingWrapper create(SettingStructure structure) {
            return create(structure, new SettingPredicate[]{});
        }

        public static List<SettingStructure> getAll() {
            return settings.values().stream().map(SettingWrapper::getStructure).toList();
        }

        @Override
        public void setValue(String value) {
            if (SettingTypeChecker.check(setting.getType(), value)) {
                for (SettingPredicate predicate : predicates)
                    checkFieldUpdate(predicate, Type.FOR_VALUE, value);
                setting.setValue(value);
            } else {
                throw new BadRequestException("Неправильный формат данных");
            }
            SettingsService.settingsRepo.save(this.setting);
        }

        @Override
        public void setDescription(String description) {
            for (SettingPredicate predicate : predicates)
                checkFieldUpdate(predicate, Type.FOR_DESCRIPTION, description);
            setting.setDescription(description);
            SettingsService.settingsRepo.save(this.setting);
        }

        private void checkFieldUpdate(SettingPredicate predicate, Type fieldType, String value) {
            if (predicate.getType() == fieldType) {
                String message = predicate.check(value);
                if (message != null)
                    throw new CustomValidationException(message, fieldType == Type.FOR_VALUE ? "value" : "description");
            }
        }

        public SettingStructure getStructure() {
            return setting;
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

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (SettingWrapper) obj;
            return Objects.equals(this.setting, that.setting);
        }

        @Override
        public int hashCode() {
            return Objects.hash(setting);
        }
    }
}
