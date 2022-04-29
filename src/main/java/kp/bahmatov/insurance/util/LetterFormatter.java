package kp.bahmatov.insurance.util;

public class LetterFormatter {
    public static String getLetterWithCode(String code) {
        return String.format("""
                Код для подтверждения личности: <b>%s</b>
                """, code);
    }

    public static String getLetterWithQuestionAnswer(String questionText, String answerText) {
        return String.format("""
                Ваш вопрос:
                <b>%s</b>
                Ответ администратора:
                <b>%s</b>
                """, questionText, answerText);
    }

    public static String getLetterFromAdmin(String secondName, String firstName, String patronymic, String text) {
        return String.format("""
                Сообщение от администратора %s %s %s:
                
                %s
                """, secondName, firstName, patronymic, text);
    }
}
