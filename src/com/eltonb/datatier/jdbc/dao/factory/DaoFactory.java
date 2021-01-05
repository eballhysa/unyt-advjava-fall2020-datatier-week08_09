package com.eltonb.datatier.jdbc.dao.factory;

import com.eltonb.datatier.jdbc.dao.impl.DepartmentDaoInMemoryImpl;
import com.eltonb.datatier.jdbc.dao.impl.DepartmentDaoJdbcImpl;
import com.eltonb.datatier.jdbc.dao.impl.InstructorDaoInMemoryImpl;
import com.eltonb.datatier.jdbc.dao.impl.InstructorDaoJdbcImpl;
import com.eltonb.datatier.jdbc.dao.interfaces.DepartmentDao;
import com.eltonb.datatier.jdbc.dao.interfaces.InstructorDao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DaoFactory {

    static {
        String model = "memory";
        try(InputStream is = new FileInputStream("resources/application.properties")) {
            Properties props = new Properties();
            props.load(is);
            model = props.getProperty("db.factory");
        } catch (Exception e) {
            e.printStackTrace();
        }
        factoryModel = model;
    }

    private static final String factoryModel;
    private static DepartmentDaoInMemoryImpl memDepartmentDao;
    private static InstructorDaoInMemoryImpl memInstructorDao;

    public static DepartmentDao departmentFactory() {
        return departmentFactory(null);
    }

    public static DepartmentDao departmentFactory(Connection conn) {
        if ("jdbc".equals(factoryModel)) {
            DepartmentDaoJdbcImpl dao = new DepartmentDaoJdbcImpl();
            dao.setConnection(conn);
            return dao;
        } else {
            if (memDepartmentDao == null) {
                memDepartmentDao = new DepartmentDaoInMemoryImpl();
            }
            return memDepartmentDao;
        }
    }

    public static InstructorDao instructorFactory() {
        return instructorFactory(null);
    }

    public static InstructorDao instructorFactory(Connection conn) {
        if ("jdbc".equals(factoryModel)) {
            InstructorDaoJdbcImpl dao = new InstructorDaoJdbcImpl();
            dao.setConnection(conn);
            return dao;
        } else {
            if (memInstructorDao == null) {
                memInstructorDao = new InstructorDaoInMemoryImpl();
            }
            return memInstructorDao;
        }
    }


    private static DepartmentDao initDepartmentDao(Connection conn) {
        if ("memory".equals(factoryModel)) {
            return new DepartmentDaoInMemoryImpl();
        } else {
            DepartmentDaoJdbcImpl dao = new DepartmentDaoJdbcImpl();
            dao.setConnection(conn);
            return dao;
        }
    }

    private static InstructorDao initInstructorDao(Connection conn) {
        if ("memory".equals(factoryModel)) {
            return new InstructorDaoInMemoryImpl();
        } else {
            InstructorDaoJdbcImpl dao = new InstructorDaoJdbcImpl();
            dao.setConnection(conn);
            return dao;
        }
    }

}
