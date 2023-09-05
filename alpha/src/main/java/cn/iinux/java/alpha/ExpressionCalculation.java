package cn.iinux.java.alpha;

import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class ExpressionCalculation
{

    public ExpressionCalculation()
    {
        elementStack = new Stack();
        newElementStack = new Stack();
    }

    public void calcAndPrintResult(String expression)
    {
        byteArray = expression.getBytes();
        expressionLength = expression.length();

        //拆分表达式，并且所有元素入栈
        if(!initStack()) {
            System.out.println("Input ERROR");
        } else {
            calc();
            System.out.println(lastResult);
        }
    }

    /**
     *
     * @return boolean false on fail
     */
    public boolean initStack()
    {
        boolean numFlag = false;
        int num = 0, i;

        //仿vim的退出风格
        if(byteArray[0] == ':' && byteArray[1] == 'q') {
            System.exit(0);
        }

        for(i = 0; i < expressionLength; i++) {
            byte aChar = byteArray[i];
            if(aChar >= '0' && aChar <= '9') {
                num = (num * 10 + aChar) - 48;
                numFlag = true;
                continue;
            }
            if(aChar == '+' || aChar == '-' || aChar == '*' || aChar == '/' || aChar == '^' || aChar == '('
                    || aChar == '!' || aChar == '=' || aChar == ')') {
                if(numFlag) {
                    elementStack.push(num);
                    numFlag = false;
                    num = 0;
                }
                elementStack.push((int)aChar);
            } else {
                return false;
            }
        }

        if(byteArray[--i] != '=') {
            elementStack.push(num);
            elementStack.push((int)('='));
        }
        return true;
    }

    public void calc()
    {
        do {
            if(elementStack.empty()) {
                break;
            }
            int element = (Integer) elementStack.pop();
            newElementStack.push(element);
            if(element == '(') {
                elementStack.push(newElementStack.pop());
                calcNewElement();
            }
            if(elementStack.empty()) {
                calcNewElement();
            }
        } while(true);
    }

    public void calcNewElement()
    {
        int index = 0, aElement;
        int numbers[] = { 0, 0, 0 };
        Stack stack = new Stack();
        do {
            aElement = (Integer) newElementStack.pop();
            if(index < 3) {
                if(aElement == ')') {
                    newElementStack.push(numbers[0]);
                    elementStack.pop();
                    return;
                }
                if(aElement == '=') {
                    lastResult = numbers[0];
                    return;
                }
                numbers[index++] = aElement;
            } else if(priority(aElement) > priority(numbers[1])) {
                stack.push(numbers[0]);
                stack.push(numbers[1]);
                numbers[0] = numbers[2];
                numbers[1] = aElement;
                index = 2;
            } else {
                int result = 0;
                int operator = numbers[1];
                if(operator == '+') {
                    result = numbers[0] + numbers[2];
                }
                if(operator == '-') {
                    result = numbers[0] - numbers[2];
                }
                if(operator == '*') {
                    result = numbers[0] * numbers[2];
                }
                if(operator == '/') {
                    try {
                        result = numbers[0] / numbers[2];
                    } catch(ArithmeticException arithmeticException) {
                        arithmeticException.printStackTrace();
                    }
                }
                if(operator == '^') {
                    result = (int)Math.pow(numbers[0], numbers[2]);
                }
                if(aElement == '=' && stack.empty()) {
                    lastResult = result;
                    return;
                }
                newElementStack.push(aElement);
                newElementStack.push(result);
                for(; !stack.empty(); newElementStack.push(stack.pop()));
                index = 0;
            }
        } while(true);
    }

    public int priority(int element)
    {
        if(element == '+' || element == '-') {
            return 2;
        }
        if(element == '*' || element == '/') {
            return 3;
        }
        if(element == '^') {
            return 4;
        }
        if (element != ')' && element != '=') {
            return 0;
        }
        return 1;
    }

    public static void main(String args[])
    {
        Runtime runtime = Runtime.getRuntime();
        Scanner scanner = new Scanner(System.in);
        ExpressionCalculation expressionCalculation = new ExpressionCalculation();
        do {
            try {
                runtime.exec("cls.exe");
            } catch(IOException ioException) { }
            System.out.println("Input expression:");
            String inputString = scanner.next();
            expressionCalculation.calcAndPrintResult(inputString);
        } while(true);
    }

    public byte byteArray[];
    public int expressionLength;
    public Stack elementStack;
    public Stack newElementStack;
    public int lastResult;
}