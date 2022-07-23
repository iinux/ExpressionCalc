package com.mycompany.helloworld.powermockstudy;

import org.springframework.beans.factory.annotation.Autowired;

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

    public List<Integer> getFlowByPrjId0(int prjId, int status) {
        // do something
        return null;
    }
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