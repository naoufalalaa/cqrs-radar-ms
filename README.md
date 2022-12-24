# cqrs-radar-ms
CQRS Event Sourcing microservice based application
## Architechure technique
![Architecture - Page 4](https://user-images.githubusercontent.com/61352259/209450330-d4366541-5b7d-468b-8c08-2a68fe3ca3fc.png)

## Architecture des fichiers
![Screenshot 2022-12-24 at 21 32 43](https://user-images.githubusercontent.com/61352259/209450344-f6323d5b-149e-4e43-baa9-b61fe0c1ab65.png)

- **Common-api** : contient les éléments en commun (Events/Commands/DTos/Queries...)
- **immatriculation-service** : Microservice de matruculation et vehicule - proprietaire.
- **radar-service** : Microservice de radar.
- **infraction-service** : Microservice d'établissement des infractions et amendes.


## Testing

![Screenshot 2022-12-24 at 21 27 57](https://user-images.githubusercontent.com/61352259/209450307-0bd0b2fb-ee78-4e85-b7ec-dd55c1f4b216.png)

![Screenshot 2022-12-24 at 21 28 41](https://user-images.githubusercontent.com/61352259/209450303-b82ddc32-f0bc-4a85-b32b-d890d0190e83.png)
![Screenshot 2022-12-24 at 21 28 27](https://user-images.githubusercontent.com/61352259/209450305-3ce8a46c-b6c2-4b3c-8ae5-b86070b48a87.png)
