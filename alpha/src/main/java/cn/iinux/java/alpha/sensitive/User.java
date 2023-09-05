package cn.iinux.java.alpha.sensitive;

import com.github.houbb.sensitive.annotation.Sensitive;
import com.github.houbb.sensitive.core.api.strategory.*;
import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    @Sensitive(strategy = StrategyChineseName.class)
    private String username;

    @Sensitive(strategy = StrategyCardId.class)
    private String idCard;

    // @Sensitive(strategy = StrategyPassword.class)
    @Sensitive(condition = ConditionFooPassword.class, strategy = StrategyPassword.class)
    private String password;

    @Sensitive(strategy = StrategyEmail.class)
    private String email;

    @Sensitive(strategy = StrategyPhone.class)
    private String phone;

    //Getter & Setter
    //toString()
}
