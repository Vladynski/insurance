package kp.bahmatov.insurance.service.settingspredicates;

import lombok.Getter;

public abstract class SettingPredicate {
    @Getter
    private final Type type;

    public SettingPredicate() {
        this.type = Type.FOR_VALUE;
    }

    public SettingPredicate(Type type) {
        this.type = type;
    }

    /**
     * Проверка считается пройденной, если метод вернул true,
     * иначе, метод возвращает сообщение ошибки
     *
     * @param check значение настройки для проверки
     * @return null - если проверка пройдена, String message - сообщение, почему проверка не прошла
     */
    public abstract String check(String check);

    public enum Type {
        FOR_VALUE, FOR_DESCRIPTION
    }
}
