package tn.ilyeszouaoui.withoutauth.service;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import tn.ilyeszouaoui.withoutauth.adapter.FoodRestClient;
import tn.ilyeszouaoui.withoutauth.dataobject.FoodRestClientDTO;
import tn.ilyeszouaoui.withoutauth.dataobject.CarDTO;
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
public class CarService {

    @Inject
    @RestClient
    FoodRestClient foodRestClient;

    @Inject
    CarMapper carMapper;

    @Inject
    CarRepository carRepository;

    public FoodRestClientDTO findFoodRestClientByName(String name){
        return foodRestClient.getFoodName(name);
    }

    public void createCar(String name, double price) {
        CarEntity carEntity = new CarEntity(name, price);
        carRepository.persist(carEntity);
    }

    public void updateCar(int id, String name, double price) {
        CarEntity carEntity = carRepository.findById(id);
        carEntity.setName(name);
        carEntity.setPrice(price);
        carEntity.persist();
    }

    public void deleteCar(int id) {
        carRepository.deleteById(id);
    }


    public List<CarDTO> findCars() {
        return carRepository.listAll()
                .stream()
                .map(carEntity -> carMapper.carEntityToCarDTO(carEntity))
                .collect(Collectors.toList());
    }

    public CarEntity findCarById(int id) {
        return carRepository.findById(id);
    }


}
