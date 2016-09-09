package rest.constants;

/**
 * Created by jtduan on 2016/9/6.
 */
public enum VIP {
    VIP0(1.0),
    VIP1(0.9),
    VIP2(0.8),
    VIP3(0.5),
    SUPERVIP(0.0);

    private double discount;
    VIP(Double discount){
        this.discount = discount;
    }

    public double getDiscount(){
        return discount;
    }
}
