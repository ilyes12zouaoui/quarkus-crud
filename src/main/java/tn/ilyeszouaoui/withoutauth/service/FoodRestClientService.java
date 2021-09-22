package tn.ilyeszouaoui.withoutauth.service;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import tn.ilyeszouaoui.withoutauth.adapter.FoodRestClient;
import tn.ilyeszouaoui.withoutauth.dataobject.CarDTO;
import tn.ilyeszouaoui.withoutauth.dataobject.FoodRestClientDTO;
import tn.ilyeszouaoui.withoutauth.dataobject.mapper.CarMapper;
import tn.ilyeszouaoui.withoutauth.persistence.CarRepository;
import tn.ilyeszouaoui.withoutauth.persistence.entity.CarEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class FoodRestClientService {

    @Inject
    @RestClient
    FoodRestClient foodRestClient;

    public FoodRestClientDTO findFoodUsingRestClientByName(String name) {
        return foodRestClient.getFoodName(name);
    }

}
