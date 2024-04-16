package _com6.Parcial.Corte2.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    LocalDateTime loginDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @Fetch(FetchMode.JOIN)
    UserAccount userAccount;

}
