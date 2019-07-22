package com.demo.show.bean;


import com.demo.show.state.States;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    public String orderId;

    public States state;


}

