package helper;

import lombok.*;

@Getter
public class OrderClo {
    String id;
    int bookId;
    String customerName;
    String createdBy;
    int quantity;
    int timestamp;

    public OrderClo(String id, int bookId, String customerName, String createdBy, int quantity, int timestamp) {
        this.id = id;
        this.bookId = bookId;
        this.customerName = customerName;
        this.createdBy = createdBy;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }
}
