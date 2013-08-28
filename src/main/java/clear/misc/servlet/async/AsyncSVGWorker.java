package clear.misc.servlet.async;

import clear.misc.servlet.SVGTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO
 *
 * @author Viktor Gamov (viktor.gamov@faratasystems.com)
 * @since 8/26/13
 */
public class AsyncSVGWorker implements Runnable {

    private static Logger log = LoggerFactory.getLogger(AsyncSVGWorker.class);

    AsyncContext asyncContext;

    public AsyncSVGWorker() {
    }


    public AsyncSVGWorker(AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
    }

    @Override
    public void run() {
        log.debug("Is async supported {}", asyncContext.getRequest().isAsyncSupported());

        HttpServletRequest request = (HttpServletRequest) asyncContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
        try {
            new SVGTransformer().transform(request, response);
        } catch (Exception e1) {
            log.error("Error", e1);
        }
        asyncContext.complete();
    }
}

