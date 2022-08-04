package ru.greatstep.spring_sqlite.models;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id")
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

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "users_dates",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "date_id"))
    private List<SelectedDate> selectedDates;

    @Override
    public String toString() {
        return "Id = " + id + "\n" + "Full Name = " + fullName + "\n" + "Position = " + position + "\n"
                + "Vacation start = " + vacationStart + "\n" + "Vacation end = " + vacationEnd + "\n"
                + "Vacation days count = " + vacationDaysCount + "\n";
    }

}
