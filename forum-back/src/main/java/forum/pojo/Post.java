package forum.pojo;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@ToString
@AllArgsConstructor
public class Post {

  private long postId;
  private long uId;
  private long plateId;
  private String postName;
  private Timestamp createDatetime;
  private boolean recycle;
  private String content;
  private Timestamp updateDatetime;


  public long getPostId() {
    return postId;
  }

  public void setPostId(long postId) {
    this.postId = postId;
  }

  public long getUId() {
    return uId;
  }

  public void setUId(long uId) {
    this.uId = uId;
  }

  public long getPlateId() {
    return plateId;
  }

  public void setPlateId(long plateId) {
    this.plateId = plateId;
  }

  public String getPostName() {
    return postName;
  }

  public void setPostName(String postName) {
    this.postName = postName;
  }


  public Timestamp getCreateDatetime() {
    return createDatetime;
  }

  public void setCreateDatetime(Timestamp createDatetime) {
    this.createDatetime = createDatetime;
  }


  public boolean getRecycle() {
    return recycle;
  }

  public void setRecycle(boolean recycle) {
    this.recycle = recycle;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public Timestamp getUpdateDatetime() {
    return updateDatetime;
  }

  public void setUpdateDatetime(Timestamp updateDatetime) {
    this.updateDatetime = updateDatetime;
  }

}
