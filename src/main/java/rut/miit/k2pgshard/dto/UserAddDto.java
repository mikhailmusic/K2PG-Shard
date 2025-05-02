package rut.miit.k2pgshard.dto;

import java.time.LocalDate;

public record UserAddDto(
        String firstName,
        String email,
        String phoneNumber,
        LocalDate birthDate,
        String country
) {}