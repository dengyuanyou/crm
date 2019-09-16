public class test008 {
    public static void main(String[] args) {
        //第一种方法:定义中间变量
        int a =10;
        int b=15;
        int temp;
        temp=a;
        a=b;
        b=temp;
        System.out.println("a的值"+a);
        System.out.println("b的值"+b);

        //第二种
        a=20;
        b=25;
        a=a+b;
        b=a-b;
        a=a-b;
        System.out.println("a的值"+a);
        System.out.println("b的值"+b);

        //第三种
        a=30;
        b=35;
        a=a*b;
        b=a/b;
        a=a/b;
        System.out.println("a的值"+a);
        System.out.println("b的值"+b);

        //第四种,用异或的方法:相同为0,不同为1
        a=40;
        b=45;
        a=a^b;
        b=a^b;
        a=a^b;
        System.out.println("a的值"+a);
        System.out.println("b的值"+b);

        //第五种方法用字符串的方法
        String c="50";
        String d ="55";
        int e =d.length();
        c=d+c;
        d=c.substring(e);
        c=c.substring(0,e);
        System.out.println(d);
        System.out.println(c);

    }

}
