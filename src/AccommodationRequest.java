//клас заявки
class AccommodationRequest implements Runnable {

    private final Hotel hotel;
    private final String guestName;
    private final int stayDuration;

    private static long firstAvaibleTime =Long.MAX_VALUE;
    public AccommodationRequest(Hotel hotel, String guestName, int stayDuration) {
        this.hotel = hotel;
        this.guestName = guestName;
        this.stayDuration = stayDuration;
    }

    @Override
    public void run() {
        //якщо немає місць
        if (!hotel.checkIn(guestName)) {
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
            firstAvaibleTime = Math.min(firstAvaibleTime,System.currentTimeMillis()+stayDuration);
            Thread.sleep(stayDuration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //виселення з гостиниці методом checkOut().
        hotel.checkOut(guestName);
    }
}