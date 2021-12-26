package forum.pojo;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class Apply {

    private long aId;
    private long uId;
    private String text;
    private boolean dispose;


    public long getAId() {
        return aId;
    }

    public void setAId(long aId) {
        this.aId = aId;
    }


    public long getUId() {
        return uId;
    }

    public void setUId(long uId) {
        this.uId = uId;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public boolean getDispose() {
        return dispose;
    }

    public void setDispose(boolean dispose) {
        this.dispose = dispose;
    }

}
