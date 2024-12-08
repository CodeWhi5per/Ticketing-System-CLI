package CLI.Core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Vendor implements Runnable {

    private static final Logger logger = LogManager.getLogger(Vendor.class);

    private final int vendorId;
    private final TicketPool ticketPool;
    private final int releaseInterval;

    public Vendor(int vendorId, TicketPool ticketPool, int releaseInterval) {
        this.vendorId = vendorId;
        this.ticketPool = ticketPool;
        this.releaseInterval = releaseInterval;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (ticketPool.isFull()) {

                    logger.info("Vendor {}: is waiting. Ticket pool is full.", vendorId);
                    Thread.sleep(3000);
                    continue;
                }
                ticketPool.addTickets("Vendor " + vendorId + ": Added a Ticket.");
                Thread.sleep(releaseInterval * 1000);

                if (ticketPool.isLimitReached()){

                    logger.info("Vendor {}: Ticket Releasing stopped. Limit is reached.", vendorId);
                    break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
