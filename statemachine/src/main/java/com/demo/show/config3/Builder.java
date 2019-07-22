package com.demo.show.config3;

import com.demo.show.event.Events;
import com.demo.show.state.States;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.statemachine.StateMachine;

/**
 * 使用创建builderFactory来利用InitializingBean灵活注册spring bean，
 * 同时将已经注册到spring中的各个stateMachine builder在afterPropertiesSet写入到一个ConcurrentHashMap中，
 * 这个map的key是当前状态，value是注册到spring中的各个状态机的builder。那么我们就可以传入状态和事件，
 * 直接找到对应的builder创建statemachine，然后通过statemachine.send(Event)进行操作。
 * 那么我们就可以传入状态和事件，直接找到对应的builder创建statemachine，然后通过statemachine.send(Event)进行操作。
 */
public interface Builder {

    States getName();
    StateMachine<States, Events> build(BeanFactory beanFactory) throws Exception;
}
