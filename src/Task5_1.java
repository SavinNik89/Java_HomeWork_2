import java.util.ArrayList;
import java.util.Arrays;

public class Task5_1 {

    static char[] equationGlobal;
    static ArrayList<Integer> indexesGlobal = new ArrayList<>();

    public static void main(String[] args) {
        String equation = "?2 +3? = ??2";
        ArrayList<Integer> indexes = new ArrayList<>();

        char[] equationChar = equation
                .replace(" ", "")
                .replace('+', '-')
                .replace('=', '-')
                .toCharArray();

        for (int i = 0; i < equationChar.length; i++) {
            if (equationChar[i] == '?')
                indexes.add(i);
        }

        equationGlobal = equationChar;
        indexesGlobal = indexes;

        generate(new int [indexes.size()], 0, 9);

    }

    private static void generate(int[] arrays, int index, int k) {
        if (index == arrays.length) {
            check(arrays);
            return;
        }
        for (int i = 0; i <= k; i++) {
            arrays[index] = i;
            generate(arrays, index + 1, k);
        }
    }

    private static void check(int[] arrays) {
        for (int i = 0; i < arrays.length; i++) {
            equationGlobal[indexesGlobal.get(i)] = Character.forDigit(arrays[i], 10);
        }
        String[] numbers = String.valueOf(equationGlobal).split("-");
        int a = Integer.parseInt(numbers[0]);
        int b = Integer.parseInt(numbers[1]);
        int c = Integer.parseInt(numbers[2]);
        if (a + b == c) {
            System.out.printf("%d + %d = %d", a, b, c);
            System.out.println();
        }
    }
}