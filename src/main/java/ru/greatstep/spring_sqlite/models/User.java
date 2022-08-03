package ru.greatstep.spring_sqlite.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "position")
    private String position;

    @Column(name = "vacation_start")
    private String vacationStart;

    @Column(name = "vacation_end")
    private String vacationEnd;

    @Column(name = "vacation_days_count")
    private int vacationDaysCount;

    @Column(name = "vacation")
    private String vacation;

    @ManyToMany
    @JoinTable(name = "users_dates",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "date_id"))
    private Collection<SelectedDate> selectedDates;

    @Override
    public String toString() {
        return "Id = " + id + "\n" + "Full Name = " + fullName + "\n" + "Position = " + position + "\n"
                + "Vacation start = " + vacationStart + "\n" + "Vacation end = " + vacationEnd + "\n"
                + "Vacation days count = " + vacationDaysCount + "\n";
    }

}
