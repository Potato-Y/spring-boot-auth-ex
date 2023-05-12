package io.github.potatoy.auth_ex.entity.conference.list;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import io.github.potatoy.auth_ex.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
// @AllArgsConstructor
public class ConferenceList {
    @Id
    @Column(name = "conference_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conferenceId;

    @ManyToOne
    private User user;

    private LocalDateTime createDate;
}
