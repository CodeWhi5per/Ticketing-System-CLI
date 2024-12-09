package CLI.Core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Vendor implements Runnable {

    // Logger to log vendor actions in CLI and the ticketing_system.log file.
    private static final Logger logger = LogManager.getLogger(Vendor.class);

    private final int vendorId;  // Unique ID for the vendor.
    private final TicketPool ticketPool;  // Shared ticket pool for adding tickets.
    private final int releaseInterval;   // Time delay for releasing a ticket (in seconds).


    // Constructor to initiate the vendor object.
    public Vendor(int vendorId, TicketPool ticketPool, int releaseInterval) {
        this.vendorId = vendorId;
        this.ticketPool = ticketPool;
        this.releaseInterval = releaseInterval;
    }

    @Override
    public void run() {
        try {
            // Each vendor adds tickets to the pool at regular intervals (releaseRate seconds)
            while (true) {
                if (ticketPool.isFull()) {

                    logger.info("Vendor {}: is waiting. Ticket pool is full.", vendorId);
                    Thread.sleep(3000);  // Sleep 3 seconds the thread until customers buy tickets.
                    continue;
                }
                ticketPool.addTickets("Vendor " + vendorId + ": Added a Ticket."); // add ticket to the pool.
                Thread.sleep(releaseInterval * 1000); //after added a ticket thread will stop for 1 second.

                if (ticketPool.isLimitReached()){

                    // after the totalTicket limit reached, ticket releasing process will stop.
                    logger.info("Vendor {}: Ticket Releasing stopped. Limit is reached.", vendorId);
                    break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
