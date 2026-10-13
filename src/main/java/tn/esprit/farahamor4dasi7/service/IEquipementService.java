package tn.esprit.farahamor4dasi7.service;

import java.util.List;
import tn.esprit.farahamor4dasi7.domaine.Equipement;

public interface IEquipementService {

    Equipement create(Equipement equipement);

    Equipement findById(Long id);

    List<Equipement> findAll();

    Equipement update(Long id, Equipement equipement);

    void deleteById(Long id);
}