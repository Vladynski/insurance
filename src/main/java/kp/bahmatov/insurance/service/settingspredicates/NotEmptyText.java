package kp.bahmatov.insurance.service.settingspredicates;

public class NotEmptyText extends SettingPredicate {
    @Override
    public String check(String check) {
        if (check.isBlank())
            return "Текст не может быть пустым";
        return null;
    }
}
