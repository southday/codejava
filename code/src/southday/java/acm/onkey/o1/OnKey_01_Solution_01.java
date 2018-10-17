package southday.java.acm.onkey.o1;

/*
1.You're given strings J representing the types of stones that are jewels, and S representing the stones you have.  
Each character in S is a type of stone you have.  You want to know how many of the stones you have are also jewels. 
 
The letters in J are guaranteed distinct, and all characters in J and S are letters. 
Letters are case sensitive, so "a" is considered a different type of stone from "A". 
Example 
Input: J = "aA", S = "aAAbbbb" 
Output: 3

Input: J = "z", S = "ZZ" 
Output: 0

Note:S and J will consist of letters and have length at most 50.
The characters in J are distinct. 
*/

public class OnKey_01_Solution_01 {
    public static void main(String[] args) {
        String J = "aA";
        String S = "aAAbbbaA";
        System.out.println(foo(J, S));
    }
    
    public static int foo(String J, String S) {
        boolean[] map = new boolean[60];
        char[] jchs = J.toCharArray();
        for (int i = 0; i < jchs.length; i++)
            map[(int)jchs[i]-65] = true;
        int cnt = 0;
        char[] schs = S.toCharArray();
        for (int i = 0; i < schs.length; i++)
            if (map[(int)schs[i]-65] == true)
                cnt++;
        return cnt;
    }
}
