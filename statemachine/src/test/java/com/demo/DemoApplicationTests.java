package com.demo;

import com.demo.show.bean.Order;
import com.demo.show.config2.UNPAID_Builder;
import com.demo.show.config2.WAITING_FOR_RECEIVE_Builder;
import com.demo.show.config3.BuilderFactory;
import com.demo.show.event.Events;
import com.demo.show.state.States;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private StateMachine<States, Events> stateMachine;
    @Test
    public void test1() throws Exception {

        stateMachine.start();
        System.out.println("--------------------------");
        stateMachine.sendEvent(Events.PAY_CANCEL);
        stateMachine.sendEvent(Events.PAY);
        stateMachine.sendEvent(Events.RECEIVE);
    }


    @Autowired
    public BeanFactory beanFactory;

    @Autowired
    public UNPAID_Builder unpaid_builder;
    @Autowired
    public WAITING_FOR_RECEIVE_Builder waiting_for_receive_builder;
    @Test
    public void test2() throws Exception {

        //创建待支付状态机
        StateMachine<States, Events> stateMachine_unpaid = unpaid_builder.create(beanFactory);
        stateMachine_unpaid.sendEvent(Events.PAY);
        stateMachine_unpaid.sendEvent(Events.PAY_CANCEL);

        System.out.println("--------------------------");
        StateMachine<States, Events> stateMachine_waiting_for_receive = waiting_for_receive_builder.create(beanFactory);
        stateMachine_waiting_for_receive.sendEvent(Events.RECEIVE);
    }

    @Autowired
    public BuilderFactory builderFactory;

    /**
     * 测试状态机工厂，unpaid状态
     * @throws Exception
     */
    @Test
    public void test3() throws Exception {
        Order order = new Order("1001", States.UNPAID);

        StateMachine<States, Events> stateMachine = builderFactory.create(order);

        stateMachine.sendEvent(Events.PAY_CANCEL);

    }

    /**
     * message传参
     * @throws Exception
     */
    @Test
    public void test4() throws Exception {
        Order order = new Order("1001", States.WAITING_FOR_RECEIVE);

        StateMachine<States, Events> stateMachine = builderFactory.create(order);

        Message<Events> message = MessageBuilder.withPayload(Events.RECEIVE).setHeader("order", order).build();
        boolean result = stateMachine.sendEvent(message);

    }
    /**
     * ExtendedState传参
     * @throws Exception
     */
    @Test
    public void test5() throws Exception {
        Order order = new Order("1001", States.WAITING_FOR_RECEIVE);

        StateMachine<States, Events> stateMachine = builderFactory.create(order);


        stateMachine.getExtendedState().getVariables().put(Order.class,order);

        stateMachine.sendEvent(Events.RECEIVE);

    }


}
