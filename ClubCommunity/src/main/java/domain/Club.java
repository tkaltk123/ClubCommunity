package domain;

import com.sun.istack.internal.NotNull;

import javax.validation.constraints.*;
import java.sql.Timestamp;

public class Club {
    protected  Long id;
    protected  Long owner_id;
    //소모임 이름은 2~12 글자의 알파벳,한글, 숫자, 언더바
    @Size(min=2,max=12)
    @Pattern(regexp = "[a-zA-Z0-9가-힣_]+")
    protected  String club_name;
    //소모임 소개는 100글자 이하의 텍스트
    @Size(max=100)
    protected  String introduce;
    protected  int member_num;
    protected Timestamp created_at;
    protected Timestamp updated_at;
    protected boolean deleted;
    //join 을 위한 필드
    protected String owner_name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Long owner_id) {
        this.owner_id = owner_id;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getMember_num() {
        return member_num;
    }

    public void setMember_num(int member_num) {
        this.member_num = member_num;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }
}
