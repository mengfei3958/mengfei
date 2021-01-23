package com.example.demo.shiro;


import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.bean.Permission;
import com.example.demo.bean.Role;
import com.example.demo.bean.User;
import com.example.demo.dao.PermissionDao;
import com.example.demo.dao.UserDao;

/**
 * Created by cdyoue on 2016/10/21.
 */

public class ShiroRealm extends AuthorizingRealm {
    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userService;
    @Autowired
    private PermissionDao permissionService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("doGetAuthorizationInfo+"+principalCollection.toString());
//        User user = userService.getByUserName((String) principalCollection.getPrimaryPrincipal());
        User user = new User();
        user.setUserName("admin");
        user.setPassword("111111");


        //把principals放session中 key=userId value=principals
//        SecurityUtils.getSubject().getSession().setAttribute(String.valueOf(user.getId()),SecurityUtils.getSubject().getPrincipals());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //赋予角色
//        for(Role userRole:user.getRoles()){
//            info.addRole(userRole.getName());
//        }
//        //赋予权限
//        for(Permission permission:permissionService.getByUserId(user.getId())){
////            if(StringUtils.isNotBlank(permission.getPermCode()))
//                info.addStringPermission(permission.getName());
//        }
        info.addRole("admin");
        info.addRole("root");
        info.addStringPermission("system:user:exportMeng");

        //设置登录次数、时间
//        userService.updateUserLogin(user);
        return info;
    }

    //登陆验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("doGetAuthenticationInfo +"  + authenticationToken.toString());

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName=token.getUsername();
        logger.info(userName+"密码----"+token.getPassword());
        String password = "";
        if (token.getPassword() != null)
        {
            password = new String(token.getPassword());
        }

        User user = userService.selectUserByLoginName(userName);
        validate(user, password);
        if (user != null) {
//            byte[] salt = Encodes.decodeHex(user.getSalt());
//            ShiroUser shiroUser=new ShiroUser(user.getId(), user.getLoginName(), user.getName());
            //设置用户session
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("user", user);
            System.out.println("getName-------" + getName());
            return new SimpleAuthenticationInfo(userName,user.getPassword(),ByteSource.Util.bytes(password + user.getSalt()), getName());
//            return new SimpleAuthenticationInfo(user,password, getName());
        } else {
            return null;
        }
//        return null;
    }
    
    public boolean validate(User user, String password){
    	return user.getPassword().equals(encryptPassword(user.getLoginName(), password, user.getSalt()));
    }
    
    public String encryptPassword(String username, String password, String salt)
    {
        return new Md5Hash(username + password + salt).toHex().toString();
    }

    public static void main(String[] args) {
    	String password = "111111";
    	String salt = "8c5414";
		System.out.println(new Md5Hash(password, salt).toString().toUpperCase());
		System.out.println(new Md5Hash(password + salt, salt).toString().toUpperCase());
		System.out.println(new Md5Hash(password, password + salt).toString().toUpperCase());
		System.out.println(new Md5Hash(password + salt).toString().toString());
		String hash = new SimpleHash("MD5", password, password + salt, 1).toHex().toString();
		System.out.println(hash);
//		System.out.println(new Md5Hash("root" + "111111" + "8c5414").toHex().toString());
//		System.out.println(new Md5Hash("admin" + "admin123" + "111111").toHex().toString());
	}
}
