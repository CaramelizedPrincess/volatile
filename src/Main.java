import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final AtomicInteger length3 = new AtomicInteger(0);
    private static final AtomicInteger length4 = new AtomicInteger(0);
    private static final AtomicInteger length5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Thread thrd1 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                String str = texts[i];
                if (palindromic(str)) {
                    switch (str.length()) {
                        case 3:
                            length3.incrementAndGet();
                            break;
                        case 4:
                            length4.incrementAndGet();
                            break;
                        case 5:
                            length5.incrementAndGet();
                            break;
                    }
                }
            }
        });
        Thread thrd2 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                String str = texts[i];
                if (oneLetter(str)) {
                    switch (str.length()) {
                        case 3:
                            length3.incrementAndGet();
                            break;
                        case 4:
                            length4.incrementAndGet();
                            break;
                        case 5:
                            length5.incrementAndGet();
                            break;
                    }
                }
            }

        });
        Thread thrd3 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                String str = texts[i];
                if (complexity(str)) {
                    switch (str.length()) {
                        case 3:
                            length3.incrementAndGet();
                            break;
                        case 4:
                            length4.incrementAndGet();
                            break;
                        case 5:
                            length5.incrementAndGet();
                            break;
                    }
                }
            }
        });

        thrd1.start();
        thrd2.start();
        thrd3.start();

        thrd3.join();
        thrd2.join();
        thrd1.join();

        System.out.println("Красивых слов с длиной 3: " + length3 + " шт");
        System.out.println("Красивых слов с длиной 4: " + length4 + " шт");
        System.out.println("Красивых слов с длиной 5: " + length5 + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean palindromic(String str) {
        String strReverse = new StringBuilder(str).reverse().toString();
        return str.equals(strReverse);
    }

    public static boolean oneLetter(String str) {
        char chr = str.charAt(0);
        return str.chars().allMatch(x -> x == chr);
    }

    public static boolean complexity(String str) {
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        String strRes = new String(chars);
        return strRes.equals(str);
    }
}

