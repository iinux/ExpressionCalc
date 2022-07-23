package com.mycompany.helloworld.powermockstudy;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    @Autowired
    private UserService userService;

    public boolean addUser(UserDto userDto) {
        int added = userService.addUser(userDto);
        if (added <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean delUser(int id) {
        try {
            userService.delUser(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void saveUser(UserDto userDto) {
        userService.saveUser(userDto);
    }

    public int countUser() {
        UserDto ud = new UserDto();

        int count = 0;

        if (ud.getId() > 0) {
            count += 1;
        }
        return count;
    }

    public boolean modUser(UserDto userDto) {
        int moded = userService.modUser(userDto);
        return verifyMod(moded);
    }

    private boolean verifyMod(int moded) {
        if (moded <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public List<Integer> getFlowByPrjId(int prjId, Integer ...status) {
        // do something
        return null;
    }

    public List<Integer> getFlowByPrjId0(Integer prjId, Integer status) {
        // do something
        return null;
    }

    private static class InnerClassA {
        String name;

        private InnerClassA(String name) {
            this.name = name;
        }

        public void run() {
            System.out.println("InnerClassA run");
        }
    }

    public void getAllChildren(int parentNodeId, List<Node> allChildren) {
        List<Node> children = getChildren(parentNodeId);
        // some other logic
        allChildren.addAll(children);
    }

    public List<Node> getChildren(int nodeId) {
        List<Node> children = new ArrayList<>();
        return children;
    }
}

class Node {

}

interface UserService {
    int addUser(UserDto userDto);

    int delUser(int id) throws Exception;

    int modUser(UserDto userDto);

    void saveUser(UserDto userDto);
}

class UserDto {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

class FileHelper {
    public static String getName(String name) {
        return "A_" + name;
    }

    public static void voidMethod(String name) {
        System.out.println(name);
    }
}

class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        System.out.println("DispatcherServlet init");
    }

    @Override
    public void destroy() {
        System.out.println("DispatcherServlet destroy");
    }
}