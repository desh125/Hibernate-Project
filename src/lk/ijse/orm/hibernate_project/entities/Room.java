package lk.ijse.orm.hibernate_project.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @Column(name = "room_type_id")
    private String typeId;

    @Column(name = "room_type")
    private String type;

    @Column(name = "key_money")
    private BigDecimal keyMoney;

    @Column(name = "room_quantity")
    private int quantity;

    public Room(String typeId, String type, BigDecimal keyMoney, int quantity) {
        this.typeId = typeId;
        this.type = type;
        this.keyMoney = keyMoney;
        this.quantity = quantity;
    }

    public Room() {
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getKeyMoney() {
        return keyMoney;
    }

    public void setKeyMoney(BigDecimal keyMoney) {
        this.keyMoney = keyMoney;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}