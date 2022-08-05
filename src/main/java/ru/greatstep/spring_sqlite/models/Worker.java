package ru.greatstep.spring_sqlite.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String fullName;

    @Column
    private String position;

    @Column
    private int daysCount;

    @OneToMany(mappedBy = "worker",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<User> user;


}
