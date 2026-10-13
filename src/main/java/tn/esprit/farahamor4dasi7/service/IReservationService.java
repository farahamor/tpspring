package tn.esprit.farahamor4dasi7.service;

import java.util.List;
import tn.esprit.farahamor4dasi7.domaine.Reservation;

public interface IReservationService {

    Reservation create(Reservation reservation);

    Reservation findById(Long id);

    List<Reservation> findAll();

    Reservation update(Long id, Reservation reservation);

    void deleteById(Long id);
}