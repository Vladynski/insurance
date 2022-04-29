package kp.bahmatov.insurance.service.settingspredicates;


public class NotLessZero extends SettingPredicate {
    @Override
    public String check(String s) {
        double value = Double.parseDouble(s);
        if (value < 0)
            return "Значение не может быть меньше нуля";
        return null;
    }
}
