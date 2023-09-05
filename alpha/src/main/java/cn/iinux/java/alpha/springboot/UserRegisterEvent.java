package cn.iinux.java.alpha.springboot;

import cn.iinux.java.alpha.sensitive.User;
import org.springframework.context.ApplicationEvent;

public class UserRegisterEvent extends ApplicationEvent {
    private User user;

    /**
     *
     * @param source 发生事件的对象
     * @param user 注册用户对象
     */
    public UserRegisterEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
