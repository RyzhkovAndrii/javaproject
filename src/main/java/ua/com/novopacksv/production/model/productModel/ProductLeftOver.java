package ua.com.novopacksv.production.model.productModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.BaseEntity;

import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "product_left_over")
public class ProductLeftOver extends BaseEntity {

    //LocalDateTime for take all changes up to the midnight (here we point a time for this)
    @Column(name = "left_date", nullable = false)
    private LocalDateTime leftDate;

    @OneToOne
    @PrimaryKeyJoinColumn
    @MapsId
    private ProductType productType;

    @Column(name = "amount", nullable = false)
    private Integer amount;
}
