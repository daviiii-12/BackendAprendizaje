package co.edu.usb.cali.reservasCanchamax.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name="reservation_series")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationSeries {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private  Integer id;

        @ManyToOne
        @JoinColumn(name = "court_id", nullable = false, referencedColumnName = "id")
        private Court court;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
        private User user;

        @Column(name = "tz", nullable = false)
        private String tz;

        @Column(name = "starts_on", nullable = false)
        private Date startsOn;

        @Column(name = "ends_on", nullable = false)
        private Date endsOn;

        @Column(name = "start_time", nullable = false)
        private Time startTime;

        @Column(name = "duration_minutes", nullable = false)
        private Integer durationMinutes;

        @Column(name = "frequency", nullable = false)
        private SeriesFrequency frequency;

        @Column(name = "interval", nullable = false)
        private Integer interval;

        @Column(name = "by_weekday")
        private String byWeekday;

        @Column(name = "is_active", nullable = false)
        private Boolean isActive;

        @Column(name = "created_at", nullable = false)
        private Timestamp createdAt;

        @Column(name = "updated_at", nullable = false)
        private Timestamp updatedAt;
}