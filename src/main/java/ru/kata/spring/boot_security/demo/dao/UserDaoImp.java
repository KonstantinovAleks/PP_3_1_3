package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @PersistenceContext
   private EntityManager entityManager;

   @Override
   @Transactional(readOnly = true)
   @Query("Select u from User u left join fetch u.roles where u.email=:email")
   public User findByEmail(String email) {
      TypedQuery<User> query = entityManager.createQuery(
              "SELECT u FROM User u WHERE u.email = :email", User.class);
      query.setParameter("email", email);
      try {
         return query.getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   @Override
   @Transactional(readOnly = true)
   @Query("Select u from User u left join fetch u.roles")
   public List<User> getUsersList() {
      return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
   }

   @Override
   @Transactional(readOnly = true)
   public User getUserById(Long id) {
      return entityManager.find(User.class, id);
   }

   @Override
   @Transactional
   public void addUser(User user) {
      entityManager.persist(user);
   }

   @Override
   @Transactional
   public void updateUser(User user) {
      entityManager.merge(user);
   }

   @Override
   @Transactional
   public void deleteUser(User user) {
      entityManager.remove(user);
   }
}
