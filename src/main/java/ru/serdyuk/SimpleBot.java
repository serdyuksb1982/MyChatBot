package ru.serdyuk;

import java.util.*;
import java.util.regex.*;
/**
 * @author Serdyuk S.B.
 */
public class SimpleBot {
    private final String[] COMMON_PHRASES = {
            "Нет ничего ценнее слов, сказанных к месту и ко времени.",
            "Порой молчание может сказать больше, нежели уйма слов.",
            "Перед тем как писать/говорить всегда лучше подумать.",
            "Вежливая и грамотная речь говорит о величии души.",
            "Приятно когда текст без орфографических ошибок.",
            "Многословие есть признак неупорядоченного ума.",
            "Слова могут ранить, но могут и исцелять",
            "Записывая слова, мы удваиваем их силу.",
            "Кто ясно мыслит, тот ясно излагает",
            "Боюсь Вы что-то не договариваете."
    };

    private final String[] ELUSIVE_ANSWERS = {
            "Вопрос непростой, прошу тайм-аут на раздумье.",
            "Не уверен, что располагаю такой информацией.",
            "Может лучше поговорим о чем-то другом?",
            "Простите, но это очень личный вопрос.",
            "Не уверен, что Вам понравиться ответ.",
            "Поверьте, я сам хотел бы это знать.",
            "Вы действительно хотите это знать?",
            "Уверен, Вы уже догадались сами.",
            "Зачем Вам такая информация?",
            "Давайте сохраним интригу."
    };

    Pattern pattern;
    Random random;
    Date date;

    public SimpleBot() {
        random = new Random();
        date = new Date();
    }

    String sayInReturn(String msg, boolean ai) {
        String say = (msg.trim().endsWith("?"))?
                ELUSIVE_ANSWERS[random.nextInt(ELUSIVE_ANSWERS.length)]:
                COMMON_PHRASES[random.nextInt(COMMON_PHRASES.length)];

        return say;
    }

}
