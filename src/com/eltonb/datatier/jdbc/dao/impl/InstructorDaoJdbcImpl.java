package com.eltonb.datatier.jdbc.dao.impl;

import com.eltonb.datatier.jdbc.dao.utils.JdbcUtils;
import com.eltonb.datatier.jdbc.dao.interfaces.InstructorDao;
import com.eltonb.datatier.jdbc.dao.model.Department;
import com.eltonb.datatier.jdbc.dao.model.Instructor;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InstructorDaoJdbcImpl implements InstructorDao {

    private Connection connection;

    public InstructorDaoJdbcImpl() {
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Department getDepartment(Instructor instructor) {
        DepartmentDaoJdbcImpl departmentDao = new DepartmentDaoJdbcImpl();
        departmentDao.setConnection(this.connection);
        return departmentDao.find(instructor.getDepartmentCode());
    }

    @Override
    public Instructor find(Long id) {
        String sql = "select * from instructors i where i.id = ?";
        try (PreparedStatement stat = connection.prepareStatement(sql)) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next())
                    return JdbcUtils.newInstructor(rs);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Instructor> findAll() {
        String sql = "select * from instructors i";
        try (PreparedStatement stat = connection.prepareStatement(sql)) {
            try (ResultSet rs = stat.executeQuery()) {
                List<Instructor> instructors = new ArrayList<>();
                while (rs.next())
                    instructors.add(JdbcUtils.newInstructor(rs));
                return instructors;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Instructor instructor) {
        String sql = "insert into instructors(id, name, surname, department_code) values (?, ?, ?, ?)";
        try (CallableStatement stat = connection.prepareCall(sql)) {
            stat.setLong(1, instructor.getId());
            stat.setString(2, instructor.getName());
            stat.setString(3, instructor.getSurname());
            stat.setString(4, instructor.getDepartmentCode());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Instructor instructor) {
        String sql = "update instructors set name=?, surname=?, department_code=? where id = ?";
        try (CallableStatement stat = connection.prepareCall(sql)) {
            stat.setString(1, instructor.getName());
            stat.setString(2, instructor.getSurname());
            stat.setString(3, instructor.getDepartmentCode());
            stat.setLong(4, instructor.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Instructor instructor) {
        String sql = "delete from instructors where id = ?";
        try (CallableStatement stat = connection.prepareCall(sql)) {
            stat.setLong(1, instructor.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
