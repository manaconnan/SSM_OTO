package com.mazexiang;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

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
    
}
