package org.example.repository;

import org.example.entity.Appointment;
import org.example.entity.AppointmentResult;
import org.example.entity.MedicalCard;

import java.util.Collection;
import java.util.List;

public interface PolyclinicRepository {
    public MedicalCard addMedicalCard(MedicalCard medicalCard);
    public MedicalCard getMedicalCard(int cardNumber) throws CardDoesNotExistException;
    public List<MedicalCard> getAllMedicalCards();
    public Collection<Appointment> getAppointmentsForPatient(int cardNumber) throws CardDoesNotExistException;
    public Appointment addAppointmentForPatient(Appointment appointment) throws CardDoesNotExistException;
    public Appointment getAppointment(int appointmentId) throws AppointmentDoesNotExistException;
    public Appointment updateAppointment(int appointmentId, Appointment appointment) throws CardDoesNotExistException, AppointmentDoesNotExistException;
    public Appointment deleteAppointment(int appointmentId) throws AppointmentDoesNotExistException;
    public AppointmentResult addAppointmentResult(AppointmentResult appointmentResult) throws CardDoesNotExistException;
    public AppointmentResult updateAppointmentResult(AppointmentResult appointmentResult) throws CardDoesNotExistException, AppointmentResultDoesNotExistException;
}
