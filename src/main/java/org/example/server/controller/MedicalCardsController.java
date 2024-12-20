package org.example.server.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.Constants;
import org.example.entity.MedicalCard;
import org.example.repository.CardDoesNotExistException;
import org.example.repository.PolyclinicRepository;
import org.example.server.DependenciesContainer;

import java.util.List;

@Path(Constants.MEDICAL_CARDS_CONTROLLER_PATH)
public class MedicalCardsController {
    private final PolyclinicRepository repository;

    public MedicalCardsController() {
        this.repository = DependenciesContainer.getPolyclinicRepository();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<MedicalCard> getAllCards() {
        return repository.getAllMedicalCards();
    }

    @Path("/{id:\\d+}")
    public MedicalCardLocator getMedicalCard(@PathParam("id") int id) throws CardDoesNotExistException {
        MedicalCard medicalCard = repository.getMedicalCard(id);
        return new MedicalCardLocator(medicalCard);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public MedicalCard addMedicalCard(MedicalCard medicalCard) {
        return repository.addMedicalCard(medicalCard);
    }
}
