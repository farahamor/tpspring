package tn.esprit.farahamor4dasi7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.farahamor4dasi7.domaine.Client;

public interface IClientRepository extends JpaRepository<Client, Long> {
}