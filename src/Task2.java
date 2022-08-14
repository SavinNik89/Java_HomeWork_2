public class Task2 {
    public static void main (String [] args){
        String str = "aaaabbbcccccddfffllhhjjbbbbb";
        System.out.println(str);
        char [] chars = str.toCharArray();
        char symbol = chars[0];
        int count = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i<chars.length; i++) {
            if (chars[i] == symbol) {
                count++;
            }
            else {
                stringBuilder
                        .append(symbol)
                        .append(count);
                symbol = chars[i];
                count = 1;
            }
            if (i == chars.length-1){
                stringBuilder
                        .append(symbol)
                        .append(count);
            }
        }
        System.out.println(stringBuilder.toString());
    }
}
