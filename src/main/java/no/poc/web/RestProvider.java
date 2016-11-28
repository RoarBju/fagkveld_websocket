package no.poc.web;

import no.poc.domain.Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ScheduledFuture;

@RestController
public class RestProvider {

    @Autowired
    private TaskScheduler scheduler;
    @Autowired
    private SimpMessagingTemplate websocketTemplate;

    private ScheduledFuture<?> scheduledFuture;

    @RequestMapping(path = "/start", method = RequestMethod.GET)
    public int start() {
        //TODO
        if (scheduledFuture == null) {
            scheduledFuture = scheduler.scheduleAtFixedRate(() -> {
                websocketTemplate.convertAndSend("/topic/start", new Clock());

            }, 1000);
        }
        return 1;
    }

    @RequestMapping(path = "/populate", method = RequestMethod.GET, produces = "application/json")
    public String cont() {
        //TODO
        return "1231";
    }

    @RequestMapping(path = "/stop", method = RequestMethod.GET)
    public int stop() {
        //TODO
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            scheduledFuture = null;
            websocketTemplate.convertAndSend("/topic/stop", 1);
        }
        return 1;
    }
}
