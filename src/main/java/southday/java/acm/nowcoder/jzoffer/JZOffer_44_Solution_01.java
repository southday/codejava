package southday.java.acm.nowcoder.jzoffer;

/* 翻转单词顺序列
题目描述
牛客最近来了一个新员工Fish，每天早晨总是会拿着一本英文杂志，写些句子在本子上。同事Cat对Fish写的内容颇感兴趣，
有一天他向Fish借来翻看，但却读不懂它的意思。例如，“student. a am I”。
后来才意识到，这家伙原来把句子单词的顺序翻转了，正确的句子应该是“I am a student.”。
Cat对一一的翻转这些单词顺序可不在行，你能帮助他么？
*/

public class JZOffer_44_Solution_01 {
    
    public String ReverseSentence(String str) {
        if (str == null || "".equals(str.trim())) return str;
        String[] array = str.split("[ |\\t|\\s]+");
        StringBuilder sb = new StringBuilder();
        for (int i = array.length-1; i >= 0; i--) {
            sb.append(array[i] + " ");
        }
        return sb.deleteCharAt(sb.length()-1).toString();
    }
    
    public static void main(String[] args) {
        JZOffer_44_Solution_01 o = new JZOffer_44_Solution_01();
        String str = "student. a am I";
        System.out.println(o.ReverseSentence(str));
    }
}
