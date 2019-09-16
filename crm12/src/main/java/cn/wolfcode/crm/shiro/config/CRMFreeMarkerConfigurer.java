package cn.wolfcode.crm.shiro.config;

import com.jagregory.shiro.freemarker.ShiroTags;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

//注册shiro中提供的标签,直接在freemark中使用
public class CRMFreeMarkerConfigurer extends FreeMarkerConfigurer {
    @Override
    public void afterPropertiesSet() throws IOException, TemplateException {
        super.afterPropertiesSet();

        Configuration configuration = super.getConfiguration();
        configuration.setSharedVariable("shiro",new ShiroTags());
    }
}
