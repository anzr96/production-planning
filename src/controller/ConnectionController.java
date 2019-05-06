package controller;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/document/connection")
@Scope(value = "SESSION")
public class ConnectionController {
    private static boolean connect;
    private static Logger logger = Logger.getLogger(ConnectionController.class.getName());

    public static boolean isConnect() {
        return connect;
    }

    public static void setConnect(boolean connect) {
        ConnectionController.connect = connect;
        logger.info("coonect : " + connect);
    }
}
