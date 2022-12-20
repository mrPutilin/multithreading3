import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger threeLetters = new AtomicInteger(0);
    public static AtomicInteger fourLetters = new AtomicInteger(0);
    public static AtomicInteger fiveLetters = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        new Thread(() -> {
            for (String palindrom : texts) {
                if (palindrom.replaceAll("\\W", "")
                        .equalsIgnoreCase(new StringBuilder(palindrom.replaceAll("\\W", ""))
                                .reverse().toString()));
                if (palindrom.length() == 3) {
                    threeLetters.getAndIncrement();
                } else if (palindrom.length() == 4) {
                    fourLetters.getAndIncrement();
                } else {
                    fiveLetters.getAndIncrement();
                }
            }
        }).start();


        new Thread(() -> {
            for (String a : texts) {
                switch (a) {
                    case "aaa", "bbb", "ccc" -> threeLetters.getAndIncrement();
                    case "aaaa", "bbbb", "cccc" -> fourLetters.getAndIncrement();
                    case "aaaaa", "bbbbb", "ccccc" -> fiveLetters.getAndIncrement();
                }

            }
        }).start();


        new Thread(() -> {
            for (String a : texts) {
                if (a.contains("abc") && a.length() == 3)
                    threeLetters.getAndIncrement();
                else if (a.contains("abc") && a.length() == 4)
                    fourLetters.getAndIncrement();
                else if (a.contains("abc") && a.length() == 5)
                    fiveLetters.getAndIncrement();
            }
        }).start();


        System.out.printf("Кравсисых слов с длинной 3 : %d штук\n", threeLetters.get());
        System.out.printf("Красивых слов с длинной 4 : %d штук\n", fourLetters.get());
        System.out.printf("Красивых слов с длинной 4 %d штук\n", fiveLetters.get());

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

}
