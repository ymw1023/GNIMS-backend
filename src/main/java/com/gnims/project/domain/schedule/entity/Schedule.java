package com.gnims.project.domain.schedule.entity;

import com.gnims.project.domain.event.entity.Event;
import com.gnims.project.domain.schedule.dto.ReadAllUserDto;
import com.gnims.project.domain.user.entity.User;

import com.gnims.project.util.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity
@NoArgsConstructor
public class Schedule extends TimeStamped {

    @Id @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="event_id")
    private Event event;

    private Boolean isAttend;

    private Boolean isAccepted;

    public Schedule(User user, Event event) {
        this.user = user;
        this.event = event;
        this.isAttend = false;
        this.isAccepted = false;
    }

    private String receiveUsername() {
        return this.getUser().getUsername();
    }

    public List<ReadAllUserDto> findInvitees() {
        return event.getSchedule().stream().map(s -> new ReadAllUserDto(s.receiveUsername()))
                .collect(Collectors.toList());
    }
}
