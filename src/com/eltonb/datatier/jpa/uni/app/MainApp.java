package com.eltonb.datatier.jpa.uni.app;

import com.eltonb.datatier.jpa.uni.entities.Department;
import com.eltonb.datatier.jpa.uni.entities.Instructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MainApp {

    public static void main(String[] args) {
        MainApp app = new MainApp();
        app.go();
    }

    private void go() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("uni-PU");
        EntityManager em = emf.createEntityManager();
        Department csd = em.find(Department.class, "CS");
        System.out.println(csd);
        System.out.println("----students------");
        csd.getStudents().forEach(System.out::println);
        String query = "select i from Instructor i where i.name = :name and i.surname = :surname";
        Instructor nutty =
                em.createQuery(query, Instructor.class)
                        .setParameter("name", "Nutty")
                        .setParameter("surname", "Professor")
                        .getSingleResult();
        Department xx = em.find(Department.class, "XX");
        em.getTransaction().begin();
        em.remove(xx);
        em.getTransaction().commit();
        System.out.println("----instructors------");
        csd.getInstructors().forEach(System.out::println);
    }
}
