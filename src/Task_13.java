
/*
RULES

expr : plusminus* EOF;
plusminus : multdiv (('+' | '-') multdiv)*;
multdiv : factor (('*' | '/') factor)*;
factor : NUMBER | '('expr')' | TRIG_NUMBER | CONSTANT;
*/

/*(2^3 * (10 / (5 - 3)))^(Sin(Pi))*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Task_13 {

    public static HashMap<String, Function> functionMap;


    public static void main(String args[]) {
        functionMap = getFunctionMap();

        String expText = "(2^3 * (10 / (5 - 3)))^(sin ( pi ))";
        System.out.println(expText);
        List<Lexeme> lexemes = lexAnalyze(expText);
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
        System.out.println(expr(lexemeBuffer));

    }

    public interface Function {
        Double apply (List<Double> args);
    }

    public static HashMap <String, Function> getFunctionMap(){
        HashMap<String, Function> functionTable = new HashMap<>();
        functionTable.put("sin", args -> {
            if (args.isEmpty()){
                throw new RuntimeException("No arguments for function sin");
            }
            double sin = Math.sin(args.get(0));
            return sin;
        });
       /* functionTable.put("pi", args -> {
            if (!args.isEmpty()){
                throw new RuntimeException("Wrong arguments for function pi");
            }
            double pi = Math.PI;
            return pi;
        });*/
        return functionTable;
    }



    public enum LexemeType {
        LEFT_BRACKET, RIGHT_BRACKET,
        OP_PLUS, OP_MINUS, OP_MULT, OP_DIV, OP_POW, OP_SIN, OP_COS, OP_TG, OP_CTG,
        NUMBER,NAME, COMMA, CONSTANT,
        EOF;
    }

    public static class Lexeme {
        LexemeType type;
        String value;

        public Lexeme(LexemeType type, String value) {
            this.type = type;
            this.value = value;
        }

        public Lexeme(LexemeType type, Character value) {
            this.type = type;
            this.value = value.toString();
        }

        @Override
        public String toString() {
            return "Lexeme{" +
                    "type=" + type +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    public static class LexemeBuffer {
        private int pos;
        public List<Lexeme> lexemes;

        public LexemeBuffer(List<Lexeme> lexemes) {
            this.lexemes = lexemes;
        }

        public Lexeme next() {
            return lexemes.get(pos++);
        }

        public void back() {
            pos--;
        }

        public int getPos() {
            return pos;
        }
    }

    public static List<Lexeme> lexAnalyze(String expText) {
        ArrayList<Lexeme> lexemes = new ArrayList<>();
        int pos = 0;
        while (pos < expText.length()) {
            char s = expText.charAt(pos);
            switch (s) {
                case '(':
                    lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, s));
                    pos++;
                    continue;
                case ')':
                    lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, s));
                    pos++;
                    continue;
                case '+':
                    lexemes.add(new Lexeme(LexemeType.OP_PLUS, s));
                    pos++;
                    continue;
                case '-':
                    lexemes.add(new Lexeme(LexemeType.OP_MINUS, s));
                    pos++;
                    continue;
                case '*':
                    lexemes.add(new Lexeme(LexemeType.OP_MULT, s));
                    pos++;
                    continue;
                case '/':
                    lexemes.add(new Lexeme(LexemeType.OP_DIV, s));
                    pos++;
                    continue;
                case '^':
                    lexemes.add(new Lexeme(LexemeType.OP_POW, s));
                    pos++;
                    continue;
                case ',':
                    lexemes.add(new Lexeme(LexemeType.COMMA, s));
                    pos++;
                    continue;
                case 'p':
                    lexemes.add(new Lexeme(LexemeType.CONSTANT, s));
                    pos++;
                    continue;
                default:
                    if (s <= '9' && s >= '0' || s == '.') {
                        StringBuilder sb = new StringBuilder();
                        do {
                            sb.append(s);
                            pos++;
                            if (pos >= expText.length()) break;
                            s = expText.charAt(pos);
                        } while (s <= '9' && s >= '0' || s == '.');
                        lexemes.add(new Lexeme(LexemeType.NUMBER, sb.toString()));
                    } else {
                        if (s != ' ') {
                            if (s >= 'a' && s <= 'z' || s >= 'A' && s <= 'Z') {
                                StringBuilder sb = new StringBuilder();
                                do {
                                    sb.append(s);
                                    pos++;
                                    if (pos >= expText.length()) break;
                                    s = expText.charAt(pos);
                                } while (s >= 'a' && s <= 'z' || s >= 'A' && s <= 'Z');

                                if (functionMap.containsKey(sb.toString())) {
                                    lexemes.add(new Lexeme(LexemeType.NAME, sb.toString()));
                                }
                            } else {
                                    throw new RuntimeException("Uncexpected character: " + s);
                                }
                           /* if (s >= 'a' && s <= 'z' || s >= 'A' && s <= 'Z') {
                                StringBuilder sb = new StringBuilder();
                                do {
                                    sb.append(s);
                                    pos++;
                                    if (pos >= expText.length()) break;
                                    s = expText.charAt(pos);
                                    } while (s >= 'a' && s <= 'z' || s >= 'A' && s <= 'Z');
                                if (sb.toString() == "pi") {
                                    lexemes.add(new Lexeme(LexemeType.CONSTANT, sb.toString()));
                                }
                            }*/
                            } else {
                            pos++;
                        }
                        }
                    }
            }
        lexemes.add(new Lexeme(LexemeType.EOF, ""));
        return lexemes;
    }

    public static double factor(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        switch (lexeme.type) {
            case NAME:
                lexemes.back();
                return func(lexemes);
            case OP_MINUS:
                double value = factor(lexemes);
                return -value;
            case NUMBER:
                return Double.parseDouble(lexeme.value);
            case CONSTANT:
                return Math.PI;
            case LEFT_BRACKET:
                value = expr(lexemes);
                lexeme = lexemes.next();
                if (lexeme.type != LexemeType.RIGHT_BRACKET) {
                    throw new RuntimeException("Unexpected token: " + lexeme.value + "at position: " + lexemes.getPos());
                }
                return value;
            default:
                throw new RuntimeException("Unexpected token: " + lexeme.value + "at position: " + lexemes.getPos());
        }
    }

    public static double multdiv(LexemeBuffer lexemes) {
        double value = factor(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case OP_DIV:
                    value /= factor(lexemes);
                    break;
                case OP_MULT:
                    value *= factor(lexemes);
                    break;
                case OP_POW:
                    value = Math.pow(value, factor(lexemes));
                    break;
                case EOF:
                case RIGHT_BRACKET:
                case OP_MINUS:
                case OP_PLUS:
                case COMMA:
                    lexemes.back();
                    return value;
                default:
                    throw new RuntimeException("Unexpected token: " + lexeme.value + " at position: " + lexemes.getPos());
            }

        }
    }

    public static double plusminus(LexemeBuffer lexemes) {
        double value = multdiv(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case OP_PLUS:
                    value += multdiv(lexemes);
                    break;
                case OP_MINUS:
                    value -= multdiv(lexemes);
                    break;
                case EOF:
                case RIGHT_BRACKET:
                case COMMA:
                    lexemes.back();
                    return value;
                default:
                    throw new RuntimeException("Unexpected token: " + lexeme.value + "at position: " + lexemes.getPos());
            }

        }
    }

    public static double expr(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        if (lexeme.type == LexemeType.EOF) {
            return 0;
        } else {
            lexemes.back();
            return plusminus(lexemes);
        }
    }

    public static double func (LexemeBuffer lexemeBuffer){
        String name = lexemeBuffer.next().value;
        Lexeme lexeme = lexemeBuffer.next();
        if (lexeme.type != LexemeType.LEFT_BRACKET){
            throw new RuntimeException("Wrong syntax at " + lexeme.value);
        }

        ArrayList<Double> args = new ArrayList<Double>();

        lexeme = lexemeBuffer.next();
        if (lexeme.type != LexemeType.RIGHT_BRACKET){
            lexemeBuffer.back();
            do {
                args.add(expr(lexemeBuffer));
                lexeme = lexemeBuffer.next();
                if (lexeme.type != LexemeType.COMMA && lexeme.type != LexemeType.RIGHT_BRACKET) {
                    throw new RuntimeException("Wrong syntax at " + lexeme.value);
                }

            } while (lexeme.type == LexemeType.COMMA);
        }
        return  functionMap.get(name).apply(args);
    }
}
