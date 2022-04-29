package kp.bahmatov.insurance.service.settingspredicates;

public class MoreZero extends SettingPredicate {
    @Override
    public String check(String check) {
        double value = Double.parseDouble(check);
        if (value <= 0)
            return "Значение должно быть больше нуля";
        return null;
    }
}
