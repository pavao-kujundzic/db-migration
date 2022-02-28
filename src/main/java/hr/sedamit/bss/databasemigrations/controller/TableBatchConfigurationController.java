package hr.sedamit.bss.databasemigrations.controller;

import hr.sedamit.bss.databasemigrations.batch.model.TableBatchConfiguration;
import hr.sedamit.bss.databasemigrations.batch.service.TableBatchConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/table-configuration")
public class TableBatchConfigurationController {

    @Autowired
    private TableBatchConfigurationService tableBatchConfigurationService;


    @GetMapping("")
    public List<TableBatchConfiguration> list() {
        return tableBatchConfigurationService.list();
    }


    @GetMapping("/{name}")
    public TableBatchConfiguration details(@PathVariable String name) {
        return tableBatchConfigurationService.details(name);
    }

    @PostMapping("")
    public TableBatchConfiguration save(@RequestBody TableBatchConfiguration entity) {
        return tableBatchConfigurationService.save(entity);
    }

    @PostMapping("/all")
    public List<TableBatchConfiguration> saveAll(@RequestBody List<TableBatchConfiguration> entities) {
        return tableBatchConfigurationService.saveAll(entities);
    }

    @PostMapping("/{name}")
    public TableBatchConfiguration update(@PathVariable String name, @RequestBody TableBatchConfiguration entity) {
        return tableBatchConfigurationService.update(name, entity);
    }

    @PutMapping("/{name}")
    public TableBatchConfiguration updateLastValue(@PathVariable String name, @RequestBody String lastValue) {
        return tableBatchConfigurationService.updateLastColumnValue(name, lastValue);
    }

    @DeleteMapping("/{name}")
    public void delete(@PathVariable String name) {
        tableBatchConfigurationService.delete(name);
    }
}
