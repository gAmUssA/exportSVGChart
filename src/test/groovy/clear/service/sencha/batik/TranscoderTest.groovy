package clear.service.sencha.batik

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import org.apache.batik.transcoder.Transcoder
import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * TODO
 *
 * @since 8/26/13
 * @author Viktor Gamov (viktor.gamov@faratasystems.com)
 */
public class TranscoderTest {

    File file

    @Before
    public void setup() {
        URL url = new TranscoderTest().getClass().getResource('/Arctic_big.svg');
        file = new File(url.getFile());
    }

    @Test
    public void batikTranscoder() {
        Transcoder transcoder = new PNGTranscoder();
        TranscoderInput transcoderInput = new TranscoderInput(new StringReader(file.text))
        FileOutputStream fos = new FileOutputStream('Arctic_big.png');
        TranscoderOutput output = new TranscoderOutput(fos);
        transcoder.transcode(transcoderInput, output);
        Assert.assertEquals("just 1==1", 1, 1)
    }

    @Test
    public void submitLargeFile() {
        def http = new HTTPBuilder('http://localhost:8080/svg4sencha/')
        // auth omitted...
        def postBody = [width: '589', height: '246', type: 'image/jpeg', svg: file.text];
        http.post(path: 'asyncSvg', body: postBody, requestContentType: ContentType.URLENC) { resp ->
            println resp
        }
    }
}
