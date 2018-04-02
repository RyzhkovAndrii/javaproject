package ua.com.novopacksv.production.model.orderModel;

import lombok.*;
import org.hibernate.annotations.Type;
import ua.com.novopacksv.production.model.BaseEntity;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "order")
public class Order extends BaseEntity {

    @ManyToOne
    @PrimaryKeyJoinColumn
    private Client client;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @Type(type = "true_false")
    @Column(name = "is_important")
    @NonNull
    private Boolean isImportant;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "delivery_date")
    private Date deliveryDate;
}
