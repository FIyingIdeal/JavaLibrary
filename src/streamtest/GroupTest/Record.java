package streamtest.GroupTest;

/**
 * Created by Administrator on 2017/8/3.
 */
public class Record {

    private String hotel;
    private String room;
    private String name;

    public Record(String hotel, String room, String name) {
        this.hotel = hotel;
        this.room = room;
        this.name = name;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Record{" +
                "hotel='" + hotel + '\'' +
                ", room='" + room + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
