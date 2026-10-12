# Atelier 2 — Associations JPA, Cascade et Fetch

## 1. Contrat et Paiement

- Relation : `Contrat` possède plusieurs `Paiement`.
- Propriétaire : `Paiement`, avec `@ManyToOne`.
- Fetch : `LAZY`, pour charger les paiements uniquement lorsqu'ils sont nécessaires.
- Cascade : `CascadeType.ALL` du contrat vers ses paiements.
- `orphanRemoval = true` : un paiement retiré de la collection du contrat est supprimé lors de la synchronisation avec la base.

## 2. Agence et Véhicule

- Relation : une agence possède plusieurs véhicules.
- Propriétaire : `Vehicule`, avec `@ManyToOne` et la clé étrangère `id_agence`.
- Fetch : `LAZY`.
- Cascade : aucune cascade.
- Justification : supprimer une agence ne doit pas entraîner automatiquement la suppression des véhicules.

## 3. Agence et Employé

- Relation : une agence peut être associée à plusieurs employés.
- Propriétaire : `Employe`, avec `@ManyToOne` et la clé étrangère `id_agence`.
- Fetch : `LAZY`.
- Cascade : aucune cascade.
- Justification : le cycle de vie des employés est géré indépendamment de celui de l'agence.

## 4. Véhicule et Équipement

- Relation : `ManyToMany`.
- Propriétaire : `Vehicule`, avec la table de jointure `vehicule_equipement`.
- Côté inverse : `Equipement`, avec `mappedBy = "equipements"`.
- Fetch : `LAZY`.
- Cascade : aucune cascade.
- Justification : un équipement peut être partagé entre plusieurs véhicules et son cycle de vie est indépendant.

## 5. Client et Réservation

- Relation : un client peut posséder plusieurs réservations.
- Propriétaire : `Reservation`, avec `@ManyToOne` et la clé étrangère `id_client`.
- Fetch : `LAZY`.
- Cascade : `CascadeType.PERSIST` de `Client` vers ses réservations.
- Justification : permettre la persistance des nouvelles réservations avec le client sans propager automatiquement toutes les opérations.

## 6. Réservation et Véhicule

- Relation : plusieurs réservations peuvent concerner un même véhicule.
- Propriétaire : `Reservation`, avec `@ManyToOne` et la clé étrangère `id_vehicule`.
- Fetch : `LAZY`.
- Cascade : aucune cascade.
- Justification : le véhicule conserve son cycle de vie indépendamment de celui d'une réservation.

## 7. Réservation et Contrat

- Relation : `OneToOne`.
- Propriétaire : `Contrat`, avec la clé étrangère unique `id_reservation`.
- Côté inverse : `Reservation`, avec `mappedBy = "reservation"`.
- Fetch : `LAZY`.
- Cascade : `CascadeType.ALL` du côté de `Reservation` vers `Contrat`.
- Justification : les opérations sur une réservation sont propagées à son contrat associé, conformément à la configuration retenue.

## 8. Véhicule et Maintenance

- Relation : un véhicule peut avoir plusieurs maintenances.
- Propriétaire : `Maintenance`, avec `@ManyToOne` et la clé étrangère `id_vehicule`.
- Fetch : `LAZY`.
- Cascade : `CascadeType.PERSIST` de `Vehicule` vers ses maintenances.
- Justification : enregistrer un véhicule peut entraîner la persistance de ses nouvelles maintenances, sans propager automatiquement les autres opérations.

## Conclusion

Les stratégies de chargement `LAZY` limitent le chargement automatique des associations. Les cascades sont définies selon le cycle de vie attendu des entités, tandis que l'absence de cascade préserve l'indépendance des entités associées.