package kp.bahmatov.insurance.util;

import kp.bahmatov.insurance.domain.util.setting.SettingType;

public class SettingTypeChecker {
    public static boolean check(SettingType type, String value) {
        try {
            switch (type) {
                case REAL -> Double.parseDouble(value);
                case BOOLEAN -> Boolean.parseBoolean(value);
                case INTEGER -> Integer.parseInt(value);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
