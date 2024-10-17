package cl.villegas.mybatis.util;

import cl.villegas.mybatis.constants.Constants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HttpHeaderUtil {
    public static HttpHeaders getHttpHeadersByFileType(String name, String type) {
        HttpHeaders httpHeaders = new HttpHeaders();
        switch (type.toUpperCase()) {
            case Constants.FileType.XLSX:
                httpHeaders.setContentType(MediaType.parseMediaType(Constants.ContentType.APPLICATION_EXCEL));
                httpHeaders.add(Constants.HttpHeaders.CONTENT_DISPOSITION_PROPERTY, Constants.HttpHeaders.INLINE_FILENAME_PROPERTY + name + Constants.FileExtension.XLSX);
                break;
            case Constants.FileType.PDF:
                httpHeaders.setContentType(MediaType.parseMediaType(Constants.ContentType.APPLICATION_PDF));
                httpHeaders.add(Constants.HttpHeaders.CONTENT_DISPOSITION_PROPERTY, Constants.HttpHeaders.INLINE_FILENAME_PROPERTY + name + Constants.FileExtension.PDF);
                break;
            case Constants.FileType.CSV:
                httpHeaders.setContentType(MediaType.parseMediaType(Constants.ContentType.TEXT_CSV));
                httpHeaders.add(Constants.HttpHeaders.CONTENT_DISPOSITION_PROPERTY, Constants.HttpHeaders.INLINE_FILENAME_PROPERTY + name + Constants.FileExtension.CSV);
                break;
            case Constants.FileType.HTML:
                httpHeaders.setContentType(MediaType.parseMediaType(Constants.ContentType.TEXT_HTML));
                httpHeaders.add(Constants.HttpHeaders.CONTENT_DISPOSITION_PROPERTY, Constants.HttpHeaders.INLINE_FILENAME_PROPERTY + name + Constants.FileExtension.HTML);
                break;
        }
        return httpHeaders;
    }
}