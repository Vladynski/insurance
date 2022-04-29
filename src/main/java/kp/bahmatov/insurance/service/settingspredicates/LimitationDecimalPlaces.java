package kp.bahmatov.insurance.service.settingspredicates;

public class LimitationDecimalPlaces extends SettingPredicate {
    private final int decimalPlacesCount;

    public LimitationDecimalPlaces(int decimalPlacesCount) {
        this.decimalPlacesCount = decimalPlacesCount;
    }

    @Override
    public String check(String check) {
        String decimal = check.substring(check.indexOf('.') + 1);
        if (decimal.length() > decimalPlacesCount)
            return "Допустимое количество знаков после запятой: " + decimalPlacesCount;
        return null;
    }
}
