import org.junit.Test;

public class Test1 {
    
    
    @Test
    public void  testSys(){
        String property = System.getProperty("os.name");
        String property1 = System.getProperty("user.dir");
        System.out.println("-------------------------------------------------------");
        System.out.println("============>property的值是： " + property+"ssdf "+property1);

    }


    @Test
    public void testInt(){
        int a = 10;
        for( int i=0;i<32;i++){
            int t = (a & 0x80000000>>>i)>>>(31-i);
            System.out.print(t);
        }

    }


}
