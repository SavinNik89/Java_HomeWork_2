/*
Пример 1: a+(d*3) - истина
        Пример 2: [a+(1*3) - ложь
        Пример 3: [6+(3*3)] - истина
        Пример 4: {a}[+]{(d*3)} - истина
        Пример 5: <{a}+{(d*3)}> - истина
        Пример 6: {a+]}{(d*3)} - ложь
*/


import java.util.*;

public class Task_12 {
    public static void main(String args[]) {
        String exp = "[a+(1*3)";
        System.out.println(checkParenthesis(exp));

    }
    public static boolean checkParenthesis (String exp) {
        Map <Character, Character> parenthesis = new HashMap <>();
        parenthesis.put(')', '(');
        parenthesis.put(']', '[');
        parenthesis.put('>', '<');
        parenthesis.put('}', '{');
        Deque <Character> stack = new ArrayDeque <Character>();

        for (int i = 0; i<exp.length(); i++) {
            if (parenthesis.containsKey(exp.charAt(i))) {
                if (stack.isEmpty() || parenthesis.get(exp.charAt(i)) != stack.pollLast()) return false;
            }
            if (parenthesis.containsValue(exp.charAt(i))) {
                stack.addLast(exp.charAt(i));
            }
        }
        if (!stack.isEmpty()) return false;
        return true;
    }
}
