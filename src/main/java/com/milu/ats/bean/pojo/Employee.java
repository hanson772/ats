package com.milu.ats.bean.pojo;

import com.milu.ats.bean.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author max.chen
 * @class
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    String name;
    String account;
    String email;
    String key;
    ERole active;

    public String getOperator(){
        return this.name + "-" + this.getAccount();
    }
}
