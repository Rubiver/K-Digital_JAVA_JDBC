package day21.quiz.dto;

import java.sql.Timestamp;

public class GuestbookDTO {
    private int seq;
    private String writer;
    private String message;
    private Timestamp write_date;

    public GuestbookDTO() {
    }

    public GuestbookDTO(int seq, String writer, String message, Timestamp write_date) {
        this.seq = seq;
        this.writer = writer;
        this.message = message;
        this.write_date = write_date;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setWrite_date(Timestamp write_date) {
        this.write_date = write_date;
    }

    public int getSeq() {
        return seq;
    }

    public String getWriter() {
        return writer;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getWrite_date() {
        return write_date;
    }
}
