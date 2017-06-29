package cn.ucai.jkbd.bean;

import java.util.List;

/**
 * Created by clawpo on 2017/6/29.
 */

public class Result {

    /**
     * error_code : 0
     * reason : ok
     * result : [{"id":3,"question":"这个标志是何含义？","answer":"1","item1":"车道数变少","item2":"合流处","item3":"应急车道","item4":"向左变道","explains":"三车道变成二车道故车道数变少，选A。","url":"http://images.juheapi.com/jztk/c1c2subject1/3.jpg"}]
     */

    private int error_code;
    private String reason;

    private List<Exam> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Exam> getResult() {
        return result;
    }

    public void setResult(List<Exam> result) {
        this.result = result;
    }
}
