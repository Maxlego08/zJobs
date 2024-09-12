package fr.maxlego08.jobs.dto;

import java.util.UUID;

public record PlayerPointsDTO(UUID unique_id,
                              int points
) {
}
