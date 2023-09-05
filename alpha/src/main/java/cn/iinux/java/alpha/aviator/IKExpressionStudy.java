package cn.iinux.java.alpha.aviator;

import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.Variable;

import java.util.ArrayList;
import java.util.List;

public class IKExpressionStudy {
    public static void main(String[] args) throws Exception {
        String expression = " x == '50701' ||  x == '50401' ||  x == '51001' ||  x == '50301' ||  x == '50204' ||  x == '50205' ||  x == '50305'";
        expression = expression.replaceAll("'", "\"");

//        String expression = "x>=60.5&&x<=80";

        List<Variable> variables = new ArrayList<Variable>();
        Variable variable = Variable.createVariable("x", "50701");
        variables.add(variable);

        Boolean result = (Boolean) ExpressionEvaluator.evaluate(expression, variables);
        System.out.println(result);
    }
}
