package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {

        FetchedOrders ordersBefore = new FetchedOrders();
        FetchedOrders ordersAfter = new FetchedOrders();

        String pathToAfter = "/Users/vladimirhomola/Documents/GitHub/Shipping/stock.pickingAfter.csv";
        String pathToBefore = "/Users/vladimirhomola/Documents/GitHub/Shipping/stock.pickingBefore2.csv";

        fetchOrdersFromCsv(ordersBefore, pathToBefore);
        fetchOrdersFromCsv(ordersAfter, pathToAfter);


        System.out.println(ordersBefore);


    }


    private static void fetchOrdersFromCsv(FetchedOrders orders, String pathToCsv) throws IOException {
        String row;
        BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
        int i = 0;
        while ((row = csvReader.readLine()) != null) {
            if(i++ == 0){
                continue;
            }
            //String[] data = row.split(",");
            List<String> data = new ArrayList<>();
            Pattern p = Pattern.compile("\\G\"(.*?)\",?|([^,]*),?");
            for(Matcher m = p.matcher(row); m.find(); ) {
                String token = m.group(m.start(1)<0? 2: 1);
                data.add(token);
            }

            OrderLine orderLine = new OrderLine();

            orderLine.id = getValue(data, 7);
            orderLine.quantity = Integer.valueOf(getValue(data, 13).split("\\.")[0]);
            orderLine.reservedQuantity = Integer.valueOf(getValue(data, 14).split("\\.")[0]);
            orderLine.doneQuantity = Integer.valueOf(getValue(data, 15).split("\\.")[0]);
            orderLine.isBike = isBike(getValue(data, 12));

            String orderReference = getValue(data, 9);
            if(orders.allOrders.containsKey(orderReference)){
                Order order = orders.allOrders.get(orderReference);
                order.lines.put(orderLine.id, orderLine);
            } else {
                Order order = new Order();
                order.lines = new HashMap<>();
                order.reference = orderReference;
                order.policy = getValue(data, 3);
                order.scheduledDate = getValue(data, 4);
                order.status = getValue(data, 2);
                order.lines.put(orderLine.id, orderLine);
                orders.allOrders.put(orderReference, order);


                if(order.status.equals("Ready")){
                    orders.readyOrders.put(orderReference, order);

                }
            }
            if(orders.readyOrders.containsKey(orderReference)){
                if(withMissingBike(orderLine)){
                    Order readyMissingBike =  orders.readyOrders.get(orderReference);

                    String policy = readyMissingBike.policy;
                    if(orders.readyOrdersMissingBikeByPolicy.containsKey(policy)){
                        orders.readyOrdersMissingBikeByPolicy.get(policy).add(readyMissingBike);
                    } else {
                        Set<Order> newOrdersWithPolicy = new HashSet<>();
                        newOrdersWithPolicy.add(readyMissingBike);
                        orders.readyOrdersMissingBikeByPolicy.put(policy, newOrdersWithPolicy);
                    }

                }
            }
        }
        csvReader.close();
    }

    private static boolean withMissingBike(OrderLine orderLine) {
        return orderLine.isBike && (orderLine.reservedQuantity < (orderLine.quantity + orderLine.doneQuantity));
    }

    public static String getValue(List<String> data, int position){
        return data.get(position).replaceAll("\"", "");
    }

    public static boolean isBike(String category){
        return category.contains("Bikes");
    }
}
