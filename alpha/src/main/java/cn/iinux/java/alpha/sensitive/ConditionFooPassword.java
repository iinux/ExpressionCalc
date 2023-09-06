package cn.iinux.java.alpha.sensitive;

import com.github.houbb.sensitive.api.ICondition;
import com.github.houbb.sensitive.api.IContext;

import java.lang.reflect.Field;

public class ConditionFooPassword implements ICondition {
    @Override
    public boolean valid(IContext context) {
        try {
            Field field = context.getCurrentField();
            final Object currentObj = context.getCurrentObject();
            final String password = (String) field.get(currentObj);
            return !password.equals("123456");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
