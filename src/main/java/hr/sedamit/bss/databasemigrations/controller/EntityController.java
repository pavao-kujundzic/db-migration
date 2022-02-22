package hr.sedamit.bss.databasemigrations.controller;

import hr.sedamit.bss.databasemigrations.postgre.entity.T1;
import hr.sedamit.bss.databasemigrations.postgre.repository.PostgreRepository;
import hr.sedamit.bss.databasemigrations.sqlserver.repository.SqlServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/test")
public class EntityController {

    @Autowired
    PostgreRepository postgreRepo;

    @Autowired
    @Qualifier("postgreSqlJdbcTemplate")
    JdbcTemplate postgreSqlJdbcTemplate;

    @Autowired
    @Qualifier("sqlServerJdbcTemplate")
    JdbcTemplate sqlServerJdbcTemplate;


    @GetMapping("/")
    public T1 test() {
        T1 t1 = new T1();

        List<Map<String, Object>> x = sqlServerJdbcTemplate.queryForList("select * from dbo.Termination_Call_Detail");
        Set<String> y = x.get(0).keySet();
        Map<String, Object> z = x.get(0);
        Object v = z.get(y.stream().findFirst().get());
        System.out.println(v.toString());
        for (int i = 0; i < 10; i++) {
            t1.setName("name " + i);
            t1.setCode("code " + i);
            postgreRepo.save(t1);


            postgreSqlJdbcTemplate.execute("INSERT INOT public.t1 Values(80,name,code)");
        }
        return t1;
    }


}
