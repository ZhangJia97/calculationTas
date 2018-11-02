import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BigNum {

    public static void main(String[] args) throws IOException {
//        mySqrt("25");
//        while (true) {
//            Scanner sc = new Scanner(System.in);
//            System.out.println("--------------------\n请选择方式:");
//            System.out.println("1.手动输入\n2.从文件读取\n0.退出系统\n--------------------");
//            String choice = sc.nextLine();
//            switch (choice) {
//                case "1":
//                    arithmetic();
//                    break;
//                case "2":
//                    arithmeticByFile();
//                    break;
//                case "0":
//                    break;
//                default:
//                    System.out.println("非法输入，请重试！");
//            }
//            if (choice.equals("0")) {
//                break;
//            }
//        }
    }


    /**
     * 手动输入
     */
    private static void arithmetic() {
        while (true) {
            System.out.println("\n\n--------------------\n请选择要执行的运算：");
            System.out.println("1.加法");
            System.out.println("2.减法");
            System.out.println("3.乘法");
            System.out.println("4.除法");
            System.out.println("0.退出\n--------------------");
            Scanner sc = new Scanner(System.in);
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    Scanner sc1 = new Scanner(System.in);
                    System.out.print("\n\n请输入被加数：");
                    String a1 = sc1.nextLine();
                    System.out.print("请输入加数：");
                    String b1 = sc1.nextLine();
                    if (checkNum(a1) && checkNum(b1)) {
                        String num1 = add(a1, b1);
                        System.out.println(mergeStr(a1, b1, choice, num1));
                    } else {
                        System.out.println("数字格式有误，请检查");
                    }
                    break;
                case "2":
                    Scanner sc2 = new Scanner(System.in);
                    System.out.print("\n\n请输入被减数：");
                    String a2 = sc2.nextLine();
                    System.out.print("请输入减数：");
                    String b2 = sc2.nextLine();
                    if (checkNum(a2) && checkNum(b2)) {
                        String num2 = subtract(a2, b2);
                        System.out.println(mergeStr(a2, b2, choice, num2));
                    } else {
                        System.out.println("数字格式有误，请检查");
                    }
                    break;
                case "3":
                    Scanner sc3 = new Scanner(System.in);
                    System.out.print("\n\n请输入被乘数：");
                    String a3 = sc3.nextLine();
                    System.out.print("请输入乘数：");
                    String b3 = sc3.nextLine();
                    if (checkNum(a3) && checkNum(b3)) {
                        String num3 = mul(a3, b3);
                        System.out.println(mergeStr(a3, b3, choice, num3));
                    } else {
                        System.out.println("数字格式有误，请检查");
                    }
                    break;
                case "4":
                    Scanner sc4 = new Scanner(System.in);
                    System.out.print("\n\n请输入被除数：");
                    String a4 = sc4.nextLine();
                    System.out.print("请输入除数：");
                    String b4 = sc4.nextLine();
                    if (checkNum(a4) && checkNum(b4)) {
                        String num4 = division(a4, b4);
                        System.out.println(mergeStr(a4, b4, choice, num4));
                    } else {
                        System.out.println("数字格式有误，请检查");
                    }
                    break;
                case "0":
                    break;
                default:
                    System.out.println("非法输入，请重试！");
            }
            if (choice.equals("0")) {
                break;
            }
        }
    }


    /**
     * 以文件的形式来计算
     */
    private static void arithmeticByFile() {
        String str = "";
        try {
            str = readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> list = checkFileStr(str);
        if (list.size() == 0) {
            System.out.println("字符串匹配时有误，请检查");
        } else {
            String num = "";
            String num1 = list.get(0);
            String sign = list.get(1);
            String num2 = list.get(2);
            if (!checkNum(num1) || !checkNum(num2)) {
                System.out.println("数字格式有误，请检查");
                return;
            }
            switch (sign) {
                case "+":
                    num = add(num1, num2);
                    break;
                case "-":
                    num = subtract(num1, num2);
                    break;
                case "*":
                    num = mul(num1, num2);
                    break;
                case "/":
                    num = division(num1, num2);
                default:
                    System.out.println("运算符有误，请检查");
                    return;
            }
            try {
                writeFile(str + " = " + num);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 检查文件中字符串是否合法
     *
     * @param str 需要检查的字符串
     * @return 是否合法
     */
    private static List<String> checkFileStr(String str) {
        String pattern = "\\((-?[\\d+])\\)(\\W)\\((-?[\\d+])\\)";
        List<String> list = new ArrayList<>();

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(str);
        if (m.find()) {
            list.add(m.group(1));
            list.add(m.group(2));
            list.add(m.group(3));
        }
        return list;
    }


    /**
     * 检查输入的数字是否合法
     *
     * @param str 需要检查的字符串
     * @return 是否合法
     */
    private static boolean checkNum(String str) {
        String pattern = "^-?\\d+$";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(str);

        return m.find() && m.group(0).equals(str);
    }


    /**
     * 读文件
     *
     * @return 若文件不存在则返回空，正常则返回文件中字符串
     */
    private static String readFile() throws IOException {
        File file = new File("file/arithmetic.txt");
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
            System.out.println("\n\n获取到的算式为: " + line);
            return line;
        } else {
            return "";
        }
    }


    /**
     * 写文件
     *
     * @param result 计算结果
     */
    private static void writeFile(String result) throws IOException {
        File file = new File("file/result.txt");
        if (file.exists()) {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(result);
            bufferedWriter.close();
            outputStreamWriter.close();
            fileOutputStream.close();
            System.out.println("计算结果为: " + result + "\n\n");
        } else {
            System.out.println("文件不存在");
        }
    }


    /**
     * 合并字符串
     *
     * @param num1 数1
     * @param num2 数2
     * @param sign 运算符
     * @param num  结果
     * @return 合并后的字符串
     */
    private static String mergeStr(String num1, String num2, String sign, String num) {
        switch (sign) {
            case "1":
                sign = "+";
                break;
            case "2":
                sign = "-";
                break;
            case "3":
                sign = "*";
                break;
            case "4":
                sign = "/";
                break;
        }
        return "\n答案为:" + num1 + " " + sign + " " + num2 + " = " + num;
    }


    /**
     * 大整数的加法
     *
     * @param a 被加数
     * @param b 加数
     * @return 和
     */
    private static String add(String a, String b) {
        boolean plus = true;
        if (a.charAt(0) == '-' && b.charAt(0) != '-') {
            return subtract(b, a.replace("-", ""));
        } else if (a.charAt(0) != '-' && b.charAt(0) == '-') {
            return subtract(a, b.replace("-", ""));
        } else if (a.charAt(0) == '-' && b.charAt(0) == '-') {
            plus = false;
        }
        a = a.replace("-", "");
        b = b.replace("-", "");
        if (a.length() < b.length()) {
            String tmp = a;
            a = b;
            b = tmp;
        }
        char[] aList = a.toCharArray();
        char[] bList = b.toCharArray();
        int aLen = aList.length;
        int bLen = bList.length;
        StringBuilder num = new StringBuilder();

        //是否进位
        boolean carry = false;
        while (aLen > 0 && bLen > 0) {
            int addNum = Integer.parseInt(String.valueOf(aList[aLen - 1])) + Integer.parseInt(String.valueOf(bList[bLen - 1]));
            if (carry) {
                addNum += 1;
            }
            if (addNum >= 10) {
                addNum %= 10;
                carry = true;
            } else {
                carry = false;
            }
            num.insert(0, String.valueOf(addNum));
            aLen--;
            bLen--;
        }

        while (aLen > 0) {
            int addNum = Integer.parseInt(String.valueOf(aList[aLen - 1]));
            if (carry) {
                addNum += 1;
                if (addNum >= 10) {
                    addNum %= 10;
                    carry = true;
                } else {
                    carry = false;
                }
            }
            num.insert(0, addNum);
            aLen--;
        }
        if (carry) {
            num.insert(0, 1);
        }

        if (!plus) {
            num.insert(0, "-");
        }
        return num.toString();
    }


    /**
     * 大整数减法
     *
     * @param a 被减数
     * @param b 减数
     * @return 差
     */
    private static String subtract(String a, String b) {
        boolean isPlus = true;
        if (a.charAt(0) != '-' && b.charAt(0) == '-') {
            return add(a, b.replace("-", ""));
        } else if (a.charAt(0) == '-' && b.charAt(0) != '-') {
            return add(a, b.replace("-", ""));
        } else if (a.charAt(0) == '-' && b.charAt(0) == '-') {
            String tmp = a.replace("-", "");
            a = b.replace("-", "");
            b = tmp;
            isPlus = false;
        }
        a = a.replace("-", "");
        b = b.replace("-", "");
        if (a.length() < b.length()) {
            String tmp = a;
            a = b;
            b = tmp;
            isPlus = false;
        }
        char[] aList = a.toCharArray();
        char[] bList = b.toCharArray();
        int aLen = aList.length;
        int bLen = bList.length;
        if (aLen == bLen) {
            for (int i = aLen; i > 0; i--) {
                if (Integer.parseInt(String.valueOf(aList[i - 1])) < Integer.parseInt(String.valueOf(bList[i - 1]))) {
                    isPlus = false;
                    char[] tmpList = aList;
                    aList = bList;
                    bList = tmpList;
                    break;
                }
            }
        }
        StringBuilder num = new StringBuilder();

        //是否借位
        boolean borrow = false;
        while (aLen > 0 && bLen > 0) {
            int subtractNum;
            int m = Integer.parseInt(String.valueOf(aList[aLen - 1]));
            int n = Integer.parseInt(String.valueOf(bList[bLen - 1]));
            if (borrow) {
                if (m - 1 < n) {
                    subtractNum = m - 1 + 10 - n;
                    borrow = true;
                } else {
                    subtractNum = m - 1 - n;
                    borrow = false;
                }
            } else {
                if (m < n) {
                    subtractNum = m + 10 - n;
                    borrow = true;
                } else {
                    subtractNum = m - n;
                    borrow = false;
                }
            }
            num.insert(0, String.valueOf(subtractNum));
            aLen--;
            bLen--;
        }

        while (aLen > 0) {
            int subtractNum = Integer.parseInt(String.valueOf(aList[aLen - 1]));
            if (borrow) {
                subtractNum -= 1;
                if (subtractNum < 0) {
                    subtractNum = -subtractNum;
                    borrow = true;
                } else {
                    borrow = false;
                }
            }
            num.insert(0, subtractNum);
            aLen--;
        }

        if (!isPlus) {
            num.insert(0, "-");
        }
        return String.valueOf(Integer.parseInt(num.toString()));
    }


    /**
     * 大整数乘法
     *
     * @param a 被乘数
     * @param b 乘数
     * @return 积
     */
    private static String mul(String a, String b) {
        boolean isPlus = true;
        if (a.charAt(0) == '-' && b.charAt(0) != '-') {
            isPlus = false;
        } else if (a.charAt(0) != '-' && b.charAt(0) == '-') {
            isPlus = false;
        }
        if (Integer.parseInt(a) == 0 || Integer.parseInt(b) == 0) {
            return "0";
        }

        a = a.replace("-", "");
        b = b.replace("-", "");
        if (a.length() < b.length()) {
            String tmp = a;
            a = b;
            b = tmp;
        }

        char[] aList = a.toCharArray();
        char[] bList = b.toCharArray();
        int bLen = bList.length;
        String num = "0";

        while (bLen > 0) {
            int aLen = aList.length;
            StringBuilder mulNum = new StringBuilder();
            int carry = 0;
            while (aLen > 0) {
                int sigleMulNum = Integer.parseInt(String.valueOf(bList[bLen - 1])) * Integer.parseInt(String.valueOf(aList[aLen - 1])) + carry;
                carry = sigleMulNum / 10;
                sigleMulNum %= 10;
                mulNum.insert(0, sigleMulNum);
                aLen--;
            }
            if (carry != 0) {
                mulNum.insert(0, carry);
            }
            num = String.valueOf(Integer.parseInt(num) + Integer.parseInt(mulNum.toString()) * Integer.parseInt(String.valueOf((int) Math.pow(10, bList.length - bLen))));
            bLen--;
        }

        if (!isPlus) {
            num = "-" + num;
        }
        return num;
    }


    /**
     * 大整数除法
     *
     * @param a 被除数
     * @param b 除数
     * @return 商，只保留整数位
     */
    private static String division(String a, String b) {
        boolean isPlus = true;
        int aSign = a.charAt(0) == '-' ? -1 : 1;
        int bSign = b.charAt(0) == '-' ? -1 : 1;
        if (aSign * bSign < 0) {
            isPlus = false;
        }
        a = a.replace("-", "");
        b = b.replace("-", "");
        if (subtract(a, b).charAt(0) == '-') {
            return "0";
        }
        String num = "0";

        while (subtract(a, b).charAt(0) != '-') {
            String numTimes = "1";
            String m = b;
            while (subtract(a, mul(m, "2")).charAt(0) != '-') {
                numTimes = mul(numTimes, "2");
                m = mul(m, "2");
            }
            num = add(num, numTimes);
            a = subtract(a, m);
        }

        if (!isPlus) {
            num = "-" + num;
        }
        return num;
    }


//    /**
//     * d大整数求平方根
//     * @param x
//     * @return
//     */
//    private static String mySqrt(String x) {
//        String x0 = x;
//        x0 = add(division(x0,"2"),division(x,mul("2",x0)));
//        String min = x;
//        while (subtract(subtract(mul(add(division(x0,"2"),division(x,mul("2",x0))),add(division(x0,"2"),division(x,mul("2",x0)))), x),min).charAt(0) == '-'){
//            x0 = add(division(x0,"2"),division(x,mul("2",x0)));
//            min = subtract(mul(x0,x0), x);
//        }
////        while(Math.abs(x0*x0-t)>0.00001)
////            x0=x0/2+t/(2*x0);
//        System.out.println(x0);
//        return x0;//double 转int类型必须使用强制类型转化
//    }

}
