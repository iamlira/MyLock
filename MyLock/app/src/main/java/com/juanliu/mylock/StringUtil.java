package com.juanliu.mylock;

/**
 * Created by Administrator on 2015/7/15.
 */
public class StringUtil {
    public static String removerepeat(String password){
        char temp=' ';
        String result="";
        for(int i=0;i<password.length();i++){
            if(!(temp==password.charAt(i))){
                temp=password.charAt(i);
                result+=temp;
            }
        }
        return result;
    }
}
