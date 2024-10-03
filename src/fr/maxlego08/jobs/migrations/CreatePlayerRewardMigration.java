package fr.maxlego08.jobs.migrations;

import fr.maxlego08.jobs.api.Tables;
import fr.maxlego08.sarah.database.Migration;

public class CreatePlayerRewardMigration extends Migration {
    @Override
    public void up() {
        create(Tables.REWARDS, table -> {
            table.uuid("unique_id").primary();
            table.longText("content");
        });
    }
}
