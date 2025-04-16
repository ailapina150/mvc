package com.resume.hibernate;

import com.resume.EmployeeRepository;
import com.resume.model.Education;
import com.resume.model.Employee;
import com.resume.model.Project;
import com.resume.model.Task;
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
public class HibernateEmployeeRepositoryImpl implements EmployeeRepository<Employee> {

    SessionFactory sessionFactory;
    HibernateEmployeeRepositoryImpl() {
        try {
            sessionFactory = new Configuration()
                    .configure()
                    .addAnnotatedClass(Employee.class)
                    .addAnnotatedClass(Project.class)
                    .addAnnotatedClass(Task.class)
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
            List<Employee> list = session.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
            return list;
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

            Employee employee = session.get(Employee.class, id);
            if(employee != null) {
                Hibernate.initialize(employee.getProjects());//for LazyInitializationException
                Hibernate.initialize(employee.getEducations());
                for(int i = 0; i < employee.getProjects().size(); i++){
                    Hibernate.initialize(employee.getProjects().get(i).getTasks());
                }
            }
            return Optional.of(employee);
        }
    }

    @Override
    public Employee save (Employee employee) {;
        try (Session session = sessionFactory.openSession()) {
            System.out.println("*******save********");
            session.beginTransaction();
            Employee oldEmployee = session.get(Employee.class, employee.getId());
            if(oldEmployee != null) {
                session.delete(oldEmployee);
            }
            session.save(employee);
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
                session.delete(employee);
            }
            session.getTransaction().commit();
       }
    }
}
