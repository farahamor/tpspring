package tn.esprit.farahamor4dasi7.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.farahamor4dasi7.domaine.Reservation;
import tn.esprit.farahamor4dasi7.exception.ResourceNotFoundException;
import tn.esprit.farahamor4dasi7.repository.IReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;

    private void valider(Reservation reservation) {
        if (reservation.getDateDebut() != null
                && reservation.getDateFin() != null
                && reservation.getDateFin().isBefore(reservation.getDateDebut())) {
            throw new IllegalArgumentException(
                "La date de fin ne peut pas être antérieure à la date de début"
            );
        }
    }

    @Override
    public Reservation create(Reservation reservation) {
        if (reservation.getIdReservation() != null) {
            throw new IllegalArgumentException(
                "Une nouvelle réservation ne doit pas avoir d'identifiant"
            );
        }

        valider(reservation);
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Réservation", id));
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation update(Long id, Reservation reservation) {
        valider(reservation);

        Reservation existante = findById(id);
        existante.setDateDebut(reservation.getDateDebut());
        existante.setDateFin(reservation.getDateFin());
        existante.setStatut(reservation.getStatut());
        existante.setClient(reservation.getClient());
        existante.setVehicule(reservation.getVehicule());

        return reservationRepository.save(existante);
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Réservation", id);
        }

        reservationRepository.deleteById(id);
    }
}