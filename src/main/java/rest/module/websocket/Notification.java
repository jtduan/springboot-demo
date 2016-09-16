package rest.module.websocket;

import rest.entity.Order;

public class Notification {

    private String message;

    public Notification (String content) {
        this.message = content;
    }

    public Notification (String type,Order o) {
        this.message=new StringBuilder().append(type)
                .append(":")
                .append(o.getId())
                .append(":")
                .append(o.getDish_name())
                .append(":")
                .append(o.getDish_price())
                .append(":")
                .append(o.getState()).toString();
    }

    public String getContent() {
        return message;
    }
}
