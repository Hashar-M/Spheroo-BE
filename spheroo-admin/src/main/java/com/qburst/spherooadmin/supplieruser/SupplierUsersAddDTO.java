package com.qburst.spherooadmin.supplieruser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.qburst.spherooadmin.constants.SupplierUserModelConstants.MOBILE_NUMBER_REGEX;

/**
 * It carries all necessary informations for {@link SupplierUser} while addind a new supplier{@link com.qburst.spherooadmin.supplier.Supplier}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SupplierUsersAddDTO {
    @NotNull
    @NotEmpty
    @Size(min = 6,max = 30)
    private String supplierUserName;

    @NotNull
    @NotEmpty
    @Pattern(regexp = MOBILE_NUMBER_REGEX,message = "not a valid phone number")
    private String supplierUserMobileNumber;

    @NotNull
    @NotEmpty
    @Pattern(regexp = MOBILE_NUMBER_REGEX,message = "not a valid phone number")
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
