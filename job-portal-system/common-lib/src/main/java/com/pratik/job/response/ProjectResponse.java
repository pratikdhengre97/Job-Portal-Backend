package com.pratik.job.response;

import lombok.*;


import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponse {

    private Long id;
    private String title;
    private String description;
    private List<String> technologies;
    private String projectUrl;
    private String sourceCodeUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isOngoing;
    private Integer displayOrder;
}
