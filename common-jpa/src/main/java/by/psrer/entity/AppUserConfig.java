package by.psrer.entity;

import by.psrer.entity.enums.Role;
import by.psrer.entity.enums.Status;
import by.psrer.entity.enums.UserState;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = "appUserConfigId")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_user_config")
public class AppUserConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appUserConfigId;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private UserState userState;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Long intermediateValue;
    private Integer lastBotMessageId;
}