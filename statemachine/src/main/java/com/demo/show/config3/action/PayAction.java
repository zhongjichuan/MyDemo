package com.demo.show.config3.action;

import com.demo.show.event.Events;
import com.demo.show.state.States;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

/*
AbstractStateMachine中的onInit会自动捕获异常，并打印异常。并不会终止状态机的运行。
 */
/**
 * action执行当前状态变更导致的业务逻辑处理，以及出异常时的处理.
 */
@Component
public class PayAction implements Action<States, Events> {

    @Override
    public void execute(StateContext<States, Events> stateContext) {
        try {

            System.out.println("------PayAction-----");
        }catch (Exception exception){//异常处理。
            stateContext.getStateMachine().getExtendedState().getVariables().put(RuntimeException.class, exception);
        }

    }
}
