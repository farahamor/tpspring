package tn.esprit.farahamor4dasi7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.farahamor4dasi7.domaine.Reservation;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
}