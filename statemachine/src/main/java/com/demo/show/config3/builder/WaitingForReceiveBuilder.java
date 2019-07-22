package com.demo.show.config3.builder;

import com.demo.show.config3.Builder;
import com.demo.show.config3.action.PayAction;
import com.demo.show.config3.action.ReceiveAction;
import com.demo.show.event.Events;
import com.demo.show.state.States;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

@Component
public class WaitingForReceiveBuilder implements Builder {

    @Autowired
    public ReceiveAction receiveAction;

    @Override
    public States getName() {
        return States.WAITING_FOR_RECEIVE;
    }


    @Override
    public StateMachine<States, Events> build(BeanFactory beanFactory) throws Exception {

        StateMachineBuilder.Builder<States,Events> builder = StateMachineBuilder.builder();
        builder.configureConfiguration().withConfiguration()
                .machineId("WAITING_FOR_RECEIVE2").autoStartup(true).beanFactory(beanFactory);

        builder.configureStates().withStates().initial(States.WAITING_FOR_RECEIVE).states(EnumSet.allOf(States.class));

        //指定状态机有哪些节点，即迁移动作
        builder.configureTransitions().withExternal().source(States.WAITING_FOR_RECEIVE)
                .target(States.DONE)
                .event(Events.RECEIVE)
                .action(receiveAction);//用于实现当前节点对应的业务逻辑处理

        return builder.build();
    }
}
