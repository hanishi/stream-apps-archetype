package org.springframework.cloud.stream.app.#foreach( $word in $rootArtifactId.split('-') )$word.#end${appType};

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

#macro( ccase $str )#*
*#$str.substring(0, 1).toUpperCase()$str.substring(1).toLowerCase()#*
*##end
#set( $appNamePrefix = "#ccase( $rootArtifactId )" )
#set( $appTypePrefix = "#ccase( $appType )" )
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext
public abstract class ${appNamePrefix}${appTypePrefix}Tests {

    public static class Default${appNamePrefix}${appTypePrefix}Tests extends ${appNamePrefix}${appTypePrefix}Tests {

        @Test
        public void test() throws Exception {

        }

    }

    @SpringBootApplication
    public static class Default${appNamePrefix}${appTypePrefix}Application {

    }
}
