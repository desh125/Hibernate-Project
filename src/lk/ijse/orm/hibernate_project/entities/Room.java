package lk.ijse.orm.hibernate_project.entities;

import lk.ijse.orm.hibernate_project.dto.RoomDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @Column(name = "room_type_id", length = 50)
    private String RoomTypeId;
    @Column(name = "type", nullable = false, length = 50)
    private String Type;
    @Column(name = "key_money", nullable = false, length = 50)
    private String KeyMoney;
    @Column(name = "qty", nullable = false, length = 50)
    private int Qty;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "Room")
    private List<Reservation> reservationEntities = new ArrayList<>();

    public Room() {
    }

    public Room(String roomTypeId, String type, String keyMoney, int qty, List<Reservation> reservationEntities) {
        RoomTypeId = roomTypeId;
        Type = type;
        KeyMoney = keyMoney;
        Qty = qty;
        this.reservationEntities = reservationEntities;
    }

    public String getRoomTypeId() {
        return RoomTypeId;
    }

    public void setRoomTypeId(String roomTypeId) {
        RoomTypeId = roomTypeId;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getKeyMoney() {
        return KeyMoney;
    }

    public void setKeyMoney(String keyMoney) {
        KeyMoney = keyMoney;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public List<Reservation> getReservationEntities() {
        return reservationEntities;
    }

    public void setReservationEntities(List<Reservation> reservationEntities) {
        this.reservationEntities = reservationEntities;
    }

    @Override
    public String toString() {
        return "RoomEntity{" +
                "RoomTypeId='" + RoomTypeId + '\'' +
                ", Type='" + Type + '\'' +
                ", KeyMoney='" + KeyMoney + '\'' +
                ", Qty=" + Qty +
                ", reservationEntities=" + reservationEntities +
                '}';
    }

    public RoomDTO ToDto() {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomTypeId(this.RoomTypeId);
        roomDTO.setType(this.Type);
        roomDTO.setKeyMoney(this.KeyMoney);
        roomDTO.setQty(this.Qty);
        roomDTO.setReservationEntities(this.reservationEntities);
        return roomDTO;
    }

}