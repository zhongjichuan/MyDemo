package com.demo.show.config3.listen;


import com.demo.show.bean.Order;
import com.demo.show.event.Events;
import com.demo.show.state.States;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.annotation.OnStateChanged;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * 用注解的方式实现状态监听器，不需要在configh中再次配置监听器
 */
@WithStateMachine(name ="UNPAID2" )
public class UNPAID_listen {


    @OnTransition(target = "UNPAID",source = {})
    public void create(StateContext<States, Events> context) {
        if(context.getSource()==null){
            System.out.println("订单创建，待支付");
        }
    }

    @OnTransition(source = "UNPAID",target = "UNPAID")
    public void pay_cancel(StateContext<States, Events> context) {
        System.out.println("支付取消，订单仍为待支付状态");
    }

    @OnTransition(source = "UNPAID", target = "WAITING_FOR_RECEIVE")
    public void pay(StateContext<States, Events> context) {
        System.out.println("用户完成支付，待收货");
    }
}
