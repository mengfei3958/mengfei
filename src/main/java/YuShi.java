import java.util.Scanner;

public class YuShi {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int value = scanner.nextInt();
        if(value > 100 || value < 1){
            System.out.println(-1);
            return;
        }
        int count = 0;
        for(;value > 6;){
            if(value % 8 == 0){
               count += value / 8;
               value = 0;
               break;
            } else if(value % 6 == 0){
                count += value / 6;
                value = 0;
                break;
            } else if(value > 8){
                value -= 8;
                count += 1;
            } else{
                value -= 6;
                count += 1;
            }
        }
        if(value == 0){
            System.out.println(count);
        } else{
            System.out.println(-1);
        }
    }
}
