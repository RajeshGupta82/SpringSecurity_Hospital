package com.rajesh.hospital.hospitalmanagement;

import com.rajesh.hospital.hospitalmanagement.entity.Appointment;
import com.rajesh.hospital.hospitalmanagement.entity.Insurance;
import com.rajesh.hospital.hospitalmanagement.entity.Patient;
import com.rajesh.hospital.hospitalmanagement.service.AppointmentService;
import com.rajesh.hospital.hospitalmanagement.service.InsuranceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class InsuranceTests {

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void testInsurance() {
        Insurance insurance = new Insurance();
        insurance.setPolicyNumber("HDFC_1234");
        insurance.setProvider("HDFC");
        insurance.setValidUntil(LocalDate.of(2030, 12, 12));
            

        Patient patient = insuranceService.assignInsuranceToPatient(insurance, 1L);

        System.out.println(patient);

        var newPatient = insuranceService.disaccociateInsuranceFromPatient(patient.getId());

        System.out.println(newPatient);
    }


    @Test
    public void testCreateAppointment() {
        Appointment appointment = new  Appointment();
        appointment.setAppointmentTime(LocalDateTime.of(2025, 11, 1, 14, 0, 0));
        appointment.setReason("Cancer");
           

//        var newAppointment = appointmentService.createNewAppointment(appointment, 1L, 2L);

//        System.out.println(newAppointment);

//        var updatedAppointment = appointmentService.reAssignAppointmentToAnotherDoctor(newAppointment.getId(), 3L);

//        System.out.println(updatedAppointment);
    }
}











