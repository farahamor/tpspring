package tn.esprit.farahamor4dasi7.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.farahamor4dasi7.domaine.Employe;
import tn.esprit.farahamor4dasi7.exception.ResourceNotFoundException;
import tn.esprit.farahamor4dasi7.repository.IEmployeRepository;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;

    private void valider(Employe employe) {
        if (employe.getNom() == null || employe.getNom().isBlank()) {
            throw new IllegalArgumentException(
                "Le nom de l'employé est obligatoire"
            );
        }
    }

    @Override
    public Employe create(Employe employe) {
        if (employe.getIdEmploye() != null) {
            throw new IllegalArgumentException(
                "Un nouvel employé ne doit pas avoir d'identifiant"
            );
        }

        valider(employe);
        return employeRepository.save(employe);
    }

    @Override
    public Employe findById(Long id) {
        return employeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employé", id));
    }

    @Override
    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    @Override
    public Employe update(Long id, Employe employe) {
        valider(employe);
        Employe existant = findById(id);

        existant.setNom(employe.getNom());
        existant.setPrenom(employe.getPrenom());
        existant.setRole(employe.getRole());

        return employeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employé", id);
        }

        employeRepository.deleteById(id);
    }
}