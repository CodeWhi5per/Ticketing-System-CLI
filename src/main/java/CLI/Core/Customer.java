package CLI.Core;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Customer implements Runnable {

    private static final Logger logger = LogManager.getLogger(Customer.class);

    private final int customerId;
    private final TicketPool ticketPool;
    private final int retrievalInterval;

    public Customer(int customerId, TicketPool ticketPool, int retrievalInterval) {
        this.customerId = customerId;
        this.ticketPool = ticketPool;
        this.retrievalInterval = retrievalInterval;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (ticketPool.isEmpty()) {
                    logger.info("Customer {}: is waiting. No tickets available.", customerId);
                    Thread.sleep(3000);
                    continue;
                }
                ticketPool.removeTicket("Customer " + customerId + ": Purchased a Ticket.");
                Thread.sleep(retrievalInterval * 1000);

                if (ticketPool.isLimitReached() && ticketPool.isEmpty()){
                    logger.info("Customer {}: Purchasing Stopped. All Tickets are Sold out.", customerId);
                    break;
                }
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
