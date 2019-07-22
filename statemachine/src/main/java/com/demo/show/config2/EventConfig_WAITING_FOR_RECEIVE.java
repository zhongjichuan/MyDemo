package com.demo.show.config2;


import com.demo.show.bean.Order;
import com.demo.show.event.Events;
import com.demo.show.state.States;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * 用注解的方式实现状态监听器，不需要在configh中再次配置监听器
 */
@WithStateMachine(id ="WAITING_FOR_RECEIVE" )
public class EventConfig_WAITING_FOR_RECEIVE {


    @OnTransition(source = "WAITING_FOR_RECEIVE", target = "DONE")
    public void receive() {
        System.out.println("用户已收货，订单完成");
    }
}
