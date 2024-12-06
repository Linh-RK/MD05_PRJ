package com.ra.md05_project.model.error;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class DataError<T>{
    private T message;
    private int code;
}

