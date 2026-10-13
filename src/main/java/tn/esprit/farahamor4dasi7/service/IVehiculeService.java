package tn.esprit.farahamor4dasi7.service;

import java.util.List;
import tn.esprit.farahamor4dasi7.domaine.Vehicule;

public interface IVehiculeService {

    Vehicule create(Vehicule vehicule);

    Vehicule findById(Long id);

    List<Vehicule> findAll();

    Vehicule update(Long id, Vehicule vehicule);

    void deleteById(Long id);
}