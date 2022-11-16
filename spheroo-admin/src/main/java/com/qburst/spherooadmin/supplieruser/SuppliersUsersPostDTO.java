package com.qburst.spherooadmin.supplieruser;

import com.qburst.spherooadmin.supplieruser.SupplierUserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SuppliersUsersPostDTO {
    @NotNull
    @NotEmpty
    @Size(min = 6,max = 30)
    private String supplierUserName;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^\\\\s?((\\\\+[1-9]{1,4}[ \\\\-]*)|(\\\\([0-9]{2,3}\\\\)[ \\\\-]*)|([0-9]{2,4})[ \\\\-]*)*?[0-9]{3,4}?[ \\\\-]*[0-9]{3,4}?\\\\s?",message = "not a valid phone number")
    private String supplierUserMobileNumber;

    @NotNull
    @NotEmpty
    private String supplierUserFixedMobileNumber;

    @NotNull
    @NotEmpty
    @Email
    @Size(min =3,max = 320)
    private String supplierUserEmailId;

    @NotNull
    @Size(min = 1,max = 30)
    private SupplierUserType supplierUserType;
}
