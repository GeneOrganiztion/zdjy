package po;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "image")
public class Image extends BaseEntity {

  /**
   *
   */
  private static final long serialVersionUID = -8481743384527912965L;

  @Id
  private Integer imageId;

  private Integer assessId;

  private Integer proId;

  private String url;


  public Integer getImageId() {
    return imageId;
  }

  public void setImageId(Integer imageId) {
    this.imageId = imageId;
  }

  public Integer getAssessId() {
    return assessId;
  }

  public void setAssessId(Integer assessId) {
    this.assessId = assessId;
  }

  public Integer getProId() {
    return proId;
  }

  public void setProId(Integer proId) {
    this.proId = proId;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url == null ? null : url.trim();
  }
}