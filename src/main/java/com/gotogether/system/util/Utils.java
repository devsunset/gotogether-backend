package com.gotogether.system.util;

import com.gotogether.entity.Role;
import com.gotogether.system.constants.Constants;
import com.gotogether.system.enums.PostType;
import com.gotogether.system.enums.SkillLevelType;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Utils {

    public static boolean isAdmin(Set<Role> role) {
        for (Role element : role) {
            if (element.getRolename().toString().equals(Constants.ROLE_ADMIN)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUser(Set<Role> role) {
        for (Role element : role) {
            if (element.getRolename().toString().equals(Constants.ROLE_USER)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isGuest(Set<Role> role) {
        for (Role element : role) {
            if (element.getRolename().toString().equals(Constants.ROLE_GUEST)) {
                return true;
            }
        }
        return false;
    }

    public static HashMap<String, String> getRoleHashMap(Set<Role> role) {
        HashMap<String, String> map = new HashMap<String, String>();
        for (Role element : role) {
            map.put(element.getRolename().toString(), element.getRolename().toString());
        }
        return map;
    }

    public static ArrayList<String> getRolesArray(Set<Role> role) {
        ArrayList<String> list = new ArrayList<String>();
        for (Role element : role) {
            list.add(element.getRolename().toString());
        }
        return list;
    }

    public static boolean isValidPostType(String category) {
        boolean result = false;
        for (PostType type : PostType.values()) {
            if (type.getName().equals(category)) {
                result = true;
            }
        }
        return result;
    }

    public static boolean isValidSkillLevelType(String itemLevel) {
        boolean result = false;
        for (SkillLevelType type : SkillLevelType.values()) {
            if (type.getName().equals(itemLevel)) {
                result = true;
            }
        }
        return result;
    }


    public static String getPrintStackTrace(Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }
}
