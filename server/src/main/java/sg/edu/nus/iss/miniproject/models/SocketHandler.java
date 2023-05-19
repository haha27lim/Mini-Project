package sg.edu.nus.iss.miniproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocketHandler implements Comparable<SocketHandler> {

    private String user;
    private String token;
    private WebSocketSession session;

    @Override
    public int compareTo(SocketHandler socketHandler) {
        if ((this.hashCode() == socketHandler.hashCode()) && this.equals(socketHandler)) {
            return 0;
        }
        return 1;
    }
}