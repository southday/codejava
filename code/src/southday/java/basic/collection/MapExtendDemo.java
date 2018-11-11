package southday.java.basic.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/* Map 扩展知识
 * Map 集合被使用是因为它具备映射关系，
 * 
 * 问题描述：【用集合嵌套的方式来实现】：一个年级对应多个班级，一个班级对应多个学生
 * （1）一个班里面有学生，学生有学号，姓名属性，学号作为key
 * （2）该年级有两个班：Stu1班，Stu2班
 * 
 * 实际开发中，学生的学号，姓名等往往被封装为一个学生对象。
 * 这时候就不需要使用Map嵌套，保存学生对象可以使用一个List集合来搞定Collection
 */
class MapStu {
    private String stuNo;
    private String stuName;
    
    MapStu(String stuNo, String stuName) {
        this.stuName = stuName;
        this.stuNo = stuNo;
    }
    
    public String getStuNo() {
        return this.stuNo;
    }
    public String getStuName() {
        return this.stuName;
    }
}

public class MapExtendDemo {
    public static void main(String[] args) {
        System.out.println("使用Map集合嵌套方法实现：");
        MapNest_Method();
         System.out.println("-------------------");
        System.out.println("将学生封装为一个对象，非Map嵌套的实现：");
        MapUnNest_Method();
        System.out.println("-------------------");
    }
    
    // 采用非嵌套的方式来实现，将学生封装为一个类
    public static void MapUnNest_Method() {
        HashMap<String, ArrayList<MapStu>> Grade = new HashMap<String, ArrayList<MapStu>>();    //创建年级
        ArrayList<MapStu> Stu1 = new ArrayList<MapStu>();    //创建班级
        ArrayList<MapStu> Stu2 = new ArrayList<MapStu>();
        
        Stu1.add(new MapStu("stu1-01", "stu1-孙一"));
        Stu1.add(new MapStu("stu1-02", "stu1-钱二"));
        Stu1.add(new MapStu("stu1-03", "stu1-张三"));
        
        Stu2.add(new MapStu("stu2-04", "stu2-李四"));
        Stu2.add(new MapStu("stu2-05", "stu2-王五"));
        Stu2.add(new MapStu("stu2-06", "stu2-赵六"));
        
        Grade.put("1班",Stu1);
        Grade.put("2班",Stu2);
        
        Print_Grade2(Grade);
    }
    // 输出信息
    public static void Print_Grade2(HashMap<String, ArrayList<MapStu>> grade) {
        String gradeNo;
        ArrayList<MapStu> al;
        MapStu ms;
        Set<String> keySet = grade.keySet();
        Iterator<String> it = keySet.iterator();
        while(it.hasNext()) {
            gradeNo = it.next();
            al = grade.get(gradeNo);
            System.out.println(gradeNo + ":");
            Iterator<MapStu> it2 = al.iterator();
            while(it2.hasNext()) {
                ms = it2.next();
                System.out.println("<" + ms.getStuNo() + "," + ms.getStuName() + ">");
            }
        }
    }
    
    
    // 采用集合嵌套的方式来实现：一个年级对应多个班级，一个班级对应多个学生
    public static void MapNest_Method() {
        HashMap<String, String> Stu1 = new HashMap<String, String>();
        Stu1.put("stu1-01", "stu1-孙一");
        Stu1.put("stu1-02", "stu1-钱二");
        Stu1.put("stu1-03", "stu1-张三");
        
        HashMap<String, String> Stu2 = new HashMap<String, String>();
        Stu2.put("stu2-04", "stu2-李四");
        Stu2.put("stu2-05", "stu2-王五");
        Stu2.put("stu2-06", "stu2-赵六");
        
        HashMap<String, HashMap<String, String>> Grade = new HashMap<String, HashMap<String, String>>();
        Grade.put("1班", Stu1);
        Grade.put("2班", Stu2);
        
        Print_Grade(Grade);
    }
    // 遍历【班级】集合，获取所有学生信息
    public static void Print_Stu(HashMap<String, String> StuMap) {
        String stuNo,stuName;
        Set<HashMap.Entry<String, String>> entrySet = StuMap.entrySet();
        Iterator<HashMap.Entry<String, String>> it = entrySet.iterator();
        while(it.hasNext()) {
            HashMap.Entry<String, String> smap = it.next();
            stuNo = smap.getKey();
            stuName = smap.getValue();
            System.out.println("<" + stuNo + "," + stuName + ">");
        }
    }
    // 遍历 【年级】集合，获取所有班级信息
    public static void Print_Grade(HashMap<String, HashMap<String, String>> GradeMap) {
        String gradeNo;
        Set<HashMap.Entry<String, HashMap<String, String>>> entrySet = GradeMap.entrySet();
        Iterator<HashMap.Entry<String, HashMap<String, String>>> it = entrySet.iterator();
        while(it.hasNext()) {
            HashMap.Entry<String, HashMap<String, String>> gmap = it.next();
            gradeNo = gmap.getKey();
            System.out.println(gradeNo + ":");
            Print_Stu(gmap.getValue());
        }
    }
}
