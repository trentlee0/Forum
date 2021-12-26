package forum.resolver;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class MyCommonsMultipartResolver extends CommonsMultipartResolver {
    public static final long MAX_FILE_SIZE = 52428800;

    public MyCommonsMultipartResolver() {
        super();
        setMaxUploadSize(MAX_FILE_SIZE);
    }
}
