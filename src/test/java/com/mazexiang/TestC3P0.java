package com.mazexiang;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Stack;
import java.util.Vector;

public class TestC3P0 {
    @Test
    public void testc3p0() throws SQLException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
            dataSource.setJdbcUrl("jdbc:mysql:///oto?useSSL=false&useUnicode=true&characterEncoding=utf8");
            dataSource.setUser("root");
            dataSource.setPassword("1234");
        } catch (PropertyVetoException e) {

            e.printStackTrace();
        }
        System.out.println(dataSource.getConnection());

    }

    public static void main(String[] args) {
        Stack<Character> ops = new Stack<>();
        Stack<Integer> vals = new Stack<>();
        String str = new String("(1+(((2*3)+(3*4))+6))");

        char c;
        for(int i = 0;i<str.length();i++){
            c = str.charAt(i);
            if(c=='(')     ;
            else if (c=='+'||c=='-'||c=='*'||c=='/')  ops.push(c);
            else if (c==')'){
                char op = ops.pop();
                int v = vals.pop();
                if(op=='+') v += vals.pop();
                else if(op=='-') v -= vals.pop();
                else if(op=='*') v*= vals.pop();
                else v/=vals.pop();
                vals.push(v);
            }else {
                String s = c+"";
                vals.push(Integer.parseInt(s));
            }
        }
        System.out.println(vals.pop()+"");

    }
    
}
