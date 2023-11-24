package Controller;

import com.mysql.cj.conf.StringProperty;

public class appointmentSum {
    private String type;
    private String month;
    private int total;

    public appointmentSum(String type, String month, int total) {
        this.type = type;
        this.month = month;
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String Month) {
        this.month = month;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
