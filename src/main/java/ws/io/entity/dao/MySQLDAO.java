package ws.io.entity.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ws.utils.HibernateUtils;

public class MySQLDAO implements DAO {
    Session session;
    private ThemesDAO themesDAO;
    private UsersDAO usersDAO;

    @Override
    public void openConnection() {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        session = sessionFactory.openSession();
        themesDAO = new ThemesDAO(session);
        usersDAO = new UsersDAO(session);
    }

    @Override
    public void closeConnection() {
        if (session != null) {
            session.close();
        }
    }

    @Override
    public UsersDAO getUsersDAO() {
        return usersDAO;
    }

    @Override
    public ThemesDAO getThemesDAO() {
        return themesDAO;
    }
}
