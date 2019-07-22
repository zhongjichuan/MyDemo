package com.demo.show.example;

import com.demo.show.bean.Order;
import com.demo.show.state.States;


public class OrderService {

    /**
     * 支付订单操作
     */
    public void pay(Order order){
        if(order.state.equals(States.UNPAID)){//订单目前为“未支付”状态
            order.setState(States.WAITING_FOR_RECEIVE);//变更状态
            System.out.println("----支付成功----");

        }else if(order.state.equals(States.WAITING_FOR_RECEIVE)){//“待收货状态”
            System.out.println("----订单为收货状态，请勿再支付----");

        }else if(order.state.equals(States.DONE)){//“订单交易结束”
            System.out.println("----订单交易结束，请勿再支付----");
        }
    }

    /**
     * 订单确认收货操作
     */
    public void receive(Order order){
        if(order.state.equals(States.UNPAID)){//订单目前为“未支付”状态
            System.out.println("----订单未支付，请先支付----");

        }else if(order.state.equals(States.WAITING_FOR_RECEIVE)){//“待收货状态”
            order.setState(States.DONE);//变更状态
            System.out.println("----订单收货成功----");

        }else if(order.state.equals(States.DONE)){//“订单交易结束”
            System.out.println("----订单已被确认收货----");
        }
    }


    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        Order order = new Order();
        order.setState(States.DONE);
        orderService.pay(order);
    }

}
