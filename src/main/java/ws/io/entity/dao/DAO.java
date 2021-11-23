package ws.io.entity.dao;

public interface DAO {
    void openConnection();
    void closeConnection();
    ThemesDAO getThemesDAO();
    UsersDAO getUsersDAO();
}
