package hr.sedamit.bss.databasemigrations.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hr.sedamit.bss.databasemigrations.service.DatabaseService;
import hr.sedamit.bss.databasemigrations.service.JobInvokerService;

@RestController
@RequestMapping("/import")
public class JobInvokerController {

	@Autowired
	private JobInvokerService jobInvokerService;

	@Autowired
	private DatabaseService service;

	@GetMapping("/migrate")
	public String importCodebooks(@RequestParam List<String> appendTables, @RequestParam List<String> updateTables)
			throws Exception {
		return jobInvokerService.invokeJob(appendTables, updateTables);
	}

	@GetMapping("/test")
	public String testCopyDatabase() throws Exception {
		long time = System.currentTimeMillis();
		service.createTable();
		time = System.currentTimeMillis() - time;
		return "Copy database finished in " + time + " milliseconds";
	}

}
