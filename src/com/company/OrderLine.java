package com.company;

import java.util.Objects;

public class OrderLine {
    public String id;
    public boolean isBike;
    public int quantity;
    public int reservedQuantity;
    public int doneQuantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLine orderLine = (OrderLine) o;
        return isBike == orderLine.isBike &&
                quantity == orderLine.quantity &&
                reservedQuantity == orderLine.reservedQuantity &&
                doneQuantity == orderLine.doneQuantity &&
                Objects.equals(id, orderLine.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isBike, quantity, reservedQuantity, doneQuantity);
    }
}
