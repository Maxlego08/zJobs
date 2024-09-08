package fr.maxlego08.jobs.dto;

import java.util.UUID;

public record PlayerJobDTO(UUID unique_id,
                           String job_id,
                           int level,
                           int prestige,
                           double experience
) {
}
