package link.yauritux.phonebookingrestws.usecase.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
@Data
@Builder
public class PhoneSummaryResponse {

    private String phoneId;
    private String brand;
    private String deviceName;
    private String technology;
    private String _2gBands;
    private String _3gBands;
    private String _4gBands;
    private String isAvailable;
    private LocalDateTime latestBookedOn;
    private String latestBookedBy;
    private String errorMessage;
}
