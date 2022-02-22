package hr.sedamit.bss.databasemigrations.command;

import lombok.Data;

import java.util.List;

@Data
public class TableCommand {

    private List<String> appendTables;
    // UPDATE or APPEND
    private List<String> updateTables;


}
