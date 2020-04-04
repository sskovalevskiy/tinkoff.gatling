package unit;

//# есть строка 2as3(kd2(ab)) -> aaskdababkdababkdabab на любом удобном ЯП реализовать адгоритм из задания

public class StringParser {
    public static void main(String[] args) {
        String str = "2as3(kd2(ab))";

        System.out.println("aaskdababkdababkdabab".equals(parse(str)));
    }

    static String parse(String string){
        StringBuilder str = new StringBuilder(string);
        String num = "";

        for (int i = 0; i < str.length(); i++){
            if (Character.isLetter(str.charAt(i)))  continue;
            while (Character.isDigit(str.charAt(i))){
                num += str.charAt(i);
                str.deleteCharAt(i);
            }
            if (Character.isLetter(str.charAt(i))) {
                String insertion = insertion(Integer.parseInt(num), String.valueOf(str.charAt(i)));
                str.insert(i, insertion);
            } else {
                int bracketsCounter = 1;
                str.deleteCharAt(i);
                int closeBracketIndex = i;
                while (bracketsCounter != 0){
                    if (str.charAt(closeBracketIndex) == '(') bracketsCounter++;
                    if (str.charAt(closeBracketIndex) == ')') bracketsCounter--;
                    if (bracketsCounter != 0) closeBracketIndex++;
                }
                String insertion = insertion(Integer.parseInt(num), str.substring(i, closeBracketIndex));
                str.deleteCharAt(closeBracketIndex);
                str.insert(i, insertion);
            }
            num = "";
        }


        return  str.toString();
    }

    static String insertion(int num, String string){
        StringBuilder str = new StringBuilder("");
        for (int i = 1; i < num; i++){
            str.append(string);
        }
        return str.toString();
    }
}
