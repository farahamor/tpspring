package tn.esprit.farahamor4dasi7.service;

import java.util.List;
import tn.esprit.farahamor4dasi7.domaine.Paiement;

public interface IPaiementService {

    Paiement findById(Long id);

    List<Paiement> findAll();
}