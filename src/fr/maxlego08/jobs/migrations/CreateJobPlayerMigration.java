package fr.maxlego08.jobs.migrations;

import fr.maxlego08.jobs.api.Tables;
import fr.maxlego08.sarah.database.Migration;

public class CreateJobPlayerMigration extends Migration {
    @Override
    public void up() {

        create(Tables.JOBS, table -> {
            table.uuid("unique_id").primary();
            table.string("job_id", 255).primary();
            table.integer("level").defaultValue(0);
            table.integer("prestige").defaultValue(0);
            table.decimal("experience", 65, 3).defaultValue(0);
        });

    }
}
