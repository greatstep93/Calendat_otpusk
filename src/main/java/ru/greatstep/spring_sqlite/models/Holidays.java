package ru.greatstep.spring_sqlite.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Holidays {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "holidays_id")
    Long id;

    @Column
    String date;
}
