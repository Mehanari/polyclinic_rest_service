package org.example.server.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.entity.Appointment;
import org.example.entity.AppointmentResult;
import org.example.entity.MedicalCard;
import org.example.repository.AppointmentDoesNotExistException;
import org.example.repository.AppointmentResultDoesNotExistException;
import org.example.repository.CardDoesNotExistException;
import org.example.repository.PolyclinicRepository;
import org.example.server.DependenciesContainer;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.List;

public class MedicalCardLocator {
    private final MedicalCard medicalCard;
    private final PolyclinicRepository repository;

    public MedicalCardLocator(MedicalCard card){
        this.medicalCard = card;
        this.repository = DependenciesContainer.getPolyclinicRepository();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public MedicalCard getMedicalCard(){
        return medicalCard;
    }

    @GET
    @Path("/appointments")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Appointment> getAppointments(@QueryParam("roomNumber") int roomNumber ) throws CardDoesNotExistException {
        List<Appointment> appointments = (List<Appointment>) repository.getAppointmentsForPatient(medicalCard.getCardNumber());
        if (roomNumber == 0) {
            return appointments;
        }
        return appointments.stream().filter(appointment -> appointment.getRoomNumber() == roomNumber).toList();
    }

    @POST
    @Path("/appointment")
    public int addAppointment(Appointment appointment) throws CardDoesNotExistException {
        return repository.addAppointmentForPatient(appointment).getId();
    }

    @PUT
    @Path("/appointment")
    public Appointment updateAppointment(Appointment appointment) throws CardDoesNotExistException, AppointmentDoesNotExistException {
        return repository.updateAppointment(appointment.getId(), appointment);
    }

    @DELETE
    @Path("/appointment/{appointmentId}")
    public Appointment deleteAppointment(@PathParam("appointmentId") int appointmentId) throws AppointmentDoesNotExistException {
        return repository.deleteAppointment(appointmentId);
    }

    @POST
    @Path("/appointmentResult")
    public int addAppointmentResult(AppointmentResult appointmentResult) throws CardDoesNotExistException {
        return repository.addAppointmentResult(appointmentResult).getId();
    }

    @PUT
    @Path("/appointmentResult")
    public AppointmentResult updateAppointmentResult(AppointmentResult appointmentResult) throws CardDoesNotExistException, AppointmentResultDoesNotExistException {
        return repository.updateAppointmentResult(appointmentResult);
    }
}
