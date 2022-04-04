package kp.bahmatov.insurance.util;

public class LetterFormatter {
    public static String getLetterWithCode(String code) {
        return String.format("""
                Код для подтверждения личности: <b>%s</b>
                """, code);
    }
}
