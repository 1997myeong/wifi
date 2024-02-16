package project.wifi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class LocationHistory {

    private Long id;
    private String LNT;
    private String LAT;
    private LocalDateTime searchDate;
    private String note;

}
