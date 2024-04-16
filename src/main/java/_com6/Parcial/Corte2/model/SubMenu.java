package _com6.Parcial.Corte2.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    Menu menu;

    LocalDateTime creationDate;

}
