package domain;

import com.sun.istack.internal.NotNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

public class User {
    protected  Long id;
    @NotEmpty
    @Min(6)
    @Max(16)
    protected  String account_id;
    @NotEmpty
    @Min(6)
    @Max(16)
    protected  String password;
    @NotEmpty
    @Min(2)
    @Max(12)
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
