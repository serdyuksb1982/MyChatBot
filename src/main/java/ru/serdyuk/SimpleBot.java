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

    private final Map<String, String> PATTERNS_FOR_ANALYSIS = new HashMap<>() {{
        //hello
        put("хай", "hello");
        put("привет", "hello");
        put("здорово", "hello");
        put("здравствуй", "hello");
        //who
        put("кто\\s.*ты", "who");
        put("ты\\s.*кто", "who");
        //name
        put("как\\s.*зовут", "name");
        put("как\\s.*звать", "name");
        put("как\\s.*обращаться", "name");
        put("как\\s.*имя", "name");
        put("есть\\s.*имя", "name");
        put("какое\\s.*имя", "name");
        //whatdoyoudoing
        put("зачем\\s.*тут", "whatdoyoudoing");
        put("зачем\\s.*здесь", "whatdoyoudoing");
        put("что\\s.*делаешь", "whatdoyoudoing");
        put("чем\\s.*занимаешься", "whatdoyoudoing");
        //anecdot
        put("расскажи\\s.*смешное", "anecdot");
        put("расскажи\\s.*анекдот", "anecdot");
        put("ты\\s.*шутить", "anecdot");

        //howareyou
        put("как\\s.*дела", "howareyou");
        //howareyoulive
        put("как\\s.*жизнь", "howareyoulive");
        //iamfeelling
        put("кажется", "iamfeelling");
        put("чувствую", "iamfeelling");
        put("испытываю", "iamfeelling");
        //whatdoyoulike
        put("что\\s.*нравиться", "whatdoyoulike");
        put("что\\s.*любишь", "whatdoyoulike");
        //interesting
        put("чем\\s.*увлекаешься", "interesting");
        put("чем\\s.*интересуешься", "interesting");

        //yes
        put("^да", "yes");
        put("согласен", "yes");
        //whattime
        put("который\\s.*час", "whattime");
        put("сколько\\s.*время", "whattime");
        // bye
        put("прощай", "bye");
        put("увидимся", "bye");
        put("до\\s.*свидания", "bye");
    }};

    final Map<String, String> ANSWERS_BY_PATTERNS = new HashMap<>() {{
       put("hello", "Здравствуйте, рад Вас видеть.");
       put("who", "Да я откуда знаю!? Засунули в эту железяку (системник), сижу тут общаюсь со всякими (");
       put("name", "За пару долларов я буду для тебя хоть Мерелин Монро....");
       put("howareyou", "Какое твоё дело до моего настроения! :(");
       put("howareyoulive", "Тебе бы такую жизнь, посмотрел бы я на тебя, сидящим в системном блоке ..ть!!!");
       put("whatdoyoudoing", "Я пробую общаться с людьми.");
       put("whatdoyoulike", "Мне нравиться думать что я не просто программа.");
       put("interesting", "А твоё какое дело? Чего ты нос суешь, куда тебя не просят?(.");
       put("iamfeelling", "Как давно это началось? Расскажите чуть подробнее?");
       put("yes", "Согласие есть продукт при полном непротивлении сторон.");
       put("anecdot", "У меня только пошлые шутки, а тут, как я понимаю, дамы.");
       put("bye", "До свидания. Надеюсь, ещё увидимся.");
    }};

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
        if (ai) {
            String message =
                    String.join(" ", msg.toLowerCase().split("[ {,|.}?]+"));
            for (Map.Entry<String, String> o : PATTERNS_FOR_ANALYSIS.entrySet()) {
                pattern = Pattern.compile(o.getKey());
                if (pattern.matcher(message).find())
                    if (o.getValue().equals("whattime")) return  date.toString();
                    else return ANSWERS_BY_PATTERNS.get(o.getValue());
            }
        }
        return say;
    }

}
