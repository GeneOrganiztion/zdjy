package po;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
@Table(name = "orders")
public class Orders {
    @Id
    private Integer orderId;

    private String ordNum;

    private Integer ordState;

    private Integer ordPrice;

    private String ordPay;

    private Integer ordUser;

    private String userName;

    private String userPhone;

    private String userAddress;

    private String userPostal;

    private String userCourierNum;

    private String userCourierName;

    private String prepayId;

    private String timestamp;

    private String noncestr;

    private String finalsign;

    private Boolean isdelete;

    private Date createTime;

    private Date lastModifiedTime;
    @Transient
    private List<MapOrderProductPo> mapOrderProductList;

    public String getOrdNum() {
        return ordNum;
    }

    public Integer getId() {
        return orderId;
    }

    public void setId(Integer orderId) {
        this.orderId = orderId;
    }
    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return noncestr;
    }

    public void setNonceStr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getFinalsign() {
        return finalsign;
    }

    public void setFinalsign(String finalsign) {
        this.finalsign = finalsign;
    }
    public void setOrdNum(String ordNum) {
        this.ordNum = ordNum == null ? null : ordNum.trim();
    }

    public Integer getOrdState() {
        return ordState;
    }

    public void setOrdState(Integer ordState) {
        this.ordState = ordState;
    }
    public Integer getOrdPrice() {
        return ordPrice;
    }

    public void setOrdPrice(Integer ordPrice) {
        this.ordPrice = ordPrice;
    }

    public String getOrdPay() {
        return ordPay;
    }

    public void setOrdPay(String ordPay) {
        this.ordPay = ordPay == null ? null : ordPay.trim();
    }

    public Integer getOrdUser() {
        return ordUser;
    }

    public void setOrdUser(Integer ordUser) {
        this.ordUser = ordUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress == null ? null : userAddress.trim();
    }

    public String getUserPostal() {
        return userPostal;
    }

    public void setUserPostal(String userPostal) {
        this.userPostal = userPostal == null ? null : userPostal.trim();
    }

    public Boolean getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public List<MapOrderProductPo> getMapOrderProductList() {
        return mapOrderProductList;
    }

    public void setMapOrderProductList(List<MapOrderProductPo> mapOrderProductList) {
        this.mapOrderProductList = mapOrderProductList;
    }

    public String getUserCourierNum() {
        return userCourierNum;
    }

    public void setUserCourierNum(String userCourierNum) {
        this.userCourierNum = userCourierNum;
    }

    public String getUserCourierName() {
        return userCourierName;
    }

    public void setUserCourierName(String userCourierName) {
        this.userCourierName = userCourierName;
    }

}