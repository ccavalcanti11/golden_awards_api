package com.goldenawards.data.dto;


import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RangeDTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<MovieDetailsDTO> min;
    private List<MovieDetailsDTO> max;
}
