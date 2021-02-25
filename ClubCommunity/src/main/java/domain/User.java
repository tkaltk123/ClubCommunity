package domain;

import com.sun.istack.internal.NotNull;

import javax.validation.constraints.*;
import java.sql.Timestamp;

public class User {
    protected  Long id;
    //계정은 6~16글자 영문 숫자만 허용
    @Size(min=6,max=16)
    @Pattern(regexp = "[a-zA-Z0-9]+")
    protected  String account_id;
    //비밀번호는 6~16글자 영문, 숫자, 11개의 특수문자만 허용
    @Size(min=6,max=16)
    @Pattern(regexp = "[a-zA-Z0-9~!@#$%^&*()]+")
    protected  String password;
    //닉네임은 2~12글자 영문, 숫자, 한글, 언더바만 허용 모음이나 자음은 불가
    @Size(min=2,max=12)
    @Pattern(regexp = "[a-zA-Z0-9가-힣_]+")
    protected  String nick_name;
    protected Timestamp created_at;
    protected Timestamp updated_at;
    protected boolean deleted;

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
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

}
