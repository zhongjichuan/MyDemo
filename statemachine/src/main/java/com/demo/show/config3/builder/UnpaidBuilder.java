package com.demo.show.config3.builder;

import com.demo.show.config3.Builder;
import com.demo.show.event.Events;
import com.demo.show.state.States;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

/*
Transition: 节点，是组成状态机引擎的核心
source：节点的当前状态
target：节点的目标状态
event：触发节点从当前状态到目标状态的动作
guard：起校验功能，一般用于校验是否可以执行后续action
action：用于实现当前节点对应的业务逻辑处理
 */

/**
 * 状态转变触发（trigger）->状态机启动并加载配置->guard->action->onTransition->变成新状态
 */
@Component
public class UnpaidBuilder implements Builder {


    @Override
    public States getName() {
        return States.UNPAID;
    }

    @Override
    public StateMachine<States, Events> build(BeanFactory beanFactory) throws Exception {

        StateMachineBuilder.Builder<States,Events> builder = StateMachineBuilder.builder();//建造者模式
        builder.configureConfiguration()
                .withConfiguration()
                .machineId("UNPAID2")
                .autoStartup(true)
                .beanFactory(beanFactory);

        builder.configureStates().withStates().initial(States.UNPAID).states(EnumSet.allOf(States.class));

        builder.configureTransitions()
                .withExternal()
                .source(States.UNPAID)
                .target(States.WAITING_FOR_RECEIVE)
                .event(Events.PAY)

                .and()
                .withExternal()    //.withInternal()//source和target的串联写法。
                .source(States.UNPAID).target(States.UNPAID)
                .event(Events.PAY_CANCEL);//取消支付

        return builder.build();
    }
}
