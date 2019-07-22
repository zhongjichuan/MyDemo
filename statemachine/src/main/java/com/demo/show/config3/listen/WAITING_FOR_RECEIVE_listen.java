package com.demo.show.config3.listen;


import com.demo.show.bean.Order;
import com.demo.show.event.Events;
import com.demo.show.state.States;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * 用注解的方式实现状态监听器，不需要在configh中再次配置监听器
 */
@WithStateMachine(name ="WAITING_FOR_RECEIVE2" )
public class WAITING_FOR_RECEIVE_listen {


/*    @OnTransition(source = "WAITING_FOR_RECEIVE", target = "DONE")
    public void receive() {
        System.out.println("用户已收货，订单完成");
    }*/


    /**
     * message传参
     * @param message
     */
 /*   @OnTransition(source = "WAITING_FOR_RECEIVE", target = "DONE")
    public void receive(Message<Events> message) {
        Order order = (Order) message.getHeaders().get("order");
        System.out.println("listen:::用户已收货，订单完成,前台传递的参数：："+order);
        order.setState(States.DONE);
        //保存order逻辑.
    }*/


    /**
     *stateContext传参
     * @param stateContext
     */
    @OnTransition(source = "WAITING_FOR_RECEIVE", target = "DONE")
    public void receive(StateContext<States, Events> stateContext) {
        Order order = (Order) stateContext.getExtendedState().getVariables().get(Order.class);
        System.out.println("listen:::用户已收货，订单完成,前台传递的参数：："+order);
        order.setState(States.DONE);
        //保存order逻辑.
    }
}
