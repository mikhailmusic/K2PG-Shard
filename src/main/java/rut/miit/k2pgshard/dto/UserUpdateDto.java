package rut.miit.k2pgshard.dto;

public record UserUpdateDto(
        String id,
        String firstName,
        String email,
        String phoneNumber,
        String country
) {}