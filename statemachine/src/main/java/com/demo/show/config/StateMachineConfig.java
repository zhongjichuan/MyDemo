package com.demo.show.config;

import com.demo.show.event.Events;
import com.demo.show.state.States;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;

/**
 *创建状态机的配置类，继承EnumStateMachineConfigurerAdapter<States, Events>类
 */
@Configuration
@EnableStateMachine //表示这个配置类是用在spring statemachine上面的。
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    /**
     * configure(StateMachineStateConfigurer<States, Events> states)方法用来初始化当前状态机拥有哪些状态，
     * @param states
     * @throws Exception
     */
    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        //定义状态机中的状态
        states.withStates()//状态配置
                .initial(States.UNPAID)//初始状态为UNPAID
                .states(EnumSet.allOf(States.class));//指定状态机的所有的状态
    }


    /**
     * 初始化当前状态机有哪些状态迁移动作
     * @param transitions
     * @throws Exception
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions.withExternal()//转换方式。从A->B。
                .source(States.UNPAID).target(States.WAITING_FOR_RECEIVE)// 指定状态来源和目标,状态转换
                .event(Events.PAY)//指定触发事件

                .and()
                .withExternal()
                .source(States.UNPAID).target(States.UNPAID)// 指定状态来源和目标,状态转换
                .event(Events.PAY_CANCEL)

                .and()
                .withExternal()
                .source(States.WAITING_FOR_RECEIVE).target(States.DONE)
                .event(Events.RECEIVE);
    }


    /**
     * 为当前的状态机指定了状态监听器，其中listener()则是调用了下一个内容创建的监听器实例，用来处理各个各个发生的状态迁移事件。
     * @param config
     * @throws Exception
     */
    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config.withConfiguration()
                .machineId("one")//此处machineid定义了状态机的Id名称,在@WithStateMachine（name=''）中指定
                .autoStartup(true)//设置是否自动启动，否则需要手动启动。
                .listener(listener());// 指定状态机的处理监听器
    }


    /**
     * 创建StateMachineListener状态监听器的实例，在该实例中会定义具体的状态迁移处理逻辑，上面的实现中只是做了一些输出，
     * 实际业务场景会会有更负责的逻辑，所以通常情况下，我们可以将该实例的定义放到独立的类定义中，并用注入的方式加载进来。
     * @return
     */
    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {


            @Override
            public void transition(Transition<States, Events> transition) {

                if(transition.getSource()==null&&transition.getTarget().getId() == States.UNPAID) {
                    System.out.println("----订单创建，待支付----");
                    return;
                }
                if(transition.getTarget().getId() == States.UNPAID&&transition.getSource().getId()==States.UNPAID) {
                    System.out.println("----支付取消，订单仍为待支付状态----");
                    return;
                }

                if(transition.getSource().getId() == States.UNPAID
                        && transition.getTarget().getId() == States.WAITING_FOR_RECEIVE) {
                    System.out.println("-----用户完成支付，待收货-----");
                    return;
                }

                if(transition.getSource().getId() == States.WAITING_FOR_RECEIVE
                        && transition.getTarget().getId() == States.DONE) {
                    System.out.println("----用户已收货，订单完成----");
                    return;
                }
            }
            @Override
            public void stateChanged(State<States,Events> from, State<States,Events> to){//初始化也会触发
                System.out.println("---订单由<-"+from.getId()+"->转换成<-"+to.getId()+"->状态了----");
            }

        };
    }

}
