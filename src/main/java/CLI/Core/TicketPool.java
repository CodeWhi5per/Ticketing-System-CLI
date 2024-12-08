package CLI.Core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import java.util.concurrent.locks.Lock;

public class TicketPool {

    private static final Logger logger = LogManager.getLogger(TicketPool.class);

    private final List<String> tickets = Collections.synchronizedList(new ArrayList<>());
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

            if (ticketCount >= totalTickets) {
                return;
            }

            tickets.add(ticket);
            Thread.sleep(1000);
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
        lock.lock();
        try {
            return tickets.size() >= maxTicketCapacity;
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        lock.lock();
        try {
            return tickets.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    public boolean isLimitReached() {
        lock.lock();
        try {
            return ticketCount >= totalTickets;
        } finally {
            lock.unlock();
        }
    }
}

