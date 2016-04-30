package fang.weighttracker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Fang2 on 2016/4/22.
 */
public class Weight {
    private UUID mId;
    private String mWeight;
    private Date mDate;
    private User user = User.getUser();


    public Weight(){
        mId = UUID.randomUUID();
        mDate = new Date();
        mWeight = user.getCurrent_weight();
    }

    public UUID getId() {
        return mId;
    }

    public String getWeight() {
        return mWeight;
    }

    public void setWeight(String weight) {
        mWeight = weight;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
