package po;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
@Table(name ="address")
public class Address extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5682785350279338037L;

	@Id
	private Integer addressId;
	
    private String addrProvince;

    private String addrCity;

    private String addrRegion;

    private String addrDetial;

    private Integer userId;

    private Boolean isdelete;

    private Date createTime;

    private Date lastModifiedTime;

    
    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
    
    public String getAddrProvince() {
        return addrProvince;
    }

    public void setAddrProvince(String addrProvince) {
        this.addrProvince = addrProvince == null ? null : addrProvince.trim();
    }

    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity == null ? null : addrCity.trim();
    }

    public String getAddrRegion() {
        return addrRegion;
    }

    public void setAddrRegion(String addrRegion) {
        this.addrRegion = addrRegion == null ? null : addrRegion.trim();
    }

    public String getAddrDetial() {
        return addrDetial;
    }

    public void setAddrDetial(String addrDetial) {
        this.addrDetial = addrDetial == null ? null : addrDetial.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
}