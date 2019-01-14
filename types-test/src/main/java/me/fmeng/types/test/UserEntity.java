package me.fmeng.types.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fmeng
 * @since 2019/01/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    private Integer id;

    private String username;

    private String password;

    private Integer pms;

    private UserTypeEnum userType;

    private String address;
}