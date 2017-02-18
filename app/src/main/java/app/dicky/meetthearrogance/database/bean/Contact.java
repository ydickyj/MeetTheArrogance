package app.dicky.meetthearrogance.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 创建者:   dicky
 * 创建时间:  2016/10/21 17:47
 * 描述：    TODO
 */
@Entity
public class Contact {
    public static final String TAG = "Contact";
    @Id(autoincrement = true)
    private Long id;

    private String username;

    private String headPortraitPath;

    @Generated(hash = 2011378201)
    public Contact(Long id, String username, String headPortraitPath) {
        this.id = id;
        this.username = username;
        this.headPortraitPath = headPortraitPath;
    }

    @Generated(hash = 672515148)
    public Contact() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadPortraitPath() {
        return this.headPortraitPath;
    }

    public void setHeadPortraitPath(String headPortraitPath) {
        this.headPortraitPath = headPortraitPath;
    }
}
