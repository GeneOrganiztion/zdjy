package domain;

/**
 * @author yakungao
 * @date 2017/12/9
 **/
public enum OrdersStatus {

    WAITPAY(1, 1),
    WAITSEND(2, 2),
    WAITRECEIVE(3, 3),
    COMPLETE(4, 4);

    private Integer value;
    private Integer index;

    private OrdersStatus(Integer val, Integer idx){
        this.value = val;
        this.index = idx;
    }

    public Integer getValue() {
        return value;
    }

    public Integer getIndex() {
        return index;
    }
}
