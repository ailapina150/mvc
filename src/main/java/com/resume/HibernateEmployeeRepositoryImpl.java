package com.resume;

import com.resume.Port;
import com.resume.model.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class HibernateEmployeeRepositoryImpl implements Port<Employee> {

    SessionFactory sessionFactory;

    HibernateEmployeeRepositoryImpl() {

        try {
            sessionFactory = new Configuration()
                    .configure()
                    .addAnnotatedClass(Employee.class)
                    .addAnnotatedClass(Project.class)
                    .addAnnotatedClass(Education.class)
                    //  .addPackage("com.resume.model")
                    .buildSessionFactory();
        }catch (Exception e){
           e.printStackTrace();
        }
    }

    @Override
    public List<Employee> findAll() {
//        try (Session session = sessionFactory.openSession()) {
//            return session.createQuery("FROM Employee", Employee.class).list();
//        }
        try (Session session = sessionFactory.openSession()) {
            System.out.println("*******findAll********");
            return session
                    .createQuery("SELECT e FROM Employee e", Employee.class)
                    .getResultList();
        }
    }

    @Override
    public Optional<Employee> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            System.out.println("*******findById********");
//            Employee employee = session.createQuery("""
//               SELECT e FROM Employee e
//               LEFT JOIN FETCH e.projects p
//               WHERE e.id = :employeeId
//               """, Employee.class)
//                    .setParameter("employeeId", id) // Parameterized query
//                    .setMaxResults(1) // Limit to at most one result
//                    .uniqueResult();

            Employee employee = session.find(Employee.class, id);
            if(employee != null) {
                Hibernate.initialize(employee.getProjects());//for LazyInitializationException
                Hibernate.initialize(employee.getEducations());
                Hibernate.initialize(employee.getSkill());
                for(int i = 0; i < employee.getProjects().size(); i++){
                    Hibernate.initialize(employee.getProjects().get(i).getTasks());
                }
            }
            return Optional.of(employee);
        }
    }

    @Override
    public Employee save (Employee employee) {
        try (Session session = sessionFactory.openSession()) {
            if(employee == null) return employee;
            System.out.println("*******save********");
            System.out.println(employee);
            session.beginTransaction();
            if(employee.getId() !=null && session.find(Employee.class, employee.getId()) != null) {
                System.out.println("*******merge********");
                session.merge(employee);
                deleteEducations(session, employee.getId());
                deleteProjects(session, employee.getId());
            }else {
                System.out.println("*******persist********");
                session.persist(employee);
            }
            session.getTransaction().commit();
            return employee;
        }
    }

    @Override
    public void deleteById(Long id) {
     try (Session session = sessionFactory.openSession()) {
         System.out.println("*******delete********");
            session.beginTransaction();
            Employee employee = session.get(Employee.class, id);
            if (employee != null){
                session.remove(employee);
            }
            session.getTransaction().commit();
       }
    }

    public void deleteEducations(Session session, Long id){
        List<Education> educations = session
                .createQuery("SELECT e FROM Education e", Education.class)
                .getResultList()
                .stream()
                .filter(e->e.getEmployee().getId().equals(id))
                .toList();
        Employee employee = session.get(Employee.class, id);
        if(employee != null) {
            var educationIds = employee.getEducations().stream().map(Education::getId).toList();
            for (Education education : educations) {
                if (!educationIds.contains(education.getId())) {
                    session.remove(education);
                }
            }
        }
    }

    public void deleteProjects(Session session, Long id){
        List<Project> projects = session
                .createQuery("SELECT p FROM Project p", Project.class)
                .getResultList()
                .stream()
                .filter(e->e.getEmployee().getId().equals(id))
                .toList();
        Employee employee = session.get(Employee.class, id);
        if(employee != null) {
            var projectIds = employee.getProjects().stream().map(Project::getId).toList();
            for (Project project : projects) {
                if (!projectIds.contains(project.getId())) {
                    session.remove(project);
                }
            }
        }
    }

}
