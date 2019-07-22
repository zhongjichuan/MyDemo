package com.demo.show.config3.action;

import com.demo.show.bean.Order;
import com.demo.show.event.Events;
import com.demo.show.state.States;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

/**
 * action执行当前状态变更导致的业务逻辑处理，以及出异常时的处理.
 */
@Component
public class ReceiveAction implements Action<States, Events> {

    /**
     * 从message中获取参数
     * @param stateContext
     */
  /*  @Override
    public void execute(StateContext<States, Events> stateContext) {

        Order order = (Order) stateContext.getMessageHeaders().get("order");//stateContext.getMessage().getHeaders().get("xxx")
        System.out.println("--ReceiveAction得到的参数：："+order);
        order.setState(States.DONE);
        //更新order业务逻辑。
    }*/

    /**
     * 从ExtendedState中获取参数
     */
    @Override
    public void execute(StateContext<States, Events> stateContext) {

        Order order = (Order) stateContext.getExtendedState().getVariables().get(Order.class);
        System.out.println("--ReceiveAction得到的参数：："+order);
        order.setState(States.DONE);
        //更新order业务逻辑。
    }



}
