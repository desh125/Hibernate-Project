package lk.ijse.orm.hibernate_project.dto;

import lk.ijse.orm.hibernate_project.entities.Reservation;
import lk.ijse.orm.hibernate_project.entities.Room;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RoomDTO {

    private String RoomTypeId;
    private String Type;
    private String KeyMoney;
    private int Qty;
    private List<Reservation> reservationEntities = new ArrayList<>();

    public Room ToEntity() {
        Room room = new Room();
        room.setRoomTypeId(this.RoomTypeId);
        room.setType(this.Type);
        room.setKeyMoney(this.KeyMoney);
        room.setQty(this.Qty);
        room.setReservationEntities(this.reservationEntities);
        return room;
    }

}