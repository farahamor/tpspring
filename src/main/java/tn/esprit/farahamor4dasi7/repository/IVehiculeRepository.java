package tn.esprit.farahamor4dasi7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.farahamor4dasi7.domaine.Vehicule;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {
}