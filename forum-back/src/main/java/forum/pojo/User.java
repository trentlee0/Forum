package forum.pojo;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Timestamp;

@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class User {

  private long uId;
  private String uName;
  private String uPass;
  private byte uGrade;
  private byte uGender;
  private Timestamp uBirthday;
  private String avatar;
  private boolean available;


  public long getUId() {
    return uId;
  }

  public void setUId(long uId) {
    this.uId = uId;
  }


  public String getUName() {
    return uName;
  }

  public void setUName(String uName) {
    this.uName = uName;
  }


  public String getUPass() {
    return uPass;
  }

  public void setUPass(String uPass) {
    this.uPass = uPass;
  }


  public long getUGrade() {
    return uGrade;
  }

  public void setUGrade(byte uGrade) {
    this.uGrade = uGrade;
  }


  public long getUGender() {
    return uGender;
  }

  public void setUGender(byte uGender) {
    this.uGender = uGender;
  }


  public Timestamp getUBirthday() {
    return uBirthday;
  }

  public void setUBirthday(Timestamp uBirthday) {
    this.uBirthday = uBirthday;
  }


  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }


  public boolean getAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

}
