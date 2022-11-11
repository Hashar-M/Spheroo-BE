package com.qburst.spherooadmin.signup;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Akhilesh
 * For the purpose of transfer as body of http response in json format,
 * carying information for a particular http request.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
    private boolean success;
    private String message;
    private Object data;
}
