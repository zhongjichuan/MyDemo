package com.demo.show.config2;

import com.demo.show.event.Events;
import com.demo.show.state.States;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

@Component
public class WAITING_FOR_RECEIVE_Builder{


    public StateMachine<States, Events> create(BeanFactory beanFactory) throws Exception {

        StateMachineBuilder.Builder<States,Events> builder = StateMachineBuilder.builder();
        builder.configureConfiguration().withConfiguration()
                .machineId("WAITING_FOR_RECEIVE").autoStartup(true).beanFactory(beanFactory);

        builder.configureStates().withStates().initial(States.WAITING_FOR_RECEIVE).states(EnumSet.allOf(States.class));

        //指定状态机有哪些节点，即迁移动作
        builder.configureTransitions().withExternal().source(States.WAITING_FOR_RECEIVE)
                .target(States.DONE)
                .event(Events.RECEIVE);

        return builder.build();
    }
}
