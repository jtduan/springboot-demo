package rest.entity;

public interface UserType {
    /**
     * 充值
     * @param money
     */
    public void recharge(double money);

    /**
     * 消费
     * @param money
     */
    public void cost(double money);

    /**
     * 取消消费
     */
    public void cancel(double money);
}
