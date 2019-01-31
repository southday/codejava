package southday.java.thinkinginjava.c20.s02;

import southday.java.thinkinginjava.c20.s02.dbanno.Constraints;
import southday.java.thinkinginjava.c20.s02.dbanno.DBTable;
import southday.java.thinkinginjava.c20.s02.dbanno.SQLInteger;
import southday.java.thinkinginjava.c20.s02.dbanno.SQLString;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 一个简单的注解处理器，根据输入的类来生成对应的SQL语句
 * Author southday
 * Date   2019/2/1
 */
public class TableCreator {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("arguments: annotated classes");
            System.exit(0);
        }
        for (String className : args) {
            Class<?> cl = Class.forName(className);
            DBTable dbTable = cl.getAnnotation(DBTable.class);
            if (dbTable == null) {
                System.out.println("No DBTable annotations in class " + className);
                continue;
            }
            String tableName = dbTable.name();
            if (tableName.length() < 1)
                tableName = cl.getName().toUpperCase();
            List<String> columnDefs = new ArrayList<>();
            for (Field field : cl.getDeclaredFields()) {
                String columnName = null;
                Annotation[] annos = field.getDeclaredAnnotations();
                if (annos.length < 1)
                    continue;
                // 这里默认每个字段只有一个注解，所以用annos[0]
                if (annos[0] instanceof SQLInteger) {
                    SQLInteger sInt = (SQLInteger) annos[0];
                    columnName = sInt.name().length() < 1 ? field.getName().toUpperCase() : sInt.name();
                    columnDefs.add(columnName + " INT" + getConstaints(sInt.constraints()));
                }
                if (annos[0] instanceof SQLString) {
                    SQLString sString = (SQLString) annos[0];
                    columnName = sString.name().length() < 1 ? field.getName().toUpperCase() : sString.name();
                    columnDefs.add(columnName + " VARCHAR(" + sString.value() + ")" + getConstaints(sString.constraints()));
                }
                StringBuilder createCommand = new StringBuilder("CREATE TABLE " + tableName + "(");
                for (String columnDef : columnDefs)
                    createCommand.append("\n    " + columnDef + ",");
                String tableCreate = createCommand.substring(0, createCommand.length() - 1) + ");";
                System.out.println("Table Creation SQL for " + className + " is :\n" + tableCreate);
            }
        }
    }

    private static String getConstaints(Constraints con) {
        String constraints = "";
        if (!con.allowNull())
            constraints += " NOT NULL";
        if (con.primaryKey())
            constraints += " PRIMARY KEY";
        if (con.unique())
            constraints += " UNIQUE";
        return constraints;
    }
}
