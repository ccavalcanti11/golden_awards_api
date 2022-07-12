package com.goldenawards.data.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MovieDetailsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String producer;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;
}
