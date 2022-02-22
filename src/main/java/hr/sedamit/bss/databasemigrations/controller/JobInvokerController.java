package hr.sedamit.bss.databasemigrations.controller;


import hr.sedamit.bss.databasemigrations.service.JobInvokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/import")
public class JobInvokerController {

    @Autowired
    private JobInvokerService jobInvokerService;

    @GetMapping("/migrate")
    public String importCodebooks(@RequestParam List<String> appendTables, @RequestParam List<String> updateTables) throws Exception {
        return jobInvokerService.invokeJob(appendTables, updateTables);
    }


}
