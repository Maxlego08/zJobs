package fr.maxlego08.jobs.dto;

import java.util.UUID;

public record PlayerRewardDTO(
        UUID unique_id,
        String content
) {
}
