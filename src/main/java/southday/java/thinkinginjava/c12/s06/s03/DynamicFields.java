package southday.java.thinkinginjava.c12.s06.s03;

/* 异常链
=> 我们在捕获一个异常后，常常会抛出另一个异常，并且希望把原始的异常信息保存下来，这被称为异常链

有两种方法：
1) 使用带cause对象的构造函数，cause（cause是Throwable类型）就表示原始异常
2) 使用 initCause(e)，参数 e 就代表原始异常

需要注意的是：对于方法(1)，带cause对象的构造函数，只有以下3个基类有这个构造函数：
    a) Error
    b) Exception
    c) RuntimeException
所以，对于其他的异常类型，我们使用(2)的方法来链接异常类

下面的例子中允许你在运行时动态地向DynamicFields对象添加字段
 */

class DynamicFieldsException extends Exception {}

/**
 * Author southday
 * Date   2018/11/14
 */
public class DynamicFields {
    private Object[][] fields;

    public DynamicFields(int sz) {
        // fields[i][0]:id | fields[i][1]:value
        fields = new Object[sz][2];
        for (int i = 0; i < sz; i++)
            fields[i] = new Object[] {null, null};
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Object[] obj : fields) {
            sb.append(obj[0]);
            sb.append(": ");
            sb.append(obj[1]);
            sb.append("\n");
        }
        return sb.toString();
    }

    private int hasField(String id) {
        for (int i = 0; i < fields.length; i++)
            if (id.equals((fields[i][0])))
                return i;
            // IDEA 对于代码不加{}(for+if)时，换行+Backspace是鸡肋，不是往前移而是往上跳
        return -1;
    }

    private int getFieldNumber(String id) throws NoSuchFieldException {
        int fieldNumber = hasField(id);
        if (fieldNumber == -1)
            throw new NoSuchFieldException();
        return fieldNumber;
    }

    private int makeField(String id) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i][0] == null) {
                fields[i][0] = id;
                return i;
            }
        }
        // 扩容
        Object[][] tmp = new Object[fields.length+1][2];
        for (int i = 0; i < fields.length; i++)
            tmp[i] = fields[i];
        for (int i = fields.length; i < tmp.length; i++)
            tmp[i] = new Object[] {null, null};
        fields = tmp;
        return makeField(id);
    }

    public Object getField(String id) throws NoSuchFieldException {
        return fields[getFieldNumber(id)][1];
    }

    // 返回旧的value
    public Object setField(String id, Object value) throws DynamicFieldsException {
        if (value == null) {
            DynamicFieldsException dfe = new DynamicFieldsException();
            // 2) 使用initCause(e)来链接异常
            dfe.initCause(new NullPointerException());
            throw dfe;
        }
        int fieldNumber = hasField(id);
        if (fieldNumber == -1)
            fieldNumber = makeField(id);
        Object oldv = null;
        try {
            oldv = getField(id);
        } catch (NoSuchFieldException e) {
            // 1) 使用带 cause对象的构造函数来链接异常
            throw new RuntimeException(e);
        }
        fields[fieldNumber][1] = value;
        return oldv;
    }

    public static void main(String[] args) {
        DynamicFields df = new DynamicFields(3);
        System.out.println(df);
        try {
            df.setField("d", "A value for d");
            df.setField("number1", 47);
            df.setField("number2", 48);
            System.out.println(df);
            df.setField("d", "A new value for d");
            df.setField("number3", 11);
            System.out.println("df: " + df);
            System.out.println("df.getField(\"d\"): " + df.getField("d"));
            Object field = df.setField("d", null); // Exception
        } catch (NoSuchFieldException e) {
            e.printStackTrace(System.out);
        } catch (DynamicFieldsException e) {
            e.printStackTrace(System.out);
        }
    }
}
