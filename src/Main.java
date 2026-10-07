// 1. УСІ імпорти розміщуються суворо НА ПОЧАТКУ файлу
import java.util.Scanner;
import java.util.Objects;

// 2. Назва public класу збігається з назвою файлу Main.java
public class Main {
    public static void main(String[] args) {
        System.out.println("=== ЗАВДАННЯ 1 ===");
        Task1_Demo.run();

        System.out.println("\n=== ЗАВДАННЯ 2 ===");
        Task2_Demo.run();

        System.out.println("\n=== ЗАВДАННЯ 3 ===");
        Task3_Demo.run();
    }
}

// ------------------- ЗАВДАННЯ 1 -------------------
class Trial {
    private String name;
    private int maxScore;

    public Trial(String name, int maxScore) {
        this.name = name;
        this.maxScore = maxScore;
    }

    public String getName() { return name; }
    public int getMaxScore() { return maxScore; }

    public void show() {
        System.out.println("Випробування: " + name + " | Макс. бал: " + maxScore);
    }
}

class Test extends Trial {
    private int questionsCount;

    public Test(String name, int maxScore, int questionsCount) {
        super(name, maxScore);
        this.questionsCount = questionsCount;
    }

    @Override
    public void show() {
        System.out.println("Тест: " + getName() + " | Макс. бал: " + getMaxScore() +
                " | Кількість питань: " + questionsCount);
    }
}

class Exam extends Trial {
    private String examiner;

    public Exam(String name, int maxScore, String examiner) {
        super(name, maxScore);
        this.examiner = examiner;
    }

    public String getExaminer() { return examiner; }

    @Override
    public void show() {
        System.out.println("Іспит: " + getName() + " | Макс. бал: " + getMaxScore() +
                " | Екзаменатор: " + examiner);
    }
}

class FinalExam extends Exam {
    private String qualification;

    public FinalExam(String name, int maxScore, String examiner, String qualification) {
        super(name, maxScore, examiner);
        this.qualification = qualification;
    }

    @Override
    public void show() {
        System.out.println("Випускний іспит: " + getName() + " | Макс. бал: " + getMaxScore() +
                " | Екзаменатор: " + getExaminer() + " | Рівень: " + qualification);
    }
}

class Task1_Demo {
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть кількість випробувань: ");
        int n = Integer.parseInt(scanner.nextLine());
        Trial[] trials = new Trial[n];

        for (int i = 0; i < n; i++) {
            System.out.println("\nТип (#" + (i + 1) + "): 1-Випробування, 2-Тест, 3-Іспит, 4-Випускний іспит");
            int type = Integer.parseInt(scanner.nextLine());
            System.out.print("Назва: ");
            String name = scanner.nextLine();
            System.out.print("Макс. бал: ");
            int score = Integer.parseInt(scanner.nextLine());

            switch (type) {
                case 2 -> {
                    System.out.print("Кількість питань: ");
                    int q = Integer.parseInt(scanner.nextLine());
                    trials[i] = new Test(name, score, q);
                }
                case 3 -> {
                    System.out.print("Викладач: ");
                    String ex = scanner.nextLine();
                    trials[i] = new Exam(name, score, ex);
                }
                case 4 -> {
                    System.out.print("Викладач: ");
                    String ex = scanner.nextLine();
                    System.out.print("Рівень: ");
                    String qf = scanner.nextLine();
                    trials[i] = new FinalExam(name, score, ex, qf);
                }
                default -> trials[i] = new Trial(name, score);
            }
        }

        System.out.println("\n--- ВВЕДЕНИЙ МАСИВ ---");
        for (Trial t : trials) {
            t.show();
        }
    }
}

// ------------------- ЗАВДАННЯ 2 -------------------
abstract class Triad {
    protected int first;
    protected int second;
    protected int third;

    public Triad(int first, int second, int third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public abstract void increment();
    public abstract void printValue();
}

class DateClass extends Triad {
    public DateClass(int day, int month, int year) {
        super(day, month, year);
    }

    @Override
    public void increment() {
        first++;
        int[] daysInMonths = {31, (isLeapYear(third) ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (first > daysInMonths[second - 1]) {
            first = 1;
            second++;
            if (second > 12) {
                second = 1;
                third++;
            }
        }
    }

    private boolean isLeapYear(int y) {
        return (y % 4 == 0 && y % 100 != 0) || (y % 400 == 0);
    }

    @Override
    public void printValue() {
        System.out.printf("Дата: %02d.%02d.%04d\n", first, second, third);
    }

    @Override
    public String toString() {
        return String.format("Date{day=%02d, month=%02d, year=%04d}", first, second, third);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof DateClass date)) return false;
        return first == date.first && second == date.second && third == date.third;
    }
}

class TimeClass extends Triad {
    public TimeClass(int hour, int minute, int second) {
        super(hour, minute, second);
    }

    @Override
    public void increment() {
        third++;
        if (third >= 60) {
            third = 0;
            second++;
            if (second >= 60) {
                second = 0;
                first++;
                if (first >= 24) {
                    first = 0;
                }
            }
        }
    }

    @Override
    public void printValue() {
        System.out.printf("Час: %02d:%02d:%02d\n", first, second, third);
    }

    @Override
    public String toString() {
        return String.format("Time{hour=%02d, minute=%02d, second=%02d}", first, second, third);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TimeClass time)) return false;
        return first == time.first && second == time.second && third == time.third;
    }
}

class Task2_Demo {
    public static void run() {
        Triad[] triads = new Triad[] {
                new DateClass(31, 12, 2024),
                new TimeClass(23, 59, 59)
        };

        for (Triad t : triads) {
            t.printValue();
            t.increment();
            t.printValue();
            System.out.println(t);
        }
    }
}

// ------------------- ЗАВДАННЯ 3 -------------------
interface ITriad {
    void increment();
    void printValue();
}

class DateInterface implements ITriad {
    private int day, month, year;

    public DateInterface(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public void increment() {
        day++;
        int[] daysInMonths = {31, (isLeapYear(year) ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (day > daysInMonths[month - 1]) {
            day = 1;
            month++;
            if (month > 12) {
                month = 1;
                year++;
            }
        }
    }

    private boolean isLeapYear(int y) {
        return (y % 4 == 0 && y % 100 != 0) || (y % 400 == 0);
    }

    @Override
    public void printValue() {
        System.out.printf("Дата (інтерфейс): %02d.%02d.%04d\n", day, month, year);
    }

    @Override
    public String toString() {
        return String.format("Date{day=%02d, month=%02d, year=%04d}", day, month, year);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof DateInterface date)) return false;
        return day == date.day && month == date.month && year == date.year;
    }
}

class Task3_Demo {
    public static void run() {
        ITriad[] items = new ITriad[] {
                new DateInterface(31, 12, 2025)
        };

        for (ITriad item : items) {
            item.printValue();
            item.increment();
            item.printValue();
        }
    }
}