package com.red.webflux.webflux;

import com.red.webflux.kafka.KafkaSender;
import com.red.webflux.model.Red;
import org.aspectj.lang.annotation.Around;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebfluxApplicationTests {


    @Autowired
    private KafkaSender<Red> kafkaSender;

    @Test
    public void contextLoads()   {
        //模拟发消息
        for (int i = 0; i < 5; i++) {

            Red user = new Red();
            user.setAge(100);
            user.setMessage("第一次使用kafka");
            user.setName("kafka");

            kafkaSender.send(user);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}
