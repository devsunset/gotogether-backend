package com.gotogether.system.filter;

import com.nhncorp.lucy.security.xss.XssFilter;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
public class RequestWrapper extends HttpServletRequestWrapper {
    private byte[] b;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        XssFilter filter = XssFilter.getInstance("lucy-xss-sax.xml",true);
        b = new String(filter.doFilter(getBody(request))).getBytes();
    }

    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bis = new ByteArrayInputStream(b);

        return new ServletInputStreamImpl(bis);
    }

    class ServletInputStreamImpl extends ServletInputStream {
        private InputStream is;

        public ServletInputStreamImpl(InputStream bis) {
            is = bis;
        }

        public int read() throws IOException {
            return is.read();
        }

        public int read(byte[] b) throws IOException {
            return is.read(b);
        }


        @Override
        public boolean isFinished() {
            try {
                return is.available() == 0;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            throw new RuntimeException("Not implemented");
        }
    }


    public static String getBody(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }
}
