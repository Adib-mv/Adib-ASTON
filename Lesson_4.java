public class Lesson_4 {
    public static void main(String[] args) {

        Dog dog = new Dog("Барс");
        Cat cat1 = new Cat("Вася");
        Cat cat2 = new Cat("Оля");

        System.out.println("Собака " + dog.getName() + " ");
        dog.run(100);
        dog.run(700);
        dog.swim(2);
        dog.swim(11);

        System.out.println("\nКот " + cat1.getName() + " ");
        cat1.run(100);
        cat1.run(250);
        cat1.swim(1);

        System.out.println("\nКот " + cat2.getName() + " ");
        cat2.run(100);
        cat2.run(400);
        cat2.swim(1);

        Bowl bowl = new Bowl(15);
        System.out.println("\nПоявилась миска в ней " + bowl.getFoodAmount() + " единиц еды");

        Cat[] cats = {cat1, cat2};

        System.out.println("\nПервый корм (в миске 15 еды)");
        for (Cat cat : cats) {
            cat.eatFromBowl(bowl);
        }

        System.out.println("\nСостояние котов после первого корма");
        for (Cat cat : cats) {
            System.out.println(cat.getName() + ": " + (cat.isHungry() ? "голоден" : "сыт"));
        }
        System.out.println("Еды в миске осталось: " + bowl.getFoodAmount());

        System.out.println("\nДобавили еду");
        bowl.addFood(20);

        for (Cat cat : cats) {
            if (cat.isHungry()) {
                cat.eatFromBowl(bowl);
            }
        }

        System.out.println("\nСытость котиков: ");
        for (Cat cat : cats) {
            System.out.println(cat.getName() + ": " + (cat.isHungry() ? "голоден" : "сыт"));
        }
        System.out.println("Еды в миске осталось: " + bowl.getFoodAmount());

        System.out.println("\nВсего животных: " + Animal.getAnimalCount());
        System.out.println("Собак: " + Dog.getDogCount());
        System.out.println("Котов: " + Cat.getCatCount());

        //Задание 2.Фигуры

        try {
            Circle circle = new Circle(7.0, "Красный", "Черный");
            Rectangle rectangle = new Rectangle(5.0, 8.0, "Желтый", "Зеленый");
            Triangle triangle = new Triangle(4.0, 6.0, 8.0, "Белый", "Синий");

            System.out.println("\n1. Круг:");
            circle.printInfo();
            System.out.println("Диаметр круга: " + String.format("%.2f", circle.getDiameter()));

            System.out.println("\n2. Прямоугольник:");
            rectangle.printInfo();

            System.out.println("\n3. Треугольник:");
            triangle.printInfo();

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        GeometricShape[] shapes = {new Circle(10.0, "Красный", "Белый"), new Rectangle(4.0, 6.0, "Черный", "Белый"), new Triangle(5.0, 9.0, 9.0, "Синий", "Желтый")};

        System.out.println("\nинтерфейс:");
        for (int i = 0; i < shapes.length; i++) {
            System.out.println("\nфигура #" + (i + 1) + ":");
            shapes[i].printInfo();
        }
    }
}

abstract class Animal {
    private static int animalCount = 0;
    protected String name;

    public Animal(String name) {
        this.name = name;
        animalCount++;
    }

    public abstract void run(int distance);

    public abstract void swim(int distance);

    public static int getAnimalCount() {
        return animalCount;
    }

    public String getName() {
        return name;
    }
}

class Cat extends Animal {
    private static int catCount = 0;
    private final int MAX_RUN_DISTANCE = 200;
    private boolean isHungry = true;

    public Cat(String name) {
        super(name);
        catCount++;
    }

    @Override
    public void run(int distance) {
        if (distance <= MAX_RUN_DISTANCE) {
            System.out.println(name + " пробежал " + distance + " м.");
        } else {
            System.out.println(name + " не может пробежать " + distance + " м. Максимум: " + MAX_RUN_DISTANCE + " м.");
        }
    }

    @Override
    public void swim(int distance) {
        System.out.println(name + " не умеет плавать!");
    }

    public void eatFromBowl(Bowl bowl) {
        if (bowl.takeFood(13)) {
            isHungry = false;
            System.out.println(name + " покушал из миски и теперь сыт.");
        } else {
            System.out.println(name + " не смог покушать. Мало еды в миске.");
        }
    }

    public boolean isHungry() {
        return isHungry;
    }

    public static int getCatCount() {
        return catCount;
    }
}

class Dog extends Animal {
    private static int dogCount = 0;
    private final int MAX_RUN_DISTANCE = 500;
    private final int MAX_SWIM_DISTANCE = 10;

    public Dog(String name) {
        super(name);
        dogCount++;
    }

    @Override
    public void run(int distance) {
        if (distance <= MAX_RUN_DISTANCE) {
            System.out.println(name + " пробежал " + distance + " м.");
        } else {
            System.out.println(name + " не может пробежать " + distance + " м. Максимум: " + MAX_RUN_DISTANCE + " м.");
        }
    }

    @Override
    public void swim(int distance) {
        if (distance <= MAX_SWIM_DISTANCE) {
            System.out.println(name + " проплыл " + distance + " м.");
        } else {
            System.out.println(name + " не может проплыть " + distance + " м. Максимум: " + MAX_SWIM_DISTANCE + " м.");
        }
    }

    public static int getDogCount() {
        return dogCount;
    }
}

class Bowl {
    private int foodAmount;

    public Bowl(int initialFood) {
        this.foodAmount = Math.max(initialFood, 0);
    }

    public boolean takeFood(int amount) {
        if (amount <= 0) {
            return false;
        }

        if (foodAmount >= amount) {
            foodAmount -= amount;
            return true;
        }
        return false;
    }

    public void addFood(int amount) {
        if (amount > 0) {
            foodAmount += amount;
            System.out.println("В миску добавлено " + amount + " еды. Всего: " + foodAmount);
        }
    }

    public int getFoodAmount() {
        return foodAmount;
    }
}

//Фигуры
interface GeometricShape {
    double calculateArea();

    String getFillColor();

    String getBorderColor();

    default double calculatePerimeter() {
        return 0;
    }

    default void printInfo() {
        System.out.println("Площадь: " + String.format("%.2f", calculateArea()));
        System.out.println("Периметр: " + String.format("%.2f", calculatePerimeter()));
        System.out.println("Цвет заливки: " + getFillColor());
        System.out.println("Цвет границ: " + getBorderColor());
        System.out.println();
    }
}

class Circle implements GeometricShape {
    private double radius;
    private String fillColor;
    private String borderColor;

    public Circle(double radius, String fillColor, String borderColor) {
        this.radius = radius;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public String getFillColor() {
        return fillColor;
    }

    @Override
    public String getBorderColor() {
        return borderColor;
    }

    public double getDiameter() {
        return 2 * radius;
    }
}

class Rectangle implements GeometricShape {
    private double width;
    private double height;
    private String fillColor;
    private String borderColor;

    public Rectangle(double width, double height, String fillColor, String borderColor) {
        this.width = width;
        this.height = height;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (width + height);
    }

    @Override
    public String getFillColor() {
        return fillColor;
    }

    @Override
    public String getBorderColor() {
        return borderColor;
    }
}

class Triangle implements GeometricShape {
    private double sideA;
    private double sideB;
    private double sideC;
    private String fillColor;
    private String borderColor;

    public Triangle(double sideA, double sideB, double sideC, String fillColor, String borderColor) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    private boolean isValidTriangle(double a, double b, double c) {
        return a > 0 && b > 0 && c > 0 && a + b > c && a + c > b && b + c > a;
    }

    @Override
    public double calculateArea() {
        double p = calculatePerimeter() / 2;
        return Math.sqrt(p * (p - sideA) * (p - sideB) * (p - sideC));
    }

    @Override
    public double calculatePerimeter() {
        return sideA + sideB + sideC;
    }

    @Override
    public String getFillColor() {
        return fillColor;
    }

    @Override
    public String getBorderColor() {
        return borderColor;
    }
}