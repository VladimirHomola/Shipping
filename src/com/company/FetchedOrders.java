package com.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FetchedOrders {
    Map<String, Order> allOrders = new HashMap<>();
    Map<String, Order> readyOrders = new HashMap<>();
    Map<String, Set<Order>> readyOrdersMissingBikeByPolicy = new HashMap<>();

}
