package helper;

import lombok.*;

@Getter
public class OrderClo {
    int id;
    String name;
    String type;
    Boolean available;


    public OrderClo(int id, String name, String type, Boolean available) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.available = available;
    }
}
