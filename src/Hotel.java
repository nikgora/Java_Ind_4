import java.util.ArrayList;

public class Hotel {

    private final int capacity;     //кількість місць у готелі
    private ArrayList<String> guests;     //список клієнтів

    public Hotel(int capacity) {
        this.capacity = capacity;
        this.guests = new ArrayList<>();
    }
    //функціія регестрації перевіприймає призвіще клієнта, якщо є вільні місця, то поселяємо його
    public synchronized boolean checkIn(String guestName) {
        if (guests.size() < capacity) {
            guests.add(guestName);
            System.out.println("Guest " + guestName + " checked in. Available slots: " + (capacity - guests.size() ));
            return true;
        } else {
            System.out.println("No available slots for guest " + guestName);
            return false;
        }
    }
    //функція видалення клієнта за призвищем
    public synchronized void checkOut(String guestName) {
        guests.remove(guestName);
        System.out.println("Guest " + guestName + " checked out. Available slots: " + (capacity - guests.size() ));
    }
    //функція перевіряє, чи гость з прізвищем проживає у готелі чи ні
    public synchronized boolean findGuest(String guestName) {
        return guests.contains(guestName);
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayList<String> getGuests() {
        return guests;
    }

    public void setGuests(ArrayList<String> guests) {
        this.guests = guests;
    }
}


