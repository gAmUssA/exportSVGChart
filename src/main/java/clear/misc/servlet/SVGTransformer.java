package clear.misc.servlet;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.StringReader;
import java.util.UUID;

/**
 * TODO
 *
 * @author Viktor Gamov (viktor.gamov@faratasystems.com)
 * @since 8/26/13
 */
public class SVGTransformer {

    private static Logger logger = LoggerFactory.getLogger(SVGTransformer.class);

    public void transform(HttpServletRequest request, HttpServletResponse response) {
        long startTime = System.currentTimeMillis();

        String type = request.getParameter("type");
        String svgXml = request.getParameter("svg");
        // TODO
        //String width = request.getParameter("width");
        //String height = request.getParameter("height");
        Transcoder transcoder = null;

        try {
            ServletOutputStream out = response.getOutputStream();
            if (type.equals("image/png")) {
                transcoder = new PNGTranscoder();
            } else if (type.equals("image/jpeg")) {
                transcoder = new JPEGTranscoder();
                transcoder.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.8));
            }

            String fileName = String.format("chart-%s.%s", UUID.randomUUID(), type.split("/")[1]);

            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            response.setHeader("Content-type", type);

            TranscoderInput input = new TranscoderInput(new StringReader(svgXml));
            TranscoderOutput output = new TranscoderOutput(out);
            transcoder.transcode(input, output);

            out.flush();
            out.close();
            logger.debug("Converted SVG in {} ms", System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            logger.error("Error", e);
        }
    }
}
