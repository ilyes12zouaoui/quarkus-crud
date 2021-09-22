package tn.ilyeszouaoui.withjwtauth.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import tn.ilyeszouaoui.withjwtauth.persistence.entity.ShopEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class ShopRepository implements PanacheRepositoryBase<ShopEntity,Integer> {

    public ShopEntity findOneByName(String name){
        return find("name", name).firstResult();
    }

    public List<ShopEntity> findAllByType(String type){
        return list("type", type);
    }

}