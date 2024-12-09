package CLI.Core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import java.util.concurrent.locks.Lock;

public class TicketPool {

    // Logger to log TicketPool actions in CLI and the ticketing_system.log file.
    private static final Logger logger = LogManager.getLogger(TicketPool.class);

    // List to store tickets in a synchronized collection.
    private final List<String> tickets = Collections.synchronizedList(new ArrayList<>());
    private final Lock lock = new ReentrantLock();  // Lock to handle thread safety.
    private final int maxTicketCapacity;
    private final int totalTickets;
    private int  ticketCount = 0;   // To track added ticket count.

    // Constructor to initiate ticket pool.
    public TicketPool(int totalTickets, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    // Method to add tickets to the pool.
    public void addTickets(String ticket) throws InterruptedException {
        lock.lock();  // Lock to ensure thread safety when adding tickets.
        try {

            if (ticketCount >= totalTickets) {
                return;  // Exit if ticket count reaches the maximum.
            }

            tickets.add(ticket);  // Add ticket to the pool.
            logger.info("{}", ticket);  // Log ticket addition.
            ticketCount++;  // Increment ticket count.

        } finally {
            lock.unlock();  // Release the lock.
        }
    }

    // Method to buy tickets from the pool.
    public void removeTicket(String ticket) throws InterruptedException {
        lock.lock();  // Lock to ensure thread safety when adding tickets.
        try {

            tickets.remove(0);  // remove ticket from the pool.
            logger.info("{}", ticket); // Log ticket purchasing.

        } finally {
            lock.unlock();  // Release the lock.
        }
    }

    // Method to ensure the pool capacity reached.
    public boolean isFull() {
        lock.lock();
        try {
            return tickets.size() >= maxTicketCapacity;
        } finally {
            lock.unlock();
        }
    }

    // Method to ensure the pool is empty.
    public boolean isEmpty() {
        lock.lock();
        try {
            return tickets.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    // Method to ensure the total tickets are completely added.
    public boolean isLimitReached() {
        lock.lock();
        try {
            return ticketCount >= totalTickets;
        } finally {
            lock.unlock();
        }
    }
}

