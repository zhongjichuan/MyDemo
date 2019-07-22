package com.demo.show.config3;

import com.demo.show.bean.Order;
import com.demo.show.event.Events;
import com.demo.show.state.States;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Spirng的InitializingBean为bean提供了定义初始化方法的方式。InitializingBean是一个接口，它仅仅包含一个方法：afterPropertiesSet()。在spring 初始化后，
 * 执行完所有属性设置方法(即setXxx)将自动调用 afterPropertiesSet(), 在配置文件中无须特别的配置， 但此方式增加了bean对spring 的依赖。
 */
/**
 * 使用创建builderFactory来利用InitializingBean灵活注册spring bean，
 * 同时将已经注册到spring中的各个stateMachine builder在afterPropertiesSet写入到一个ConcurrentHashMap中，
 * 这个map的key是当前状态，value是注册到spring中的各个状态机的builder。那么我们就可以传入状态和事件，
 * 直接找到对应的builder创建statemachine，然后通过statemachine.send(Event)进行操作。
 * 那么我们就可以传入状态和事件，直接找到对应的builder创建statemachine，然后通过statemachine.send(Event)进行操作。
 */
@Component
public class BuilderFactory implements InitializingBean {


    @Autowired
    private BeanFactory beanFactory;
    @Autowired
    private List<Builder> builders;

    private Map<States, Builder> statesStateMachineMap = new ConcurrentHashMap<>();//该map用于存放所有的状态机，形式（state,对应的builder）


    /**
     * 根据order创建状态机
     * @param order
     * @return
     * @throws Exception
     */
    public StateMachine<States, Events> create(Order order) throws Exception{
        States state = order.getState();

        Builder builder = statesStateMachineMap.get(state);//多态

        StateMachine<States, Events> stateMachine = builder.build(beanFactory);//初始化状态机


        return stateMachine;
    }

    /**
    生成一个map的key是当前状态，value是注册到spring中的各个状态机的builder
     **/
    @Override
    public void afterPropertiesSet() throws Exception {
        statesStateMachineMap = builders.stream().collect(Collectors.toMap(Builder::getName, Function.identity()));
    }
}
