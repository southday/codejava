package southday.java.acm.other;

import org.junit.Test;
import southday.java.acm.other.solution.RMBCapitalFigureTranslator;

/**
 * @author southday
 * @date 2019/3/21
 */
public class RMBCapitalFigureTranslatorTest {

    @Test
    public void translateIntegerTest() {
        test01("1409"); // 壹仟肆佰零玖元
        test01("6007"); // 陆仟零柒元
        test01("107000"); // 壹拾万零柒仟元
        test01("10107000"); // 壹仟零壹拾万零柒仟元
        test01("16409"); // 壹万陆仟肆佰零玖元
        test01("325"); // 叁佰贰拾伍元
    }

    private void test01(String s) {
        System.out.println(s + " -> " + RMBCapitalFigureTranslator.translateInteger(s));
    }

    @Test
    public void translateDecimalTest() {
        test02("23", true); // 贰角叁分
        test02("00", true); // null
        test02("30", true); // 叁角
        test02("02", true); // 贰分
        System.out.println("------------------------");
        test02("23", false); // 贰角叁分
        test02("00", false); // null
        test02("30", false); // 叁角
        test02("02", false); // 零贰分
    }

    private void test02(String s, boolean izero) {
        System.out.println(s + " -> " + RMBCapitalFigureTranslator.translateDecimal(s, izero));
    }

    @Test
    public void translateTest() {
        test03("￥1409.50"); // 人民币壹仟肆佰零玖元伍角
        test03("￥6007.14"); // 人民币陆仟零柒元壹角肆分
        test03("￥107000.53"); // 人民币壹拾万零柒仟元伍角叁分
        test03("￥16409.02"); // 人民币壹万陆仟肆佰零玖元零贰分
        test03("￥325.04"); // 人民币叁佰贰拾伍元零肆分
        test03("￥0.00"); // 人民币零元整
        test03("￥300.00"); // 人民币叁佰元整
        test03("￥301.00"); // 人民币叁佰零壹元整
        test03("￥10107000"); // 人民币壹仟零壹拾万零柒仟元整
        test03("￥101.00.0"); // null
    }

    private void test03(String s) {
        System.out.println(s + " -> " + RMBCapitalFigureTranslator.translate(s));
    }
}
