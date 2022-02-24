package hr.sedamit.bss.databasemigrations.destination.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class T1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String code;


}
