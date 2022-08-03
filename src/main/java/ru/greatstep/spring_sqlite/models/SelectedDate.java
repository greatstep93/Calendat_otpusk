package ru.greatstep.spring_sqlite.models;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SelectedDate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private LocalDate localDate;

    public SelectedDate(LocalDate localDate){
        this.localDate = localDate;
    }

    @Override
    public String toString() {
        return localDate.toString();
    }
}
