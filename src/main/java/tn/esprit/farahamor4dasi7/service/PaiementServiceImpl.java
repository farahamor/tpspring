package tn.esprit.farahamor4dasi7.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.farahamor4dasi7.domaine.Paiement;
import tn.esprit.farahamor4dasi7.exception.ResourceNotFoundException;
import tn.esprit.farahamor4dasi7.repository.IPaiementRepository;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;

    @Override
    public Paiement findById(Long id) {
        return paiementRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
    }

    @Override
    public List<Paiement> findAll() {
        return paiementRepository.findAll();
    }
}