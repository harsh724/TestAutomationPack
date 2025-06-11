package rough;

import java.util.Arrays;
import java.util.List;



public class Rough {
    public int maxNum = 120;
    public static void main(String[] args) {

        /*String str = "my name is harsh";
        String[] arr = str.split(" ");
        List<String> lis = Arrays.asList(arr);
        System.out.println(lis);
        StringBuilder newString = new StringBuilder();

        for(String a : lis){
            a = reverse(a);
            newString.append(a).append(" ");
        }
        System.out.println(newString.toString().trim());*/
        String str1 = "harsh";
        StringBuilder str = new StringBuilder();
        str = new StringBuilder(str1);

    }
    public static String reverse(String s){
        int maxNum = 120;
        int len = s.length();
        StringBuilder newString= new StringBuilder();
        for(int i = len-1; i>=0; i--){
            newString.append(s.charAt(i));
        }
        return newString.toString();
    }
    void printMax(){
        int maxNum = 180;
        System.out.println(new Rough().maxNum);
    }

}
