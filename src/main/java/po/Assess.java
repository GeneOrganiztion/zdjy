package po;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "assess")
public class Assess extends BaseEntity {

  /**
   *
   */
  private static final long serialVersionUID = 3943632921429279126L;

  @Id
  private Integer assessId;

  private Integer proId;

  private Integer userId;

  private String userName;

  private String assessContent;

  private Boolean isdelete;

  private Date lastModifiedTime;

  private Date createTime;

  @Transient
  private Product product;

  @Transient
  public List<Image> imageList;

  public Integer getAssessId() {
    return assessId;
  }

  public void setAssessId(Integer assessId) {
    this.assessId = assessId;
  }

  public List<Image> getImageList() {
    return imageList;
  }

  public void setImageList(List<Image> imageList) {
    this.imageList = imageList;
  }

  public Integer getProId() {
    return proId;
  }

  public void setProId(Integer proId) {
    this.proId = proId;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName == null ? null : userName.trim();
  }

  public String getAssessContent() {
    return assessContent;
  }

  public void setAssessContent(String assessContent) {
    this.assessContent = assessContent == null ? null : assessContent.trim();
  }

  public Boolean getIsdelete() {
    return isdelete;
  }

  public void setIsdelete(Boolean isdelete) {
    this.isdelete = isdelete;
  }

  public Date getLastModifiedTime() {
    return lastModifiedTime;
  }

  public void setLastModifiedTime(Date lastModifiedTime) {
    this.lastModifiedTime = lastModifiedTime;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

}