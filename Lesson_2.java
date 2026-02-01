import java.util.Arrays;

public class Lesson_2 {


        public static void main(String[] args) {

            printThreeWords();
            checkSumSign();
            printColor();
            compareNumbers();
            System.out.println(checkSumInRange(11,9));
            checkNumberSign(11);
            System.out.println(NegativeNumberChecker(-11));
            printStringMultipleTimes("Hello!",3);
	        System.out.println(checkYear(2024));
	        BinaryArrayConverter();
	        FillArray();
 	        SimpleSolution();
	        SimpleDiagon();


        }

        public static void printThreeWords() {
            System.out.println("Orange");
            System.out.println("Banana");
            System.out.println("Apple");
       
        }

    public static void checkSumSign() {
        int a = 5;
        int b = 8;

        int sum = a + b;

        if (sum >= 0) {
            System.out.println("Сумма положительная");
        } else {
            System.out.println("Сумма отрицательная");
        }
    }

    public static void printColor() {

        int value = 118;
        if (value <= 0) {
            System.out.println("Красный");
        }
        else if (value > 0 && value <= 100) {
            System.out.println("Желтый");
        }
        else {
            System.out.println("Зеленый");
        }
    }

    public static void compareNumbers() {

        int a = 15;
        int b = 100;

        if (a >= b) {
            System.out.println("a >= b");
        } else {
            System.out.println("a < b");
        }
    }

          public static boolean checkSumInRange(int a, int b) {

        int sum = a + b;

        if (sum >= 10 && sum <= 20) {
            return true;
        } else {
            return false;
        }
    }

        public static void checkNumberSign(int a) {

        int b = a;

            if (b >= 0) {
                System.out.println(a + " - положительное число");
            } else {
                System.out.println(a + " - отрицательное число");
            }
        }

	public static boolean NegativeNumberChecker(int a) {
        int b = a;

            if (b < 0) {
                return true;
            } else {
                return false;
            }

    }
    {
        printStringMultipleTimes("Hello!",3);
    }

    public static void printStringMultipleTimes(String text, int count) {

        if (count <= 0) {

            return;
        }
        for (int i = 0; i < count; i++) {
        }
    }

{
    System.out.println(checkYear(2024));
}
    public static boolean checkYear(int year) {
        if (year % 400 == 0) {
            return true;
        } else if (year % 100 == 0) {
            return false;
        } else if (year % 4 == 0) {
            return true;
        } else {
            return false;
        }
    }

{
        BinaryArrayConverter();
    }
    public static void BinaryArrayConverter() {
        int[] array = {1, 1, 0, 0, 1, 0, 1, 1, 0, 0};

        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                array[i] = 1;
            } else {
                array[i] = 0;
            }
        }
        System.out.println(Arrays.toString(array));
    }

{
             FillArray();
        }
    public static void FillArray() {
            int[] arr = new int[100];
            for (int i = 0; i < 100; i++) {
            arr[i] = i;
    }
    System.out.println(Arrays.toString(arr));

        }
{
            SimpleSolution();
        }
        public static void SimpleSolution() {
            int[] arr = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};

            for (int i = 0; i < arr.length; i++) {
                if (arr[i] < 6) {
                    arr[i] = arr[i] * 2;
                }
            }

            for (int num : arr) {
                System.out.print(num + " ");
            }
        }
{
            SimpleDiagon();
        }
            public static void SimpleDiagon() {

            int n = 5;
            int[][] arr = new int[n][n];


            for (int i = 0; i < n; i++) {
                arr[i][i] = 1;
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    System.out.print(arr[i][j] + " ");
                }
                System.out.println();
            }
        }

    }

