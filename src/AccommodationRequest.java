import java.util.*;

//клас заявки
class AccommodationRequest implements Runnable {

    private static Hotel hotel;
    private final String guestName;
    private final int stayDuration;

    private static ArrayList<Long> endTimes =new ArrayList<>();
    public AccommodationRequest(Hotel hotel, String guestName, int stayDuration) {
        AccommodationRequest.hotel = hotel;
        this.guestName = guestName;
        this.stayDuration = stayDuration;
    }

    @Override
    public void run() {
        //якщо немає місць
        if (!hotel.checkIn(guestName)) {
            Collections.sort(endTimes);
            long firstAvaibleTime = endTimes.get(0);
            endTimes.set(0,firstAvaibleTime+stayDuration);
            System.out.println("Guest " + guestName + " is waiting for available slots.\n"+"Wait time: "+ (firstAvaibleTime-System.currentTimeMillis()));
            //якщо немає місць, то поток зупиняється на 1000 секунд, якщо є місце то заcеляється
            while (!hotel.checkIn(guestName)) {
                System.out.println("Wait time: "+ (firstAvaibleTime-System.currentTimeMillis()));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        //після заселення клієнта потік зупиняється на заданий період часу, переданий у параметрі stayDuration
        try {
            endTimes.add(System.currentTimeMillis()+stayDuration);
            Thread.sleep(stayDuration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //виселення з гостиниці методом checkOut().
        hotel.checkOut(guestName);
    }
}