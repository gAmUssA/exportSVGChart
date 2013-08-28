package clear.misc.servlet.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * TODO
 *
 * @author Viktor Gamov (viktor.gamov@faratasystems.com)
 * @since 8/26/13
 */
@WebListener
public class AppAsyncListener implements AsyncListener {
    private static Logger logger = LoggerFactory.getLogger(AsyncListener.class);

    @Override
    public void onComplete(AsyncEvent event) throws IOException {
        logger.debug("async onComplete");
    }

    @Override
    public void onTimeout(AsyncEvent event) throws IOException {
        logger.debug("async onTimeout");
        ServletResponse response = event.getAsyncContext().getResponse();
        PrintWriter out = response.getWriter();
        out.write("Timeout Error in Processing");
    }

    @Override
    public void onError(AsyncEvent event) throws IOException {
        logger.debug("async onError");
    }

    @Override
    public void onStartAsync(AsyncEvent event) throws IOException {
        logger.debug("async onStartAsync");
    }
}
