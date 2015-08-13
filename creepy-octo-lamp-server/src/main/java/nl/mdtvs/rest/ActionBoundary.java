package nl.mdtvs.rest;

import nl.mdtvs.models.Message;
import nl.mdtvs.models.WsDevice;
import nl.mdtvs.util.ConvertObject;
import nl.mdtvs.websocket.SessionHandler;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Map;
import javax.ws.rs.core.MediaType;

@Path("/action")
public class ActionBoundary {

    @Inject
    private SessionHandler sh;

    @GET
    public String getActionMessage() throws JAXBException, IOException {
        sh.sendAction(new Message("dos", "https://www.google.nl"));
        return "Message send";
    }

    @GET
    @Path("terminal")
    @Produces(MediaType.APPLICATION_JSON)
    public String sendTerminalMessage() throws JAXBException, IOException {
        sh.sendAction(new Message("terminal", "ls -ap"));
        return "TerminalMessage send";
    }

    @GET
    @Path("terminalresponse")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTerminalResponse(@QueryParam("session") String sessionId) throws JAXBException, IOException {
        return sh.getDevices().entrySet().stream()
                .map(Map.Entry::getValue).map(WsDevice::getTerminalResponse)
                .reduce((s, s2) -> s + s2).get();
    }

    @GET
    @Path("/sessions")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSessionsMessage() throws IOException {
        return ConvertObject.devicesToJsonString(sh.getDevices());
    }
}
