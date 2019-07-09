package org.springframework.cloud.stream.app.#foreach( $word in $rootArtifactId.split('-') )$word.#end${appType};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.#if( $appType == 'source')Source#elseif($appType == 'processor')Processor#elseif($appType == 'sink')Sink#end;

#macro( ccase $str )
#foreach( $word in $str.split('-') )$word.substring(0, 1).toUpperCase()$word.substring(1).toLowerCase()#end
#end
#set( $appNamePrefix = "#ccase( $rootArtifactId )" )
#set( $appTypePrefix = "#ccase( $appType )" )
@EnableBinding(${appTypePrefix}.class)
@EnableConfigurationProperties(${appNamePrefix}${appTypePrefix}Properties.class)
public class ${appNamePrefix}${appTypePrefix}Configuration {
    @Autowired
    private ${appNamePrefix}${appTypePrefix}Properties properties;
}
