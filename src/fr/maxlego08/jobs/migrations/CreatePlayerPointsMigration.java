package fr.maxlego08.jobs.migrations;

import fr.maxlego08.sarah.database.Migration;

public class CreatePlayerPointsMigration extends Migration {
    @Override
    public void up() {

        create("%prefix%points", table -> {
            table.uuid("unique_id").primary();
            table.integer("points").defaultValue(0);
        });

    }
}
