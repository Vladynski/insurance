package kp.bahmatov.insurance.service.settingspredicates;

public class HasVersion extends SettingPredicate {
    public HasVersion() {
        super(Type.FOR_DESCRIPTION);
    }

    @Override
    public String check(String check) {
        if (!check.trim().matches(".+(v\\d+)$"))
            return "Укажите в конце версию vЧисло (v15)";
        return null;
    }
}
