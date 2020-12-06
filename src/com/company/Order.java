package com.company;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Order {
    public String reference;
    public String status;
    public String policy;
    public String scheduledDate;
    public Map<String ,OrderLine> lines;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        boolean equals =  Objects.equals(reference, order.reference) &&
                Objects.equals(status, order.status) &&
                Objects.equals(policy, order.policy) &&
                Objects.equals(scheduledDate, order.scheduledDate) &&
                lines.size()==order.lines.size();
        if(!equals){
            return false;
        }
        for(OrderLine ol : lines.values()){
            if(!order.lines.get(ol.id).equals(ol)){
                return false;
            }

        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference, status, policy, scheduledDate, lines);
    }
}
