package com.mycompany.helloworld.basic;

import javax.swing.*;
import java.util.Date;

public class Dmb {
    public void run(String content, String title, boolean useHtml) {
        try {
            if (title == null || title.equals("")) {
                title = "xargin";
            }
            if (content == null || content.equals("")) {
                content = "If you don't keep moving, you'll quickly fall behind.";
            }

            Date now = new Date();
            System.out.println(now + " show " + title + " " + content);

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // JOptionPane.showMessageDialog(null, "我是普通提示框！╮(╯▽╰)╭");
            // JOptionPane.showMessageDialog(null, "我是警告提示框！╮(╯▽╰)╭", "标题",JOptionPane.WARNING_MESSAGE);
            // JOptionPane.showMessageDialog(null, "我是错误提示框！╮(╯▽╰)╭", "标题",JOptionPane.ERROR_MESSAGE);
            JOptionPane.getRootFrame().setAlwaysOnTop(true);
            if (useHtml) {
                JOptionPane.showMessageDialog(null,
                        "<html><body><p style='width: 600px;'>" + content + "</p></body></html>",
                        title,
                        JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, content, title, JOptionPane.PLAIN_MESSAGE);
            }
            // int n = JOptionPane.showConfirmDialog(null, "你会了吗?", "标题",JOptionPane.YES_NO_OPTION); //返回值为0或1

            /*
            Object[] options ={ "必须是", "当然是" };  //自定义按钮上的文字
            int m = JOptionPane.showOptionDialog(null, "钓鱼岛是中国的吗？", "标题",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

             */

            /*
            Object[] obj2 ={ "路人甲", "路人乙", "路人丙" };
            String s = (String) JOptionPane.showInputDialog(null,"请选择你的身份:\n", "身份", JOptionPane.PLAIN_MESSAGE, new ImageIcon("icon.png"), obj2, "足球");

             */

            // JOptionPane.showInputDialog(null,"请输入：\n","title",JOptionPane.PLAIN_MESSAGE);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
