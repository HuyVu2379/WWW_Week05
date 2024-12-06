package vuquochuy.week05_vuquochuy.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vuquochuy.week05_vuquochuy.backend.models.Address;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "candidate")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address", nullable = false)
    private Address address;

    public Candidate(String fullName, LocalDate dob, Address address, String phone, String email) {
        this.id = id;
        this.dob = dob;
        this.fullName = fullName;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }

    public Candidate() {
    }
}