package ws.io.entity.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;
import ws.dto.UserDTO;
import ws.io.entity.UserEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {
    Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }


    public UserDTO getUserByUserName(String userName) {
        UserDTO user = null;

        CriteriaBuilder cb = session.getCriteriaBuilder();

        // create criteria against a particular persistence class
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);
        // Query roots always reference entity
        Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
        criteria.where(cb.equal(profileRoot.get("email"), userName));

        // fetch singe result
        Query<UserEntity> query = session.createQuery(criteria);
        List<UserEntity> resultList = query.getResultList();

        if (resultList != null && resultList.size() > 0) {
            UserEntity userEntity = resultList.get(0);
            user = new UserDTO();
            BeanUtils.copyProperties(userEntity, user);
        }

        return user;
    }

    public UserDTO getUser(String id) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

        Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
        criteria.select(profileRoot);
        criteria.where(cb.equal(profileRoot.get("userId"), id));

        UserEntity userEntity = session.createQuery(criteria).getSingleResult();
        UserDTO user = new UserDTO();
        BeanUtils.copyProperties(userEntity, user);

        return user;
    }

    public UserDTO saveUser(UserDTO user) {
        UserDTO returnValue = new UserDTO();
        UserEntity userEntity = new UserEntity();

        BeanUtils.copyProperties(user, userEntity);

        session.beginTransaction();
        session.save(userEntity);
        session.getTransaction().commit();

        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    public void updateUserProfile(UserDTO userProfile) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userProfile, userEntity);

        session.beginTransaction();
        session.update(userEntity);
        session.getTransaction().commit();
    }

    public List<UserDTO> getUsers(int start, int limit) {
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);
        Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
        List<UserEntity> searchResults = session.createQuery(criteria)
                .setFirstResult(start).
                setMaxResults(limit)
                .getResultList();


        List<UserDTO> users = new ArrayList<>();

        searchResults.forEach(entity -> {
            UserDTO user = new UserDTO();
            BeanUtils.copyProperties(entity, user);
            users.add(user);
        });

        return users;
    }

    public void deleteUserProfile(UserDTO storedUserDetails) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(storedUserDetails, userEntity);

        session.beginTransaction();
        session.delete(userEntity);
        session.getTransaction().commit();
    }
}
