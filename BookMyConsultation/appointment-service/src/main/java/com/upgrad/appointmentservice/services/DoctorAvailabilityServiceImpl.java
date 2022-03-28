package com.upgrad.appointmentservice.services;

import com.upgrad.appointmentservice.entity.Availability;
import com.upgrad.appointmentservice.exception.AvailabilityUnAvailableException;
import com.upgrad.appointmentservice.model.DoctorAvailability;
import com.upgrad.appointmentservice.repositories.DoctorAvailabilityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DoctorAvailabilityServiceImpl implements DoctorAvailabilityService{

    @Autowired
    DoctorAvailabilityRepo repo;

    @Override
    public void savedDoctorAvailability(DoctorAvailability availability) throws AvailabilityUnAvailableException {
        if (availability.getAvailabilityMap()==null || availability.getAvailabilityMap().isEmpty())
            throw new AvailabilityUnAvailableException();
        List<Availability> availabilityList = new ArrayList<>();
        List<Availability> existingAvailability = repo.findByDoctorIdAndAvailabilityDateIn(
                availability.getDoctorId(),
                availability.getAvailabilityMap().keySet().stream().collect(Collectors.toList())
        );
        repo.deleteAll(existingAvailability);
        availability.getAvailabilityMap().forEach((date,slot) -> {
            availabilityList.addAll(slot.stream().map(s -> {
                return Availability.builder().doctorId(availability.getDoctorId())
                        .availabilityDate(date).isBooked(false).timeSlot(s).build();
            }).collect(Collectors.toList()));
        });
        repo.saveAll(availabilityList);
    }

    @Override
    public DoctorAvailability getDoctorAvailability(String doctorId) {
        List<Availability> availabilities = getAvailabilities(doctorId);
        if (availabilities!=null)
            return DoctorAvailability.builder()
                    .doctorId(doctorId)
                    .availabilityMap(timeSlots(availabilities))
                    .build();
        return null;
    }

    @Override
    public List<Availability> getAvailabilities(String doctorId) {
        return repo.findByDoctorIdAndIsBooked(doctorId,false);
    }

    @Override
    public Availability updateAvailability(Availability availability){
        return repo.save(availability);
    }

    private Map<String,List<String>> timeSlots(List<Availability> availabilities){
        Map<String,List<String >> newTimeSlots = new HashMap<>();
        for (Availability availability : availabilities){
            List<String> timeSlotList= newTimeSlots.get(availability.getAvailabilityDate());
            if (timeSlotList==null || timeSlotList.isEmpty())
                timeSlotList = new ArrayList<>();
            timeSlotList.add(availability.getTimeSlot());
            newTimeSlots.put(availability.getAvailabilityDate(), timeSlotList);
        }
        return newTimeSlots;
    }
}
