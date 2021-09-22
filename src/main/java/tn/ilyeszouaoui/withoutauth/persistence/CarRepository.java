package tn.ilyeszouaoui.withoutauth.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import tn.ilyeszouaoui.withoutauth.persistence.entity.CarEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class CarRepository implements PanacheRepositoryBase<CarEntity,Integer> {

    public CarEntity findOneByName(String name){
        return find("name", name).firstResult();
    }

}