import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String S = scanner.nextLine();
            String L = scanner.nextLine();
            if(!S.equals("") && S.length() > 100){
                continue;
            }
            if(!L.equals("") && L.length() > 500000){
                continue;
            }
            char[] front = S.toCharArray();
            char[] end = L.toCharArray();
            int j = 0;
            StringBuilder result = new StringBuilder();
            for(int i = 0; i < S.length(); i++){
                char frontChar = front[i];
                for(; j < L.length(); j++){
                    char endChar = end[j];
                    if(frontChar != endChar){
                        continue;
                    } else {
                        result.append(frontChar);
                        j++;
                        break;
                    }
                }
            }
            if(S.equals(result.toString())){
                System.out.println(L.indexOf(front[S.length() - 1]));
            } else {
                System.out.println(-1);
            }
        }
    }

    public void test(){
        System.out.println(11);
        this.getValue();
    }

    public void getValue(){
        System.out.println(22);
    }
}
