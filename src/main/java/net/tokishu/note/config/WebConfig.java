package net.tokishu.note.config;

import lombok.RequiredArgsConstructor;
import net.tokishu.note.resolver.CurrentUserArgumentResolver;
import net.tokishu.note.resolver.NullableCurrentUserArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final CurrentUserArgumentResolver currentUserArgumentResolver;
    private final NullableCurrentUserArgumentResolver nullableCurrentUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserArgumentResolver);
        resolvers.add(nullableCurrentUserArgumentResolver);
    }
}