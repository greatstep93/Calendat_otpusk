package ru.greatstep.spring_sqlite.models;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class InvalidDate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invalid_date_id")
    Long id;

    @Column
    String date;

    @Override
    public String toString() {
        return date;
    }
}
