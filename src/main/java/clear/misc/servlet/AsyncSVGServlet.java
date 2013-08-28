package clear.misc.servlet;

import clear.misc.servlet.async.AppAsyncListener;
import clear.misc.servlet.async.AsyncSVGWorker;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * TODO
 *
 * @author Viktor Gamov (viktor.gamov@faratasystems.com)
 * @since 8/26/13
 */
@WebServlet(urlPatterns = "/asyncSvg", asyncSupported = true)
public class AsyncSVGServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        AsyncContext asyncContext = request.startAsync();
        asyncContext.addListener(new AppAsyncListener());

        ThreadPoolExecutor executor = (ThreadPoolExecutor) request.getServletContext().getAttribute("executor");
        executor.execute(new AsyncSVGWorker(asyncContext));
    }
}
