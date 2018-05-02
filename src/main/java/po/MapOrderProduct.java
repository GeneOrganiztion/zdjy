package po;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
@Table(name ="map_order_product")
public class MapOrderProduct extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5317539730539311916L;

	@Id
	private Integer mapOrderProductId;
	
    private Integer ordId;

    private Integer proId;

    private Integer proClassifyId;

    private String proName;

    private Integer proPrice;

    private Integer proCount;

    private Boolean isdelete;

    private Date createTime;

    private Date lastModifiedTime;
    
    @Transient
    private Product product;
    
    public Integer getId() {
        return mapOrderProductId;
    }

    public void setId(Integer mapOrderProductId) {
        this.mapOrderProductId = mapOrderProductId;
    }

    public Integer getOrdId() {
        return ordId;
    }

    public void setOrdId(Integer ordId) {
        this.ordId = ordId;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public Integer getProClassifyId() {
        return proClassifyId;
    }

    public void setProClassifyId(Integer proClassifyId) {
        this.proClassifyId = proClassifyId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName == null ? null : proName.trim();
    }

    public Integer getProPrice() {
        return proPrice;
    }

    public void setProPrice(Integer proPrice) {
        this.proPrice = proPrice;
    }

    public Integer getProCount() {
        return proCount;
    }

    public void setProCount(Integer proCount) {
        this.proCount = proCount;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
    
    
}