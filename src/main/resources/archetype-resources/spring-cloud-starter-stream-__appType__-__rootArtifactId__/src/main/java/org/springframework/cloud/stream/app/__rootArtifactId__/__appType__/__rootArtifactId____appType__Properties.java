package org.springframework.cloud.stream.app.#foreach( $word in $rootArtifactId.split('-') )$word.#end${appType};

import org.springframework.boot.context.properties.ConfigurationProperties;
#macro( ccase $str )#*
*#$str.substring(0, 1).toUpperCase()$str.substring(1).toLowerCase()#*
*##end
#set( $appNamePrefix = "#ccase( $rootArtifactId )" )
#set( $appTypePrefix = "#ccase( $appType )" )

@ConfigurationProperties("${rootArtifactId}")
public class ${appNamePrefix}${appTypePrefix}Properties {

}