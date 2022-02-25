package hr.sedamit.bss.databasemigrations.controller;

import hr.sedamit.bss.databasemigrations.service.TableBatchConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TableBatchConfigurationController {
    @Autowired
    private TableBatchConfigurationService tableBatchConfigurationService;


}
