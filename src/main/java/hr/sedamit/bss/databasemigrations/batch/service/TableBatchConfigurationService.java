package hr.sedamit.bss.databasemigrations.batch.service;

import hr.sedamit.bss.databasemigrations.batch.model.TableBatchConfiguration;
import hr.sedamit.bss.databasemigrations.batch.repository.TableBatchConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TableBatchConfigurationService {

    @Autowired
    private TableBatchConfigurationRepository tableBatchConfigurationRepository;


    public List<TableBatchConfiguration> list() {
        return tableBatchConfigurationRepository.findAll();
    }
    
    public TableBatchConfiguration details(String name) {
        Optional<TableBatchConfiguration> tableBatchConfiguration = tableBatchConfigurationRepository.findByTableName(name);
        return tableBatchConfiguration.isPresent() ? tableBatchConfiguration.get() : null;
    }

    public TableBatchConfiguration save(TableBatchConfiguration tableBatchConfiguration) {
        return tableBatchConfigurationRepository.save(tableBatchConfiguration);
    }

    public List<TableBatchConfiguration> saveAll(List<TableBatchConfiguration> tableBatchConfigurations) {
        return tableBatchConfigurationRepository.saveAllAndFlush(tableBatchConfigurations);
    }


    @Transactional
    public TableBatchConfiguration update(String name, TableBatchConfiguration tableConfiguration) {
        Optional<TableBatchConfiguration> optional = tableBatchConfigurationRepository.findByTableName(name);
        if (optional.isPresent()) {
            TableBatchConfiguration old = optional.get();
            old.setBatchSize(tableConfiguration.getBatchSize());
            old.setKeyColumnName(tableConfiguration.getKeyColumnName());
            old.setKeyColumnValue(tableConfiguration.getKeyColumnValue());
            old.setActive(tableConfiguration.isActive());
            return tableBatchConfigurationRepository.save(old);
        }
        return null;
    }

    @Transactional
    public TableBatchConfiguration updateLastColumnValue(String name, String keyColumnValue) {
        Optional<TableBatchConfiguration> optional = tableBatchConfigurationRepository.findByTableName(name);
        if (optional.isPresent()) {
            TableBatchConfiguration old = optional.get();
            old.setKeyColumnValue(keyColumnValue);
            return tableBatchConfigurationRepository.save(old);
        }
        return null;
    }

    @Transactional
    public TableBatchConfiguration setActive(String name, boolean active) {
        Optional<TableBatchConfiguration> optional = tableBatchConfigurationRepository.findByTableName(name);
        if (optional.isPresent()) {
            TableBatchConfiguration old = optional.get();
            old.setActive(active);
            return tableBatchConfigurationRepository.save(old);
        }
        return null;
    }

    public void delete(TableBatchConfiguration entity) {
        tableBatchConfigurationRepository.delete(entity);
    }


    public void delete(String name) {
        Optional<TableBatchConfiguration> optional = tableBatchConfigurationRepository.findByTableName(name);
        if (optional.isPresent()) {
            TableBatchConfiguration entity = optional.get();
            tableBatchConfigurationRepository.delete(entity);
        }
    }


}
