package parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import websockts.message.MessageReceive;
import websockts.parser.Parser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:application-service.xml")
public class ParserTest {

    @Autowired
    private Parser parser;


    @Test
    public void testStringToObject() throws Exception {
        File file=new File("F:\\test\\msg_list.txt");
        BufferedInputStream bi=new BufferedInputStream(new FileInputStream(file));
        byte []buf=new byte[1024];
        StringBuilder sb=new StringBuilder();
        int len=-1;
        while ((len=bi.read(buf))!=-1){
            sb.append(new String(buf,0,len));
        }
        bi.close();
        System.out.println(sb.toString());
        MessageReceive msg=parser.stringToObject(sb.toString(),MessageReceive.class);
        System.out.println(msg);
        if (msg.getTo() instanceof List){
            for (int i=0;i<((List) msg.getTo()).size();i++){
                System.out.println(((List) msg.getTo()).get(i));
            }
        }
    }

    @Test
    public void testObjectToStringString(){
        MessageReceive message = new MessageReceive();
        message.setType(1);
        String content="12123";
        message.setContent(content);
        String res = parser.ObjectToString(message);
        System.out.println(res);
    }

    @Test
    public void testObjectToStringList(){
        MessageReceive message = new MessageReceive();
        message.setType(1);
        List<String> content=new ArrayList<String>();
        content.add("111111");
        content.add("222222");
        message.setContent(content);
        message.setTo(((ArrayList<String>) content).clone());
        String res = parser.ObjectToString(message);
        System.out.println(res);
    }
}
