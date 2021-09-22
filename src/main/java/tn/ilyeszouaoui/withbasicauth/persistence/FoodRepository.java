package tn.ilyeszouaoui.withbasicauth.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import tn.ilyeszouaoui.withbasicauth.persistence.entity.FoodEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class FoodRepository implements PanacheRepositoryBase<FoodEntity,Integer> {

    public FoodEntity findOneByName(String name){
        return find("name", name).firstResult();
    }

}