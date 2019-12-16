package hr254.repositories;

import org.jboss.logging.Logger;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class LoginAttemptsLogger {
    private static Logger logger = Logger.getLogger(LoginAttemptsLogger.class);


    @EventListener
    public void auditEventHappened(
            AuditApplicationEvent auditApplicationEvent) {
        AuditEvent auditEvent = auditApplicationEvent.getAuditEvent();

        logger.info("Principal " + auditEvent.getPrincipal()
                + " - " + auditEvent.getType());

        WebAuthenticationDetails details
                = (WebAuthenticationDetails) auditEvent.getData().get("details");

        logger.info("  Remote IP address: "
                + details.getRemoteAddress());
        logger.info("  Session Id: " + details.getSessionId());
        logger.info("  Request URL: "
                + auditEvent.getData().get("requestUrl"));
    }
}