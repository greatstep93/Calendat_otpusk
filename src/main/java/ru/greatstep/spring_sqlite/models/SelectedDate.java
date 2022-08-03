package ru.greatstep.spring_sqlite.models;


import lombok.*;
import javax.persistence.*;

@Entity
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SelectedDate {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    Long id;

    @Column
    String date;

    @Override
    public String toString() {
        return date;
    }
}
