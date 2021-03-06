package ua.com.novopacksv.production.dto.plan;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.dto.BaseEntityResponse;

@Getter
@Setter
public class MachinePlanItemResponse extends BaseEntityResponse {

    private Long rollTypeId;

    private Integer rollAmount;

    private Integer productAmount;

    private Long machinePlanId;

}
