package CLI.Core;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Customer implements Runnable {

    // Logger to log Customer actions in CLI and the ticketing_system.log file.
    private static final Logger logger = LogManager.getLogger(Customer.class);

    private final int customerId;  // Unique ID for the customer.
    private final TicketPool ticketPool;  // Shared ticket pool for buying tickets.
    private final int retrievalInterval;  // Time delay for purchasing a ticket (in seconds).

    // Constructor to initiate the customer object.
    public Customer(int customerId, TicketPool ticketPool, int retrievalInterval) {
        this.customerId = customerId;
        this.ticketPool = ticketPool;
        this.retrievalInterval = retrievalInterval;
    }

    @Override
    public void run() {
        try {
            //  Each customer attempts to retrieve a ticket every retrievalRate seconds.
            while (true) {
                if (ticketPool.isEmpty()) {
                    logger.info("Customer {}: is waiting. No tickets available.", customerId);
                    Thread.sleep(3000);  // Sleep 3 seconds the thread until vendors add tickets.
                    continue;
                }
                ticketPool.removeTicket("Customer " + customerId + ": Purchased a Ticket."); // purchase ticket from the pool.
                Thread.sleep(retrievalInterval * 1000);  //after purchase a ticket thread will stop for 1 second.

                if (ticketPool.isLimitReached() && ticketPool.isEmpty()){

                    // after the totalTicket limit reached and ticket pool is empty,  ticket purchasing process will stop.
                    logger.info("Customer {}: Purchasing Stopped. All Tickets are Sold out.", customerId);
                    break;
                }
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
