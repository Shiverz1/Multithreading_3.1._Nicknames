import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static final AtomicInteger beatifullWordCount3 = new AtomicInteger(0);
    public static final AtomicInteger beatifullWordCount4 = new AtomicInteger(0);
    public static final AtomicInteger beatifullWordCount5 = new AtomicInteger(0);

    public static void main(String[] args) {
        String[] text = generateText(100_000);
        Thread thread3 = new Thread(() -> checkBeauty(text, 3));
        Thread thread4 = new Thread(() -> checkBeauty(text, 4));
        Thread thread5 = new Thread(() -> checkBeauty(text, 5));

        thread3.start();
        thread4.start();
        thread5.start();

        try {
            thread3.join();
            thread4.join();
            thread5.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        printResult();
    }

    public static String[] generateText(int count) {
        Random random = new Random();
        String[] texts = new String[count];
        for (int i = 0; i < count; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        return texts;
    }

    public static String generateText(String letters, int lenght) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < lenght; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isBeatiful(String text) {
        // проверка на палиндром
        if (isPalindrome(text)) {
            return true;
        }
        // проверка на однородность
        if (isHomogeneous(text)) {
            return true;
        }
        // проверка на возрастание
        if (isAscending(text)) {
            return true;
        }
        return false;
    }

    public static boolean isPalindrome(String text) {
        int left = 0;
        int right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static boolean isHomogeneous(String text) {
        char c = text.charAt(0);
        for (char ch : text.toCharArray()) {
            if (ch != c) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAscending(String text) {
        char prev = 'a';
        for (char ch : text.toCharArray()) {
            if (ch < prev) {
                return false;
            }
            prev = ch;
        }
        return true;
    }


    public static void checkBeauty(String[] texts, int lenght) {
        for (String text : texts) {
            if (text.length() == lenght && isBeatiful(text)) {
                if (lenght == 3) {
                    beatifullWordCount3.incrementAndGet();
                } else if (lenght == 4) {
                    beatifullWordCount4.incrementAndGet();
                } else if (lenght == 5) {
                    beatifullWordCount5.incrementAndGet();
                }
            }
        }
    }

    public static void printResult() {
        System.out.println("Красивых слов длинной 3: " + beatifullWordCount3.get() + " шт.");
        System.out.println("Красивых слов длинной 4: " + beatifullWordCount4.get() + " шт.");
        System.out.println("Красивых слов длинной 5: " + beatifullWordCount5.get() + " шт.");
    }
}
