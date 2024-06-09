package org.delivery.storeadmin.domain.sse.connection.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.delivery.storeadmin.domain.sse.connection.ifs.ConnectionPoolIfs;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Getter
@ToString
@EqualsAndHashCode
@Slf4j(topic="user session connection")
public class UserSseConnection {
    private final String uniqueKey;
    private final SseEmitter sseEmitter;
    private final ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs;

    private final ObjectMapper objectMapper;

    private UserSseConnection(
            String uniqueKey,
            ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs,
            ObjectMapper objectMapper
    ){
        this.uniqueKey = uniqueKey;
        //sse 초기화
        this.sseEmitter = new SseEmitter(60*1000L);

        //call back 초기화
        this.connectionPoolIfs = connectionPoolIfs;
        // objectMapper 초기화
        this.objectMapper = objectMapper;

        //on completion
        this.sseEmitter.onCompletion(()->{
            this.connectionPoolIfs.onCompletionCallback(this);
        });

        //on timeout
        this.sseEmitter.onTimeout(()->{
            this.sseEmitter.complete();
        });

        // opopen 메시지
        sendMessage("onopen", "connect");

    }

    public static UserSseConnection connect(
            String uniqueKey,
            ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs,
            ObjectMapper objectMapper){
        return new UserSseConnection(uniqueKey,connectionPoolIfs,objectMapper);
    }
        public void sendMessage(String eventName, Object data){
            try {
                var json = this.objectMapper.writeValueAsString(data);
                var event = SseEmitter.event()
                        .name(eventName)
                        .data(json)
                        ;
                this.sseEmitter.send(event);
            } catch (IOException e) {
                this.sseEmitter.completeWithError(e);
            }
        }

    public void sendMessage(Object data){
        log.info("send message data : {}",data);

        try {
            var json = this.objectMapper.writeValueAsString(data);

            log.info("send message json : {}",json);

            var event = SseEmitter.event()
                    .data(json)
                    ;
            this.sseEmitter.send(event);
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }

}
