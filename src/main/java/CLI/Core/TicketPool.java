package CLI.Core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import java.util.concurrent.locks.Lock;

public class TicketPool {

    private static final Logger logger = LogManager.getLogger(TicketPool.class);

    private final List<String> tickets = new LinkedList<>();
    private final Lock lock = new ReentrantLock();
    private final int maxTicketCapacity;
    private final int totalTickets;
    private int  ticketCount = 0;

    public TicketPool(int totalTickets, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public void addTickets(String ticket) throws InterruptedException {
        lock.lock();
        try {

            tickets.add(ticket);
            logger.info("{}", ticket);
            ticketCount++;

        } finally {
            lock.unlock();
        }
    }

    public void removeTicket(String ticket) throws InterruptedException {
        lock.lock();
        try {

            tickets.remove(0);
            logger.info("{}", ticket);

        } finally {
            lock.unlock();
        }
    }

    public boolean isFull() {
        return tickets.size() >= maxTicketCapacity;
    }

    public boolean isEmpty() {
        return tickets.isEmpty();
    }
    public boolean isLimitReached(){
        return ticketCount==totalTickets;
    }
}

