package org.example.repository;


import org.example.entity.Appointment;
import org.example.entity.AppointmentResult;
import org.example.entity.MedicalCard;

import java.util.*;

public class InMemoryPolyclinicRepository implements PolyclinicRepository{
    private Dictionary<Integer, MedicalCard> medicalCards = new Hashtable<>();


    @Override
    public MedicalCard addMedicalCard(MedicalCard medicalCard){
        int cardNumber = medicalCards.size() + 1;
        medicalCard.setCardNumber(cardNumber);
        medicalCards.put(cardNumber, medicalCard);
        return medicalCard;
    }

    @Override
    public MedicalCard getMedicalCard(int cardNumber) throws CardDoesNotExistException {
        if (medicalCards.get(cardNumber) == null) {
            throw new CardDoesNotExistException("Card with number " + cardNumber + " does not exist");
        }
        return medicalCards.get(cardNumber);
    }

    @Override
    public List<MedicalCard> getAllMedicalCards() {
        Enumeration<MedicalCard> elements = medicalCards.elements();
        return Collections.list(elements);
    }

    @Override
    public List<Appointment> getAppointmentsForPatient(int cardNumber) throws CardDoesNotExistException {
        if (medicalCards.get(cardNumber) == null) {
            throw new CardDoesNotExistException("Card with number " + cardNumber + " does not exist");
        }
        return medicalCards.get(cardNumber).getAppointments().getAppointment();
    }

    @Override
    public Appointment addAppointmentForPatient(Appointment appointment) throws CardDoesNotExistException {
        int patientCardNumber = appointment.getPatientCardNumber();
        if (medicalCards.get(patientCardNumber) == null) {
            throw new CardDoesNotExistException("Card with number " + patientCardNumber + " does not exist");
        }
        MedicalCard medicalCard = medicalCards.get(patientCardNumber);
        if (medicalCard.getAppointments() == null) {
            medicalCard.setAppointments(new MedicalCard.Appointments());
        }
        MedicalCard.Appointments appointments = medicalCard.getAppointments();
        appointments.getAppointment().add(appointment);
        int appointmentId = appointments.getAppointment().size();
        appointment.setId(appointmentId);
        return appointment;
    }

    @Override
    public Appointment getAppointment(int appointmentId) throws AppointmentDoesNotExistException {
        var medicalCards = this.medicalCards.elements();
        while (medicalCards.hasMoreElements()) {
            MedicalCard medicalCard = medicalCards.nextElement();
            List<Appointment> appointments = medicalCard.getAppointments().getAppointment();
            for (Appointment appointment : appointments) {
                if (appointment.getId() == appointmentId) {
                    return appointment;
                }
            }
        }
        throw new AppointmentDoesNotExistException("Appointment with id " + appointmentId + " does not exist");
    }

    @Override
    public Appointment updateAppointment(int appointmentId, Appointment appointment) throws CardDoesNotExistException, AppointmentDoesNotExistException {
        int medicalCardNumber = appointment.getPatientCardNumber();
        if (medicalCards.get(medicalCardNumber) == null) {
            throw new CardDoesNotExistException("Card with number " + medicalCardNumber + " does not exist");
        }
        List<Appointment> appointments = medicalCards.get(medicalCardNumber).getAppointments().getAppointment();
        for (Appointment target : appointments) {
            if (target.getId() == appointmentId) {
                target.setStartTime(appointment.getStartTime());
                target.setEndTime(appointment.getEndTime());
                target.setRoomNumber(appointment.getRoomNumber());
                return target;
            }
        }
        throw new AppointmentDoesNotExistException("Appointment with id " + appointmentId + " does not exist");
    }

    @Override
    public Appointment deleteAppointment(int appointmentId) throws AppointmentDoesNotExistException {
        var medicalCards = this.medicalCards.elements();
        while (medicalCards.hasMoreElements()) {
            MedicalCard medicalCard = medicalCards.nextElement();
            List<Appointment> appointments = medicalCard.getAppointments().getAppointment();
            for (Appointment appointment : appointments) {
                if (appointment.getId() == appointmentId) {
                    appointments.remove(appointment);
                    return appointment;
                }
            }
        }
        throw new AppointmentDoesNotExistException("Appointment with id " + appointmentId + " does not exist");
    }

    @Override
    public AppointmentResult addAppointmentResult(AppointmentResult appointmentResult) throws CardDoesNotExistException {
        int medicalCardNumber = appointmentResult.getPatientCardNumber();
        if (medicalCards.get(medicalCardNumber) == null) {
            throw new CardDoesNotExistException("Card with number " + medicalCardNumber + " does not exist");
        }
        MedicalCard medicalCard = medicalCards.get(medicalCardNumber);
        if (medicalCard.getResults() == null) {
            medicalCard.setResults(new MedicalCard.Results());
        }
        medicalCard.getResults().getAppointmentResult().add(appointmentResult);
        return appointmentResult;
    }

    @Override
    public AppointmentResult updateAppointmentResult(AppointmentResult appointmentResult) throws CardDoesNotExistException, AppointmentResultDoesNotExistException {
        int medicalCardNumber = appointmentResult.getPatientCardNumber();
        if (medicalCards.get(medicalCardNumber) == null) {
            throw new CardDoesNotExistException("Card with number " + medicalCardNumber + " does not exist");
        }
        List<AppointmentResult> results = medicalCards.get(medicalCardNumber).getResults().getAppointmentResult();
        for (AppointmentResult target : results) {
            if (target.getId() == appointmentResult.getId()) {
                target.setDiagnosis(appointmentResult.getDiagnosis());
                target.setActions(appointmentResult.getActions());
                target.setRecommendations(appointmentResult.getRecommendations());
                target.setAnamnesis(appointmentResult.getAnamnesis());
                target.setConclusion(appointmentResult.getConclusion());
                target.setObjectively(appointmentResult.getObjectively());
                target.setRadiationDose(appointmentResult.getRadiationDose());
                target.setPrescription(appointmentResult.getPrescription());
                target.setReason(appointmentResult.getReason());
                return target;
            }
        }
        throw new AppointmentResultDoesNotExistException("Appointment result with id " + appointmentResult.getId() + " does not exist");
    }
}
