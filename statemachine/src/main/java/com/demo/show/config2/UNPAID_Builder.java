package com.demo.show.config2;

import com.demo.show.event.Events;
import com.demo.show.state.States;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

@Component
public class UNPAID_Builder {

    /**
     * 创建一个状态机
     * @param beanFactory
     * @return
     */
    public StateMachine<States, Events> create(BeanFactory beanFactory) throws Exception {

        StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();//建造者模式
        builder.configureConfiguration()
                .withConfiguration()
                .machineId("UNPAID")
                .autoStartup(true)
                .beanFactory(beanFactory);//ioc容器

        builder.configureStates().withStates().initial(States.UNPAID).states(EnumSet.allOf(States.class));

        builder.configureTransitions().withExternal().source(States.UNPAID)
                .target(States.WAITING_FOR_RECEIVE)
                .event(Events.PAY)

                .and()
                .withExternal()    //.withInternal()//source和target的串联写法。
                .source(States.UNPAID).target(States.UNPAID)
                .event(Events.PAY_CANCEL);

        StateMachine<States, Events> b =builder.build();
        return b;
    }

}
