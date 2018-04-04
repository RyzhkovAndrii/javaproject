package ua.com.novopacksv.production.model.rollModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.BaseEntity;

import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "roll_batch")
public class RollBatch extends BaseEntity {

    @Column(name = "manufactured_date")
    private LocalDate manufacturedDate;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private RollType rollType;

    @Column(name = "manufacture_amount")
    private Integer manufactureAmount;

    @OneToMany(mappedBy = "rollBatch")
    private List<RollUsed> rollUsedList;
}
