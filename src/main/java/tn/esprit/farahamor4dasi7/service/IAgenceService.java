package tn.esprit.farahamor4dasi7.service;

import java.util.List;
import tn.esprit.farahamor4dasi7.domaine.Agence;

public interface IAgenceService {

    Agence create(Agence agence);

    Agence findById(Long id);

    List<Agence> findAll();

    Agence update(Long id, Agence agence);

    void deleteById(Long id);
}