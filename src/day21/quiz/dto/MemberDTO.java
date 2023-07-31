package day21.quiz.dto;

public class MemberDTO {
    private String id;
    private String pw;
    private String name;

    public MemberDTO() {
    }

    public MemberDTO(String id, String pw, String name) {
        this.id = id;
        this.pw = pw;
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }
}
