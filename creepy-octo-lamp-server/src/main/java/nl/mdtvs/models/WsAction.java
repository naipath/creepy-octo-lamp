package nl.mdtvs.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WsAction {
    private String actionName;
    private String actionMessage;
}