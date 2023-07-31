package day21.exam;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ExamDTO {
    private Timestamp date;
    public ExamDTO() {
    }

    public ExamDTO(Timestamp date) {
        this.date = date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Timestamp getDate() {
        return date;
    }
    public String getFormedDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(date);
    }
}
