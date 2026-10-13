# Atelier 4 — IoC, injection de dépendances et couche Service

## A. Comparaison des modes d'injection

| Critère | Injection par constructeur | Injection par attribut |
|---|---|---|
| Champ final possible | Oui | Non |
| Dépendance visible | Dans le constructeur | Non, elle est cachée dans la classe |
| Objet utilisable hors Spring | Oui, si la dépendance est fournie | Risque de dépendance null |
| Bonne pratique retenue | Oui | Non |
| SonarQube for IDE | Pas d'alerte liée à l'injection par attribut | Vérifier la règle S6813 pendant l'expérience |

Dans notre projet, l'injection par constructeur est mise en œuvre avec
@RequiredArgsConstructor et un attribut final. Lombok génère le constructeur,
que Spring utilise pour fournir le repository.

## B. Services et dépendances

| Service | Dépendance injectée | Injection | Justification |
|---|---|---|---|
| ContratServiceImpl | IContratRepository | Constructeur | Gérer les contrats, vérifier leur existence et refuser un montant négatif |
| AgenceServiceImpl | IAgenceRepository | Constructeur | Gérer les agences et imposer un nom obligatoire |
| EmployeServiceImpl | IEmployeRepository | Constructeur | Gérer les employés et imposer un nom obligatoire |
| VehiculeServiceImpl | IVehiculeRepository | Constructeur | Gérer les véhicules et refuser un tarif journalier négatif |
| EquipementServiceImpl | IEquipementRepository | Constructeur | Gérer les équipements et imposer un libellé |
| ClientServiceImpl | IClientRepository | Constructeur | Gérer les clients et imposer le nom et l'e-mail |
| ReservationServiceImpl | IReservationRepository | Constructeur | Gérer les réservations et vérifier la cohérence des dates |
| PaiementServiceImpl | IPaiementRepository | Constructeur | Lecture seule : rechercher un paiement ou lister les paiements |
| MaintenanceServiceImpl | IMaintenanceRepository | Constructeur | Gérer les maintenances et vérifier la cohérence des dates |

Tous les services sont annotés avec @Service et utilisent un champ final.
Les méthodes de recherche par identifiant lèvent ResourceNotFoundException
lorsque la ressource n'existe pas.

## C. Diagnostic des erreurs du conteneur Spring

### Message 1 — NoSuchBeanDefinitionException

Cause possible : aucun bean du type demandé n'a été trouvé, par exemple
IContratService, parce que l'implémentation manque de @Service ou se trouve
hors du périmètre de scan.

Correction : vérifier l'annotation @Service, le package et le scan des composants.

### Message 2 — NoUniqueBeanDefinitionException

Cause : plusieurs beans correspondent au même type demandé.

Corrections possibles :
- Placer @Primary sur l'implémentation choisie par défaut.
- Utiliser @Qualifier sur le paramètre du constructeur pour sélectionner
  explicitement le bean souhaité.

### Message 3 — UnsatisfiedDependencyException avec une dépendance circulaire

Cause possible : deux services dépendent mutuellement l'un de l'autre,
par exemple ClientServiceImpl et ReservationServiceImpl.

Correction : revoir la conception pour supprimer la dépendance circulaire
et faire porter les responsabilités métier au bon service.

## D. Analyse SonarQube for IDE

Cette section doit être finalisée à partir de l'analyse réellement exécutée
dans IntelliJ IDEA. Ne pas déclarer une règle détectée sans vérifier le panneau
SonarQube for IDE.

| Anomalie à examiner | Vérification et correction |
|---|---|
| Injection par attribut avec @Autowired | Expérience temporaire demandée : relever la règle affichée, puis restaurer l'injection par constructeur |
| Exception générique RuntimeException | Dans findById, vérifier la règle S112 lors de l'expérience puis restaurer ResourceNotFoundException |
| Autre anomalie réelle du projet | Relever le nom de la règle, son explication et la correction effectuée dans SonarQube for IDE |

Résultats de l'analyse SonarQube for IDE à compléter après exécution dans IntelliJ.

## E. Questions de compréhension

### 1. Où se trouve le new de ContratServiceImpl et qui l'exécute ?

Nous n'écrivons pas explicitement new ContratServiceImpl dans le code métier.
Le conteneur Spring détecte la classe grâce à @Service, crée son instance et
fournit le repository via le constructeur.

### 2. Pourquoi le contrôleur dépend-il de IContratService ?

L'interface réduit le couplage entre le contrôleur et l'implémentation.
L'implémentation peut évoluer sans imposer de modification du contrôleur.

### 3. Pourquoi un service singleton doit-il rester sans état ?

Le même bean est partagé entre les appels. Un attribut mutable contenant,
par exemple, le contrat courant pourrait être écrasé par une autre requête.
Les données propres à un appel doivent rester dans les variables locales.

### 4. Pourquoi update charge-t-il l'entité existante avant save ?

Cela permet de modifier uniquement les attributs prévus, sans remplacer
l'identifiant ni écraser involontairement des associations ou une collection
de paiements liée à orphanRemoval.

### 5. Quelle différence entre les annotations Spring ?

- @Component déclare un bean générique détecté par le scan.
- @Bean déclare explicitement un bean à partir d'une méthode de configuration.
- @Primary indique le bean privilégié lorsqu'il existe plusieurs candidats.
- @Qualifier sélectionne explicitement un candidat.
- IPaiementService reste en lecture seule : la création, la modification et la
  suppression des paiements passent par le service Contrat, conformément au modèle métier.

---

Note : l'analyse SonarQube for IDE doit être exécutée dans IntelliJ afin de
compléter la section D avec les règles effectivement observées.