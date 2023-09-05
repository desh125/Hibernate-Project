package lk.ijse.orm.hibernate_project.bo.custom;

import lk.ijse.orm.hibernate_project.bo.SuperBo;
import lk.ijse.orm.hibernate_project.dto.RoomDTO;

import java.util.List;

public interface RoomBo extends SuperBo {
    boolean SaveRoom(RoomDTO roomDTO);

    List<RoomDTO> getAllRooms();

    RoomDTO getRoom(String room_type_id);

    boolean UpdateRoom(RoomDTO roomDTO);

    boolean DeleteRoom(RoomDTO roomDTO);

    List<String> getAllRoomType();

    List<String> getAllRoomTypeID();
}