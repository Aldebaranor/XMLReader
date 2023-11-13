package song;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import song.Service.xmlReaderService;

@SpringBootTest(classes = {Application.class})
@RunWith(SpringRunner.class)
public class xmlTest {

    @Autowired
    private xmlReaderService service;

    @Test
    public void xmlOV() throws Exception {
//        String filePath ="E:\\军晟\\14润科\\OV-5b作战活动模型.xml";
        String filePath ="C:\\Users\\26594\\Desktop\\OV-5b作战活动模型.xml";
        service.readXML(filePath);
    }

    private static Object o1 = new Object();
    private static Object o2 = new Object();
    @Test
    public void lock(){

        Thread t1 =new Thread(()->{
            System.out.println("T1申请o1");
            synchronized (o1){
                System.out.println("T1占有o1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("T1申请o2");
                synchronized (o2){
                    System.out.println("T1占有o2");
                }
            }
        });
        Thread t2 = new Thread(()->{
            System.out.println("T2申请o2");
            synchronized (o2){
                System.out.println("T2占有o2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("T2申请o1");
                synchronized (o1){
                    System.out.println("T2占有o1");
                }
            }

        });
        t1.start();
        t2.start();
    }

}
