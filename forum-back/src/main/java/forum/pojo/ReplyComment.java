package forum.pojo;


import lombok.AllArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@ToString
@AllArgsConstructor
public class ReplyComment {

  private long rcId;
  private String text;
  private long uId;
  private long cId;
  private Timestamp publishDatetime;


  public long getRcId() {
    return rcId;
  }

  public void setRcId(long rcId) {
    this.rcId = rcId;
  }


  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }


  public long getUId() {
    return uId;
  }

  public void setUId(long uId) {
    this.uId = uId;
  }


  public long getCId() {
    return cId;
  }

  public void setCId(long cId) {
    this.cId = cId;
  }


  public Timestamp getPublishDatetime() {
    return publishDatetime;
  }

  public void setPublishDatetime(Timestamp publishDatetime) {
    this.publishDatetime = publishDatetime;
  }

}
