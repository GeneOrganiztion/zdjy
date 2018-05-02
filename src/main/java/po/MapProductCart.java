package po;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
@Table(name ="map_product_cart")
public class MapProductCart extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5736229507219715609L;

	@Id
    private Integer mapProductCartId;
	
    private Integer proId;

    private Integer cartId;

    private Integer proSize;

    private Integer proColor;

    private Integer proCount;

    private Boolean isdelete;

    private Date createTime;

    private Date lastModifiedTime;
    
    @Transient
    private Product product;
    
    public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

    public Integer getMapProductCartId() {
		return mapProductCartId;
	}

	public void setMapProductCartId(Integer mapProductCartId) {
		this.mapProductCartId = mapProductCartId;
	}

	public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getProSize() {
        return proSize;
    }

    public void setProSize(Integer proSize) {
        this.proSize = proSize;
    }

    public Integer getProColor() {
        return proColor;
    }

    public void setProColor(Integer proColor) {
        this.proColor = proColor;
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
}