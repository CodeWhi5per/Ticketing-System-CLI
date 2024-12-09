package CLI.Core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VIPCustomer implements Runnable {

    // Logger to log VIP Customer actions in CLI and the ticketing_system.log file.
    private static final Logger logger = LogManager.getLogger(VIPCustomer.class);

    private final int vipCustomerId;  // Unique ID for the VIP customer.
    private final TicketPool ticketPool;  // Shared ticket pool for buying tickets.
    private final int retrievalInterval;  // Time delay for purchasing a ticket (in seconds).

    // Constructor to initiate the VIP customer object.
    public VIPCustomer(int customerId, TicketPool ticketPool, int retrievalInterval) {
        this.vipCustomerId = customerId;
        this.ticketPool = ticketPool;
        this.retrievalInterval = retrievalInterval;
    }


    @Override
    public void run() {
        try {
            //  Each VIP customer attempts to retrieve a ticket every retrievalRate seconds.
            while (true) {
                if (ticketPool.isEmpty() && !ticketPool.isLimitReached()) {
                    logger.info("VIP Customer {}: is waiting. No tickets available.", vipCustomerId);
                    Thread.sleep(3000);  // Sleep 3 seconds the thread until vendors add tickets.
                    continue;
                }
                ticketPool.removeTicket("VIP Customer " + vipCustomerId + ": Purchased a Ticket."); // purchase ticket from the pool.
                Thread.sleep(retrievalInterval * 1000);  //after purchase a ticket thread will stop for 1 second.

                if (ticketPool.isLimitReached() && ticketPool.isEmpty()){

                    // after the totalTicket limit reached and ticket pool is empty,  ticket purchasing process will stop.
                    logger.info("VIP Customer {}: Purchasing Stopped. All Tickets are Sold out.", vipCustomerId);
                    break;
                }
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
