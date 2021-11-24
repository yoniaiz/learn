package ws.io.entity.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;
import ws.dto.ThemeDTO;
import ws.exceptions.NotFoundRecordException;
import ws.io.entity.ThemeEntity;
import ws.ui.model.response.ErrorMessages;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ThemesDAO {
    Session session;

    public ThemesDAO(Session session) {
        this.session = session;
    }

    public List<ThemeDTO> getThemes(int start, int limit) {
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<ThemeEntity> criteria = cb.createQuery(ThemeEntity.class);
        Root<ThemeEntity> themeRoot = criteria.from(ThemeEntity.class);
        List<ThemeEntity> searchResults = session.createQuery(criteria)
                .setFirstResult(start).
                setMaxResults(limit)
                .getResultList();


        List<ThemeDTO> themes = new ArrayList<>();
        for (ThemeEntity themeEntity : searchResults) {
            ThemeDTO user = new ThemeDTO();
            BeanUtils.copyProperties(themeEntity, user);
            themes.add(user);
        }

        return themes;
    }

    public ThemeDTO getThemeByColors(ThemeDTO themeRequest) {
        ThemeDTO theme = null;

        CriteriaBuilder cb = session.getCriteriaBuilder();

        // create criteria against a particular persistence class
        CriteriaQuery<ThemeEntity> criteria = cb.createQuery(ThemeEntity.class);

        Root<ThemeEntity> themeRoot = criteria.from(ThemeEntity.class);
        Predicate[] predicates = new Predicate[4];
        predicates[0] = cb.equal(themeRoot.get("primaryColor"), themeRequest.getPrimaryColor());
        predicates[1] = cb.equal(themeRoot.get("secondaryColor"), themeRequest.getSecondaryColor());
        predicates[2] = cb.equal(themeRoot.get("fontColor"), themeRequest.getFontColor());
        predicates[3] = cb.equal(themeRoot.get("backgroundColor"), themeRequest.getBackgroundColor());

        criteria.where(predicates);

        // fetch singe result
        Query<ThemeEntity> query = session.createQuery(criteria);
        List<ThemeEntity> resultList = query.getResultList();

        if (resultList != null && resultList.size() > 0) {
            ThemeEntity userEntity = resultList.get(0);
            theme = new ThemeDTO();
            BeanUtils.copyProperties(userEntity, theme);
        }

        return theme;
    }


    public ThemeDTO saveTheme(ThemeDTO themeRequest) {
        ThemeDTO returnValue = new ThemeDTO();
        ThemeEntity themeEntity = new ThemeEntity();

        BeanUtils.copyProperties(themeRequest, themeEntity);

        session.beginTransaction();
        session.save(themeEntity);
        session.getTransaction().commit();

        BeanUtils.copyProperties(themeEntity, returnValue);
        return returnValue;
    }

    public ThemeEntity getTheme(long themeId) {
        try {
            return this.session.get(ThemeEntity.class, themeId);
        } catch (Exception e) {
            throw new NotFoundRecordException(ErrorMessages.RECORD_NOT_FOUND.name());
        }
    }
}
